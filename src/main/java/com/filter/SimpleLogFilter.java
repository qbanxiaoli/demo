package com.filter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import lombok.extern.slf4j.Slf4j;

/**
 * @author terminus
 * @description
 * @create 2019/10/31 4:29 下午
 */
@Slf4j
public class SimpleLogFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (event.getLevel() == Level.WARN) {
            switch (event.getLoggerName()) {
                case "org.mybatis.spring.mapper.ClassPathMapperScanner":
                case "org.springframework.boot.StartupInfoLogger":
                    return FilterReply.DENY;
            }
        }
        return FilterReply.ACCEPT;
    }

}