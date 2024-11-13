package com.perfree.old.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.perfree.commons.mapper.BaseMapperX;
import com.perfree.old.model.ArticleTag;
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
public interface ArticleTagMapper extends BaseMapperX<ArticleTag> {


    default ArticleTag queryByArticleIdAndTagId(Integer articleId, Integer tagId){
        return selectOne(new LambdaQueryWrapper<ArticleTag>()
                .eq(ArticleTag::getArticleId, articleId)
                .eq(ArticleTag::getTagId, tagId)
        );
    }
}
