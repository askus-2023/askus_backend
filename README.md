# cookle_backend
[ Cookle ] 데이터 서버 🧐

## 📝 Documentation

### 1. Project Structure

1.1 Overview
```java
├─.github
│  └─workflows    # github action for CI/CD
└─src             
│   ├─main
│   │  ├─java     # a root of production code in java
│   │  │  └─com.askus.askus
│   │  │     ├─configuration
│   │  │     ├─domain
│   │  │     │  ├─board
│   │  │     │  ├─chat
│   │  │     │  ├─model # a common model (BaseEntity) for domain 
│   │  │     │  ├─image
│   │  │     │  ├─like
│   │  │     │	├─reply
│   │  │     │  └─users
│   │  │     ├─global
│   │  │     └─infra
│   │  └─resources # a default image for profile
│   └─test
│       └─java
│           └─com.askus.askus
│              ├─domain
│              │  ├─board
│              │  ├─image
│              │  ├─like
│              │  ├─reply
│              │  └─users
│              ├─global
│              └─infra
│
└─ build.gradle.yml # we use gradle for build tool
```
- 패키지 구조는 [패키지구조 레퍼런스](https://cheese10yun.github.io/spring-guide-directory/#-7)를 참고하여 상황에 맞게 커스텀하여 사용하였다.
- `도메인 주도 설계(DDD)`를 통해 객체지향 프로그래밍을 하였다.
<br>

1.2 Domain
- [도메인 구조 설계](https://ko.wikipedia.org/wiki/%EB%8F%84%EB%A9%94%EC%9D%B8_%EC%A3%BC%EB%8F%84_%EC%84%A4%EA%B3%84)의 도메인을 의미한다.
    - `도메인`= 소프트웨어로 해결하고자 하는 문제영역 (이라는 추상적인 의미를 가진다).
- 각 도메인의 패키지의 하위 패키지구조.
```java
└── users   # aggregate root
  ├── controller  
  ├── service     # 응용 서비스 계층 - 비즈니스 로직 수행
  ├── repository  # 레포지토리 - 도메인 엔티티 영속화
  ├── domain      # 도메인 - 도메인 엔티티 / 도메인 서비스 핵심 로직 수행
  ├── dto         # 요청/응답 DTO, 컨트롤러/서비스 계층의 데이터 전달 모델
  └── exception   # 도메인 전용 예외 (e.g. LoginFailureException)
```
- 도메인의 핵심은 가능한 POJO를 유지하는 것 스프링을 사용함에 따른 몇몇 의존성(Spring data jpa, querydsl, Spring Security)은 허용한다.
<br>

1.3 Controller
- 역할 - 외부로 부터 요청을 받아 `응용 서비스`에게 처리를 위임, 응답을 받아 외부로 반환한다.
- `swagger-ui`를 활용한 api-documentation 생성을 위한 endpoint 정보를 제공 한다.
<br>

1.4 (application) service
- 역할 - 비즈니스 로직 처리/ 비즈니스 예외 검증
- 외부 시스템을 활용하는 경우 도메인 서비스/ 응용 서비스에 정의된 인터페이스를 이용해야 한다.
- 느슨한 결합(OCP)을 위해 인터페이스를 활용한다.
<br>

1.5 repository
- 역할 - 도메인 엔티티에 대한 CRUD 및 Join/ 필터링 등 간단한 처리를 적용한 조회 기능 제공
- `spring-data`, `querydsl` 에 대한 의존을 허용한다.
- 느슨한 결합(OCP)을 위해 `인터페이스`를 활용한다.
- 동일한 애그리거트이며 라이프사이클이 거의 유사한 도메인에 관해서는 동일한 영속화 사이클을 가지도록 한다.
<br>

1.6 domain
- 역할- 가장 core 한 도메인 영역`entity`는 JPA의 `@Entity` 의 역할도 수행한다.
- 많은 비즈니스 로직을 포함할 수 있도록 설계하면 좋다.
<br>

1.7 dto
- 역할 - 컨트롤러/서비스 계층의 매핑 모델
- 개발의 편의를 위해 두 계층에서 같은 모델을 활용한다.
<br>

1.8 exception
- 역할 - 도메인 전용 예외
- 정말 필요한지 고민해보고 도입하는 것을 추천한다.
- 대부분의 경우 예외 메시지로 예외의 의미를 전달하기 충분한 경우가 많다.
- global exception handler에 의해 다르게 처리가 될 필요가 있는경우에만 도입하자.
- 구조적 complexity와 전용 클래스가 가지는 의미에 대한 trade-off 에대한 고민은 항상 중요하다!
<br>

1.9 document
[아키텍처 및 패키지 구조 관련 정리](https://hgene.notion.site/456373c3576945c4a7555703c8eae572)
<br><br>

### 2. Code Convention
- 기본적으로 naver-hackday-convention을 활용한다.
<br>

2.1 commons
- dto 이외에서 `final` 키워드는 활용하지 않는다.
- 애너테이션의 순서는 그 역할이 중요한 것이 아래로 가도록 한다. 롬복 애너테이션은 위로`@Entity`, `@RestController`, `@Service` 와 같이 클래스의 핵심적인 역할을 드러내는 애너테이션은 아래로.
<br>

2.2 comments
- 코드의 흐름을 나타내기 위해 `// 1. find profile` 과 같이 수행 동작을 순서와 함께 주석을 남긴다.
- 클래스/인터페이스의 위에는 `javadoc` 스타일의 주석을 남긴다.html 태그 미활용.
- 컨트롤러의 매핑 메서드에는 `swagger` 애너테이션으로 충분하므로 주석을 남기지 않는다.
- Service의 경우 인터페이스에 메서드의 역할을 javadoc 스타일의 주석을 남긴다.필요하다면 `@param`, `@return` 등의 키워드 활용
- 테스트 코드에는 bdd 스타일의 `// given // when //then` 주석 및 테스트 흐름 이해를 돕기 위한 주석을 포함한다. 예외를 검증하는 경우 `// when then` 을 한번에 작성한다.
<br>

### 3. Exception Handling
- `RestControllerAdvice`로 구현체를 생성하여 스프링이 제공하는 `HandlerExceptionResolver`을 사용하여 전역적으로 예외처리를 할 수 있도록 한다.
- `MessageSourceAccesor`을 도입하여 예외 메시지를 고도화한다.
<br>

3.1 overview
```java
└── error   # aggregate root
	├── exception #프로젝트의 예외의 타입 패키지
  ├── errorResponse # 예외 응답 포맷
  └── globalExceptionHandler # 전역적인 예외처리 핸들러
```
<br>

3.2 Exception Types
- `KookleRuntimeException`: 최상위 예외 타입. 프로젝트에서 발생하는 모든 예외(외부 예외 제외)의 타입으로 사용한다.
    - `BadRequestException`: 프로젝트의 모든 bad-request의 상위 타입이 된다.
        - `AlreadyExistException`: 이미 존재하는 엔티티 생성시도시 발생.
        - `FileException`: 입출력 데이터 관련 예외 타입.
        - `MissMatchException`: 불일치하는 데이터 비교시 발생.
        - `NotFoundException`: 존재하지 않는 데이터 조회시 발생.
<br>

3.3 document
[글로벌 예외 처리 관련 정리](https://hgene.notion.site/ce085861c5e34a47a93569d6e2951084)
<br><br>

### 4. Git / Github
4.1 branch strategy
- main - trigger for the `.workflow(CD)`
- dev
- feat/xx
- refactor/xx
- branch protection on `dev` - requires at least `one approve`
<br>

4.2 commit message
- [feat/refactor/fix] free message
<br>

### Authorization/Authentication Flow
<br>

### CI/CD Workflow
<br>

