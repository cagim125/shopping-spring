package io.getarrays.contactapi.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;



    @PostMapping("/join")
    public ResponseEntity<User> joinUser(@RequestBody User user) {
        System.out.println("회원가입 컨트롤러 실행" + user);
        User registredUser = userService.register(user);
        System.out.println("회원가입 완료");

        return ResponseEntity.status(HttpStatus.CREATED).body(registredUser);
    }

    @GetMapping("/loginOk")
    public ResponseEntity<Map<String, Object>> loginOk(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        session.setAttribute("user", customUser);

        String userEmail = customUser.getUsername();
        String displayName = customUser.getDisplayName();
        String authorities = customUser.getAuthorities().toString();
        Long userId = customUser.getUserId();

        System.out.println("로그인한 유저 이메일 :" + userEmail);
        System.out.println("로그인한 유저 아이디 : " + displayName);
        System.out.println("유저 권한:" + authentication.getAuthorities());

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("email", userEmail);
        userInfo.put("displayName", displayName);
        userInfo.put("authorities", authorities);
        userInfo.put("userId", userId);

        return ResponseEntity.ok(userInfo);

    }

    // 로그아웃
    @GetMapping("/logoutOk")
    public ResponseEntity<Void> logoutOk() {
        System.out.println("로그아웃 성공");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin")
    public ResponseEntity<Void> getAdminPage() {
        System.out.println("어드민 인증 성공");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/checkSession")
    public ResponseEntity<Map<String, Boolean>> checkSession(HttpSession session) {
        Map<String, Boolean> response = new HashMap<>();
        // 세션에 저장된 "user" 속성이 있으면 인증된 상태
        response.put("isAuthenticated", session.getAttribute("user") != null);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/user")
//    public ResponseEntity<User> getUserPage() {
//        System.out.println("일반 인증 성공");
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//
//        // 유저 정보
//        User user = userService.getUserInfo(email);
//
//        return ResponseEntity.ok(user);
//    }



    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registredUser = userService.register(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(registredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestParam String email, @RequestParam String password){
        User loginUser = userService.login(email, password);

        if(loginUser != null) {
            return ResponseEntity.ok(loginUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserId(@PathVariable Long userId) {
        User user = userService.getUserById(userId);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }


    @PostMapping("/login/jwt")
    @ResponseBody
    public String loginJWT(@RequestBody Map<String, String> data){

        var authToken = new UsernamePasswordAuthenticationToken(
                data.get("username"), data.get("password")
        );
        var auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        var jwt = JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());
        System.out.println(jwt);
        return jwt;
    }
}
