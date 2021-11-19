package top.mowang.shop.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.mowang.shop.common.utils.PageUtils;
import top.mowang.shop.product.entity.SpuInfoEntity;
import top.mowang.shop.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author mowang
 * @email mowangblog@qq.com
 * @date 2021-11-06 23:40:27
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo spuSaveVo);


    void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);

    void up(Long spuId);
}

