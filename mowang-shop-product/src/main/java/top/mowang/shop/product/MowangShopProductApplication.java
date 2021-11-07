package top.mowang.shop.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("top.mowang.shop.product.dao")
public class MowangShopProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(MowangShopProductApplication.class, args);
    }

}
