package top.mowang.shop.order.dao;

import top.mowang.shop.order.entity.OrderReturnApplyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单退货申请
 * 
 * @author Xuan Li
 * @email mowangblog@qq.com
 * @date 2021-11-07 13:38:25
 */
@Mapper
public interface OrderReturnApplyDao extends BaseMapper<OrderReturnApplyEntity> {
	
}
