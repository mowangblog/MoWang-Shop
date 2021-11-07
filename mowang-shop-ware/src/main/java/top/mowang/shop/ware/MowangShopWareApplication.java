package top.mowang.shop.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("top.mowang.shop.ware.dao")
public class MowangShopWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(MowangShopWareApplication.class, args);
    }

}
