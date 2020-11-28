package com.xh.s3upload.service.impl;

import com.xh.s3upload.dao.SaleOutDao;
import com.xh.s3upload.service.uploadService;
import com.xh.s3upload.to.InterfaceResult;
import com.xh.s3upload.to.ReqParmVo;
import com.xh.s3upload.to.SaleOut;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class uploadImpl implements uploadService {

    @Autowired
    private SaleOutDao saleOutDao;

    @Override
    public  List<InterfaceResult> upload(ReqParmVo reqParmVo) {
        List<InterfaceResult> interfaceResultList=new ArrayList<>();
        switch (reqParmVo.getType()) {
            case 0:
            case 1:
               List<SaleOut>saleOuts= saleOutDao.getReqList(reqParmVo.getBillcodes());
               List<String> succescList=new ArrayList<>();
               interfaceResultList = saleOuts.parallelStream().map(s -> {
                   log.info("上传信息："+s.toString());
                    String resStr = post(1, s);
                    InterfaceResult interfaceResult = new InterfaceResult();
                    try {
                        interfaceResult = readXmlFun(resStr).get(0);
                        if (interfaceResult.getStatus().equals("0")) {
                            succescList.add(interfaceResult.getSalebill_pk());
                        }
                    } catch (Exception e) {
                        interfaceResult = new InterfaceResult();
                        interfaceResult.setSyslog("服务器上传失败");
                        interfaceResult.setStatus("1");
                        interfaceResult.setSalebill_pk(s.getSalebillid());
                    }
                    log.info("接口返回:"+ interfaceResult);
                    return interfaceResult;
                }).collect(Collectors.toList());
                if(succescList!=null&&succescList.size()>0){
               saleOutDao.updateSaleList(succescList);}
                break;
            case 2:
                break;
        }
        return interfaceResultList;

    }


    /**
     * 发送xml数据请求到server端
     *
     *
     * @return null发送失败，否则返回响应内容
     */
    public String post(int type,Object t) {
        //关闭
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");

        //创建httpclient工具对象
        HttpClient client = new HttpClient();
        //创建post请求方法
        PostMethod myPost = new PostMethod("http://59.41.111.229:6081/SPD_DDI/DDIService?wsdl");
        //设置请求超时时间
        client.setConnectionTimeout(300 * 1000);
        String responseString = null;
        try {
            //设置请求头部类型
            myPost.setRequestHeader("Content-Type", "text/xml");
            myPost.setRequestHeader("charset", "utf-8");


            myPost.setRequestBody(getXMLStr(type,t));
            //设置请求体，即xml文本内容，注：这里写了两种方式，一种是直接获取xml内容字符串，一种是读取xml文件以流的形式
//            InputStream body=this.getClass().getResourceAsStream("/"+xmlFileName);
//            myPost.setRequestBody(body);
//            myPost.setRequestEntity(new StringRequestEntity(xmlString,"text/xml","utf-8"));
            int statusCode = client.executeMethod(myPost);
            if (statusCode == HttpStatus.SC_OK) {
                BufferedInputStream bis = new BufferedInputStream(myPost.getResponseBodyAsStream());
                byte[] bytes = new byte[1024];
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int count = 0;
                while ((count = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, count);
                }
                byte[] strByte = bos.toByteArray();
                responseString = new String(strByte, 0, strByte.length, "utf-8");
                bos.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        myPost.releaseConnection();
        return responseString;
    }

    private String getXMLStr(int type,Object t) {
        StringBuilder xmlString = new StringBuilder();
        xmlString.append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ser='http://service.delivery.gzsino.com/'> ");
        xmlString.append(" <soapenv:Header/>");
        xmlString.append(" <soapenv:Body>");
        switch (type){
            case 0:
                xmlString.append(" <ser:sendBoxList>");
                xmlString.append(" <username>gdgjyy</username>");
                xmlString.append(" <password>gdgjyy2020</password>");
                xmlString.append(" <boxLists>");
                xmlString.append(" <hospital_code>0559400</hospital_code>");
                xmlString.append(" <carton_nbr>010004689010000</carton_nbr>");
                xmlString.append("<carton_pk>000042424200000</carton_pk>");
                xmlString.append("<ven_goods>2605300000</ven_goods>");
                xmlString.append("<ven_batch_nbr>2217600000</ven_batch_nbr>");
                xmlString.append(" <address_code>055940004</address_code>");
                xmlString.append("<salebillid>576387000000</salebillid>");
                xmlString.append("<salebillno>101022004295000000</salebillno>");
                xmlString.append("<ven_goodsname>盐酸羟考酮缓释片(奥施康定)</ven_goodsname>");
                xmlString.append(" <ven_spec>500ml</ven_spec>");
                xmlString.append(" <ven_producer>荷兰 ABBOTT LABORATORIES B.V</ven_producer>");
                xmlString.append(" <ven_unit>瓶</ven_unit>");
                xmlString.append("<prddate>2018-11-26 00:00:00.0</prddate>");
                xmlString.append("<enddate>2020-02-25</enddate>");
                xmlString.append("<msunitno>瓶</msunitno>");
                xmlString.append("<packnum>15</packnum>");
                xmlString.append("<units_pakd>150</units_pakd>");
                xmlString.append("</boxLists>");
                xmlString.append("</ser:sendBoxList>");
                break;
            case 1:
                SaleOut t1 = (SaleOut) t;
                xmlString.append("   <ser:sendSaleBillList>");
                xmlString.append("  <username>gdgjyy</username>");
                xmlString.append("  <password>gdgjyy2020</password>");
                xmlString.append("  <saleBillLists>");
                xmlString.append("  <hospital_code>"+t1.getHospital_code()+"</hospital_code>");
                xmlString.append("  <salebillid>"+t1.getSalebillid()+"</salebillid>");
                xmlString.append("  <ven_goods>"+t1.getVen_goods()+"</ven_goods>");
                xmlString.append("  <ven_batch_nbr>"+t1.getVen_batch_nbr()+"</ven_batch_nbr>");
                xmlString.append("   <address_code>"+t1.getAddress_code()+"</address_code>");
                xmlString.append("   <salebillno>"+t1.getSalebillno()+"</salebillno>");
                xmlString.append("   <ven_goodsname>"+t1.getVen_goodsname()+"</ven_goodsname>");
                xmlString.append("   <ven_spec>"+t1.getVen_spec()+"</ven_spec>");
                xmlString.append("   <ven_producer>"+t1.getVen_producer()+"</ven_producer>");
                xmlString.append("   <ven_unit>"+t1.getVen_unit()+"</ven_unit>");
                xmlString.append("   <prddate>"+t1.getPrddate()+"</prddate>");
                xmlString.append("   <enddate>"+t1.getEnddate()+"</enddate>");
                xmlString.append("   <ratifier>"+t1.getRatifier()+"</ratifier>");
                xmlString.append("   <msunitno>"+t1.getMsunitno()+"</msunitno>");
                xmlString.append("   <packnum>"+t1.getPacknum()+"</packnum>");
                xmlString.append("   <inv_type>"+t1.getInv_type()+"</inv_type>");
                xmlString.append("   <billamt>"+t1.getBillamt()+"</billamt>");
                xmlString.append("   <billqty>"+t1.getBillqty()+"</billqty>");
                xmlString.append("    <price>"+t1.getPrice()+"</price>");
                xmlString.append("    <prc>"+t1.getPrc()+"</prc>");
                xmlString.append("    <taxrate>"+t1.getTaxrate()+"</taxrate>");
                xmlString.append("    <tax>"+t1.getTax()+"</tax>");
                xmlString.append("     <amt>"+t1.getAmt()+"</amt>");
                xmlString.append("     <sumvalue>"+t1.getSumvalue()+"</sumvalue>");
                xmlString.append("     <trdprc>"+t1.getTrdprc()+"</trdprc>");
                xmlString.append("    <rtlprc>"+t1.getRtlprc()+"</rtlprc>");
                xmlString.append("    </saleBillLists>");
                xmlString.append("    </ser:sendSaleBillList>");
                break;
            case 3:
                break;

        }

        xmlString.append(" </soapenv:Body>");
        xmlString.append("</soapenv:Envelope>");
        return xmlString.toString();
    }

    public List<InterfaceResult> readXmlFun(String s) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, DocumentException {

        //1.反射，得到类的引用
        
        Class PackListResult = Class.forName("com.xh.s3upload.to.InterfaceResult");
        //通过类的引用，得到类的对象
        Object stuInstance = PackListResult.newInstance();
        //创建一个list 来放多个student的对象
        List<InterfaceResult> results = new ArrayList<InterfaceResult>();

//        //将XML文件读取为一份document对象
        Document document = DocumentHelper.parseText(s);



        List<Element> returnEle = document.selectNodes("//return");
        for (Element result:returnEle){
            List<Element> list = result.selectNodes("child::*");
            for (Element subElementData : list) {
                //得到每个子节点的名字
                String elementName = subElementData.getName();
                //遍历并获得每个子元素的文本内容,如得到name子节点的文本值为Claire
                String stuData = subElementData.getStringValue();
//                System.out.println(elementName +"为" + stuData);


                //通过elemetname得到对应的get set方法，先拼接出方法名，比如 name--setName
                String funName = "set" + (elementName.charAt(0)+"").toUpperCase()+elementName.substring(1);

                //通过方法名反射出方法对象
                Method method1 = PackListResult.getDeclaredMethod(funName, String.class);
                //通过反射调用方法，调用stuInstance对象的method方法，参数为stuData---给各属性赋值
                method1.invoke(stuInstance, stuData);
                //将每个对象添加到list列表中
                InterfaceResult result1 = (InterfaceResult) stuInstance;
                results.add(result1);

            }

        }

        return results;

    }
}
