package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * 컨트롤러
 * 1. 요청받기(URI)
 * 2. http body는 어떻게? (DTO)
 * 3. 기본 mime 전략 : x-www.form.urlencoded (username=ssar&password=1234)
 * 4. 유효성 검사하기 (body 데이터가 있다면)
 * 5. 클라이언트가 View만 원하는지? 혹은 DB 처리 후 View도 원하는지?
 * 6. DB처리를 원하면 Model(DAO)에게 위임 후 view를 응답하면 끝
 */
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserRepository userRepository; //의존성을 주입해야하는 거는 다 final 붙여야함
    private final HttpSession session;

    //    public UserController(UserRepository userRepository, HttpSession session) { //IoC에 뜬걸 가져와서 쓴다 = 싱글톤으로 띄워서 쓰던거랑 같다
//        this.userRepository = userRepository;
//        this.session = session;
//    }
    @PostMapping("/login")
    public String login(UserRequest.LoginDTO requestDTO){ //쿼리스트링으로 담아야하지만 예외로 로그인은 post요청함 원래는 get요청
        //1. 유효성 검사 //컨트롤러에서는 데이터베이스의 값과 비교해서 값을 체크하는게 아님,
        if(requestDTO.getUsername().length() <3){
            return "error/400";
        }

        //2. (모델필요 select)
        User user=userRepository.findByUsernameAndPassword(requestDTO);
        if(user == null){
            return "error/401";
        }else{
            session.setAttribute("sessionUser", user);
            return "redirect:/";
        }

//        System.out.println(user);
//        //3. 응답
//        return "redirect:/"; //파일명 쓰지말기 또 페이지만들어있는데
    }

    @PostMapping ("/join")
    public String join(UserRequest.JoinDTO requestDTO){
        System.out.println(requestDTO); //@DATA안에 String도 포함되어있음

        //1. 유효성 검사
        if(requestDTO.getUsername().length() <3){
            return "error/400";
        }

        //2. 동일 username 체크
        User user=userRepository.findByUsername(requestDTO);
        if (user == null){
            userRepository.save(requestDTO);
        }else {
            return "error/401";
        }


        //2. Model에게 위임하기

        userRepository.save(requestDTO);

        return "redirect:/loginForm"; //리다이렉션불러놓은게 있어서 다시부른거
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
        session.invalidate(); //1번 서랍안에 있는 내용을 다 삭제
        return "redirect:/";
    }


}