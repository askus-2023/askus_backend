package com.askus.askus.infra.image.upload;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

import com.askus.askus.domain.image.domain.Image;

import io.findify.s3mock.S3Mock;

@SpringBootTest
@Import(S3MockConfig.class)
class S3ImageUploaderTest {
	@Autowired
	private S3Mock s3Mock;
	@Autowired
	private S3ImageUploader s3ImageUploader;

	@AfterEach
	public void tearDown() {
		s3Mock.stop();
	}

	@Test
	void upload() throws IOException {
		//given
		String filename = "image.png";
		ClassPathResource testImage = new ClassPathResource("store/images/" + filename);
		assertThat(testImage.exists()).isTrue();

		Image image = new Image(testImage.getInputStream(), testImage.getFilename());

		//when
		String imageUrl = s3ImageUploader.upload(image);

		//then
		assertThat(imageUrl).isNotNull();
	}
}
