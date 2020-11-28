package com.xh.s3upload.to;

import lombok.Data;

import java.util.List;

@Data
public class ReqParmVo {
    private int type;
    private List<String> billcodes;
}
