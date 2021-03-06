package top.mowang.shop.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.mowang.shop.common.to.SkuHasStockVo;
import top.mowang.shop.common.utils.PageUtils;
import top.mowang.shop.ware.entity.WareSkuEntity;

import java.util.List;
import java.util.Map;

/**
 * εεεΊε­
 *
 * @author Xuan Li
 * @email mowangblog@qq.com
 * @date 2021-11-07 13:54:59
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);
}

