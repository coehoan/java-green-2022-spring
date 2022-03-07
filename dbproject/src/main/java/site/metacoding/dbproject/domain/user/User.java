package site.metacoding.dbproject.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// JPA 라이브러리는 Java Persistence(DB에 영구적인 저장) API(노출되어있는 메서드)
// 1. CRUD 메서드를 기본 제공
// 2. 자바코드로 DB 자동생성기능
// 3. ORM 제공

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // 서버 실행 시 해당 클래스로 DB 테이블을 생성한다.
@EntityListeners(AuditingEntityListener.class) // 현재시간 입력
public class User {
    @Id // PK설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // identity 전략은 DB가 알아서 찾아준다.
    private Integer id;

    @Column(length = 20, unique = true)
    private String username;
    @Column(length = 12, nullable = false)
    private String password;
    @Column(length = 1600000000, nullable = false)
    private String email;

    @CreatedDate // 생성일, insert만 작동
    private LocalDateTime createDate;
    @LastModifiedDate // 수정일 // insert,update에 작동
    private LocalDateTime updateTime;

    /////////////////////////////////////// DB테이블과 상관없음
    @Transient // 컬럼생성X
    private String remember;
}
