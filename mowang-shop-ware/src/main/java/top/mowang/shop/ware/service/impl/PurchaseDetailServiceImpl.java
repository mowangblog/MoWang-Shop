package top.mowang.shop.ware.service.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.mowang.shop.common.utils.PageUtils;
import top.mowang.shop.common.utils.Query;

import top.mowang.shop.ware.dao.PurchaseDetailDao;
import top.mowang.shop.ware.entity.PurchaseDetailEntity;
import top.mowang.shop.ware.service.PurchaseDetailService;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        /**
         * status: 0,//状态
         *    wareId: 1,//仓库id
         */

        QueryWrapper<PurchaseDetailEntity> queryWrapper = new QueryWrapper<PurchaseDetailEntity>();

        String key = (String) params.get("key");
        if(!StrUtil.isEmpty(key)){
            //purchase_id  sku_id
            queryWrapper.and(w->{
                w.eq("purchase_id",key).or().eq("sku_id",key);
            });
        }

        String status = (String) params.get("status");
        if(!StrUtil.isEmpty(status)){
            //purchase_id  sku_id
            queryWrapper.eq("status",status);
        }

        String wareId = (String) params.get("wareId");
        if(!StrUtil.isEmpty(wareId)){
            //purchase_id  sku_id
            queryWrapper.eq("ware_id",wareId);
        }

        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}