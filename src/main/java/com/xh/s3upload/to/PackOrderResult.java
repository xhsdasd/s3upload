package com.xh.s3upload.to;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PackOrderResult {
    private String id;
    private String carton_pk;
    private String salebill_pk;
    private String invoice_pk;
    private String invoice_detail_pk;
    private String patch;
    private String status;
    private String syslog;
    private String error;
    private String logtime;

}
