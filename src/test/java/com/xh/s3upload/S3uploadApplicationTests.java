package com.xh.s3upload;

import com.xh.s3upload.to.PackOrderResult;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class S3uploadApplicationTests {

    @Test
    void contextLoads() throws IllegalAccessException, InvocationTargetException, InstantiationException, DocumentException, NoSuchMethodException, ClassNotFoundException {
        String post = post("http://59.41.111.229:6081/SPD_DDI/DDIService?wsdl");
        readXmlFun(post);
        System.out.println(post);
    }

    /**
     * 发送xml数据请求到server端
     *
     * @param url xml请求数据地址
     * @return null发送失败，否则返回响应内容
     */
    public String post(String url) {
        //关闭
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");

        //创建httpclient工具对象
        HttpClient client = new HttpClient();
        //创建post请求方法
        PostMethod myPost = new PostMethod(url);
        //设置请求超时时间
        client.setConnectionTimeout(300 * 1000);
        String responseString = null;
        try {
            //设置请求头部类型
            myPost.setRequestHeader("Content-Type", "text/xml");
            myPost.setRequestHeader("charset", "utf-8");

            //设置请求体，即xml文本内容，注：这里写了两种方式，一种是直接获取xml内容字符串，一种是读取xml文件以流的形式
            StringBuilder xmlString = new StringBuilder();
            xmlString.append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ser='http://service.delivery.gzsino.com/'> ");
            xmlString.append(" <soapenv:Header/>");
            xmlString.append(" <soapenv:Body>");
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
            xmlString.append(" </soapenv:Body>");
            xmlString.append("</soapenv:Envelope>");
            myPost.setRequestBody(xmlString.toString());

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


    public void readXmlFun(String s) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, DocumentException {


        //1.反射，得到类的引用
        Class PackListResult = Class.forName("com.xh.s3upload.to.PackOrderResult");
        //通过类的引用，得到类的对象
        Object stuInstance = PackListResult.newInstance();
        //创建一个list 来放多个student的对象
        List<PackOrderResult> students = new ArrayList<PackOrderResult>();

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
                System.out.println(elementName +"为" + stuData);


                //通过elemetname得到对应的get set方法，先拼接出方法名，比如 name--setName
                String funName = "set" + (elementName.charAt(0)+"").toUpperCase()+elementName.substring(1);

                //通过方法名反射出方法对象
                Method method1 = PackListResult.getDeclaredMethod(funName, String.class);
                //通过反射调用方法，调用stuInstance对象的method方法，参数为stuData---给各属性赋值
                method1.invoke(stuInstance, stuData);
                //将每个学生对象添加到list列表中
                students.add((PackOrderResult)stuInstance);
            }

        }


         System.out.println(students);

    }

}


