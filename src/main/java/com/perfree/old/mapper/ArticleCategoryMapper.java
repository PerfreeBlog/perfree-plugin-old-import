package com.perfree.old.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.perfree.commons.mapper.BaseMapperX;
import com.perfree.old.model.ArticleCategory;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
@Mapper
public interface ArticleCategoryMapper extends BaseMapperX<ArticleCategory> {

    default ArticleCategory queryByArticleIdAndCategoryId(Integer articleId, Integer categoryId){
        return selectOne(new LambdaQueryWrapper<ArticleCategory>()
                .eq(ArticleCategory::getArticleId, articleId)
                .eq(ArticleCategory::getCategoryId, categoryId)
        );
    }

}
