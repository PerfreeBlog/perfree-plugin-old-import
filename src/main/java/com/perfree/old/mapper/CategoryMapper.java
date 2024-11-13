package com.perfree.old.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.perfree.commons.mapper.BaseMapperX;
import com.perfree.old.model.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 分类表 Mapper 接口
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
@Mapper
public interface CategoryMapper extends BaseMapperX<Category> {

    default Category queryBySlug(String slug){
        return selectOne(new LambdaQueryWrapper<Category>()
                .eq(Category::getSlug, slug)
        );
    }

}

