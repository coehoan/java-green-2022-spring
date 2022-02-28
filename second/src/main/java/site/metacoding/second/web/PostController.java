package site.metacoding.second.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import site.metacoding.second.domain.Post;

// View (글쓰기, 글목록, 글상세보기)
@Controller
public class PostController {

    @GetMapping("/post/writeForm")
    public String writeForm() {

        return "post/writeForm";
    }

    @GetMapping("/post/list")
    public String list(Model model) {

        List<Post> posts = new ArrayList<>();
        // Post post1 = new Post(1, "제목1", "내용1");
        // Post post2 = new Post(2, "제목2", "내용2");
        // Post post3 = new Post(3, "제목3", "내용3");
        // Post post4 = new Post(4, "제목4", "내용4");
        // Post post5 = new Post(5, "제목5", "내용5");
        // posts.add(post1);
        // posts.add(post2);
        // posts.add(post3);
        // posts.add(post4);
        // posts.add(post5);
        for (int i = 1; i < 6; i++) {
            Post post = new Post(i, "제목" + i, "내용" + i);
            posts.add(post);
        }

        model.addAttribute("posts", posts);

        return "post/list";
    }

    @GetMapping("/post/detail")
    public String detail(Model model) {

        // 1. DB 연결해서 SELECT
        // 2. ResultSet을 JavaObject로 변경
        Post post = new Post(1, "제목1", "내용1");

        // 3. request에 담기
        model.addAttribute("post", post);

        return "post/detail"; // RequestDispatcher로 forward함 = request가 복제된다.
    }
}
