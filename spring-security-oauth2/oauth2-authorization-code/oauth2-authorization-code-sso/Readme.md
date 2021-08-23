1.启动本项目下的client1、client2、resource，并启动第三方认证服务oauth2-authorization-code-qq
2.在hosts下增加 `127.0.0.1 aezocn.local`(client1)、`127.0.0.1 smalle.local`(client2) 的映射
3.访问 `http://aezocn.local:8081/client1`
4.USER账号密码 123456/admin 进行登录
5.访问 `http://smalle.local:8082/client2` 会发现无需登录
6.同理，先登录client2，再访问client1也无需登录