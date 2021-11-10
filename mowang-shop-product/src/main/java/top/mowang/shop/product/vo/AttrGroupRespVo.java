package top.mowang.shop.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import top.mowang.shop.product.entity.AttrEntity;

import java.util.List;

/**
 * MoWang-Shop
 *
 * @author : Xuan Li<mowangblog@qq.com>
 * @website : https://mowangblog.top
 * @date : 2021/11/10 17:18
 **/
@Data
public class AttrGroupRespVo {
    /**
     * 分组id
     */
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;
    /**
     * 当前组下的所有属性
     */
    private List<AttrEntity> attrs;
}
