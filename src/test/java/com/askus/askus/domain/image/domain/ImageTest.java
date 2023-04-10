package com.askus.askus.domain.image.domain;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ImageTest {

	@DisplayName("Image 생성 성공")
	@Test
	void name() throws IOException {
		//given
		byte[] byteArray = "test-image-byte".getBytes();
		String originalFilename = "image.png";
		InputStream inputStream = new ByteArrayInputStream(byteArray);
		ImageType imageType = ImageType.PROFILE;

		//when
		Image image = new Image(imageType, inputStream, originalFilename);

		//then
		assertThat(image.getImageType()).isEqualTo(imageType);
		assertThat(image.getInputStream().readAllBytes()).contains(byteArray);
	}

	@DisplayName("허용되지 않는 확장자를 가진 Image 생성 실패")
	@ParameterizedTest
	@ValueSource(strings = {"illegal_file_name.pdf", "file_with_no_extension", ".gitignore"})
	void name2(String illegalFilename) {
		//given
		InputStream inputStream = new ByteArrayInputStream("test-image-byte".getBytes());
		ImageType imageType = ImageType.PROFILE;

		//when then
		assertThatIllegalArgumentException().isThrownBy(() -> new Image(imageType, inputStream, illegalFilename));
	}
}
