package site.metacoding.first;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Data를 리턴하는 컨트롤러(PrintWriter로 리턴한다)
@RestController
public class UserController {

    // DI - IoC 컨테이너에 Dog가 있기 때문에 생성자에서 받을 수 있다.
    // Dependency Injection - 의존성 주입 (컴포지션을 알아서 넣어줌)
    public UserController(Dog dog) {
        System.out.println("UserController 생성자 실행");
        System.out.println(dog.getName());
    }

    @GetMapping("/home")
    public void home(Dog dog) {
        System.out.println("home~~~~~~~~~~~");
        System.out.println(dog.getName());
    }

    @GetMapping("/bye")
    public void bye() {
        System.out.println("bye~~~~~~~~~~~~");
    }

    @GetMapping("/data")
    public String hi() {
        return "<h1>안녕!</h1>";
    }
}