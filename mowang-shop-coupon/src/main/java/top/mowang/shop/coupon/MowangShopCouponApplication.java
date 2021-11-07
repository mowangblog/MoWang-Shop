package top.mowang.shop.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("top.mowang.shop.coupon.dao")
public class MowangShopCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(MowangShopCouponApplication.class, args);
    }

}
