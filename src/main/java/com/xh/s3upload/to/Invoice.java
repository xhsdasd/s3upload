package com.xh.s3upload.to;


import lombok.Data;

import java.util.List;

@Data
public class Invoice {
    private String hospital_code;
    private String invoiceid;
    private String invoice_type;
    private String invoiceno;
    private String inv_clientname;
    private String invoice_remrak;
    private String invoice_dt;
    private double totalsumvalue;
    private int totalsumqty;
    private String mark1;
    private String mark2;
    private int mark3;
    private int mark4;
    private String mark5;
    private List<InvoiceDetail> InputInvoiceDetailBean;

}
