package funix.lab231x_ass1.dto;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDTO {

    @NotEmpty(message = "Username cannot be empty")
    @Size(min =3, max=60,message = "Username must be between 3 to 60 characters")
    private String newUsername;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long!")
    private String newPass;

    @NotEmpty(message = "Confirm Password cannot be empty")
    private String confPass;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email address")
    private String email;

    @NotEmpty(message = "Role cannot be empty")
    private String role;

}
