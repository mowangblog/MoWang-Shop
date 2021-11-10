package top.mowang.shop.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.mowang.shop.common.utils.PageUtils;
import top.mowang.shop.product.entity.CategoryBrandRelationEntity;

import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author mowang
 * @email mowangblog@qq.com
 * @date 2021-11-06 23:40:27
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);


    void updateBrand(Long brandId, String name);

    void updateCategory(Long catId, String name);
}

