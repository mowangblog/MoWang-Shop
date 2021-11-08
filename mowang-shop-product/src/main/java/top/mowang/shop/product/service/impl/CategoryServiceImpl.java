package top.mowang.shop.product.service.impl;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.mowang.shop.common.utils.PageUtils;
import top.mowang.shop.common.utils.Query;

import top.mowang.shop.product.dao.CategoryDao;
import top.mowang.shop.product.entity.CategoryEntity;
import top.mowang.shop.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        List<CategoryEntity> allMenu = baseMapper.selectList(null);
        return allMenu.stream()
                //找到所有一级菜单
                .filter(categoryEntity -> categoryEntity.getCatLevel() == 1)
                //查找所有一级菜单的子菜单
                .peek(menu -> menu.setChildren(getChildren(menu, allMenu)))
                //菜单排序
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                .collect(Collectors.toList());
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO 1 检查当前的菜单是否被别的地方所引用
        baseMapper.deleteBatchIds(asList);
    }

    /**
     * 查找所有菜单的子菜单
     */
    private List<CategoryEntity> getChildren(CategoryEntity root,List<CategoryEntity> all){
        return all.stream()
                //筛选出当前菜单的子菜单
                .filter(menu -> root.getCatId().equals(menu.getParentCid()))
                //查找所有子菜单的子菜单
                .peek(menu -> menu.setChildren(getChildren(menu, all)))
                //子菜单排序
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                .collect(Collectors.toList());
    }

}