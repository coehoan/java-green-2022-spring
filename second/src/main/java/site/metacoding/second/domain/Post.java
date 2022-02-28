package site.metacoding.second.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// 모델
@Getter
@Setter
@AllArgsConstructor
public class Post {
    private int id;
    private String title;
    private String content;
}
