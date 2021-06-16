package com.xh.s3upload.to;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class InterfaceResult {
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

    public InterfaceResult(String syslog) {
        this.syslog = syslog;
    }
}
