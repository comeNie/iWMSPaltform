package com.huamengtong.wms.main.mapper;


import com.huamengtong.wms.entity.main.TWmsCodeHeaderEntity;

import java.util.List;
import java.util.Map;

public interface CodeHeaderMapper {

    List<TWmsCodeHeaderEntity> queryCodeHeaderPages(TWmsCodeHeaderEntity codeHeaderEntity);

    Integer queryCodeHeaderPageCount(TWmsCodeHeaderEntity codeHeaderEntity);

    Integer insertCodeHeader(TWmsCodeHeaderEntity codeHeaderEntity);

    TWmsCodeHeaderEntity selectByPrimaryKey(Long id);

    Integer updateCodeHeader(TWmsCodeHeaderEntity codeHeaderEntity);

    Integer deleteCodeHeader(Long id);

    List<TWmsCodeHeaderEntity> selectAllCodeHeaders(Map searchMap);

    TWmsCodeHeaderEntity selectByListName(String listname);
}
