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

    //@RequestParam(defaultValue = "0") 이거는 값을 안넣으면 페이지=0 으로 설정
    @GetMapping({ "/", "/board" })
    public String index(HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
        List<Board> boardList = boardRepository.findAll(page);
        request.setAttribute("boardList", boardList);

        int currentPage = page;
        int nextPage = currentPage+1;
        int prevPage = currentPage-1;
        request.setAttribute("nextPage", nextPage);
        request.setAttribute("prevPage", prevPage);

        boolean first = shop.mtcoding.blog._core.PagingUtil.isFirst(currentPage);
        boolean last = shop.mtcoding.blog._core.PagingUtil.isLast(currentPage, 4);

        request.setAttribute("first", first);
        request.setAttribute("last", last);

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
