package shop.mtcoding.blog.board;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.blog.user.User;

import java.time.LocalDate;
@Data //게터세터,toString
@Entity  // entity로 만든 것만 파싱함.
@Table(name="board_tb") // 테이블명
public class Board {
    @Id // 프라이머리키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment

    //포링키 테이블에 제약조건은 안넣는게 좋다. 삭제할 때 문제 생김
    private int id ;
    private String title;
    private String content;


    @ManyToOne // Many = board , one =user  1대 n , 저거를 걸어야 포링키를 만들어줌
    private User user;// 포링키 , 포링키에 원래는 userId 로 적어야됨, 객체로 만들었는데 테이블을 만드러줌
    //타입이 스칼라가 아닌 여러개면 쪼개야됨.
    //jpa 는 하이버네이트 orm 기술 있음. 하이버네이트 : 파싱해주는 것, orm : 데이터 세상의 데이터 타입과, 자바 세상의 데이터 타입의 불일치를 해결해줌
    // DTA DTO 만들 필요 없음. JOIN 해서 담을 수 있음.
    // jpa 가 join 을 할 때 Board 와 User 를 합쳐줌
    @CreationTimestamp
    private LocalDate createdAt ;

}