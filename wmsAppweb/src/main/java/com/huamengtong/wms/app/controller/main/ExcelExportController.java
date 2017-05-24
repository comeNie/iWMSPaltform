package com.huamengtong.wms.app.controller.main;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.huamengtong.wms.app.utils.excel.factory.ParseFactory;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.parse.BasicParse;
import com.huamengtong.wms.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.poi.xssf.usermodel.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/excel")
public class ExcelExportController extends BaseController {

    private Pattern timePattern = Pattern.compile("[0-9]{4}[\\/][0-9]{1,2}[\\/][0-9]{1,2}");

    @Autowired
    ParseFactory parseFactory;

    /**
     * 导出所有数据excel(text/plain)
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public void  excelExport(@RequestBody Map paramMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int page = 1; //导出所有,page默认从第一页开始
        int pageSize = MapUtils.getIntValue(paramMap, "total");
        String fileName = new String(getFileName(MapUtils.getString(paramMap, "fileName")).getBytes("UTF-8"), "ISO8859-1");
        String url = MapUtils.getString(paramMap, "url");
        String cols = MapUtils.getString(paramMap, "cols");
        paramMap.put("cols", JSONUtils.parse(cols));

        //筛选条件
        Map filterMap = new HashMap();
        String filter = MapUtils.getString(paramMap, "filter");
        if (StringUtils.isNotBlank(filter) && !filter.equals("null")) {
            filterMap = (Map) JSONUtils.parse(filter);
        }
        //查询结果集
        List<Map<String, Object>> dataLists = getGridData(page, pageSize, url, filterMap);
        if (CollectionUtils.isNotEmpty(dataLists)) {
            byte[] bytes = this.generateExcel(dataLists,paramMap);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentLength(bytes.length);
            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
    }

    /***
     * 组织数据
     * @param page
     * @param pageSize
     * @param url
     * @param filterMap
     * @return
     */
    public List<Map<String, Object>> getGridData(int page, int pageSize, String url, Map filterMap) {
        StringBuilder newUrl = new StringBuilder();
        if (url.indexOf("?") == -1) {
            newUrl.append(url + "/export?page=" + page + "&pageSize=" + pageSize);
        } else {
            newUrl.append(url + "&page=" + page + "&pageSize=" + pageSize);
        }
        if (MapUtils.isNotEmpty(filterMap)) {
            filterMap.forEach((k,v)->{
                if(StringUtils.isNotBlank(String.valueOf(v))){
                    try {
                        if(k.toString().contains("Time") && timePattern.matcher(v.toString()).find()){
                            newUrl.append("&" + k + "=" + DateUtil.stringToTimestamp(v.toString(),"yyyy/MM/dd HH:mm:ss"));
                        }else{
                            newUrl.append("&" + k + "=" + URLEncoder.encode(String.valueOf(v), "UTF-8"));
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        try {
            //发送http请求
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(newUrl.toString());
            httpGet.addHeader("Cookie", getCookie());
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String resultMap = EntityUtils.toString(entity);
                if (StringUtils.isNotBlank(resultMap)){
                    Map map = (Map) JSON.parseObject(resultMap);
                    Map mapResult = (Map) map.get("result");
                    List<Map<String, Object>> listMap = (List<Map<String, Object>>)mapResult.get("rows");
                    return listMap;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /***
     * 创建excel
     * @param dataLists
     * @param colsMap
     * @return
     * @throws Exception
     */
    private byte[] generateExcel(List<Map<String, Object>> dataLists, Map colsMap) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        List<Map<String, Object>> colsLists = (List) colsMap.get("cols");
        extractionExcelForAs(colsLists,dataLists, workbook);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        out.close();
        return out.toByteArray();
    }

    /***
     * 分解数据
     * @param dataLists 数据集
     * @param colsLists 列名
     * @param workbook 工作表
     */
    private void extractionExcelForAs(List<Map<String, Object>> colsLists,List<Map<String, Object>> dataLists, XSSFWorkbook workbook) {
        if (CollectionUtils.isEmpty(dataLists)) {
            return;
        }
        DbShardVO dbShardVO = super.getDbShardVO();
        List <BasicParse> parseList = parseFactory.build(colsLists,dataLists,dbShardVO,getCurrentWarehouseId());
        //创建一个sheet
        XSSFSheet sheet = workbook.createSheet();

        //标题行样式
        XSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        headerStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setFontHeightInPoints((short) 14);
        headerStyle.setFont(headerFont);

        //内容样式
        XSSFCellStyle contentStyle = workbook.createCellStyle();
        contentStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        contentStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        XSSFFont contentFont = workbook.createFont();
        contentFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        contentFont.setFontHeightInPoints((short) 8);
        headerStyle.setFont(contentFont);

        int excelRow = 0;
        for (int m = 0; m <= dataLists.size(); m++) {
            //第一行第一列数据
            if (excelRow == 0) {
                XSSFRow row = sheet.createRow(excelRow);
                int column = 0;
                for (int i = 0; i < colsLists.size(); i++) {
                    //设置title,第一行
                    Map<String, Object> first = colsLists.get(i);
                    String title = first.get("title") == null ? "" : first.get("title").toString();
                    //是否隐藏列
                    if (isHiddenCol(colsLists.get(i))) {
                        continue;
                    }
                    XSSFCell cell=row.createCell(column);
                    cell.setCellStyle(headerStyle);
                    cell.setCellValue(title);
                    column++;
                }
                excelRow++;
            } else {
                Map<String, Object> otherDataMap = dataLists.get(m-1);
                XSSFRow row = sheet.createRow(excelRow);
                int column = 0;
                for (int i = 0; i < colsLists.size(); i++) {
                    Map fieldFormatMap = colsLists.get(i);
                    String field = MapUtils.getString(fieldFormatMap, "field");
                    String format = MapUtils.getString(fieldFormatMap, "format");
                    //是否隐藏列
                    if (isHiddenCol(fieldFormatMap)) {
                        continue;
                    }
                    Object value = "";
                    if(StringUtils.isNotBlank(format)){
                        Optional<BasicParse> optional =  parseList.stream().filter(x->x.isMatch(format)).findFirst();
                        if(optional.isPresent()){
                            value = optional.get().parseValue(otherDataMap, field, format).toString();
                        }
                        if(value == null || value==""){
                            value = otherDataMap.get(field);
                        }
                    }else{
                        value = otherDataMap.get(field);
                    }
                    XSSFCell cell=row.createCell(column);
                    cell.setCellStyle(contentStyle);
                    cell.setCellValue(String.valueOf(value));
                    column++;
                }
                excelRow++;
            }
        }
    }

    private boolean isHiddenCol(Map map){
        if(map.containsKey("hidden")) {
            return MapUtils.getBoolean(map, "hidden") ? true : false;
        }
        return false;
    }

    private String dateTimeToString() {
        Calendar calendar = Calendar.getInstance();
        DateTime dateTime = new DateTime(calendar);
        return dateTime.toString("yyyyMMddHHmmss");
    }

    private String getFileName(String fileName){
        if(StringUtils.isBlank(fileName)){
            fileName = dateTimeToString() + ".xlsx";
        }
        String fileNames[] = fileName.split("\\.");
        fileName = fileNames[0] + "_" + dateTimeToString() + "." + fileNames[1];
        return fileName;
    }

    /**
     * 获取Cookie数据
     *
     * @return
     */
    private String getCookie() {
        StringBuilder builder = new StringBuilder();
        Cookie[] cookies = getRequest().getCookies();
        for (Cookie item : cookies) {
            if (builder.length() == 0) {
                builder.append(item.getName() + "=" + item.getValue());
            } else {
                builder.append("; " + item.getName() + "=" + item.getValue());
            }
        }
        return builder.toString();
    }
}
