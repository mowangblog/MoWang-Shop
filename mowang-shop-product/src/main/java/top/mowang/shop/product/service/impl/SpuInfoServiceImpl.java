package top.mowang.shop.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import org.bouncycastle.jcajce.provider.symmetric.DES;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import top.mowang.shop.common.constant.ProductConstant;
import top.mowang.shop.common.es.SkuEsModel;
import top.mowang.shop.common.to.SkuHasStockVo;
import top.mowang.shop.common.to.SkuReductionTo;
import top.mowang.shop.common.to.SpuBoundTo;
import top.mowang.shop.common.utils.PageUtils;
import top.mowang.shop.common.utils.Query;

import top.mowang.shop.common.utils.R;
import top.mowang.shop.product.dao.SpuInfoDao;
import top.mowang.shop.product.entity.*;
import top.mowang.shop.product.feign.CouponFeignService;
import top.mowang.shop.product.feign.SearchFeignService;
import top.mowang.shop.product.feign.WareFeignService;
import top.mowang.shop.product.service.*;
import top.mowang.shop.product.vo.*;

import javax.annotation.Resource;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {
    @Resource
    SpuInfoDescService descService;

    @Resource
    SpuImagesService spuImagesService;

    @Resource
    AttrService attrService;

    @Resource
    ProductAttrValueService productAttrValueService;

    @Resource
    SkuInfoService skuInfoService;

    @Resource
    SkuImagesService skuImagesService;

    @Resource
    BrandService brandService;

    @Resource
    CategoryService categoryService;

    @Resource
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Resource
    CouponFeignService couponFeignService;

    @Resource
    WareFeignService wareFeignService;

    @Resource
    SearchFeignService searchFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    @SuppressWarnings("all")
    public void saveSpuInfo(SpuSaveVo spuSaveVo) {
        //保存spu基本信息spu-info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtil.copyProperties(spuSaveVo,spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.saveBaseSpuInfo(spuInfoEntity);

        //保存spu的描述图片 spu-info-desc
        List<String> decript = spuSaveVo.getDecript();
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        spuInfoDescEntity.setDecript(String.join(",",decript));
        descService.saveDescSpuInfo(spuInfoDescEntity);

        //保存spu图片集spu_images
        List<String> images = spuSaveVo.getImages();
        spuImagesService.saveImages(spuInfoEntity.getId(),images);

        //保存spu规格参数
        List<BaseAttrs> baseAttrs = spuSaveVo.getBaseAttrs();
        List<ProductAttrValueEntity> productAttrValueEntities = baseAttrs.stream().map(baseAttr -> {
            ProductAttrValueEntity valueEntity = new ProductAttrValueEntity();
            valueEntity.setSpuId(spuInfoEntity.getId());
            AttrEntity attrEntity = attrService.getById(baseAttr.getAttrId());
            valueEntity.setAttrName(attrEntity.getAttrName());
            valueEntity.setAttrId(baseAttr.getAttrId());
            valueEntity.setAttrValue(baseAttr.getAttrValues());
            valueEntity.setQuickShow(baseAttr.getShowDesc());
            return valueEntity;
        }).collect(Collectors.toList());
        productAttrValueService.saveProductAttr(productAttrValueEntities);

        //5、保存spu的积分信息；gulimall_sms->sms_spu_bounds
        Bounds bounds = spuSaveVo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtil.copyProperties(bounds,spuBoundTo);
        spuBoundTo.setSpuId(spuInfoEntity.getId());
        couponFeignService.saveSpuBounds(spuBoundTo);

        //保存sku的信息
        List<Skus> skus = spuSaveVo.getSkus();
        if (CollectionUtil.isNotEmpty(skus)) {
            skus.forEach(sku->{
                String defaultImg = "";
                for (Images image : sku.getImages()) {
                    if(image.getDefaultImg() == 1){
                        defaultImg = image.getImgUrl();
                    }
                }
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtil.copyProperties(sku,skuInfoEntity);
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImg);
                //保存sku基本信息
                skuInfoService.save(skuInfoEntity);

                //保存sku图片
                Long skuId = skuInfoEntity.getSkuId();
                List<SkuImagesEntity> imagesEntities = sku.getImages().stream().map(img -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setImgUrl(img.getImgUrl());
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    return skuImagesEntity;
                }).filter(entity->{
                    //返回true就是需要，false就是剔除
                    return StrUtil.isNotEmpty(entity.getImgUrl());
                }).collect(Collectors.toList());
                skuImagesService.saveBatch(imagesEntities);

                //保存sku销售属性
                List<Attr> attr = sku.getAttr();
                List<SkuSaleAttrValueEntity> saleAttrValueEntities = attr.stream().map(skuAttr -> {
                    SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtil.copyProperties(skuAttr, skuSaleAttrValueEntity);
                    skuSaleAttrValueEntity.setSkuId(skuId);
                    return skuSaleAttrValueEntity;
                }).collect(Collectors.toList());
                skuSaleAttrValueService.saveBatch(saleAttrValueEntities);


                ///5.4）、sku的优惠、满减等信息；gulimall_sms->sms_sku_ladder\sms_sku_full_reduction\sms_member_price
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtil.copyProperties(sku,skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                if(skuReductionTo.getFullCount() >0 || skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) == 1){
                    couponFeignService.saveSkuReduction(skuReductionTo);
                }
            });
        }
    }

    @Override
    public void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity) {
        this.baseMapper.insert(spuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {

        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if(!StrUtil.isEmpty(key)){
            wrapper.and((w)->{
                w.eq("id",key).or().like("spu_name",key);
            });
        }
        // status=1 and (id=1 or spu_name like xxx)
        String status = (String) params.get("status");
        if(!StrUtil.isEmpty(status)){
            wrapper.eq("publish_status",status);
        }

        String brandId = (String) params.get("brandId");
        if(!StrUtil.isEmpty(brandId)&&!"0".equalsIgnoreCase(brandId)){
            wrapper.eq("brand_id",brandId);
        }

        String catelogId = (String) params.get("catelogId");
        if(!StrUtil.isEmpty(catelogId)&&!"0".equalsIgnoreCase(catelogId)){
            wrapper.eq("catalog_id",catelogId);
        }

        /**
         * status: 2
         * key:
         * brandId: 9
         * catelogId: 225
         */

        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void up(Long spuId) {

        //1、查出当前spuId对应的所有sku信息,品牌的名字
        List<SkuInfoEntity> skuInfoEntities = skuInfoService.getSkusBySpuId(spuId);

        //TODO 4、查出当前sku的所有可以被用来检索的规格属性
        List<ProductAttrValueEntity> baseAttrs = productAttrValueService.baseAttrListforspu(spuId);

        List<Long> attrIds = baseAttrs.stream().map(attr -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());

        List<Long> searchAttrIds = attrService.selectSearchAttrs(attrIds);
        //转换为Set集合
        Set<Long> idSet = searchAttrIds.stream().collect(Collectors.toSet());

        List<SkuEsModel.Attrs> attrsList = baseAttrs.stream().filter(item -> {
            return idSet.contains(item.getAttrId());
        }).map(item -> {
            SkuEsModel.Attrs attrs = new SkuEsModel.Attrs();
            BeanUtils.copyProperties(item, attrs);
            return attrs;
        }).collect(Collectors.toList());

        List<Long> skuIdList = skuInfoEntities.stream()
                .map(SkuInfoEntity::getSkuId)
                .collect(Collectors.toList());
        //TODO 1、发送远程调用，库存系统查询是否有库存
        Map<Long, Boolean> stockMap = null;
        try {
            R<List<SkuHasStockVo>> skuHasStock = wareFeignService.getSkuHasStock(skuIdList);

            stockMap = skuHasStock.getData().stream()
                    .collect(Collectors.toMap(SkuHasStockVo::getSkuId, item -> item.getHasStock()));
        } catch (Exception e) {
            log.error("库存服务查询异常：原因{}",e);
        }

        //2、封装每个sku的信息
        Map<Long, Boolean> finalStockMap = stockMap;
        List<SkuEsModel> collect = skuInfoEntities.stream().map(sku -> {
            //组装需要的数据
            SkuEsModel esModel = new SkuEsModel();
            esModel.setSkuPrice(sku.getPrice());
            esModel.setSkuImg(sku.getSkuDefaultImg());

            //设置库存信息
            if (finalStockMap == null) {
                esModel.setHasStock(true);
            } else {
                esModel.setHasStock(finalStockMap.get(sku.getSkuId()));
            }

            //TODO 2、热度评分。0
            esModel.setHotScore(0L);

            //TODO 3、查询品牌和分类的名字信息
            BrandEntity brandEntity = brandService.getById(sku.getBrandId());
            esModel.setBrandName(brandEntity.getName());
            esModel.setBrandId(brandEntity.getBrandId());
            esModel.setBrandImg(brandEntity.getLogo());

            CategoryEntity categoryEntity = categoryService.getById(sku.getCatalogId());
            esModel.setCatalogId(categoryEntity.getCatId());
            esModel.setCatalogName(categoryEntity.getName());

            //设置检索属性
            esModel.setAttrs(attrsList);

            BeanUtils.copyProperties(sku,esModel);

            return esModel;
        }).collect(Collectors.toList());

        //TODO 5、将数据发给es进行保存：gulimall-search
        R r = searchFeignService.productStatusUp(collect);

        if (r.getCode() == 0) {
            //远程调用成功
            //TODO 6、修改当前spu的状态
            this.baseMapper.updaSpuStatus(spuId, ProductConstant.ProductStatusEnum.SPU_UP.getCode());
        } else {
            //远程调用失败
            //TODO 7、重复调用？接口幂等性:重试机制
        }
    }

}