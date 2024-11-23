package funix.lab231x_ass1.controller;

import funix.lab231x_ass1.constants.Mess;
import funix.lab231x_ass1.constants.PageName;
import funix.lab231x_ass1.constants.Url;
import funix.lab231x_ass1.model.User;
import funix.lab231x_ass1.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    //Hiển thị trang đăng nhập
    @GetMapping(Url.LOGIN)
    public String login() {
        return "login";
    }

    // Xử lý đăng nhập sau khi người dùng nhập thông tin
    @PostMapping(Url.LOGIN)
    public String loginSubmit(String username, String password, Model model) {
        try {
            // Tạo đối tượng Authentication
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, password);

            // Sử dụng AuthenticationManager để thực hiện xác thực
            authenticationManager.authenticate(authenticationToken);

            // Lưu thông tin xác thực vào SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // Lấy thông tin người dùng từ cơ sở dữ liệu
            User user = userRepository.findByUsername(username);
            model.addAttribute("user", username);
            model.addAttribute("email", user.getEmail());
            model.addAttribute("role", user.getRole());
            return PageName.HOME; // Nếu đăng nhập thành công, chuyển tới trang home

        } catch (Exception e) {
            // Nếu xác thực không thành công (sai username hoặc password), hiển thị thông báo lỗi
            model.addAttribute("error", "Invalid username or password.");
            return PageName.LOGIN; // Trở lại trang đăng nhập
        }

//    @Autowired
//    private UserRepository userRepository;
//
//
//    @GetMapping(Url.LOGIN)
//    public String login() {
//        return PageName.LOGIN;
//    }
//
//    @PostMapping(Url.LOGIN)
//    public String loginSubmit(String username, String password, Model model) {
//        User optionalUser = userRepository.findByUsername(username);
//        if (optionalUser.getUsername().equals(username)) {
//            // Kiểm tra mật khẩu
//
//            if (!optionalUser.getPassword().equals(password)) {
//                model.addAttribute(Mess.MESS, Mess.ERROR_PASS);
//                System.out.println("Password from DB: " + optionalUser.getPassword());
//                System.out.println("Password entered: " + password);
//                return PageName.LOGIN; // Đảm bảo trả về tên view hợp lệ
//            }
//        }
//            // Dang nhap thanh cong chuyen huong de trang Home
//            model.addAttribute("user", username);
//            model.addAttribute("email", optionalUser.getEmail());
//            model.addAttribute("role", optionalUser.getRole());
//            model.addAttribute("password", password);
//            return PageName.HOME;
//
//
//
//    }
    }
}

