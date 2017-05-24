package com.huamengtong.wms.utils;


import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReaderUtil {

    /**
     * 根据文件输入流获得excle，默认读取第一个Sheet1
     * @param stream
     * @return
     * @throws Exception
     */
    public static List getExcelData(InputStream stream) throws Exception {
        return getExcelData(stream,"Sheet1");
    }


    /**
     * 根据文件输入流获得excle，默认表头为第一行
     *
     * @param stream
     * @param sheetName sheet页名
     * @return
     * @throws Exception
     */
    public static List getExcelData(InputStream stream, String sheetName) throws Exception {
        return getExcelData(stream, sheetName, 1);
    }

    /**
     * 根据文件输入流获得excle
     *
     * @param stream
     * @param sheetName  sheet页名
     * @param titleIndex 表头所在行数
     * @return
     * @throws Exception
     */
    public static List getExcelData(InputStream stream, String sheetName, int titleIndex) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(stream);//工作簿
        return getExcelData(workbook, sheetName, titleIndex);
    }

    /**
     * 根据Workbook取对应的sheet页，组合成List<Map>的格式传出去，Map的key是表头，value是内容
     *
     * @param workbook
     * @param sheetName  sheet页名
     * @param titleIndex 表头所在行数
     * @return
     * @throws Exception
     */
    private static List getExcelData(XSSFWorkbook workbook, String sheetName, int titleIndex) throws Exception {
        XSSFSheet sheet =null==sheetName? workbook.getSheetAt(0) : workbook.getSheet(sheetName);//获取Sheet页
        if (null == workbook || sheet == null || sheet.getPhysicalNumberOfRows() == 0 ) {
            return null;
        }

        XSSFRow row = sheet.getRow(0);
        int columnNum = row.getPhysicalNumberOfCells();

        List list = new ArrayList();
        //获取表头
        List mapKeyList = new ArrayList();
        for (int c = 0; c <= columnNum; c++) {
            String columnValue = getCellFormatValue(row.getCell(c));
            mapKeyList.add(columnValue);
        }
        int rowNum = sheet.getLastRowNum();
        for (int r = titleIndex; r <= rowNum; r++) {
            Map map = new HashMap();
            row = sheet.getRow(r);
            int c = 0;
             while ( c < row.getPhysicalNumberOfCells()) {
                String columnValue =getCellFormatValue(row.getCell(c)).trim();
                map.put(mapKeyList.get(c), columnValue);
                 c ++ ;
             }
             list.add(map);
        }
        return list;
    }


    /**
     * 根据HSSFCell类型设置数据
     * @param cell
     * @return
     */
    private static String getCellFormatValue(XSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case XSSFCell.CELL_TYPE_NUMERIC:

                case XSSFCell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式
                        //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                        //cellvalue = cell.getDateCellValue().toLocaleString();

                        //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);

                    }
                    // 如果是纯数字
                    else {
                        // 取得当前Cell的数值
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case XSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }
}

