package com.askus.askus.domain.image.domain;

import com.askus.askus.domain.image.service.ImageUploader;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.List;

import static com.askus.askus.global.util.Preconditions.checkContains;

@Getter
public class Image {

    /* 허용되는 파일 확장자 목록 */
    private static final List<String> ACCEPTED_FILE_EXTENSIONS
            = List.of("png", "webp", "jpg", "jpeg", "gif", "bmp", "svg");

    private final ImageType imageType;
    private final InputStream inputStream;
    private final String extension;
    private final String originalFilename;

    public Image(ImageType imageType, InputStream inputStream, String originalFilename) {
        this.imageType = imageType;
        this.inputStream = inputStream;
        this.extension = checkAndGetExtension(originalFilename);
        this.originalFilename = originalFilename;
    }

    /* 파일명으로 부터 확장자 얻기 및 검증 */
    private String checkAndGetExtension(String filename) {
        String extension = StringUtils.substringAfterLast(filename, ".");
        checkContains(extension, ACCEPTED_FILE_EXTENSIONS, "잘못된 파일 형식");
        return extension;
    }

    /* 도메인 서비스 ImageUploader 를 활용해 이미지 업로드 수행 */
    public String uploadBy(ImageUploader imageUploader) {
        return imageUploader.upload(this);
    }
}
