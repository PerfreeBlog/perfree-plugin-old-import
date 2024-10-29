package com.perfree.old.service;

import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import com.perfree.commons.exception.ServiceException;
import com.perfree.old.common.MarkdownUtil;
import com.perfree.old.controller.vo.OldVersionImportReqVO;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
@Slf4j
public class ImportOldVersionService {
    public Boolean oldVersionImport(OldVersionImportReqVO reqVO) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl("jdbc:mysql://124.222.171.173:60666/perfree?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8&nullCatalogMeansCurrent=true");
        hikariDataSource.setUsername("root");
        hikariDataSource.setPassword("Yin741223257..");
        try (hikariDataSource; Connection conn = hikariDataSource.getConnection()) {
            List<Entity> query = SqlExecutor.query(conn, "select * from p_article", new EntityListHandler());
            for (Entity entries : query) {
                String parseContent = MarkdownUtil.mdToHtml(entries.getStr("content"));
                System.out.println(parseContent);
            }
        } catch (SQLException e) {
            log.error("导入老版本数据出错", e);
            throw new ServiceException(9140001, "导入老版本数据出错!");
        }
        return null;
    }
}
