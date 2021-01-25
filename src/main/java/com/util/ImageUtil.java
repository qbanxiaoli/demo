package com.util;

import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.model.dto.SysFileInfoFromDTO;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

/**
 * @author terminus
 * @description
 * @create 2019/11/26 9:57 下午
 */
@Slf4j
@Component
public class ImageUtil {

    private static ThumbImageConfig thumbImageConfig;

    public ImageUtil(ThumbImageConfig thumbImageConfig) {
        ImageUtil.thumbImageConfig = thumbImageConfig;
    }

    /**
     * @param multipartFile 上传的图片
     * @return true or false
     * @author qbanxiaoli
     * @description 判断上传的文件类型是否图片
     */
    public static Boolean isImage(MultipartFile multipartFile) {
        if (multipartFile.getContentType() != null) {
            return multipartFile.getContentType().startsWith("image");
        }
        return false;
    }

    /**
     * @param multipartFile 上传的图片
     * @param sysFileInfoFromDTO   图片的属性
     * @return byte数组
     * @author qbanxiaoli
     * @description 压缩图片
     */
    @SneakyThrows
    public static byte[] compressImage(MultipartFile multipartFile, SysFileInfoFromDTO sysFileInfoFromDTO) {
        if (sysFileInfoFromDTO.getWidth() == null) {
            // 使用配置文件中默认的宽度
            sysFileInfoFromDTO.setWidth(thumbImageConfig.getWidth());
        }
        if (sysFileInfoFromDTO.getHeight() == null) {
            // 使用配置文件中默认的高度
            sysFileInfoFromDTO.setHeight(thumbImageConfig.getHeight());
        }
        log.info("上传图片大小：{}B", multipartFile.getSize());
        @Cleanup ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Thumbnails.of(multipartFile.getInputStream())
                .size(sysFileInfoFromDTO.getWidth(), sysFileInfoFromDTO.getHeight())
                .outputQuality(0.5f)//图片质量
                .toOutputStream(byteArrayOutputStream);
        log.info("压缩后图片大小：{}B", byteArrayOutputStream.toByteArray().length);
        return byteArrayOutputStream.toByteArray();
    }

}
