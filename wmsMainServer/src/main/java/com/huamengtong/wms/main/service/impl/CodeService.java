package com.huamengtong.wms.main.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huamengtong.wms.core.redis.RedisTemplate;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsCodeDetailDTO;
import com.huamengtong.wms.dto.TWmsCodeHeaderDTO;
import com.huamengtong.wms.entity.main.TWmsCodeDetailEntity;
import com.huamengtong.wms.entity.main.TWmsCodeHeaderEntity;
import com.huamengtong.wms.main.mapper.CodeDetailMapper;
import com.huamengtong.wms.main.mapper.CodeHeaderMapper;
import com.huamengtong.wms.main.service.ICodeService;
import com.huamengtong.wms.utils.BeanUtils;
import com.huamengtong.wms.vo.CodeDetailVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class CodeService implements ICodeService {

     @Autowired
     CodeHeaderMapper codeHeaderMapper;

     @Autowired
     CodeDetailMapper codeDetailMapper;

    @Autowired
    RedisTemplate redisTemplate;

    public static final String CODE_HEADER_KEY = "codeHeaderKey";

    @Override
    public PageResponse<List<TWmsCodeHeaderEntity>> getCodeHeaderLists(TWmsCodeHeaderDTO codeHeaderDTO) {
        TWmsCodeHeaderEntity codeHeaderEntity = BeanUtils.copyBeanPropertyUtils(codeHeaderDTO,TWmsCodeHeaderEntity.class);
        List<TWmsCodeHeaderEntity> codeHeaderEntities = codeHeaderMapper.queryCodeHeaderPages(codeHeaderEntity);
        Integer totalSize = codeHeaderMapper.queryCodeHeaderPageCount(codeHeaderEntity);
        PageResponse<List<TWmsCodeHeaderEntity>> response = new PageResponse();
        response.setTotal(totalSize);
        response.setRows(codeHeaderEntities);
        return  response;
    }

    @Override
    public PageResponse<List<TWmsCodeDetailEntity>> getCodeDetailLists(Map map) {
        List<TWmsCodeDetailEntity> codeDetailEntities = codeDetailMapper.queryCodeDetailPages(map);
        Integer totalSize = codeDetailMapper.queryCodeDetailPageCount(map);
        PageResponse<List<TWmsCodeDetailEntity>> response = new PageResponse();
        response.setTotal(totalSize);
        response.setRows(codeDetailEntities);
        return  response;
    }

    @Override
    public MessageResult createCodeHeader(TWmsCodeHeaderDTO codeHeaderDTO) {
        TWmsCodeHeaderEntity codeHeaderEntity = BeanUtils.copyBeanPropertyUtils(codeHeaderDTO,TWmsCodeHeaderEntity.class);
        codeHeaderMapper.insertCodeHeader(codeHeaderEntity);
        redisTemplate.lpush(CODE_HEADER_KEY,codeHeaderEntity.getListName());
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyCodeHeader(TWmsCodeHeaderDTO codeHeaderDTO) {
        TWmsCodeHeaderEntity codeHeaderEntity = BeanUtils.copyBeanPropertyUtils(codeHeaderDTO,TWmsCodeHeaderEntity.class);
        codeHeaderMapper.updateCodeHeader(codeHeaderEntity);
        redisTemplate.lpush(CODE_HEADER_KEY,codeHeaderEntity.getListName());
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult removeCodeHeader(Long id) {
        codeHeaderMapper.deleteCodeHeader(id);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult removeCodeDetail(Long detailId) {
        codeDetailMapper.deleteByPrimaryKey(detailId);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult createCodeDetail(TWmsCodeDetailDTO codeDetailDTO) {
        TWmsCodeDetailEntity codeDetailEntity = BeanUtils.copyBeanPropertyUtils(codeDetailDTO,TWmsCodeDetailEntity.class);
        codeDetailMapper.insertCodeDetail(codeDetailEntity);
        TWmsCodeHeaderEntity codeHeaderEntity = codeHeaderMapper.selectByPrimaryKey(codeDetailDTO.getCodeId());
        redisTemplate.hset(codeHeaderEntity.getListName(), codeDetailEntity.getCodeName(), JSON.toJSONString(BeanUtils.copyBeanPropertyUtils(codeDetailEntity, CodeDetailVO.class)));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyCodeDetail(TWmsCodeDetailDTO codeDetailDTO) {
        TWmsCodeDetailEntity codeDetailEntity = BeanUtils.copyBeanPropertyUtils(codeDetailDTO,TWmsCodeDetailEntity.class);
        codeDetailMapper.updateCodeDetail(codeDetailEntity);
        TWmsCodeHeaderEntity codeHeaderEntity = codeHeaderMapper.selectByPrimaryKey(codeDetailDTO.getCodeId());
        redisTemplate.hset(codeHeaderEntity.getListName(), codeDetailEntity.getCodeName(), JSON.toJSONString(BeanUtils.copyBeanPropertyUtils(codeDetailEntity, CodeDetailVO.class)));
        return MessageResult.getSucMessage();
    }

    /***
     * 从redis获取数据字典信息，如果不存在则查询数据库
     * @param searchMap
     * @return
     */
    @Override
    public Map getAllCodeDatas(Map searchMap) {
        List<String> listNames = redisTemplate.lrange(CODE_HEADER_KEY);
        Map<String, List<CodeDetailVO>> codeMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(listNames)) {
            listNames.forEach(listName ->{
                Map detailMap = redisTemplate.hgetAll(listName);
                List<CodeDetailVO>  codeDetailVOList= new ArrayList();
                Iterator it = detailMap.keySet().iterator();
                while (it.hasNext()){
                    String key = it.next().toString();
                    CodeDetailVO codeDetailVO = new CodeDetailVO();
                    JSONObject object = JSON.parseObject(detailMap.get(key).toString());
                    codeDetailVO = JSON.toJavaObject(object,CodeDetailVO.class);
//                    HashMap<String,Object> map = (HashMap<String, Object>) Arrays.asList(detailMap.get(key).toString().split(","))
//                            .stream().map(s -> s.split(":")).collect(Collectors.toMap(e -> e[0], e -> e[1]));
//                    BeanUtils.transMapToBean(map,codeDetailVO);
                    codeDetailVOList.add(codeDetailVO);
                }
                codeMap.put(listName,codeDetailVOList);
            });
        }
        if(MapUtils.isNotEmpty(codeMap) && codeMap.size() != 0){
            return codeMap;
        }
        List<TWmsCodeHeaderEntity> codeHeaderEntityList = codeHeaderMapper.selectAllCodeHeaders(searchMap);
        if (CollectionUtils.isNotEmpty(codeHeaderEntityList)) {
                codeHeaderEntityList.forEach(codeHeaderEntity -> {
                    redisTemplate.lpush(CODE_HEADER_KEY,codeHeaderEntity.getListName());
                    List<TWmsCodeDetailEntity> codeDetailEntityList = codeDetailMapper.selectAllCodeDetails(codeHeaderEntity.getId());
                    List<CodeDetailVO> codeDetailVOList = codeDetailEntityList.stream()
                            .filter(codeDetailEntity -> codeDetailEntity != null )
                            .collect(() -> new ArrayList<CodeDetailVO>(),
                                    (list, item) -> list.add(BeanUtils.copyBeanPropertyUtils(item, CodeDetailVO.class)),
                                    (list1, list2) -> list1.addAll(list2)
                            );
                    codeDetailEntityList.forEach(x -> {
                        redisTemplate.hset(codeHeaderEntity.getListName(), x.getCodeName(), JSON.toJSONString(BeanUtils.copyBeanPropertyUtils(x, CodeDetailVO.class)));
                    });
                    codeMap.put(codeHeaderEntity.getListName(), codeDetailVOList);
                });
        }
        return codeMap;
    }

    @Override
    public MessageResult flushAllRedis() {
        List<TWmsCodeHeaderEntity> codeHeaderEntityList = codeHeaderMapper.selectAllCodeHeaders(null);
        if (CollectionUtils.isNotEmpty(codeHeaderEntityList)) {
            codeHeaderEntityList.forEach(codeHeaderEntity -> {
                redisTemplate.lpush(CODE_HEADER_KEY,codeHeaderEntity.getListName());
                List<TWmsCodeDetailEntity> codeDetailEntityList = codeDetailMapper.selectAllCodeDetails(codeHeaderEntity.getId());
                codeDetailEntityList.forEach(x -> {
                    redisTemplate.hset(codeHeaderEntity.getListName(), x.getCodeName(), JSON.toJSONString(BeanUtils.copyBeanPropertyUtils(x, CodeDetailVO.class)));
                });
            });
        }
        return MessageResult.getSucMessage();
    }

    @Override
    public List getDetailByHeaderName(String headerName) {
        TWmsCodeHeaderEntity codeHeaderEntity = codeHeaderMapper.selectByListName(headerName);
        return codeDetailMapper.selectAllCodeDetails(codeHeaderEntity.getId());
    }

    @Override
    public String format(String en, String listName){
        Map map = redisTemplate.hgetAll(listName);
        if(MapUtils.isEmpty(map)){
            return traverse(en,map);
        }
        List<TWmsCodeDetailEntity> codeValueList = getDetailByHeaderName(listName);
        if(CollectionUtils.isNotEmpty(codeValueList)){
            for (TWmsCodeDetailEntity x:codeValueList) {
                if(x.getCodeValue().equals(en)){
                    return x.getCodeName();
                }
            }
        }
        return en;
    }

    @Override
    public List<Map<String, String>> getCodeList(List<String> listNames) {
        if(CollectionUtils.isEmpty(listNames)){
            return null;
        }
        List<Map<String,String>> result = new ArrayList<>();
        List<CodeDetailVO> codeDetailVOList=codeDetailMapper.getParseCodeList(listNames);
        for (CodeDetailVO codeDetailVO:codeDetailVOList){
            Map<String,String> map = new HashMap<>();
            map.put("listName",codeDetailVO.getListName());
            map.put("codeValue",codeDetailVO.getCodeValue());
            map.put("codeName",codeDetailVO.getCodeName());
            result.add(map);
        }
        return result;
    }

    private String traverse(String en,Map map){
        if (map != null){
            for (Object c : map.values()) {
                CodeDetailVO codeDetailVO = JSONObject.toJavaObject(JSON.parseObject(c.toString()),CodeDetailVO.class);
                if(codeDetailVO.getCodeValue().equals(en))
                    return codeDetailVO.getCodeName();
            }
        }
        return null;
    }

}
