package shop.mtcoding.blog.user;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;


//DAO
@Repository // 레파지토리가 new 됨
public class UserRepository {

    private EntityManager em ;

    public UserRepository(EntityManager em) {
        this.em = em;   // 의존성 주입, ioc 컨테이너에 EntityManager가 존재함.
    }
    @Transactional // 이게 없으면 쿼리문을 전송안한다. select 조회하는거기 때문에 상관없음.
    public void save(UserRequest.JoinDTO requestDTO){
        System.out.println("userRepository에 save 메서드 호출됨");
        Query query = em.createNativeQuery("insert into user_tb(username,password, email) values(?,?,?)");
        query.setParameter(1,requestDTO.getUsername());
        query.setParameter(2,requestDTO.getPassword());
        query.setParameter(3,requestDTO.getEmail());

        query.executeUpdate();
    }


    @Transactional  //하이버네이트 사용, 셀렉트에는 필요없음
    public void saveV2(UserRequest.JoinDTO requestDTO){
        User user = new User();
        user.setUsername(requestDTO.getUsername());
        user.setPassword(requestDTO.getPassword());
        user.setEmail(requestDTO.getEmail());


        em.persist(user);
    }

    public User findByUsernameAndPaaword(UserRequest.JoinDTO requsetDTO) {
        Query query = em.createNativeQuery("select * from user_tb where username=? and password=?",User.class);  //User.class 알아서 맵핑. ResultSet해서 하나씩 파싱 안해도 됨.
        query.setParameter(1,requsetDTO.getUsername());
        query.setParameter(2,requsetDTO.getPassword());

        User user = (User) query.getSingleResult(); // getSingleResult()의 리턴값이 오브젝트라 다운캐스팅
        return user ;
    }
}
