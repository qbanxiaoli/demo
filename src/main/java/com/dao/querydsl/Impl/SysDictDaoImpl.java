package com.dao.querydsl.Impl;

import com.aop.annotation.DataScope;
import com.model.dto.SysDictDTO;
import com.model.entity.QSysDict;
import com.model.vo.SysDictVO;
import com.dao.querydsl.SysDictDao;
import com.google.common.base.CaseFormat;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.RelationalPath;
import com.querydsl.sql.RelationalPathBase;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import com.util.QueryUtil;
import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-01-15 13:08
 */
@Slf4j
@Repository
@AllArgsConstructor
public class SysDictDaoImpl implements SysDictDao {

    private final DataSource dataSource;

    private final SQLQueryFactory sqlQueryFactory;

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @SneakyThrows
    @DataScope(type = DataScope.TypeEnum.SQL, variable = "#sysDictDTO.tableName")
    public List<SysDictVO> getSysDictList(SysDictDTO sysDictDTO) {
        String sysDictTableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, QSysDict.sysDict.getMetadata().getName());
        String tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, sysDictDTO.getTableName());
        String fieldNameColumn = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, sysDictDTO.getFieldName());
        String fieldName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, QSysDict.sysDict.fieldName.getMetadata().getName());
        String fieldValue = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, QSysDict.sysDict.fieldValue.getMetadata().getName());

        @Cleanup Connection connection = dataSource.getConnection();
        RelationalPath<?> sysDictTable = new RelationalPathBase<>(Object.class, sysDictTableName, connection.getCatalog(), sysDictTableName);
        RelationalPath<?> table = new RelationalPathBase<>(Object.class, tableName, connection.getCatalog(), tableName);
        PathBuilder<String> sysDict = new PathBuilder<>(String.class, sysDictTableName);
        PathBuilder<String> variable = new PathBuilder<>(String.class, tableName);

        Predicate predicate = variable.get(fieldNameColumn).eq(sysDict.get(fieldName));
        SQLQuery<SysDictVO> sqlQuery = sqlQueryFactory.select(Projections.bean(
                SysDictVO.class,
                variable.getNumber(fieldValue, Integer.class).as(fieldValue),
                sysDict.getString(QSysDict.sysDict.description.getMetadata().getName())
        ))
                .from(table)
                .leftJoin(sysDictTable)
                .on(predicate)
                .where(QueryUtil.getPredicate())
                .distinct();
        QueryUtil.remove();
        log.info(sqlQuery.getSQL().getSQL());
        return sqlQuery.fetch();
    }

    @Override
    public List<SysDictVO> getAllSysDictList(SysDictDTO sysDictDTO) {
        QSysDict sysDict = QSysDict.sysDict;
        Predicate predicate1 = sysDict.tableName.eq(sysDictDTO.getTableName());
        Predicate predicate2 = sysDict.fieldName.eq(sysDictDTO.getFieldName());
        return jpaQueryFactory.select(Projections.bean(
                SysDictVO.class,
                sysDict.fieldValue,
                sysDict.description
        ))
                .from(sysDict)
                .where(predicate1, predicate2)
                .fetch();
    }

}
