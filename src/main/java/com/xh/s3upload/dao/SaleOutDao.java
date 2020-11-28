package com.xh.s3upload.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.s3upload.to.SaleOut;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SaleOutDao extends BaseMapper<SaleOut> {

    List<SaleOut> getReqList(@Param("billcodes") List<String> billcodes);

    void updateSaleList(@Param("succescList") List<String> succescList);
}