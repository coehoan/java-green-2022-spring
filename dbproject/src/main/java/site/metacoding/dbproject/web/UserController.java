package site.metacoding.dbproject.web;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private HttpSession session;

    // @RequiredArgsConstructor
    // private final UserRepository userRepository

    public UserController(UserRepository userRepository, HttpSession session) {
        this.userRepository = userRepository;
        this.session = session;
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

        // null, 공백방지
        if (user.getUsername().trim().isEmpty() || user.getPassword().trim().isEmpty()
                || user.getEmail().trim().isEmpty()) {
            return "redirect:/joinForm";
        }

        // 비밀번호 12자 제한
        // 비밀번호 한글입력 방지
        // 이메일 형식
        String email = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern pattern = Pattern.compile(email);
        Matcher matcher = pattern.matcher(user.getEmail());
        System.out.println("매쳐 : " + matcher);
        System.out.println("유저 이메일 : " + user.getEmail());
        System.out.println("매쳐확인 : " + matcher.matches());

        if (matcher.matches() == false) {
            System.out.println("이메일 형식 오류");
            return "user/joinForm";
        }

        // 핵심 로직
        userRepository.save(user); // INSERT
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

    // http://localhost:8080/user/1
    // 유저상세 페이지(동적) - 로그인O
    @GetMapping("/user/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        User principal = (User) session.getAttribute("principal");

        // 인증 체크
        if (principal == null) {
            return "error/page1";
        }

        // 권한 체크
        if (principal.getId() != id) {
            return "error/page1";
        }

        // 핵심 로직
        Optional<User> userOp = userRepository.findById(id);

        if (userOp.isPresent()) {
            User userEntity = userOp.get();
            model.addAttribute("user", userEntity);
            return "user/detail";
        } else {
            return "error/page1";
        }
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
        session.invalidate(); // 세션 날리기
        return "redirect:/loginForm"; // PostController 만들고 수정
    }
}
