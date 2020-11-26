
package cn.aezo.springboot.webservice.cxf.client.wsimport;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the cn.aezo.springboot.webservice.cxf.client.wsimport package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetAllUser_QNAME = new QName("http://service.cxf.webservice.springboot.aezo.cn/", "getAllUser");
    private final static QName _GetAllUserResponse_QNAME = new QName("http://service.cxf.webservice.springboot.aezo.cn/", "getAllUserResponse");
    private final static QName _GetName_QNAME = new QName("http://service.cxf.webservice.springboot.aezo.cn/", "getName");
    private final static QName _GetUserResponse_QNAME = new QName("http://service.cxf.webservice.springboot.aezo.cn/", "getUserResponse");
    private final static QName _GetNameResponse_QNAME = new QName("http://service.cxf.webservice.springboot.aezo.cn/", "getNameResponse");
    private final static QName _GetUser_QNAME = new QName("http://service.cxf.webservice.springboot.aezo.cn/", "getUser");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.aezo.springboot.webservice.cxf.client.wsimport
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetUserResponse }
     */
    public GetUserResponse createGetUserResponse() {
        return new GetUserResponse();
    }

    /**
     * Create an instance of {@link GetName }
     */
    public GetName createGetName() {
        return new GetName();
    }

    /**
     * Create an instance of {@link GetNameResponse }
     */
    public GetNameResponse createGetNameResponse() {
        return new GetNameResponse();
    }

    /**
     * Create an instance of {@link GetUser }
     */
    public GetUser createGetUser() {
        return new GetUser();
    }

    /**
     * Create an instance of {@link GetAllUser }
     */
    public GetAllUser createGetAllUser() {
        return new GetAllUser();
    }

    /**
     * Create an instance of {@link GetAllUserResponse }
     */
    public GetAllUserResponse createGetAllUserResponse() {
        return new GetAllUserResponse();
    }

    /**
     * Create an instance of {@link User }
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllUser }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.cxf.webservice.springboot.aezo.cn/", name = "getAllUser")
    public JAXBElement<GetAllUser> createGetAllUser(GetAllUser value) {
        return new JAXBElement<GetAllUser>(_GetAllUser_QNAME, GetAllUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllUserResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.cxf.webservice.springboot.aezo.cn/", name = "getAllUserResponse")
    public JAXBElement<GetAllUserResponse> createGetAllUserResponse(GetAllUserResponse value) {
        return new JAXBElement<GetAllUserResponse>(_GetAllUserResponse_QNAME, GetAllUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetName }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.cxf.webservice.springboot.aezo.cn/", name = "getName")
    public JAXBElement<GetName> createGetName(GetName value) {
        return new JAXBElement<GetName>(_GetName_QNAME, GetName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.cxf.webservice.springboot.aezo.cn/", name = "getUserResponse")
    public JAXBElement<GetUserResponse> createGetUserResponse(GetUserResponse value) {
        return new JAXBElement<GetUserResponse>(_GetUserResponse_QNAME, GetUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNameResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.cxf.webservice.springboot.aezo.cn/", name = "getNameResponse")
    public JAXBElement<GetNameResponse> createGetNameResponse(GetNameResponse value) {
        return new JAXBElement<GetNameResponse>(_GetNameResponse_QNAME, GetNameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUser }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.cxf.webservice.springboot.aezo.cn/", name = "getUser")
    public JAXBElement<GetUser> createGetUser(GetUser value) {
        return new JAXBElement<GetUser>(_GetUser_QNAME, GetUser.class, null, value);
    }

}
