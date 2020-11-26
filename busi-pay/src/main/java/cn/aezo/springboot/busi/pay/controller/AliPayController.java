package cn.aezo.springboot.busi.pay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author smalle
 * @date 2019-12-21 22:09
 */
@RestController
public class AliPayController {
    @Autowired
    AlipayClient alipayClient;

    @RequestMapping("/pay")
    public String pay() {
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl("http://localhost:8080/returnUrl");
        alipayRequest.setNotifyUrl("http://localhost:8080/notifyUrl");//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"20150320010101002\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":88.88," +
                "    \"subject\":\"Iphone6 16G\"," +
                "    \"body\":\"Iphone6 16G\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }" +
                "  }");//填充业务参数
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return form;
    }

    // 测试可以使用本地地址
    @RequestMapping("/returnUrl")
    public String returnUrl() {
        System.out.println("returnUrl...");
        return "returnUrl...";
    }

    // 测试必须要外网地址
    @RequestMapping("/notifyUrl")
    public String notifyUrl() {
        System.out.println("notifyUrl...");
        return "notifyUrl...";
    }

}
