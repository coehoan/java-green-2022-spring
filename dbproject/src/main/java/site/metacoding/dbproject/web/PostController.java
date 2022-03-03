package site.metacoding.dbproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class PostController {

    // 글쓰기 페이지 /post/writeForm
    @GetMapping("/post/writeForm")
    public String writeForm() {
        return "post/writeForm";
    }

    // 글쓰기
    @PostMapping("/post")
    public String write() {
        return "redirect:/";
    }

    // 메인페이지
    // 글목록 페이지 /post/list, / 주소가 2개
    @GetMapping({ "/", "/post/list" })
    public String list() {
        return "post/list";
    }

    // 글수정페이지
    @GetMapping("/post/{id}/updateForm")
    public String updateForm(@PathVariable Integer id) {
        return "post/updateForm";
    }

    // 글수정
    @PutMapping("/post/{id}")
    public String update(@PathVariable Integer id) {
        return "redirect:/post/" + id;
    }

    // 글상세보기 페이지 /post/{id} (삭제, 수정버튼 만들기)
    @GetMapping("/post/{id}")
    public String detail(@PathVariable Integer id) {
        return "post/detail";
    }

    // 글삭제
    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable Integer id) {
        return "redirect:/";
    }
}
