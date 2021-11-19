package top.mowang.shop.product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.mowang.shop.common.es.SkuEsModel;
import top.mowang.shop.common.utils.R;

import java.util.List;


/**
 *
 * @author : Xuan Li<mowangblog@qq.com>
 * @website : https://mowangblog.top
 * @date : 2021/11/19 23:16
 **/
@FeignClient("mowang-shop-search")
public interface SearchFeignService {

    @PostMapping(value = "/search/save/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);

}
