package site.metacoding.second.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    // SELECT * FROM post WHERE id = ?
    // 구체적으로 요청 - body 없음
    @GetMapping("/post/{id}")
    public String test1(@PathVariable int id) {
        return "주세요 id : " + id;
    }

    // SELECT * FROM post WHERE title = ?
    @GetMapping("/post")
    public String search(String title) {
        return "주세요 title : " + title;
    }

    // http://localhost:8000/post
    // body : title=제목1&content=내용1
    // header : Content-Type : application/x-www-form-urlencoded
    // body 있음
    // request.getParameter() 메서드가 스프링 기본 파싱 전략
    @PostMapping("/post")
    public String test2(String title, String content) {
        return "줄게요 : title : " + title + " content : " + content;
    }

    // UPDATE post SET title = ?, content = ? WHERE id = ?
    // title,content (PK - id)
    // body 있음
    // API 문서 만들어야함
    @PutMapping("/post/{id}")
    public String test3(String title, String content, @PathVariable int id) {
        return "수정해주세요 : title : " + title + ", content : " + content + ", id : " + id;
    }

    // http://localhost:8000/post?title=제목1
    // DELETE from post WHERE title = ?
    // http://localhost:8000/post/1
    // DELETE from post WHERE id = ?
    // 구체적으로 요청 - body 없음
    @DeleteMapping("/post/{id}")
    public String test4(@PathVariable int id) {
        return "삭제해주세요 : id : " + id;
    }
}
