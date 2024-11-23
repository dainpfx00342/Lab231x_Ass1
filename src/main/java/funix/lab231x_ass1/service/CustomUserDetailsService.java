package funix.lab231x_ass1.service;

import funix.lab231x_ass1.model.User;
import funix.lab231x_ass1.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Tìm người dùng trong cơ sở dữ liệu
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: "+username);
        }

        // Đảm bảo thêm tiền tố ROLE_ vào vai trò khi trả về cho Spring Security
        String role = "ROLE_" + user.getRole().toString();


        // Trả về đối tượng UserDetails từ User
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // Mật khẩu đã mã hóa
                .roles(role) // Phải đảm bảo Role trong database là dạng STRING
                .build();
    }
}
