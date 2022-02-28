package site.metacoding.second.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecondController {

    // 데이터 요청(read, select)
    @GetMapping("/user")
    public String test1() {
        return "<h1>test1<h1/>";
    }

    // 데이터 전송(create, insert)
    @PostMapping("/user")
    public String test2() {
        return "<h1>test2<h1/>";
    }

    // 데이터 수정(update, update)
    @PutMapping("/user")
    public String test3() {
        return "<h1>test3<h1/>";
    }

    // 데이터 삭제(delete, delete)
    @DeleteMapping("/user")
    public String test4() {
        return "<h1>test4<h1/>";
    }
}
