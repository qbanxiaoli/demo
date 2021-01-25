package com.aop;

import com.aop.annotation.DataScope;
import com.util.QueryUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class DataScopeAspect {

    @Around("@annotation(dataScope)")
    public Object doAround(ProceedingJoinPoint point, DataScope dataScope) throws Throwable {
        String variable = this.generateKeyBySpEL(dataScope.variable(), point);
        if (dataScope.type().equals(DataScope.TypeEnum.SQL)) {
            QueryUtil.setSQLPredicate(dataScope.userAlias(), dataScope.deptAlias(), variable);
        } else {
            QueryUtil.setJPAPredicate(dataScope.userAlias(), dataScope.deptAlias(), variable);
        }
        // 执行，获取结果
        return point.proceed();
    }

    // 解析SpEL表达式
    private String generateKeyBySpEL(String spELString, ProceedingJoinPoint point) {
        if (StringUtils.isBlank(spELString)) {
            return null;
        }
        // 通过joinPoint获取被注解方法
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();
        // 使用spring的DefaultParameterNameDiscoverer获取方法形参名数组
        String[] paraNameArr = nameDiscoverer.getParameterNames(method);
        SpelExpressionParser parser = new SpelExpressionParser();
        // 解析过后的Spring表达式对象
        Expression expression = parser.parseExpression(spELString);
        // spring的表达式上下文对象
        EvaluationContext context = new StandardEvaluationContext();
        // 通过joinPoint获取被注解方法的形参
        Object[] args = point.getArgs();
        if (paraNameArr != null) {
            // 给上下文赋值
            for (int i = 0; i < paraNameArr.length; i++) {
                context.setVariable(paraNameArr[i], args[i]);
            }
        }
        return expression.getValue(context, String.class);
    }

}
