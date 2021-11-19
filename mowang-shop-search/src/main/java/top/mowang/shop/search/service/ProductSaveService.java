package top.mowang.shop.search.service;

import top.mowang.shop.common.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author : Xuan Li<mowangblog@qq.com>
 * @website : https://mowangblog.top
 * @date : 2021/11/19 23:34
 **/
public interface ProductSaveService {

    boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
