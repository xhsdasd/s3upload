package com.xh.s3upload.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.s3upload.to.PackOrder;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PackOrderDao extends BaseMapper<PackOrder> {

    List<PackOrder> getReqList(@Param("billcodes") List<String> billcodes);

    void updatePackList(@Param("succescList") List<String> succescList);
}