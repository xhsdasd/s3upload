package com.xh.s3upload.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.s3upload.to.Invoice;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InvoiceDao extends BaseMapper<Invoice> {

    List<Invoice> getReqList(@Param("billcodes") List<String> billcodes);

    void updateInvoiceList(@Param("succescList") List<String> succescList);
}