package com.huamengtong.wms.app.utils.excel.factory;

import com.huamengtong.wms.app.utils.excel.analysis.CalcParse;
import com.huamengtong.wms.app.utils.excel.parse.*;
import com.huamengtong.wms.core.formwork.db.splitdb.ShareDbUtil;
import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.main.service.ICargoOwnerService;
import com.huamengtong.wms.main.service.ICodeService;
import com.huamengtong.wms.main.service.IStorageRoomService;
import com.huamengtong.wms.main.service.IWarehouseService;
import com.huamengtong.wms.parse.BasicParse;
import com.huamengtong.wms.sku.service.ISkuService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

@Service
public class ParseFactory {

    Logger _logger = LoggerFactory.getLogger(ParseFactory.class);
    @Autowired
    private ICodeService codeService;

    @Autowired
    private ISkuService skuService;

    @Autowired
    private IWarehouseService warehouseService;

    @Autowired
    private ICargoOwnerService cargoOwnerService;

    @Autowired
    private IStorageRoomService storageRoomService;

    private CalcParse calcParse = new CalcParse();

    BiFunction<Object,Object,String> formatFunc = (k,v)->{
        String formatKey = "";
        if(k.equals("format")){
            formatKey = v.toString();
        }else if(k.equals("field") && v.toString().startsWith("sku")){
            formatKey = v.toString();
        }
        return formatKey;
    };

    public List <BasicParse> build(List<Map<String, Object>>colList, List<Map<String, Object>>dataList, DbShardVO dbShardVO, Long whId){
        _logger.debug("whId:"+ whId );
        List <BasicParse> allParseList = getAllList(dbShardVO);
        List <BasicParse> needParseList = new ArrayList<>();

        for(Map colMap:colList) {
            //根据列定义 增加解析器
            String field = MapUtils.getString(colMap,"field");
            String format = MapUtils.getString(colMap,"format");
            String formatKey = "";

            if(field.startsWith("sku")){
                formatKey = formatFunc.apply("field",field);
            }else{
                if(StringUtils.isNotBlank(format))
                    formatKey = formatFunc.apply("format",format);
            }
            BasicParse parse = null;
            if(StringUtils.isNotBlank(formatKey)){
                String filterKey = formatKey;
                Optional<BasicParse> optional = allParseList.stream().filter(x -> x.isMatch(filterKey)).findFirst();
                if(optional.isPresent()){
                    parse = optional.get();
                    if(parse.isNeedData(formatKey)){
                        for(Map data:dataList){
                            parse.setSourceData(data,field,formatKey);
                        }
                    }else{
                        parse.setSourceData(null,field,formatKey);
                    }
                    if(!needParseList.contains(parse))
                        needParseList.add(parse);
                }
            }
        }
        //加载要转换的数据
        needParseList.forEach(x->{
            x.loadParseData();
        });
        return needParseList;
    }



    protected DbShardVO getSkuDbShareVO(DbShardVO dbShardVO){
        return ShareDbUtil.getNewDbsharedVO(dbShardVO, DbShareField.SKU,dbShardVO.getShardDbId(),dbShardVO.getWarehouseId()+"");
    }


    private List<BasicParse>  getAllList(DbShardVO dbShardVO){
        List<BasicParse> list = new ArrayList<>();
        CodeParse codeParse = new CodeParse();
        codeParse.setCodeService(codeService);
        list.add(codeParse);

        DateParse dateParse = new DateParse();
        list.add(dateParse);

        YesOrNoParse yesOrNoParse = new YesOrNoParse();
        list.add(yesOrNoParse);

        SkuParse skuParse = new SkuParse();
        skuParse.setDbShardVO(getSkuDbShareVO(dbShardVO));
        skuParse.setSkuService(skuService);
        list.add(skuParse);

        CargoOwnerParse cargoOwnerParse = new CargoOwnerParse();
        cargoOwnerParse.setCargoOwnerService(cargoOwnerService);
        list.add(cargoOwnerParse);

        StorageRoomParse storageRoomParse = new StorageRoomParse();
        storageRoomParse.setStorageRoomService(storageRoomService);
        list.add(storageRoomParse);

        WhParse whParse = new WhParse();
        whParse.setWarehouseService(warehouseService);
        list.add(whParse);

        StorageRoomStringParse storageRoomStringParse =  new StorageRoomStringParse();
        storageRoomStringParse.setStorageRoomService(storageRoomService);
        list.add(storageRoomStringParse);

        list.add(calcParse);

        return list;
    }
}
