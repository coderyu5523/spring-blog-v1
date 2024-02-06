package shop.mtcoding.blog.board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

//DTO - 화면에 보이는 데이터를 DTO , 클라이언트가 전송하는 데이터는 REQUEST.
// 클라이언트에게 응답하는건 RESPONSE .RESPONSE 의 핵심은 화면에 필요한 데이터여야됨
//
public class BoardResponse {
    @AllArgsConstructor  // 디폴트 생성자를 때리는게 아니라 풀생성자를 때림. qmrl 은 풀생성자가 있어야됨.
    @Data
    public static class DetailDTO{

        private Integer id ;
        private String title;
        private String content ;
        private Timestamp createdAt;
        private Integer userId ;
        private String username ;

    }
}
