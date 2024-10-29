package com.perfree.old.service;

import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import com.perfree.commons.exception.ServiceException;
import com.perfree.old.common.MarkdownUtil;
import com.perfree.old.controller.vo.OldVersionImportReqVO;
import com.perfree.old.mapper.ArticleMapper;
import com.perfree.old.model.Article;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
@Slf4j
public class ImportOldVersionService {

    private final ArticleMapper articleMapper;

    public ImportOldVersionService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }


    public Boolean oldVersionImport(OldVersionImportReqVO reqVO) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl("jdbc:mysql://124.222.171.173:60666/perfree?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8&nullCatalogMeansCurrent=true");
        hikariDataSource.setUsername("root");
        hikariDataSource.setPassword("Yin741223257..");
        try (hikariDataSource; Connection conn = hikariDataSource.getConnection()) {
            List<Entity> query = SqlExecutor.query(conn, "select * from p_article", new EntityListHandler());
            for (Entity entries : query) {
                handleImportArticle(entries);
            }
        } catch (SQLException e) {
            log.error("导入老版本数据出错", e);
            throw new ServiceException(9140001, "导入老版本数据出错!");
        }
        return null;
    }

    private void handleImportArticle(Entity entries) {
        if (entries.getStr("type").equals("journal")) {
            return;
        }
        Article article = articleMapper.queryBySlug(entries.getStr("slug"));
        if (null != article) {
            return;
        }

        Article oldArticle = new Article();
        oldArticle.setFlag(entries.getStr("flag"));
        oldArticle.setContent(entries.getStr("content"));
        oldArticle.setTitle(entries.getStr("title"));
        oldArticle.setContentModel("Vditor");
        oldArticle.setGreatCount(entries.getInt("greatCount"));
        oldArticle.setSummary(entries.getStr("summary"));
        oldArticle.setIsTop(entries.getInt("isTop"));
        oldArticle.setSlug(entries.getStr("slug"));
        oldArticle.setStatus(entries.getInt("status"));
        oldArticle.setType(entries.getStr("type"));
        oldArticle.setIsComment(entries.getInt("isComment"));
        oldArticle.setTemplate(entries.getStr("template"));
        oldArticle.setThumbnail(entries.getStr("thumbnail"));
        oldArticle.setMetaDescription(entries.getStr("metaDescription"));
        oldArticle.setMetaKeywords(entries.getStr("metaKeywords"));
        oldArticle.setViewCount(entries.getInt("viewCount"));
        oldArticle.setParseContent(MarkdownUtil.mdToHtml(entries.getStr("content")));

        articleMapper.insert(oldArticle);
    }
}
