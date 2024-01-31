package shop.mtcoding.blog.user;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

//컨트롤러의 역할
// 1. 요청받기(URL, URI)
// 2. 바디는 DTO로 받음
// 3. 기본 MIME  전략 : x-www-form-urlencoded
// 4. 유효성 검사 (바디데이터가 있드면)
// 5. 클라이언트가 뷰만 원하는지, 혹은 db 처리 후 뷰(여기서는 머스타치) 도 원하는지
// 6. 뷰만 원하면 뷰만 응답, db처리를 원하면 모델에게 위임 후 뷰만 응답하면 끝

//@AllArgsConstructor //이걸 하면 생성자는 필요없으나, int id = 1 ; 이런 식의 변수가 있으면 터짐
@RequiredArgsConstructor //final 을 붙이고 이걸 사용하면 됨
@Controller
public class UserController {

    private final   UserRepository userRepository; // 의존성 주입 받기 위해 만듬. 의존성 주입을 받을 떄 final을 붙여서 사용함
    private final  HttpSession session ;

//
//    public UserController(UserRepository userRepository) {
//        this.userRepository = userRepository;  // 생성자를 만들어서 디폴트 생성자를 제거함.
//    }

    // 의존성 주입하는 법
    // 1. 내가 생성자 만든느법
    // 2. @AllArgsConstructor 만들기
    // 3. final 붙이고 @RequiredArgsConstructor 붙이기
    //

    @PostMapping("/join")
    public String join(UserRequest.JoinDTO requsetDTO){ // 클래스로 매개변수로 한방에 받기.
        System.out.println(requsetDTO);

        //1. 유효성 검사, 데이터베이스 없이 검사해야 됨. 근데 아이디가 중복되면 db를 조회해야됨

        if(requsetDTO.getUsername().length()<3){
            return "error/400";
        }
        //2.동일 유저네임 체크, 트라이캐치로 잡을 수도 있음. 근데 트라이캐치 전에 잡을 수 있다면 잡는게 좋다.



        //3.DB 인서트 - 모델에게 위임하기

        userRepository.save(requsetDTO) ; // 위임

        return"redirect:/loginForm";

    }

    @PostMapping("/login")// select 는 get 요청을 해야함. 하지만 로그인은 민감한 정보기 때문에 get 요청을 하면 쿼리스트링으로 오기 때문에 post 요청으로 함
    public String login(UserRequest.JoinDTO requsetDTO){

        if(requsetDTO.getUsername().length()<3){
            return "error/400";
        }
        // 2. 모델 연결

        User user = userRepository.findByUsernameAndPaaword(requsetDTO);

        if(user==null){
            return "error/401";
        }else {
            session.setAttribute("sessionUser",user); // setAttribute 해쉬맵  키 : 값
            return "redirect:/"; // 메인으로 연결

        }


    }


    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}
