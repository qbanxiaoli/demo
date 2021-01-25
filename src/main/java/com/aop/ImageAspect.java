package com.aop;

import com.aop.annotation.ImageClass;
import com.dao.repository.FileRepository;
import com.model.entity.SysFileInfo;
import com.model.vo.PageVO;
import com.aop.annotation.Image;
import com.querydsl.core.QueryResults;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.util.FieldUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-22 16:35
 */
@Aspect
@Component
@AllArgsConstructor
public class ImageAspect {

    private final FileRepository fileRepository;

    @Around("@annotation(imageClass)")
    public Object doAround(ProceedingJoinPoint point, ImageClass imageClass) throws Throwable {
        // 执行，获取结果
        Object result = point.proceed();
        if (result instanceof QueryResults) {
            ((QueryResults<?>) result).getResults().forEach(this::parseImageUrl);
        } else if (result instanceof PageVO) {
            ((PageVO<?>) result).getResults().forEach(this::parseImageUrl);
        } else if (result instanceof List) {
            ((List<?>) result).forEach(this::parseImageUrl);
        } else {
            this.parseImageUrl(result);
        }
        return result;
    }

    // 文件地址转化
    @SneakyThrows
    private void parseImageUrl(Object result) {
        Field[] fields = result.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Image.class)) {
                Image image = field.getAnnotation(Image.class);
                Object object = FieldUtils.getFieldValue(result, image.value());
                if (object != null) {
                    Optional<SysFileInfo> optional = fileRepository.findById(Long.parseLong(object.toString()));
                    if (optional.isPresent()) {
                        // 开启私有属性访问权限
                        field.setAccessible(true);
                        // 文件路径转化
                        field.set(result, optional.get().getWebServerUrl().concat(optional.get().getStorePath()));
                    }
                }
            }
        }
    }

}
