package petProject.pet.DTO.UserDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateDTO {

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 100, message = "Имя от 2 до 100 символов")
    private String name;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, message = "Пароль минимум 6 символов")
    private String password;

    @NotBlank(message = "Телефон не может быть пустым")
    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Формат +79991234567")
    private String phoneNumber;

    // Optional для initial balance, default 0L в mapper
    private Long amount;
}