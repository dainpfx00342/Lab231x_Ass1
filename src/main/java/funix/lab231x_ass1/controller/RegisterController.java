package funix.lab231x_ass1.controller;

import funix.lab231x_ass1.constants.Mess;
import funix.lab231x_ass1.constants.PageName;
import funix.lab231x_ass1.constants.Url;
import funix.lab231x_ass1.dto.RegisterDTO;
import funix.lab231x_ass1.model.Role;
import funix.lab231x_ass1.model.User;
import funix.lab231x_ass1.respository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(Url.REG)
    public String showRegisterForm() {
       return PageName.REG;
    }

    @PostMapping(Url.REG)
    public String registerUser(@Valid @ModelAttribute RegisterDTO registerDTO,
                               BindingResult bindingResult,
                               Model model){
        //kiểm tra mật khẩu có khớp không?
        if(bindingResult.hasErrors()) {
            // Lấy danh sách các lỗi
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();

            model.addAttribute(Mess.MESS,errorMessages);
            return PageName.REG;
        }
        //kiem tra mat khau khop
        if(!registerDTO.getNewPass().equals(registerDTO.getConfPass())) {
            model.addAttribute(Mess.MESS,Mess.PASSWORD_MISMATCH);
            return PageName.REG;
        }

        // Kiểm tra username đã tồn tại
        if (userRepository.existsByUsername(registerDTO.getNewUsername())) {
            model.addAttribute(Mess.MESS, Mess.ERROR_USERNAME_EXISTS);
            return PageName.REG;
        }

        // Lưu user mới
        User newUser = new User();
        newUser.setUsername(registerDTO.getNewUsername());
        newUser.setPassword(registerDTO.getNewPass());
        newUser.setEmail(registerDTO.getEmail());
        newUser.setRole(Role.valueOf(registerDTO.getRole().toUpperCase()));

        userRepository.save(newUser);

        //Trả về thong bao thành công.
        model.addAttribute(Mess.MESS,Mess.SUCCESS_REGISTER);
        return PageName.HOME;
    }
}
