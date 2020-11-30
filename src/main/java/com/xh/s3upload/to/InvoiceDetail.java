package com.xh.s3upload.to;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceDetail {
    private String salebillid;
    private String salebillno;
    private String ven_goods;
    private String ven_goodsname;
    private String ven_spec;
    private String ven_producer;
    private String ven_unit;
    private String batch_nbr;
    private String enddate;
    private String prddate;
    private String ratifier;
    private String msunitno;
    private String packnum;
    private int billqty;
    private BigDecimal price;
    private BigDecimal prc;
    private BigDecimal taxrate;
    private BigDecimal tax;
    private BigDecimal amt;
    private BigDecimal sumvalue;
    private BigDecimal trdprc;
    private BigDecimal rtlprc;
    private String tmp_invoive_main_id;
    private String tmp_invoive_detail_rownum;
    private String ven_invoice_detail_id;

}
