package cn.aezo.springboot.webservice.cxf.client;

import cn.aezo.springboot.webservice.cxf.service.User;
import cn.aezo.springboot.webservice.cxf.service.UserService;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import javax.xml.namespace.QName;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class MyClient {

    public static void main(String[] args) throws Exception {
        // 1.基于cxf客户端调用
        // invoke1();
        // invoke2();

        // 2.基于wsimport和JAX-WS调用Web Service接口
        // 2.1 命令行运行`wsimport -d . -s . -p cn.aezo.springboot.webservice.cxf.client.wsimport http://localhost:8080/services/user?wsdl`
        // invoke3();

        // 3.基于apache cxf生成客户端
        // 3.1 进入目录 `D:\java\apache-cxf-3.1.8\bin`
        // 3.2 运行 `wsdl2java -p cn.aezo.springboot.webservice.cxf.client.cxf -d D:\gitwork\springboot\webservice\src\main\java -encoding utf-8 -client http://localhost:8080/services/user?wsdl`
        new MyClient().invoke4();
        // new MyClient().invokeThread();
        // 多线程测试参考MyTest.java
    }

    // 基于cxf客户端调用方式一: 动态调用 (需要客户端生成服务端UserService的代码)
    public static void invoke1() throws Exception {
        System.out.println("1========>");

        JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
        Client client = clientFactory.createClient("http://localhost:8080/services/user?wsdl");

        Object[] objects1 = client.invoke("getName","1");
        System.out.println("=========>getName=" + objects1[0].toString());

        Object[] objects2 = client.invoke("getUser","1"); // 返回的是服务端扔出来的User对象
        System.out.println("=========>getUser=" + objects2[0].toString());

        Object[] objects3 = client.invoke("getAllUser");
        System.out.println("=========>getAllUser=" + objects3[0].toString());
    }

    // 基于cxf客户端调用方式二: 通过接口协议获取数据类型 (需要客户端生成服务端UserService的代码)
    public static void invoke2() {
        System.out.println("2========>");

        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setAddress("http://localhost:8080/services/user?wsdl");
        jaxWsProxyFactoryBean.setServiceClass(UserService.class);
        UserService userService = (UserService) jaxWsProxyFactoryBean.create(); // 创建客户端对象

        // 设置链接超时和响应时间
        // Client proxy = ClientProxy.getClient(userService);
        // HTTPConduit conduit = (HTTPConduit)proxy.getConduit();
        // HTTPClientPolicy policy = new HTTPClientPolicy();
        // policy.setConnectionTimeout(1000);
        // policy.setReceiveTimeout(1000);
        // conduit.setClient(policy);

        User userResult = userService.getUser("1");
        System.out.println("=========>username = " + userResult.getUsername());

        ArrayList<User> users = userService.getAllUser();
        System.out.println("=========>users.size() = " + users.size());
    }

    public static void invoke3() throws Exception {
        cn.aezo.springboot.webservice.cxf.client.wsimport.UserServiceWeb userServiceWeb =
                new cn.aezo.springboot.webservice.cxf.client.wsimport.UserServiceWeb(
                    new URL("http://localhost:8080/services/user?wsdl"), // wsdlURL
                    new QName("http://service.cxf.webservice.springboot.aezo.cn/", "UserServiceWeb") // SERVICE_NAME
                );
        cn.aezo.springboot.webservice.cxf.client.wsimport.UserService port = userServiceWeb.getUserServiceImplPort();
        String username = port.getName("1");
        System.out.println("=======>username = " + username); // id-1
    }

    public void invoke4() throws Exception {
        // try {
        //     // 线程休眠时间1-2秒
        //     int sleep = 1000*(new Random().nextInt(2)+1);
        //     System.out.println(Thread.currentThread().getName() + " => sleep: " + sleep);
        //     Thread.sleep(sleep);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        cn.aezo.springboot.webservice.cxf.client.cxf.UserServiceWeb ss =
                // new cn.aezo.springboot.webservice.cxf.client.cxf.UserServiceWeb(); // 此时也可以调用成功
                new cn.aezo.springboot.webservice.cxf.client.cxf.UserServiceWeb(
                    new URL("http://localhost:8080/services/user?wsdl"), // wsdlURL
                    new QName("http://service.cxf.webservice.springboot.aezo.cn/", "UserServiceWeb") // SERVICE_NAME
                );
        cn.aezo.springboot.webservice.cxf.client.cxf.UserService port = ss.getUserServiceImplPort();
        String username = port.getName(new Random().toString());
        System.out.println(Thread.currentThread().getName() + "==>username = " + username);
    }

    public void invokeThread() {
        for (int i = 0; i < 1000; i++) {
            Thread myThread = new Thread(new MyRun());
            myThread.setName("exe-" + (i+1));
            myThread.start();
        }
    }

    public class MyRun implements Runnable {
        @Override
        public void run() {
            try {
                invoke4();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
