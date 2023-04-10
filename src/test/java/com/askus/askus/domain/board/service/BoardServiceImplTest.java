package com.askus.askus.domain.board.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import com.askus.askus.domain.board.domain.Category;
import com.askus.askus.domain.board.dto.BoardAddRequest;
import com.askus.askus.domain.board.dto.BoardAddResponse;
import com.askus.askus.domain.board.dto.BoardsSearchCondition;
import com.askus.askus.domain.board.dto.BoardsSearchResponse;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.domain.users.repository.UsersRepository;
import com.askus.askus.global.config.TestConfig;

@Import({TestConfig.class})
@SpringBootTest
@Transactional
class BoardServiceImplTest {
	@Autowired
	private BoardService boardService;
	@Autowired
	private UsersRepository usersRepository;

	@Test
	void 게시글_등록_성공() throws IOException {
		// given
		Users users = new Users("email", "password", "nickname");
		Users savedUsers = usersRepository.save(users);

		String title = "title";
		String category = "KOREAN";
		String ingredients = "ingredients";
		String content = "content";
		String tag = "tag";
		MockMultipartFile thumbnailImage = new MockMultipartFile(
			"thumbnailImage",
			"thumbnailImage.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));
		MockMultipartFile representativeImage = new MockMultipartFile(
			"representativeImage",
			"representativeImage.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));

		BoardAddRequest request = new BoardAddRequest(
			title,
			category,
			ingredients,
			content,
			tag,
			thumbnailImage,
			representativeImage
		);

		// when
		BoardAddResponse response = boardService.addBoard(savedUsers.getId(), request);

		// then
		assertThat(response.getTitle()).isEqualTo(title);
		assertThat(response.getNickname()).isEqualTo(savedUsers.getNickname());
		assertThat(response.getIngredients()).isEqualTo(ingredients);
		assertThat(response.getCategory()).isEqualTo(Category.valueOf(category));
		assertThat(response.getContent()).isEqualTo(content);
		assertThat(response.getTag()).isEqualTo(tag);
		assertThat(response.getThumbnailImageUrl()).isNotNull();
		assertThat(response.getRepresentativeImageUrl()).isNotNull();
	}

	@Test
	void 게시글_전체조회_성공() throws IOException {
		// given
		Users users1 = new Users("email1", "password1", "nickname1");
		Users users2 = new Users("email2", "password2", "nickname2");
		Users savedUsers1 = usersRepository.save(users1);
		Users savedUsers2 = usersRepository.save(users2);

		String title1 = "title1";
		String category1 = "KOREAN";
		String ingredients1 = "ingredients1";
		String content1 = "content1";
		String tag1 = "tag1";
		MockMultipartFile thumbnailImage1 = new MockMultipartFile(
			"thumbnailImage",
			"thumbnailImage.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));
		MockMultipartFile representativeImage1 = new MockMultipartFile(
			"representativeImage",
			"representativeImage.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));

		String title2 = "title2";
		String category2 = "ETC";
		String ingredients2 = "ingredients2";
		String content2 = "content2";
		String tag2 = "tag2";
		MockMultipartFile thumbnailImage2 = new MockMultipartFile(
			"thumbnailImage",
			"thumbnailImage.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));
		MockMultipartFile representativeImage2 = new MockMultipartFile(
			"representativeImage",
			"representativeImage.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));

		BoardAddRequest request1 = new BoardAddRequest(
			title1,
			category1,
			ingredients1,
			content1,
			tag1,
			thumbnailImage1,
			representativeImage1
		);

		BoardAddRequest request2 = new BoardAddRequest(
			title2,
			category2,
			ingredients2,
			content2,
			tag2,
			thumbnailImage2,
			representativeImage2
		);

		boardService.addBoard(savedUsers1.getId(), request1);
		boardService.addBoard(savedUsers2.getId(), request2);

		BoardsSearchCondition condition1 = new BoardsSearchCondition(null, null, null, "CREATED_AT_DESC");
		BoardsSearchCondition condition2 = new BoardsSearchCondition(tag1, null, null, "CREATED_AT_DESC");
		BoardsSearchCondition condition3 = new BoardsSearchCondition(null, null, null, "CREATED_AT_ASC");

		// when
		List<BoardsSearchResponse> responses1 = boardService.searchBoards(condition1);
		List<BoardsSearchResponse> responses2 = boardService.searchBoards(condition2);
		List<BoardsSearchResponse> responses3 = boardService.searchBoards(condition3);

		// then
		assertThat(responses1.size()).isEqualTo(2);
		assertThat(responses2.size()).isEqualTo(1);
		assertThat(responses1.get(0)).isNotEqualTo(responses3.get(0));
	}
}
