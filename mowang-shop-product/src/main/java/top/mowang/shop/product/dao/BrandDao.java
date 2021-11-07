package top.mowang.shop.product.dao;

import top.mowang.shop.product.entity.BrandEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 品牌
 * 
 * @author mowang
 * @email mowangblog@qq.com
 * @date 2021-11-06 23:40:27
 */
@Mapper
public interface BrandDao extends BaseMapper<BrandEntity> {
	
}
