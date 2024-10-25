package com.perfree.old.service;

import com.perfree.old.controller.vo.OldVersionImportReqVO;
import com.zaxxer.hikari.HikariDataSource;
import org.dromara.hutool.db.Entity;
import org.dromara.hutool.db.handler.EntityListHandler;
import org.dromara.hutool.db.sql.SqlExecutor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class ImportOldVersionService {
    public Boolean oldVersionImport(OldVersionImportReqVO reqVO) {
       /* HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl("jdbc:mysql://124.222.171.173:60666/perfree?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8&nullCatalogMeansCurrent=true");
        hikariDataSource.setUsername("root");
        hikariDataSource.setPassword("Yin741223257..");
        Connection conn = null;
        try {
            conn = hikariDataSource.getConnection();
            List<Entity> query = SqlExecutor.of(null, conn).query("select * from p_article", new EntityListHandler());
            for (Entity entries : query) {
                Article bean = entries.toBean(Article.class);
                System.out.println(bean);
            }
        } catch (SQLException e) {
        } finally {
        }*/
        return null;
    }
}
