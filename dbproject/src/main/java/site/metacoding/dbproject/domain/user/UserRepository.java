package site.metacoding.dbproject.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository // 어노테이션 사용안해도 IoC Container에 있음. - 부모가 이미 떠있기때문
public interface UserRepository extends JpaRepository<User, Integer> {

    // 없는 쿼리는 직접 만들자
    // 키바인딩 : - ?는 순서를 지켜서 넣어줘야하기때문에 키값으로 받는 키바인딩 사용
    @Query(value = "SELECT * FROM user WHERE username = :username AND password = :password", nativeQuery = true)
    User mLogin(@Param("username") String username, @Param("password") String password);

    @Query(value = "SELECT * FROM user WHERE username = :username", nativeQuery = true)
    User mUsernameSameCheck(@Param("username") String username);

    // findAll()
    // SELECT * FROM user;

    // findById()
    // SELECT * FROM user WHERE id = ?;

    // save()
    // INSERT INTO user(username,password,email,createDate) VALUES(?,?,?,?);

    // deleteById()
    // DELETE FROM user WHERE id = ?;

    // update는 영속성 컨텍스트로 사용
}
