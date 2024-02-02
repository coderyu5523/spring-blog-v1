package shop.mtcoding.blog.board;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BoardRepository {


    private final EntityManager em ;

    public Board finalById(int id){
        Query query = em.createQuery("select b from Board b join fetch b.user u where b.id = :id", Board.class ); // join fetch 이너조인, ? 안해도 됨
        query.setParameter("id",id) ;

        Board board = (Board) query.getSingleResult();  //
        return board ;
     }


}
