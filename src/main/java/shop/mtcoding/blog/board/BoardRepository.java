package shop.mtcoding.blog.board;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardRepository {

    private final EntityManager em ;

    public List<Board> findAll(int page){
        final int COUNT = 3;
        int value = page * COUNT ;
     Query query = em.createNativeQuery("select * from board_tb order by id desc limit ?,?",Board.class); // 한 페이지에 3개씩 뿌림
     query.setParameter(1,value);
     query.setParameter(2,COUNT);
     List<Board> boardList = query.getResultList();
     return boardList;
    }

}
