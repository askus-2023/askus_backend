package com.askus.askus.domain.board.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.board.domain.Category;
import com.askus.askus.domain.board.dto.BoardRequest;
import com.askus.askus.domain.board.dto.BoardResponse;
import com.askus.askus.domain.board.repository.BoardRepository;
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
	@Autowired
	private BoardRepository boardRepository;

	@Test
	void 게시글_등록_성공() throws IOException {
		// given
		Users users = new Users("email", "password", "nickname");
		Users savedUsers = usersRepository.save(users);

		String title = "title";
		String foodName = "food_name";
		Category category = Category.KOREAN;
		String ingredients = "ingredients";
		String content = "content";
		String tag = "tag";
		MockMultipartFile thumbnailImage = new MockMultipartFile(
			"thumbnailImage",
			"thumbnailImage.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));
		MockMultipartFile representativeImage1 = new MockMultipartFile(
			"representativeImage1",
			"representativeImage1.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));
		MockMultipartFile representativeImage2 = new MockMultipartFile(
			"representativeImage2",
			"representativeImage2.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));

		List<MultipartFile> representativeImages = new ArrayList<>();
		representativeImages.add(representativeImage1);
		representativeImages.add(representativeImage2);

		BoardRequest.Post request = new BoardRequest.Post(
			title,
			foodName,
			category,
			ingredients,
			content,
			tag,
			thumbnailImage,
			representativeImages
		);

		// when
		BoardResponse.Post response = boardService.addBoard(savedUsers.getId(), request);

		// then
		assertThat(response.getTitle()).isEqualTo(title);
		assertThat(response.getAuthor()).isEqualTo(savedUsers.getNickname());
		assertThat(response.getIngredients()).isEqualTo(ingredients);
		assertThat(response.getCategory()).isEqualTo(category);
		assertThat(response.getContent()).isEqualTo(content);
		assertThat(response.getTag()).isEqualTo(tag);
		assertThat(response.getThumbnailImageUrl()).isNotNull();
		assertThat(response.getRepresentativeImageUrls().size()).isEqualTo(2);
	}

	@Test
	void 게시글_전체조회_성공() throws IOException {
		// given - ready for users
		Users users1 = new Users("email1", "password1", "nickname1");
		Users users2 = new Users("email2", "password2", "nickname2");
		Users savedUsers1 = usersRepository.save(users1);
		Users savedUsers2 = usersRepository.save(users2);

		// given - ready for boards
		String title1 = "title1";
		String foodName1 = "food name1";
		Category category1 = Category.KOREAN;
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
		String foodName2 = "food name2";
		Category category2 = Category.ETC;
		String ingredients2 = "ingredients2";
		String content2 = "content2";
		String tag2 = "tag2";
		MultipartFile thumbnailImage2 = new MockMultipartFile(
			"thumbnailImage",
			"thumbnailImage.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));
		MultipartFile representativeImage2 = new MockMultipartFile(
			"representativeImage",
			"representativeImage.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));

		List<MultipartFile> representativeImages = new ArrayList<>();
		representativeImages.add(representativeImage1);
		representativeImages.add(representativeImage2);

		BoardRequest.Post request1 = new BoardRequest.Post(
			title1,
			foodName1,
			category1,
			ingredients1,
			content1,
			tag1,
			thumbnailImage1,
			representativeImages
		);

		BoardRequest.Post request2 = new BoardRequest.Post(
			title2,
			foodName2,
			category2,
			ingredients2,
			content2,
			tag2,
			thumbnailImage2,
			representativeImages
		);

		boardService.addBoard(savedUsers1.getId(), request1);
		boardService.addBoard(savedUsers2.getId(), request2);

		BoardRequest.Summary condition1 = new BoardRequest.Summary(null, "", "", "CREATED_AT_DESC");
		BoardRequest.Summary condition2 = new BoardRequest.Summary(tag1, "", "", "CREATED_AT_DESC");
		BoardRequest.Summary condition3 = new BoardRequest.Summary(null, "", "", "CREATED_AT_ASC");

		// when
		List<BoardResponse.Summary> responses1 = boardService.searchBoards(users1.getId(), condition1);
		List<BoardResponse.Summary> responses2 = boardService.searchBoards(users1.getId(), condition2);
		List<BoardResponse.Summary> responses3 = boardService.searchBoards(users1.getId(), condition3);

		// then
		assertThat(responses1.get(0)).isNotEqualTo(responses3.get(0));
	}

	@Test
	void 게시글_상세조회_성공() throws IOException {
		// given
		Users users = new Users("email", "password", "nickname");
		Users savedUsers = usersRepository.save(users);

		String title = "title";
		String foodName = "food name";
		Category category = Category.KOREAN;
		String ingredients = "ingredients";
		String content = "content";
		String tag = "tag";
		MockMultipartFile thumbnailImage = new MockMultipartFile(
			"thumbnailImage",
			"thumbnailImage.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));
		MockMultipartFile representativeImage1 = new MockMultipartFile(
			"representativeImage1",
			"representativeImage1.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));
		MockMultipartFile representativeImage2 = new MockMultipartFile(
			"representativeImage2",
			"representativeImage2.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));

		List<MultipartFile> representativeImages = new ArrayList<>();
		representativeImages.add(representativeImage1);
		representativeImages.add(representativeImage2);

		BoardRequest.Post request = new BoardRequest.Post(
			title,
			foodName,
			category,
			ingredients,
			content,
			tag,
			thumbnailImage,
			representativeImages
		);

		BoardResponse.Post board = boardService.addBoard(savedUsers.getId(), request);

		// when
		BoardResponse.Detail response = boardService.searchBoard(board.getBoardId());

		// then
		assertThat(response.getTitle()).isEqualTo(title);
		assertThat(response.getAuthor()).isEqualTo(savedUsers.getNickname());
		assertThat(response.getIngredients()).isEqualTo(ingredients);
		assertThat(response.getContent()).isEqualTo(content);
		assertThat(response.getCreatedAt()).isNotNull();
		assertThat(response.getTag()).isEqualTo(tag);
		assertThat(response.getReplies().size()).isEqualTo(0);
	}

	@Test
	void 게시글_수정_성공() throws IOException {
		// given
		Users users = new Users("email", "password", "nickname");
		Users savedUsers = usersRepository.save(users);

		String title = "title";
		String foodName = "food name";
		Category category = Category.KOREAN;
		String ingredients = "ingredients";
		String content = "content";
		String tag = "tag";
		MockMultipartFile thumbnailImage = new MockMultipartFile(
			"thumbnailImage",
			"thumbnailImage.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));
		MockMultipartFile representativeImage1 = new MockMultipartFile(
			"representativeImage1",
			"representativeImage1.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));
		MockMultipartFile representativeImage2 = new MockMultipartFile(
			"representativeImage2",
			"representativeImage2.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));

		List<MultipartFile> representativeImages = new ArrayList<>();
		representativeImages.add(representativeImage1);
		representativeImages.add(representativeImage2);

		BoardRequest.Post request = new BoardRequest.Post(
			title,
			foodName,
			category,
			ingredients,
			content,
			tag,
			thumbnailImage,
			representativeImages
		);

		BoardResponse.Post response = boardService.addBoard(savedUsers.getId(), request);

		String updatedTitle = "updatedTitle";
		String updatedFoodName = "updatedFoodName";
		Category updatedCategory = Category.ETC;
		String updatedIngredients = "updatedIngredients";
		String updatedContent = "updatedContent";
		String updatedTag = null;

		BoardRequest.Post updatedRequest = new BoardRequest.Post(
			updatedTitle,
			updatedFoodName,
			updatedCategory,
			updatedIngredients,
			updatedContent,
			updatedTag,
			null,
			null);

		// when & then
		assertThat(response.getTitle()).isEqualTo(title);
		assertThat(response.getAuthor()).isEqualTo(savedUsers.getNickname());
		assertThat(response.getIngredients()).isEqualTo(ingredients);
		assertThat(response.getCategory()).isEqualTo(category);
		assertThat(response.getContent()).isEqualTo(content);
		assertThat(response.getTag()).isEqualTo(tag);
		assertThat(response.getThumbnailImageUrl()).isNotNull();
		assertThat(response.getRepresentativeImageUrls().size()).isEqualTo(2);

		BoardResponse.Post updatedResponse = boardService.updateBoard(savedUsers.getId(), response.getBoardId(),
			updatedRequest);

		assertThat(updatedResponse.getTitle()).isEqualTo(updatedTitle);
		assertThat(updatedResponse.getCategory()).isEqualTo(Category.ETC);
		assertThat(updatedResponse.getIngredients()).isEqualTo(updatedIngredients);
		assertThat(updatedResponse.getContent()).isEqualTo(updatedContent);
		assertThat(updatedResponse.getTag()).isEqualTo(updatedTag);
		assertThat(updatedResponse.getThumbnailImageUrl()).isNull();
		assertThat(updatedResponse.getRepresentativeImageUrls().size()).isEqualTo(0);
	}

	@Test
	void 게시글_삭제_성공() throws IOException {
		Users users = new Users("email", "password", "nickname");
		Users savedUsers = usersRepository.save(users);

		String title = "title";
		String foodName = "food name";
		Category category = Category.KOREAN;
		String ingredients = "ingredients";
		String content = "content";
		String tag = "tag";
		MockMultipartFile thumbnailImage = new MockMultipartFile(
			"thumbnailImage",
			"thumbnailImage.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));
		MockMultipartFile representativeImage1 = new MockMultipartFile(
			"representativeImage1",
			"representativeImage1.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));
		MockMultipartFile representativeImage2 = new MockMultipartFile(
			"representativeImage2",
			"representativeImage2.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));

		List<MultipartFile> representativeImages = new ArrayList<>();
		representativeImages.add(representativeImage1);
		representativeImages.add(representativeImage2);

		BoardRequest.Post request = new BoardRequest.Post(
			title,
			foodName,
			category,
			ingredients,
			content,
			tag,
			thumbnailImage,
			representativeImages
		);

		BoardResponse.Post board = boardService.addBoard(savedUsers.getId(), request);
		Board findBoard = boardRepository.findById(board.getBoardId()).get();

		List<Long> boardIds = new ArrayList<>();
		boardIds.add(board.getBoardId());
		BoardRequest.Delete deleteRequest = new BoardRequest.Delete(boardIds);

		// when & then
		assertThat(findBoard.getDeletedAt()).isNull();

		boardService.deleteBoard(deleteRequest);
		Board afterDelete = boardRepository.findById(boardIds.get(0)).get();

		assertThat(afterDelete.getDeletedAt()).isNotNull();
	}
}
