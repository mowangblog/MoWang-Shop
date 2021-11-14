package top.mowang.shop.search;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MowangShopSearchApplicationTests {

    @Resource
    private RestHighLevelClient client;

    @Test
    void contextLoads() {
    }

}
