package com.util;

import com.google.common.base.CaseFormat;
import com.model.entity.QUserIdEntity;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import org.apache.commons.lang3.StringUtils;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-11-13 05:33
 */
public class QueryUtil {

    private static final ThreadLocal<Predicate> predicateThreadLocal = new ThreadLocal<>();

    public static void setJPAPredicate(String userAlias, String deptAlias, String variable) {
        if (StringUtils.isNotBlank(variable)) {
            PathBuilder<String> user = new PathBuilder<>(String.class, variable);
            Predicate predicate = user.getNumber(QUserIdEntity.userIdEntity.userId.getMetadata().getName(), Long.class).eq(JwtUtil.getUserId());
            predicateThreadLocal.set(predicate);
        }
    }

    public static void setSQLPredicate(String userAlias, String deptAlias, String variable) {
        String userId = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, QUserIdEntity.userIdEntity.userId.getMetadata().getName());
        if (StringUtils.isNotBlank(variable)) {
            PathBuilder<String> user = new PathBuilder<>(String.class, variable);
            Predicate predicate = user.getNumber(userId, Long.class).eq(JwtUtil.getUserId());
            predicateThreadLocal.set(predicate);
        }
    }

    public static Predicate getPredicate() {
        return predicateThreadLocal.get();
    }

    public static void remove() {
        predicateThreadLocal.remove();
    }

}
