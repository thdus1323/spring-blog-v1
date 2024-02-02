package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


//1. 요청 받기 (URL, URI)
//2. http body는 DTO로 받음
//3. 기본 마임 전략 : x-www-form-urlencoded (username=ssar&password=1234)
//4. 유효성 검사하기 (body 데이터가 있다면)
//5. 클라이언트가 view만 원하는지? 혹은 DB처리 후 VIEW(머스태치)도 원하는지?
//6. view만 원하면 view를 응답하면 끝
//1. DB처리를 원하면 Model(DAO)에게 위임한 후, view를 응답하면 끝

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserRepository userRepository;
    private final HttpSession session;

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO requestDTO) {
        // 1. 유효성 검사
        if (requestDTO.getUsername().length() < 3) {
            return "error/400";
        }
        // 2.동일 username 체크(나중에 하나의 트랜잭션으로 묶는 것이 좋다.)
        User user = userRepository.findByUsernameAndPassword(requestDTO.getUsername());
        if (user == null){
            // 3. Model 필요 (위임. db연결)
        }else {
            return "error/400";
        }

        return "redirect:/loginForm";



        userRepository.save(requestDTO);
        return "redirect:/loginForm";

//        try {
//            userRepository.save(requestDTO);
//        }catch (Exception e){
//
//        }

        // select * from user_tb where username=? and password=?

        User user = userRepository.findByUsernameAndPassword(requestDTO);

        if (user == null) { //로그인 실패
            return "error/401";
        }else {
            session.setAttribute("sessionUser", user);
            return "redirect:/";    // 3. 응답 //파일명 적지마라. 파일이 만들어져있으면 무조건 redirection
        }

        // 유저가 null이면 error 페이지로
        // 유저가 null이 아니면, session 만들고, index 페이지로 이동
//        System.out.println(user);
    }

    @PostMapping("/join")
    public String join(UserRequest.JoinDTO requestDTO) {
        System.out.println(requestDTO);

        // 1. 유효성 검사
        if (requestDTO.getUsername().length() < 3) {
            return "error/400";
        }

        // 2. Model에게 위임 하기
        //DB insert 후. MVC 패턴
        userRepository.save(requestDTO);
        //    userRepository.saveV2(requestDTO);

        return "redirect:/loginForm";
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

        session.invalidate();
        return "redirect:/";
    }
}