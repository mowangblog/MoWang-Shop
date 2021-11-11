package top.mowang.shop.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.mowang.shop.common.utils.PageUtils;
import top.mowang.shop.product.entity.ProductAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author mowang
 * @email mowangblog@qq.com
 * @date 2021-11-06 23:40:27
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveProductAttr(List<ProductAttrValueEntity> productAttrValueEntities);
}

