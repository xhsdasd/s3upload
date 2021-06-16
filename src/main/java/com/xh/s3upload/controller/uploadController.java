package com.xh.s3upload.controller;

import com.xh.s3upload.to.InterfaceResult;
import com.xh.s3upload.to.ReqParmVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
public class uploadController {
    @Autowired
    private com.xh.s3upload.service.uploadService uploadService;
    @PostMapping("upload")
    @ResponseBody
    public List<InterfaceResult> preUpload(@RequestBody ReqParmVo reqParmVo){
       try{
        return uploadService.upload(reqParmVo);}
       catch (Exception e){
           return Arrays.asList(new InterfaceResult(e.getMessage()));
       }
    }


    @PostMapping("jj")
    public void jj(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int len = request.getContentLength();
            System.out.println("数据流长度:" +len);
            //获取HTTP请求的输入流
            InputStream is = request.getInputStream();
            //已HTTP请求输入流建立一个BufferedReader对象
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            //读取HTTP请求内容
            String buffer = null;
            StringBuffer sb = new StringBuffer();
            while ((buffer = br.readLine()) != null) {
                //在页面中显示读取到的请求参数
                sb.append(buffer+"\n");
            }
            System.out.println("接收post发送数据:\n"+sb.toString().trim());


        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}

