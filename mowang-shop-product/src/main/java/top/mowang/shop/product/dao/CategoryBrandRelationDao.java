package top.mowang.shop.product.dao;

import org.apache.ibatis.annotations.Param;
import top.mowang.shop.product.entity.CategoryBrandRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 品牌分类关联
 * 
 * @author mowang
 * @email mowangblog@qq.com
 * @date 2021-11-06 23:40:27
 */
@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelationEntity> {

    void updateCategory(@Param("catId") Long catId,@Param("name") String name);
}
