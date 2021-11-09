package top.mowang.shop.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import top.mowang.shop.product.entity.BrandEntity;
import top.mowang.shop.product.service.BrandService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MowangShopProductApplicationTests {

    @Autowired
    BrandService brandService;



    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setDescript("hello");
        brandEntity.setName("小米");
        brandService.save(brandEntity);
        System.out.println("保存成功");

        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("descript", "hello"));
        list.forEach(System.out::println);
    }





}
