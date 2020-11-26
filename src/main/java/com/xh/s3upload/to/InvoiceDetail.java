package com.xh.s3upload.to;

import lombok.Data;

@Data
public class InvoiceDetail {
    private long salebillid;
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
    private double price;
    private double prc;
    private double taxrate;
    private double tax;
    private double amt;
    private double sumvalue;
    private double trdprc;
    private long rtlprc;
    private String mark1;
    private String mark2;
    private int mark3;
    private int mark4;
    private String mark5;
    private String grpno;
}
