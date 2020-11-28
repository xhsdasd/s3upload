package com.xh.s3upload.to;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@ToString
public class SaleOut {
    private String hospital_code="0559400";
    private String salebillid;
    private String ven_goods;
    private String ven_batch_nbr;
    private String address_code="055940004";
    private String salebillno;
    private String ven_goodsname;
    private String ven_spec;
    private String ven_producer;
    private String ven_unit;
    private String prddate;
    private String enddate;
    private String ratifier;
    private String msunitno;
    private int packnum;
    private String inv_type;
    private BigDecimal billamt;
    private int billqty;
    private BigDecimal price;
    private BigDecimal prc;
    private BigDecimal taxrate;
    private BigDecimal tax;
    private BigDecimal amt;
    private BigDecimal sumvalue;
    private BigDecimal trdprc;
    private BigDecimal rtlprc;
//    private String mark1;
//    private String mark2;
//    private String mark3;
//    private String mark4;
//    private String mark5;
}
