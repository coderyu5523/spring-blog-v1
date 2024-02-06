package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {
    //Ioc컨테이너에 세션에 접근할 수 있는 변수가 들어가 있음. DI 하면 됨
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
    @PostMapping("/board/save")
    public String saveWrite(BoardRequest.saveDTO requestDTO,HttpServletRequest request){
        //1. 인증체크
        User sessionUser = (User) session.getAttribute("sessionUser");

        if(sessionUser==null){
            return "redirect:/loginForm";
        }

        //2. 바디데이터 확인 및 유효성 검사


        // 유효성 검사.
        if(requestDTO.getTitle().length()>30){
            request.setAttribute("status",400);
            request.setAttribute("msg","title의 길이가 30자를 초과할 수 없습니다.");
            return "error/40x"; //
        }

        if(requestDTO.getContent().length()>200){
            request.setAttribute("status",400);
            request.setAttribute("msg","내용은 200자를  초과할 수 없습니다.");
            return "error/40x";
        }


        boardRepository.save(requestDTO,sessionUser.getId());

        return "redirect:/";

    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if(sessionUser==null){
            return "redirect:/loginForm";
        }

        return "board/saveForm";
    }
    @GetMapping("/board/{id}")  // board 뒤에 1은 pk.  pk는 게시글 뒤에 바로 이름을 작성함
    public String detail(@PathVariable int id, HttpServletRequest request) { // {} 를 알아서 파싱해줌
        BoardResponse.DetailDTO responseDTO = boardRepository.findById(id);
        request.setAttribute("board",responseDTO);

        //바디데이터 없으면 유효성 검사는  필요없다.

        //1. 해당 페이지의 주인 여부
        boolean owner = false ;

        //2 . 작성자 userId 확읺기
        int boardUserId = responseDTO.getUserId();

        //3. 로그인 여부 체크 / 로그인이 안되면 무조건 false, 권한 체크
        User sessionUser = (User) session.getAttribute("sessionUser"); // setAttribute 에서 키를 sessionUser 로 정함
        if(sessionUser !=null){  // 로그인을 했음
            if(boardUserId == sessionUser.getId()){
                owner =true ;
            }


        }
        request.setAttribute("owner",owner);
        return "board/detail";
    }
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable int id,HttpServletRequest request){
        //인증 체크
        User sessionUser = (User) session.getAttribute("sessionUser");

        if(sessionUser==null){
            return "redirect:/loginForm";

        }
        //권한 체크
        Board board = boardRepository.findByIdCheck(id);
        //삭제 전 id 있는지 확인하는게 좋음.

        if(board.getUserId()!=sessionUser.getId()){  // 작성자 아이디 != 유저 아이디
            request.setAttribute("status",403);
            request.setAttribute("msg","게시글을 삭제할 권한이 없습니다.");
            return "error/40x";
        }


        boardRepository.deleteById(id);


        return "redirect:/";
    }



}
