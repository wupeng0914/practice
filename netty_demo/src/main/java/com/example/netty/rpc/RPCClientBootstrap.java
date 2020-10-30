package com.example.netty.rpc;

/**
 * @Description RPCClientBootstrap
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/10/28 4:29 下午
 **/
public class RPCClientBootstrap {

    //定义协议头
    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) throws Exception {
        //创建一个消费者
        NettyClient customer = new NettyClient();
        //创建一个服务的代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, providerName);
        for (;;){
            Thread.sleep(2000);
            //通过代理对象调用服务提供者的方法(服务)
            String res = service.hello("Hello, Simple Dubbo...");
            System.out.println("调用结果 res="+res);
        }
    }

}
