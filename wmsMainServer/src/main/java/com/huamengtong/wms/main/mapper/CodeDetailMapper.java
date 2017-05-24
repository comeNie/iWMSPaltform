package com.huamengtong.wms.main.mapper;


import com.huamengtong.wms.entity.main.TWmsCodeDetailEntity;
import com.huamengtong.wms.vo.CodeDetailVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CodeDetailMapper {

    List<TWmsCodeDetailEntity> queryCodeDetailPages(Map map);

    Integer queryCodeDetailPageCount(Map map);

    Integer deleteByPrimaryKey(Long detailId);

    Integer insertCodeDetail(TWmsCodeDetailEntity codeDetailEntity);

    Integer updateCodeDetail(TWmsCodeDetailEntity codeDetailEntity);

    List<TWmsCodeDetailEntity> selectAllCodeDetails(@Param("codeId") Long codeId);

    List<CodeDetailVO> getParseCodeList(@Param("listNames") List<String> listNames);
}

