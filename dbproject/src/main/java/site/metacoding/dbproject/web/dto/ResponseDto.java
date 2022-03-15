package site.metacoding.dbproject.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDto<T> {
    private Integer code; // 통신 -1 실패, 1 성공
    private String msg;
    private T data; // 공통적으로 쓰기위해 제네릭 사용
}
