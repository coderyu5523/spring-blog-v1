package shop.mtcoding.blog.user;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
// 여기 있는 @는 컨포넌트 스캔은 안됨
//db에서 조회된 user 데이터베이스 값을 여기에 받음
@Data //게터세터
@Entity  // 이게 붙으면 테이블 생성됨
@Table(name="user_tb") // 테이블명
public class User {

    @Id // 프라이머리키 설정
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY) //auto_increment
    private int id ;
    @Column(unique = true) // 유저네임은 유니크
    private String username;
    @Column(length = 60,nullable = false) //비밀번호 길이는 60, null 불가
    private String password;
    private String email;

    @CreationTimestamp
    private LocalDate createdAt ;
}
