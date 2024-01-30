package shop.mtcoding.blog.user;

import lombok.Data;

//    요청DTO - data transfer object
public class UserRequest {  // 요청데이터를 여기 받음

    @Data // 얘가 게터세터 다 가지고 있음.
    public static class JoinDTO{
        private String username ;
        private String password;
        private String email ;
    }

    @Data //
    public static class LoginDTO{
        private String username ;
        private String password;
    }


}
