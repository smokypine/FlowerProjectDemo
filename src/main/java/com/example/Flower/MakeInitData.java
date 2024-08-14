package com.example.Flower;

import com.example.Flower.entity.Announce;
import com.example.Flower.entity.User;
import com.example.Flower.entity.UserRole;
import com.example.Flower.repository.AnnounceRepository;
import com.example.Flower.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//시험문제는 아니다.
@Component //스프링 컨테이너에게 자바 객체를 생성하라는 명령.
@RequiredArgsConstructor
public class MakeInitData {
    private final UserRepository userRepository;
    private final AnnounceRepository announceRepository;

    @Transactional
    @PostConstruct //이 어노테이션은 객체가 생성된 뒤 단 한 번만 실행이 된다.
    public void makeAdminAndUser() {
        User admin1 = User.builder()
                .loginId("admin1")
                .password("1234")
                .nickname("관리자")
                .name("김동현")
                .age(31)
                .phonenumber("010-5656-1541")
                .email("hayasdan@naver.com")
                .role(UserRole.ADMIN)
                .active(1)
                .build();
        userRepository.save(admin1);

        User user1 = User.builder()
                .loginId("user1")
                .password("1234")
                .nickname("밀양 박씨")
                .name("박덕수")
                .age(20)
                .phonenumber("010-1234-1234")
                .email("user1@naver.com")
                .role(UserRole.USER)
                .active(1)
                .build();
        userRepository.save(user1);

        User user2 = User.builder()
                .loginId("user2")
                .password("1234")
                .nickname("황소그림")
                .name("이중섭")
                .age(50)
                .phonenumber("010-5432-1234")
                .email("user2@naver.com")
                .role(UserRole.USER)
                .active(1)
                .build();
        userRepository.save(user2);

        User user3 = User.builder()
                .loginId("user3")
                .password("1234")
                .nickname("AR리따움")
                .name("황진이")
                .age(30)
                .phonenumber("016-1432-1234")
                .email("hwang2@naver.com")
                .role(UserRole.USER)
                .active(1)
                .build();
        userRepository.save(user3);

        Announce announce1 = Announce.builder()
                .title("공지사항 1")
                .content("이 글은 공지사항 1번입니다.")
                .build();
        announceRepository.save(announce1);

        Announce announce2 = Announce.builder()
                .title("공지사항 2")
                .content("이 글은 공지사항 2번입니다.")
                .build();
        announceRepository.save(announce2);

        Announce announce3 = Announce.builder()
                .title("공지사항 3")
                .content("이 글은 공지사항 3번입니다.")
                .build();
        announceRepository.save(announce3);

        Announce announce4 = Announce.builder()
                .title("공지사항 4")
                .content("이 글은 공지사항 4번입니다.")
                .build();
        announceRepository.save(announce4);

        Announce announce5 = Announce.builder()
                .title("공지사항 5")
                .content("이 글은 공지사항 5번입니다.")
                .build();
        announceRepository.save(announce5);
    }
}
