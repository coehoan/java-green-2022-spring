package site.metacoding.dbproject.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.dbproject.domain.user.User;
import site.metacoding.dbproject.domain.user.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public String 유저네임중복검사(String username) {

        User userEntity = userRepository.mUsernameSameCheck(username);

        if (userEntity == null) {
            return "없음";
        } else {
            return "있음";
        }
    }

    @Transactional
    public void 회원가입(User user) {

        userRepository.save(user);

    }

    public User 로그인(User user) {

        return userRepository.mLogin(user.getUsername(), user.getPassword());

    }

    public User 회원정보보기(Integer id) {

        Optional<User> userOp = userRepository.findById(id);

        if (userOp.isPresent()) {
            User userEntity = userOp.get();
            return userEntity;
        } else
            return null;

    }

    @Transactional
    public User 회원정보수정(Integer id, User user) {
        // 1. 영속화
        Optional<User> userOp = userRepository.findById(id);

        if (userOp.isPresent()) {
            User userEntity = userOp.get();
            userEntity.setPassword(user.getPassword());
            userEntity.setEmail(user.getEmail());

            return userEntity;
        }
        return null;
    } // 트랜잭션 종료 + 영속화 되어있는 것들 전부 더티체킹(변경감지해서 DB에 flush)
}
