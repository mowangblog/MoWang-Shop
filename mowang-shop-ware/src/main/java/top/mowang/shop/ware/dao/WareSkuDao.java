package top.mowang.shop.ware.dao;

import org.apache.ibatis.annotations.Param;
import top.mowang.shop.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * εεεΊε­
 * 
 * @author Xuan Li
 * @email mowangblog@qq.com
 * @date 2021-11-07 13:54:59
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    void addStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("skuNum") Integer skuNum);

    Long getSkuStock(@Param("skuId") Long skuId);
}
