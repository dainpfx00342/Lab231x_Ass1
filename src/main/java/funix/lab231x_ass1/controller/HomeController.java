package funix.lab231x_ass1.controller;

import funix.lab231x_ass1.model.User;
import funix.lab231x_ass1.respository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/Home")
    public String homePage(Authentication authentication, Model model) {
        // Lấy thông tin người dùng từ Authentication
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        // Lấy thông tin người dùng từ cơ sở dữ liệu (nếu cần)
        User user = userRepository.findByUsername(username);

        // Thêm thông tin vào model
        model.addAttribute("user", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("role", user.getRole().name());
        model.addAttribute("password", user.getPassword()); // Lưu ý không nên hiển thị mật khẩu trong ứng dụng thực tế

        return "Home"; // Trả về tên của trang HTML
    }
}