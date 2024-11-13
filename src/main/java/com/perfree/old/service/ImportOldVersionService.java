package com.perfree.old.service;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityHandler;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import cn.hutool.http.HtmlUtil;
import com.perfree.cache.AttachConfigCacheService;
import com.perfree.commons.exception.ServiceException;
import com.perfree.commons.utils.FileTypeUtils;
import com.perfree.old.common.MarkdownUtil;
import com.perfree.old.controller.vo.OldVersionImportReqVO;
import com.perfree.old.mapper.*;
import com.perfree.old.model.*;
import com.perfree.system.api.attachConfig.dto.AttachConfigCacheDTO;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
@Slf4j
public class ImportOldVersionService {

    private final ArticleMapper articleMapper;

    private final AttachMapper attachMapper;

    private final CategoryMapper categoryMapper;

    private final ArticleCategoryMapper articleCategoryMapper;

    private final AttachConfigCacheService attachConfigCacheService;


    private final TagMapper tagMapper;

    private final ArticleTagMapper articleTagMapper;

    private final UserMapper userMapper;

    private final UserRoleMapper userRoleMapper;

    private final RoleMapper roleMapper;

    private final LinkMapper linkMapper;

    private final CommentMapper commentMapper;

    public ImportOldVersionService(ArticleMapper articleMapper, AttachConfigCacheService attachConfigCacheService,
                                   AttachMapper attachMapper,CategoryMapper categoryMapper,
                                   ArticleCategoryMapper articleCategoryMapper, TagMapper tagMapper,
                                   ArticleTagMapper articleTagMapper, UserMapper userMapper,
                                   UserRoleMapper userRoleMapper,RoleMapper roleMapper,
                                   LinkMapper linkMapper, CommentMapper commentMapper) {
        this.articleMapper = articleMapper;
        this.attachMapper = attachMapper;
        this.attachConfigCacheService = attachConfigCacheService;
        this.categoryMapper = categoryMapper;
        this.articleCategoryMapper = articleCategoryMapper;
        this.articleTagMapper = articleTagMapper;
        this.tagMapper = tagMapper;
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.roleMapper = roleMapper;
        this.linkMapper = linkMapper;
        this.commentMapper = commentMapper;
    }


