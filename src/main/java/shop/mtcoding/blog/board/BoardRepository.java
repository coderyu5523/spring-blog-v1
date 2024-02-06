package shop.mtcoding.blog.board;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Component;
import shop.mtcoding.blog._core.Constant;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BoardRepository {

    private final EntityManager em ;

    public int count(){
        Query query = em.createNativeQuery("select count(*) from board_tb");
        Long count = (Long) query.getSingleResult();
        return count.intValue();
    }


    public List<Board> findAll(int page){  // 조회해서 화면에 띄우는게 목적./
        final int COUNT = 3;
        int value = page * COUNT ;
     Query query = em.createNativeQuery("select * from board_tb order by id desc limit ?,?", Board.class); // 한 페이지에 3개씩 뿌림
     query.setParameter(1,value);
     query.setParameter(2,Constant.PAGING_COUNT);
     List<Board> boardList = query.getResultList();
     return boardList;
    }

    public BoardResponse.DetailDTO findById(int id) {
        //entity 가 아닌 것은 jpa가 파싱 안해줌. join 하면 entity가 아님. 그래서 qlrm 라이브러리를 받음
        Query query = em.createNativeQuery("select bt.id, bt.title, bt.content, bt.created_at, bt.user_id, ut.username from board_tb bt inner join user_tb ut on bt.user_id = ut.id where bt.id =?"); // 한 페이지에 3개씩 뿌림
        query.setParameter(1,id);            // 쿼리문에서 만든 값 순서대로 DTO 만들어야됨. * 쓰지말고 순서대로 적어야됨.

        JpaResultMapper rm =  new JpaResultMapper() ; // join 으로 임시테이블을 만들었을 때 받을 클래스 and 엔티티가 아닐 떄 사용
        BoardResponse.DetailDTO responseDTO = rm.uniqueResult(query,BoardResponse.DetailDTO.class);
        return responseDTO ;
    }
    @Transactional
    public void save(BoardRequest.saveDTO requestDTO,int userId) {
        Query query = em.createNativeQuery("insert into board_tb(title,content,user_id,created_at) values(?,?,?,now());");
        query.setParameter(1, requestDTO.getTitle());
        query.setParameter(2, requestDTO.getContent());
        query.setParameter(3, userId);
        query.executeUpdate();

    }
}
