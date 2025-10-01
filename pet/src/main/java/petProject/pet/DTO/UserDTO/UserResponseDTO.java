package petProject.pet.DTO.UserDTO;

import lombok.Data;
import petProject.pet.Model.UserRole;

@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private Long amount;
    private String phoneNumber;
    private UserRole role;   // 🔥 добавь это
}

