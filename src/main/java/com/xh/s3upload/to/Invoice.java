package com.xh.s3upload.to;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Invoice {
    private String hospital_code="0559400";
    private String invoiceid;
    private String invoice_type;
    private String invoiceno;
    private String inv_clientname;
    private String invoice_remrak;
    private String invoice_dt;
    private BigDecimal totalsumvalue;
    private int totalsumqty;
    private int impbatch;
    private List<InvoiceDetail> inputInvoiceDetailBean;

}
