package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.mtcoding.blog.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final HttpSession session;
    private final BoardRepository boardRepository ;
    //http://localhost:8080/page=0 가능

    //@RequestParam(defaultValue = "0") 이거는 값을 안넣으면 페이지=0 으로 설정
    @GetMapping({ "/", "/board" })
    public String index(HttpServletRequest request , @RequestParam(defaultValue = "0") int page) {
        System.out.println("페이지"+page);


        // 스프링의 기본 전략은 포워드
        List<Board> boardList = boardRepository.findAll(page);
        request.setAttribute("boardList",boardList);  // 가방에 담기
        // 가방에 담기

        int curruentPage =page ;
        int nextPage = curruentPage +1 ;
        int prevPage = curruentPage -1 ;

        request.setAttribute("nextPage",nextPage);  // 가방에 담기
        request.setAttribute("prevPage",prevPage);  // 머스태치에 담기만 하면 됨


        int totalCount = 4; // 나중에는 db에서 전체 데이터를 조회해야 됨. 그래야 라스트페이지를 알 수 있음

        // total = 4 cp = 0 false
        // total = 4 cp = 1 ture

        boolean first = (curruentPage==0 ?true :false);
        boolean last = true ;

        request.setAttribute("first",first);

        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @GetMapping("/board/1")
    public String detail() {
        return "board/detail";
    }

}
