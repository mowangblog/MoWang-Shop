package top.mowang.shop.product.dao;

import top.mowang.shop.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author mowang
 * @email mowangblog@qq.com
 * @date 2021-11-06 23:40:26
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
