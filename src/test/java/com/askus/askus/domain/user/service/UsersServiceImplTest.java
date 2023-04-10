package com.askus.askus.domain.user.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UsersServiceImplTest {
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
