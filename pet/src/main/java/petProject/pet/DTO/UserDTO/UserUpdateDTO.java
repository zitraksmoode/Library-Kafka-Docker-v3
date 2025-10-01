package petProject.pet.DTO.UserDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDTO {
    @NotBlank
    private String name;

    @Size(min = 6)
    private String password;

    @Pattern(regexp = "\\+?[0-9]{10,15}")
    private String phoneNumber;
}
