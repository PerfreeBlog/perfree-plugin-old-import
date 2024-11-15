package com.perfree.old.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.perfree.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
@Getter
@Setter
@TableName("p_article")
public class Article extends BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章内容类型:html/markdown
     */
    private String contentModel;

    /**
     * 解析后的文章内容
     */
    private String parseContent;

    /**
     * 文章类型:article文章,page页面
     */
    private String type;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * SEO关键字
     */
    private String metaKeywords;

    /**
     * SEO描述
     */
    private String metaDescription;

    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * slug
     */
    private String slug;

    /**
     * 是否置顶0:否,1:是
     */
    private Integer isTop;

    /**
     * 状态0:已发布,1:草稿
     */
    private Integer status;


    /**
     * 访问量
     */
    private Integer viewCount;

    /**
     * 点赞数
     */
    private Integer greatCount;


    /**
     * 是否允许评论0:否,1是
     */
    private Integer isComment;

    /**
     * 标识
     */
    private String flag;

    /**
     * 模板
     */
    private String template;


    /**
     * 是否可见, 0是, 1否
     */
    private Integer visibility;
}
