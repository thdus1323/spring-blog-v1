package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import shop.mtcoding.blog.user.User;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final HttpSession session;

    @GetMapping({"/", "/board"})
    public String index() {


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