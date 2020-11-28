package com.xh.s3upload.controller;

import com.xh.s3upload.to.InterfaceResult;
import com.xh.s3upload.to.ReqParmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class uploadController {
    @Autowired
    private com.xh.s3upload.service.uploadService uploadService;
    @PostMapping("upload")
    @ResponseBody
    public List<InterfaceResult> preUpload(@RequestBody ReqParmVo reqParmVo){
        return uploadService.upload(reqParmVo);
    }

}
