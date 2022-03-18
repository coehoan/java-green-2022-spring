package site.metacoding.dbproject.web;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.dbproject.domain.post.Post;
import site.metacoding.dbproject.domain.user.User;
import site.metacoding.dbproject.service.PostService;
import site.metacoding.dbproject.web.dto.ResponseDto;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final HttpSession session;
    private final PostService postService;

    // 글쓰기 페이지 /post/writeForm - 인증 O
    @GetMapping("/s/post/writeForm")
    public String writeForm() {

        if (session.getAttribute("principal") == null) {
            return "redirect:/loginForm";
        }

        return "post/writeForm";
    }

    // 이사
    // 글쓰기 - 인증 O
    @PostMapping("/s/post")
    public String write(Post post) {

        if (session.getAttribute("principal") == null) {
            return "redirect:/loginForm";
        }

        User principal = (User) session.getAttribute("principal");
        postService.글쓰기(post, principal);

        return "redirect:/";
    }

    // 이사
    // 메인페이지 - 인증 X
    // 글목록 페이지 /post/list, / 주소가 2개
    @GetMapping({ "/", "/post/list" })
    public String list(@RequestParam(defaultValue = "0") Integer page, Model model) {

        Page<Post> pagePosts = postService.글목록보기(page);

        model.addAttribute("posts", pagePosts);
        model.addAttribute("prevPage", page - 1);
        model.addAttribute("nextPage", page + 1);
        return "post/list";
    }

    // @GetMapping("test/post/list")
    // public @ResponseBody Page<Post> listTest(@RequestParam(defaultValue = "0")
    // Integer page) {
    // PageRequest pq = PageRequest.of(page, 3);
    // return postRepository.findAll(pq);
    // }

    // 글수정페이지 - 인증 O
    @GetMapping("/s/post/{id}/updateForm")
    public String updateForm(@PathVariable Integer id) {
        return "post/updateForm";
    }

    // 글수정 - 인증 O
    @PutMapping("/s/post/{id}")
    public String update(@PathVariable Integer id) {
        return "redirect:/post/" + id;
    }

    // 이사
    // 글상세보기 페이지 /post/{id} (삭제, 수정버튼 만들기) - 인증 X
    @GetMapping("/post/{id}") // Get요청에 /post 제외 시키기
    public String detail(@PathVariable Integer id, Model model) {

        User principal = (User) session.getAttribute("principal");

        Post postEntity = postService.글상세보기(id);

        // 게시물이 없으면 error페이지 이동
        if (postEntity == null) {
            return "error/page1";
        }

        if (principal != null) {
            // 권한 확인해서 view로 값 넘김
            if (principal.getId() == postEntity.getUser().getId()) { // 권한이 있음
                model.addAttribute("pageOwner", true);
            } else
                model.addAttribute("pageOwner", false);
        }

        model.addAttribute("post", postEntity);
        return "post/detail";

    }

    // 글삭제 - 인증 O
    @DeleteMapping("/s/post/{id}")
    public @ResponseBody ResponseDto<String> delete(@PathVariable Integer id) {

        User principal = (User) session.getAttribute("principal");

        if (principal == null) { // 로그인정보 없음
            return new ResponseDto<String>(-1, "로그인이 되지 않았습니다.", null);
        }

        Post postEntity = postService.글상세보기(id);

        if (principal.getId() != (postEntity.getId())) { // 권한이 없음
            return new ResponseDto<String>(-1, "해당 글 삭제 권한이 없습니다.", null);
        }

        postService.글삭제하기(id); // 내부적으로 Exception 발생 시 무조건 stacktrace 리턴

        return new ResponseDto<String>(1, "성공", null);
    }
}
