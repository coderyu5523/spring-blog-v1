package shop.mtcoding.blog.board;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(BoardRepositoryTest.class)
@DataJpaTest  // entitymanager 같은 db 관련 애들만 메모리에 띄워줌
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepasitory ;

    public void findById_Test(){

    }
}
