package com.askus.askus.domain.user.service;

import com.askus.askus.domain.users.dto.UsersRequest;
import com.askus.askus.domain.users.dto.UsersResponse;
import com.askus.askus.domain.users.repository.UsersRepository;
import com.askus.askus.domain.users.service.UsersService;
import com.askus.askus.global.config.TestConfig;
import com.askus.askus.global.error.exception.KookleRuntimeException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;

@Import({TestConfig.class})
@SpringBootTest
@Transactional
class UsersServiceImplTest {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UsersService usersService;

    private String email;
    private String password;
    private String checkedPassword;
    private String nickname;
    private MultipartFile profileImage;

    @BeforeEach
    void init() {
        email = "test@gmail.com";
        password = "password";
        checkedPassword = "password";
        nickname = "nickname";
        profileImage = null;
    }

    @Test
    void 회원가입_프로필O() throws Exception {
        // given
        profileImage = new MockMultipartFile(
                "profileImage",
                "profileImage.png",
                "image/png",
                new FileInputStream("/Users/kimhandong/repository/cookle/cookle_backend/src/test/resources/store/images/image.png"));
        UsersRequest.SignUp request = new UsersRequest.SignUp(
                email,
                password,
                checkedPassword,
                nickname,
                profileImage);

        // when
        UsersResponse.SignUp response = usersService.signUp(request);

        // then
        assertThat(response.getEmail()).isEqualTo(email);
    }

    @Test
    void 회원가입_프로필X() throws Exception {
        // given
        profileImage = null;
        UsersRequest.SignUp request = new UsersRequest.SignUp(
                email,
                password,
                checkedPassword,
                nickname,
                profileImage);

        // when
        UsersResponse.SignUp response = usersService.signUp(request);

        // then
        assertThat(response.getEmail()).isEqualTo(email);
    }

    @Test
    void 이메일_중복확인() throws Exception {
        // given
        UsersRequest.SignUp request = new UsersRequest.SignUp(
                email,
                password,
                checkedPassword,
                nickname,
                profileImage);
        usersService.signUp(request);

        // when
        UsersResponse.DupEmail dupEmail = usersService.isDupEmail(email);

        // then
        assertThat(dupEmail.isDuplicated()).isTrue();
    }

    @Test
    void 중복_회원가입() throws Exception {
        // given
        UsersRequest.SignUp request = new UsersRequest.SignUp(
                email,
                password,
                checkedPassword,
                nickname,
                profileImage);

        // when
        usersService.signUp(request);

        // then
        Assertions.assertThatThrownBy(() -> usersService.signUp(request))
                .isInstanceOf(KookleRuntimeException.class)
                .hasMessageContaining("이메일");
    }

	 @Test
	 void 가입시_비밀번호_다름() {
         // given
         password = "password1";
         checkedPassword = "password2";
         UsersRequest.SignUp request = new UsersRequest.SignUp(
                 email,
                 password,
                 checkedPassword,
                 nickname,
                 profileImage);

	     // then
	     Assertions.assertThatThrownBy(() -> usersService.signUp(request))
	             .isInstanceOf(KookleRuntimeException.class)
	             .hasMessageContaining("비밀번호");
	 }

//     @Test
//     void 로그인_성공() throws Exception {
//         // given
//         UsersRequest.SignUp request = new UsersRequest.SignUp(
//                 email,
//                 password,
//                 checkedPassword,
//                 nickname,
//                 profileImage);
//         usersService.signUp(request);
//
//         // when
//         UsersRequest.SignIn  reqeust = new UsersRequest.SignIn(email, password);
//         UsersResponse.SignIn response = usersService.signIn(reqeust);
//
//         // then
//         assertThat(response.getEmail()).isEqualTo(email);
//         assertThat(response.getNickname()).isEqualTo(nickname);
//         assertThat(response.getImageUrl()).isNotNull();
//         assertThat(response.getAccessToken()).isNotNull();
//         assertThat(response.getRefreshToken()).isNotNull();
//     }
}
