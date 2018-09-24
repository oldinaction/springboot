package cn.aezo.springboot.webservice.cxf.client.cxf;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.8
 * 2018-08-15T20:59:27.621+08:00
 * Generated source version: 3.1.8
 * 
 */
@WebService(targetNamespace = "http://service.cxf.webservice.springboot.aezo.cn/", name = "UserService")
@XmlSeeAlso({ObjectFactory.class})
public interface UserService {

    @WebMethod
    @RequestWrapper(localName = "getName", targetNamespace = "http://service.cxf.webservice.springboot.aezo.cn/", className = "cn.aezo.springboot.webservice.cxf.client.cxf.GetName")
    @ResponseWrapper(localName = "getNameResponse", targetNamespace = "http://service.cxf.webservice.springboot.aezo.cn/", className = "cn.aezo.springboot.webservice.cxf.client.cxf.GetNameResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.lang.String getName(
        @WebParam(name = "userId", targetNamespace = "")
        java.lang.String userId
    );

    @WebMethod
    @RequestWrapper(localName = "getAllUser", targetNamespace = "http://service.cxf.webservice.springboot.aezo.cn/", className = "cn.aezo.springboot.webservice.cxf.client.cxf.GetAllUser")
    @ResponseWrapper(localName = "getAllUserResponse", targetNamespace = "http://service.cxf.webservice.springboot.aezo.cn/", className = "cn.aezo.springboot.webservice.cxf.client.cxf.GetAllUserResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.util.List<cn.aezo.springboot.webservice.cxf.client.cxf.User> getAllUser();

    @WebMethod
    @RequestWrapper(localName = "getUser", targetNamespace = "http://service.cxf.webservice.springboot.aezo.cn/", className = "cn.aezo.springboot.webservice.cxf.client.cxf.GetUser")
    @ResponseWrapper(localName = "getUserResponse", targetNamespace = "http://service.cxf.webservice.springboot.aezo.cn/", className = "cn.aezo.springboot.webservice.cxf.client.cxf.GetUserResponse")
    @WebResult(name = "return", targetNamespace = "")
    public cn.aezo.springboot.webservice.cxf.client.cxf.User getUser(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );
}