package com.xh.s3upload.service;

import com.xh.s3upload.to.InterfaceResult;
import com.xh.s3upload.to.ReqParmVo;

import java.util.List;

public interface uploadService {
    List<InterfaceResult> upload(ReqParmVo reqParmVo);
}
