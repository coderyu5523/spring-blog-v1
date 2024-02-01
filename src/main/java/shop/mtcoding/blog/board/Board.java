package shop.mtcoding.blog.board;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
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
    private int userId ; // 포링키 , 포링키에
    //타입이 스칼라가 아닌 여러개면 쪼개야됨.

    @CreationTimestamp
    private LocalDate createdAt ;

}
