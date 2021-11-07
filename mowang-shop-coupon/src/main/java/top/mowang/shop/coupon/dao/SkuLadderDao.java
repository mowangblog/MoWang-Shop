package top.mowang.shop.coupon.dao;

import top.mowang.shop.coupon.entity.SkuLadderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品阶梯价格
 * 
 * @author Xuan Li
 * @email mowangblog@qq.com
 * @date 2021-11-07 13:45:01
 */
@Mapper
public interface SkuLadderDao extends BaseMapper<SkuLadderEntity> {
	
}
