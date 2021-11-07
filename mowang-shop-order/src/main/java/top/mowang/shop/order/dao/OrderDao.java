package top.mowang.shop.order.dao;

import top.mowang.shop.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author Xuan Li
 * @email mowangblog@qq.com
 * @date 2021-11-07 13:38:25
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
