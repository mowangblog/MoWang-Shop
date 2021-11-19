package top.mowang.shop.product.dao;

import top.mowang.shop.product.entity.SpuInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * spu信息
 * 
 * @author mowang
 * @email mowangblog@qq.com
 * @date 2021-11-06 23:40:27
 */
@Mapper
public interface SpuInfoDao extends BaseMapper<SpuInfoEntity> {

    void updaSpuStatus(Long spuId, int code);
}
