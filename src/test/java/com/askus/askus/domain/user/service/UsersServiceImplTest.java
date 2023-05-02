package com.askus.askus.domain.user.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.io.FileInputStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.domain.users.dto.UsersRequest;
import com.askus.askus.domain.users.dto.UsersResponse;
import com.askus.askus.domain.users.repository.UsersRepository;
import com.askus.askus.domain.users.service.UsersServiceImpl;
import com.askus.askus.global.error.exception.KookleRuntimeException;

// @ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
class UsersServiceImplTest {
	@Autowired
	private UsersServiceImpl sut;
	@Autowired
	private UsersRepository usersRepository;

	@Test
	void 프로필_이메일_닉네임_이미지_수정_성공() throws Exception {
		// given
		String email = "email0417@email.com";
		String password = "password";
		String nickname = "nickname";
		MockMultipartFile profileImage = new MockMultipartFile(
			"profileImage",
			"profileImage.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));

		UsersRequest.SignUp signUpRequest = new UsersRequest.SignUp(
			email,
			password,
			password,
			nickname,
			profileImage
		);
		UsersResponse.SignUp signUpResponse = sut.signUp(signUpRequest);
		Users users = usersRepository.findByEmail(signUpResponse.getEmail()).get();

		String updateEmail = "updateEmail@email.com";
		String updateNickname = "updateNickname";

		UsersRequest.Patch request = new UsersRequest.Patch(updateNickname, null, true);

		// when
		UsersResponse.Patch response = sut.updateUsers(users.getId(), request);

		// then
		assertThat(response.getEmail()).isEqualTo(updateEmail);
		assertThat(response.getNickname()).isEqualTo(updateNickname);
	}

	@Test
	void 프로필_비밀번호_수정() throws Exception {
		// given
		String email = "email0417@email.com";
		String password = "password";
		String nickname = "nickname";
		MockMultipartFile profileImage = new MockMultipartFile(
			"profileImage",
			"profileImage.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));

		UsersRequest.SignUp signUpRequest = new UsersRequest.SignUp(
			email,
			password,
			password,
			nickname,
			profileImage
		);
		UsersResponse.SignUp signUpResponse = sut.signUp(signUpRequest);
		Users users = usersRepository.findByEmail(signUpResponse.getEmail()).get();
		String existingPassword = users.getPassword();

		String updatePassword = "updatePassword";
		UsersRequest.PatchPassword request = new UsersRequest.PatchPassword(
			password,
			updatePassword,
			updatePassword
		);

		// when
		sut.updatePassword(users.getId(), request);
		Users updatedUsers = usersRepository.findById(users.getId()).get();

		// then
		assertThat(updatedUsers.getPassword()).isNotEqualTo(existingPassword);
	}

	@Test
	void 프로필_기존비밀번호_일치X() throws Exception {
		// given
		String email = "email0417@email.com";
		String password = "password";
		String nickname = "nickname";
		MockMultipartFile profileImage = new MockMultipartFile(
			"profileImage",
			"profileImage.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/askus/src/test/resources/store/images/image.png"));

		UsersRequest.SignUp signUpRequest = new UsersRequest.SignUp(
			email,
			password,
			password,
			nickname,
			profileImage
		);
		UsersResponse.SignUp signUpResponse = sut.signUp(signUpRequest);
		Users users = usersRepository.findByEmail(signUpResponse.getEmail()).get();

		String updatePassword = "updatePassword";
		UsersRequest.PatchPassword request = new UsersRequest.PatchPassword(
			"differentPassword",
			updatePassword,
			updatePassword
		);

		// when & then
		assertThatExceptionOfType(KookleRuntimeException.class)
			.isThrownBy(() -> sut.updatePassword(users.getId(), request));
	}

	//
	// @Mock
	// private UsersRepository usersRepository;
	// @Mock
	// private PasswordEncoder passwordEncoder;
	//
	// @InjectMocks
	// private UsersServiceImpl userService;
	//
	// @Test
	// void 기본_회원가입(){
	//     // given
	//     SignUpRequest requestDto = new SignUpRequest("nickname", "password", "password", "email");
	//     SignUpRequest requestDto = SignUpRequest.builder()
	//             .nickname("nickname")
	//             .password("password")
	//             .checkedPassword("password")
	//             .email("email")
	//             .build();
	//     Users users = requestDto.toEntity();
	//     given(usersRepository.save(any(Users.class))).willReturn(users);
	//
	//     // when
	//     try {
	//         SignUpResponse responseDto = userService.signUp(requestDto);
	//
	//         // then
	//         Assertions.assertThat(responseDto.getEmail()).isEqualTo(requestDto.getEmail());
	//         Assertions.assertThat(responseDto.getNickname()).isEqualTo(requestDto.getNickname());
	//     } catch (Exception e) {
	//         fail();
	//     }
	// }
	//
	// @Test
	// void 중복_회원가입() {
	//     // given
	//     SignUpRequest requestDto = SignUpRequest.builder()
	//             .nickname("nickname")
	//             .password("password")
	//             .checkedPassword("password")
	//             .email("email")
	//             .build();
	//     given(usersRepository.findByEmail("email")).willReturn(Optional.of(Users.builder()
	//             .nickname("nickname")
	//             .password("password")
	//             .email("email")
	//             .build()));
	//
	//     // when
	//     Assertions.assertThatThrownBy(() -> userService.signUp(requestDto))
	//             .isInstanceOf(Exception.class)
	//             .hasMessageContaining("이메일");
	// }
	//
	// @Test
	// void 가입시_비밀번호_다름() {
	//     // given
	//     SignUpRequest requestDto = SignUpRequest.builder()
	//             .nickname("nickname")
	//             .password("password")
	//             .checkedPassword("password123")
	//             .email("email")
	//             .build();
	//
	//     // when
	//     Assertions.assertThatThrownBy(() -> userService.signUp(requestDto))
	//             .isInstanceOf(Exception.class)
	//             .hasMessageContaining("비밀번호");
	// }
}
