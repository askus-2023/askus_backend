package com.askus.askus.domain.image.domain;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ImageTest {

	@Test
	void Image_생성_성공() throws IOException {
		// given
		byte[] byteArray = "test-image-byte".getBytes();
		String originalFilename = "image.png";
		InputStream inputStream = new ByteArrayInputStream(byteArray);

		// when
		Image image = new Image(inputStream, originalFilename);

		// then
		assertThat(image.getInputStream().readAllBytes()).contains(byteArray);
	}

	@ParameterizedTest
	@ValueSource(strings = {"illegal_file_name.pdf", "file_with_no_extension", ".gitignore"})
	void 허용되지_않는_확장자_Image_생성_실패(String illegalFilename) {
		//given
		InputStream inputStream = new ByteArrayInputStream("test-image-byte".getBytes());

		//when then
		assertThatIllegalArgumentException().isThrownBy(() -> new Image(inputStream, illegalFilename));
	}
}
