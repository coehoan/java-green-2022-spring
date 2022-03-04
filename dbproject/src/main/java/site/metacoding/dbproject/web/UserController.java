package site.metacoding.dbproject.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import site.metacoding.dbproject.domain.user.User;
import site.metacoding.dbproject.domain.user.UserRepository;

@Controller
public class UserController {

    // DI를 통해 의존관계 형성
    private UserRepository userRepository;

    // @RequiredArgsConstructor
    // private final UserRepository userRepository

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입 페이지(정적) - 로그인X
    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    // username=ssar&password=1234&email=ssar@naver.com (x-www-form)
    // 회원가입 - 로그인X
    @PostMapping("/join")
    public String join(User user) {
        // System.out.println("user : " + user);
        userRepository.save(user);
        // User userEntity = user;
        // System.out.println("userEntity : " + userEntity);
        // redirect:매핑주소
        return "redirect:/loginForm"; // 로그인페이지로 이동해주는 컨트롤러 메서드를 재활용
    }

    // 로그인 페이지(정적) - 로그인X
    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    // 로그인 - 로그인X
    // SELECT * FROM user WHERE username = ? AND password = ?
    // SELECT는 무조건 get이지만 로그인은 예외 (POST)
    // 이유 - 주소에 패스워드를 남길 수 없기 때문
    @PostMapping("/login")
    public String login(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        User userEntity = userRepository.mLogin(user.getUsername(), user.getPassword());
        if (userEntity == null) {
            System.out.println("아이디 혹은 패스워드가 틀렸습니다");
        } else {
            System.out.println("로그인 되었습니다.");
            session.setAttribute("principal", userEntity);
        }
        // 1. DB 연결해서 username,password 있는지 확인
        // 2. 있으면 session 영역에 인증됨 이라고 메세지를 보내자
        return "redirect:/"; // PostController 만들고 수정
    }

    // 유저상세 페이지(동적) - 로그인O
    @GetMapping("/user/{id}")
    public String detail(@PathVariable Integer id) {
        return "user/detail";
    }

    // 유저정보수정 페이지(동적) - 로그인O
    @GetMapping("/user/{id}/updateForm")
    public String updateForm(@PathVariable Integer id) {
        return "user/updateForm";
    }

    // 유저정보수정 - 로그인O
    @PutMapping("/user/{id}")
    public String update(@PathVariable Integer id) {
        return "redirect:/user/" + id;
    }

    // 로그아웃 - 로그인O
    @GetMapping("/logout")
    public String logout() {
        return "메인페이지"; // PostController 만들고 수정
    }
}
