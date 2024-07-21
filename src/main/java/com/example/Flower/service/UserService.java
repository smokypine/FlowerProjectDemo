package com.example.Flower.service;

import com.example.Flower.dto.JoinRequest;
import com.example.Flower.dto.LoginRequest;
import com.example.Flower.dto.UserForm;
import com.example.Flower.entity.User;
import com.example.Flower.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service // 서비스 클래스임을 선언
@Transactional // 트랜잭션 관리를 위한 어노테이션
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 생성해주는 Lombok 어노테이션
public class UserService {
    @Autowired
    private UserRepository userRepository; // UserRepository 의존성 주입

    public User getLoginUserById(Long userId) {
        if (userId == null) return null; // userId가 null인 경우 null 반환

        Optional<User> optionalUser = userRepository.findById(userId); // ID로 사용자 조회
        if (optionalUser.isEmpty()) return null; // 사용자가 존재하지 않으면 null 반환

        return optionalUser.get(); // 사용자가 존재하면 User 객체 반환
    }

     //로그인 처리 메서드
    public User login(LoginRequest req) {
        Optional<User> optionalUser = userRepository.findByLoginId(req.getLoginId()); // 로그인 ID로 사용자 조회
        if (optionalUser.isEmpty()) {
            return null; // 사용자가 존재하지 않으면 null 반환
        }
        User user = optionalUser.get();
        if (!user.getPassword().equals(req.getPassword())) {
            return null; // 비밀번호가 일치하지 않으면 null 반환
        }
        return user; // 로그인 성공 시 User 객체 반환
    }
    //회원 가입 메서드
    public void join(JoinRequest req) {
        userRepository.save(req.toEntity()); // JoinRequest 객체를 엔티티로 변환하여 저장
    }

    public List<User> findAllUsers() {
        // 모든 사용자 목록을 조회
        return (List<User>) userRepository.findAll();
    }

    public User findUserById(Long id) {
        // 특정 ID의 사용자 정보를 조회
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public User saveUser(User user) {
        // 새로운 사용자를 저장
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, UserForm form) {
        log.info("Updating user with ID: " + id);  // 로그 추가
        // 특정 ID의 사용자 정보를 업데이트
        User userEntity = userRepository.findById(id).orElse(null);
        if (userEntity != null) {
            userEntity.setLoginId(form.getLoginId());
            userEntity.setPassword(form.getPassword());
            userEntity.setNickname(form.getNickname());
            userEntity.setName(form.getName());
            userEntity.setAge(form.getAge());
            userEntity.setPhonenumber(form.getPhonenumber());
            userEntity.setEmail(form.getEmail());
            userEntity.setRole(form.getRole());
            log.info("User updated: " + userEntity);  // 로그 추가
            return userRepository.save(userEntity);
        }
        log.info("User not found with ID: " + id);  // 로그 추가
        return null;
    }

    //사용자 삭제
    @Transactional
    public void deleteUser(Long id) {
        // 특정 ID의 사용자를 삭제
        User target = userRepository.findById(id).orElse(null);
        if (target != null) {
            userRepository.delete(target);
        }
    }
    //중복 아이디 체크
    public boolean isLoginIdDuplicate(String loginId) {
        return userRepository.findByLoginId(loginId).isPresent();
    }
    //중복 닉네임 체크
    public boolean isNicknameDuplicate(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }
}
