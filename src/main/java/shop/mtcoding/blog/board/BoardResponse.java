package shop.mtcoding.blog.board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

public class BoardResponse {
    @AllArgsConstructor  // 디폴트 생성자를 때리는게 아니라 풀생성자를 때림. qmrl 은 풀생성자가 있어야됨.
    @Data
    public static class DetailDTO{
        //bt.id, bt.content, bt.title, bt.created_at, ut.id uid, ut.username
//bt.id, bt.title, bt.content, bt.created_at, bt.user_id, ut.username
        private Integer id ;
        private String title;
        private String content ;
        private Timestamp createdAt;
        private Integer userId ;
        private String username ;

    }
}
