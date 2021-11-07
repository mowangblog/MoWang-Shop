package top.mowang.shop.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.mowang.shop.common.utils.PageUtils;
import top.mowang.shop.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author Xuan Li
 * @email mowangblog@qq.com
 * @date 2021-11-07 13:38:25
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

