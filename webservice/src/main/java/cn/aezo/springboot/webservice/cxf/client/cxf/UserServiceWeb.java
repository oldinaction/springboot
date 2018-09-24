package cn.aezo.springboot.webservice.cxf.client.cxf;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.8
 * 2018-08-15T20:59:27.627+08:00
 * Generated source version: 3.1.8
 * 
 */
@WebServiceClient(name = "UserServiceWeb", 
                  wsdlLocation = "http://localhost:8080/services/user?wsdl",
                  targetNamespace = "http://service.cxf.webservice.springboot.aezo.cn/") 
public class UserServiceWeb extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://service.cxf.webservice.springboot.aezo.cn/", "UserServiceWeb");
    public final static QName UserServiceImplPort = new QName("http://service.cxf.webservice.springboot.aezo.cn/", "UserServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/services/user?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(UserServiceWeb.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:8080/services/user?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public UserServiceWeb(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public UserServiceWeb(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public UserServiceWeb() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public UserServiceWeb(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public UserServiceWeb(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public UserServiceWeb(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns UserService
     */
    @WebEndpoint(name = "UserServiceImplPort")
    public UserService getUserServiceImplPort() {
        return super.getPort(UserServiceImplPort, UserService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns UserService
     */
    @WebEndpoint(name = "UserServiceImplPort")
    public UserService getUserServiceImplPort(WebServiceFeature... features) {
        return super.getPort(UserServiceImplPort, UserService.class, features);
    }

}