    public Boolean oldVersionImport(OldVersionImportReqVO reqVO) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        String jdbcUrl = StrUtil.format("jdbc:mysql://{}:{}/{}?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8&nullCatalogMeansCurrent=true",
                reqVO.getDataBaseIp(), reqVO.getDataBasePort(), reqVO.getDataBaseName());
        hikariDataSource.setJdbcUrl(jdbcUrl);
        hikariDataSource.setUsername(reqVO.getDataBaseUserName());
        hikariDataSource.setPassword(reqVO.getDataBasePassword());
        try (hikariDataSource; Connection conn = hikariDataSource.getConnection()) {
            handleImportAttach(conn);
            handleImportUser(conn);
            handleImportCategory(conn);
            handleImportTag(conn);
            handleImportArticle(conn);
            handleImportArticleTag(conn);
            handleImportLink(conn);
            handleImportComment(conn);
        } catch (SQLException e) {
            log.error("导入老版本数据出错", e);
            throw new ServiceException(9140001, "导入老版本数据出错!");
        }
        return null;
    }

    private void handleImportComment(Connection conn) throws SQLException {
        List<Entity> query = SqlExecutor.query(conn, "select * from p_comment", new EntityListHandler());
        for (Entity entries : query) {
            Comment comment = new Comment();
            comment.setContent(entries.getStr("content"));
            comment.setId(entries.getInt("id"));
            comment.setEmail(entries.getStr("email"));
            comment.setArticleId(entries.getInt("articleId"));
            comment.setPid(entries.getInt("pid"));
            comment.setTopPid(entries.getInt("topPid"));
            comment.setUserId(entries.getInt("userId"));
            comment.setStatus(entries.getInt("status"));
            comment.setAvatar("/static/images/user-default.png");
            comment.setWebsite(entries.getStr("website"));
            comment.setUserName(entries.getStr("userName"));
            comment.setCreateTime(LocalDateTimeUtil.of(entries.getDate("createTime")));

            Comment selectById = commentMapper.selectById(comment.getId());
            if (null != selectById) {
                commentMapper.updateById(comment);
            } else {
                commentMapper.insert(comment);
            }
        }
    }

    private void handleImportLink(Connection conn) throws SQLException {
        List<Entity> query = SqlExecutor.query(conn, "select * from p_link", new EntityListHandler());
        for (Entity entries : query) {
            Link link = new Link();
            link.setName(entries.getStr("name"));
            link.setDesc(entries.getStr("desc"));
            link.setId(entries.getInt("id"));
            link.setLogo(entries.getStr("logo"));
            link.setAddress(entries.getStr("address"));
            link.setCreateTime(LocalDateTimeUtil.of(entries.getDate("createTime")));

            Link selectById = linkMapper.selectById(link.getId());
            if (null != selectById) {
                linkMapper.updateById(link);
            } else {
                linkMapper.insert(link);
            }
        }
    }

    private void handleImportUser(Connection conn) throws SQLException {
        List<Entity> query = SqlExecutor.query(conn, "select * from p_user", new EntityListHandler());

        Role adminRole = roleMapper.queryByCode("admin");
        Role userRole = roleMapper.queryByCode("user");
        Role editorRole = roleMapper.queryByCode("editor");
        for (Entity entries : query) {
            User user = new User();
            user.setAccount(entries.getStr("account"));
            user.setId(entries.getInt("id"));
            user.setUserName(entries.getStr("userName"));
            user.setPassword(entries.getStr("password"));
            user.setSalt(entries.getStr("salt"));
            user.setStatus(entries.getInt("status"));
            user.setAvatar("/static/images/user-default.png");
            user.setEmail(entries.getStr("email"));
            user.setWebsite(entries.getStr("website"));
            user.setCreateTime(LocalDateTimeUtil.of(entries.getDate("createTime")));

            User queryUser = userMapper.selectById(user.getId());
            if (null != queryUser) {
                userMapper.updateById(user);
            } else {
                userMapper.insert(user);
            }

            handleUserRole(conn, user.getId(), entries.getInt("roleId"), adminRole, userRole, editorRole);
        }
    }

    private void handleUserRole(Connection conn, Integer userId, Integer roleId, Role queryAdminRole, Role queryUserRole, Role queryEditorRole) throws SQLException {
        Entity query = SqlExecutor.query(conn, "select * from p_role where id = ?", new EntityHandler(), roleId);
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        if (query.getStr("code").equals("admin")) {
            userRole.setRoleId(queryAdminRole.getId());
        } else if (query.getStr("code").equals("user")) {
            userRole.setRoleId(queryUserRole.getId());
        } else if (query.getStr("code").equals("editor")) {
            userRole.setRoleId(queryEditorRole.getId());
        } else {
            userRole.setRoleId(queryUserRole.getId());
        }

        UserRole findUserRole = userRoleMapper.queryByUserIdAndRoleId(userRole.getUserId(), userRole.getRoleId());
        if (null != findUserRole) {
            return;
        }
        userRoleMapper.insert(userRole);
    }

    private void handleImportArticleTag(Connection conn) throws SQLException {
        List<Entity> query = SqlExecutor.query(conn, "select * from p_article_tag", new EntityListHandler());
        for (Entity entries : query) {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(entries.getInt("articleId"));
            articleTag.setTagId(entries.getInt("tagId"));

            ArticleTag queryArticleTag = articleTagMapper.queryByArticleIdAndTagId(articleTag.getArticleId(), articleTag.getTagId());
            if (null != queryArticleTag) {
                continue;
            }
            articleTagMapper.insert(articleTag);
        }
    }

    private void handleImportTag(Connection conn) throws SQLException {
        List<Entity> query = SqlExecutor.query(conn, "select * from p_tag", new EntityListHandler());
        for (Entity entries : query) {
            Tag tag = new Tag();
            tag.setId(entries.getInt("id"));
            tag.setSlug(entries.getStr("slug"));
            tag.setName(entries.getStr("name"));
            tag.setColor(entries.getStr("color"));
            tag.setCreateUserId(entries.getInt("userId"));
            tag.setCreateTime(LocalDateTimeUtil.of(entries.getDate("createTime")));

            Tag queryTag = tagMapper.selectById(tag.getId());
            if (null != queryTag) {
                tagMapper.updateById(tag);
            } else {
                tagMapper.insert(tag);
            }
        }
    }

    /**
     * 导入附件数据
     * @param conn conn
     */
    private void handleImportAttach(Connection conn) throws SQLException {
        List<Entity> query = SqlExecutor.query(conn, "select * from p_attach", new EntityListHandler());
        for (Entity entries : query) {
            String path = entries.getStr("path");
            if (path.startsWith("/attach/")) {
                path = path.replaceFirst("/attach/", "");
            }

            Attach attach = new Attach();
            attach.setId(entries.getInt("id"));
            attach.setPath(path);
            attach.setType(entries.getStr("type"));
            attach.setAttachGroup("old");
            attach.setName(entries.getStr("name"));
            AttachConfigCacheDTO masterAttachConfig = attachConfigCacheService.getMasterAttachConfig();
            attach.setConfigId(masterAttachConfig.getId());
            attach.setStorage(masterAttachConfig.getStorage());
            attach.setUrl("/api/attach/" + path);
            attach.setMineType(FileTypeUtils.getMineType(path));
            attach.setCreateTime(LocalDateTimeUtil.of(entries.getDate("createTime")));


            Attach queryAttach = attachMapper.selectById(attach.getId());
            if (null != queryAttach) {
                attachMapper.updateById(attach);
            } else {
                attachMapper.insert(attach);
            }
        }
    }

    /**
     * 导入文章/页面数据
     */
    private void handleImportArticle(Connection conn) throws SQLException {
        List<Entity> query = SqlExecutor.query(conn, "select * from p_article", new EntityListHandler());
        for (Entity entries : query) {
            if (entries.getStr("type").equals("journal")) {
                continue;
            }

            Article article = new Article();
            article.setId(entries.getInt("id"));
            article.setFlag(entries.getStr("flag"));
            article.setContent(entries.getStr("content"));
            article.setTitle(entries.getStr("title"));
            article.setContentModel("Vditor");
            article.setGreatCount(entries.getInt("greatCount"));
            String summary = entries.getStr("summary");
            article.setParseContent(MarkdownUtil.mdToHtml(entries.getStr("content")));
            if (StringUtils.isNotBlank(summary)) {
                article.setSummary(summary);
            } else {
                String cleanHtmlTag = HtmlUtil.cleanHtmlTag(article.getParseContent());
                if (cleanHtmlTag.length() > 150){
                    article.setSummary(cleanHtmlTag.substring(0, 150));
                } else {
                    article.setSummary(cleanHtmlTag);
                }
            }
            article.setIsTop(entries.getInt("isTop"));
            article.setSlug(entries.getStr("slug"));
            article.setStatus(entries.getInt("status"));
            article.setType(entries.getStr("type"));
            article.setIsComment(entries.getInt("isComment"));
            article.setTemplate(entries.getStr("template"));
            article.setThumbnail(entries.getStr("thumbnail"));
            article.setMetaDescription(entries.getStr("metaDescription"));
            article.setMetaKeywords(entries.getStr("metaKeywords"));
            article.setViewCount(entries.getInt("viewCount"));
            article.setCreateTime(LocalDateTimeUtil.of(entries.getDate("createTime")));
            article.setCreateUserId(entries.getInt("userId"));

            Article queryArticle = articleMapper.selectById(article.getId());
            if (null != queryArticle) {
                articleMapper.updateById(article);
            } else {
                articleMapper.insert(article);
            }
            handleCategoryArticle(entries.getInt("categoryId"), article.getId());
        }
    }

    private void handleImportCategory(Connection conn) throws SQLException {
        List<Entity> query = SqlExecutor.query(conn, "select * from p_category", new EntityListHandler());
        for (Entity entries : query) {
            Category category = new Category();
            category.setId(entries.getInt("id"));
            category.setPid(entries.getInt("pid"));
            category.setName(entries.getStr("name"));
            category.setSlug(entries.getStr("slug"));
            category.setDesc(entries.getStr("desc"));
            category.setThumbnail(entries.getStr("thumbnail"));
            category.setStatus(entries.getInt("status"));
            category.setMetaKeywords(entries.getStr("metaKeywords"));
            category.setMetaDescription(entries.getStr("metaDescription"));
            category.setCreateTime(LocalDateTimeUtil.of(entries.getDate("createTime")));

            Category queryCategory = categoryMapper.selectById(category.getId());

            if (null != queryCategory) {
                categoryMapper.updateById(category);
            } else {
                categoryMapper.insert(category);
            }
        }
    }

    private void handleCategoryArticle(Integer categoryId, Integer articleId) {
        if (null == articleId || null == categoryId){
            return;
        }

        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setArticleId(articleId);
        articleCategory.setCategoryId(categoryId);

        ArticleCategory queryArticleCategory = articleCategoryMapper.queryByArticleIdAndCategoryId(articleId, categoryId);
        if (null != queryArticleCategory) {
            return;
        }
        articleCategoryMapper.insert(articleCategory);
    }
}
