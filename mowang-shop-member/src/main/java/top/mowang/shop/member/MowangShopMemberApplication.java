package top.mowang.shop.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("top.mowang.shop.member.dao")
@EnableFeignClients(basePackages="top.mowang.shop.member.feign")
public class MowangShopMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(MowangShopMemberApplication.class, args);
    }

}
