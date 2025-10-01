package petProject.pet.controller;  // <-- одна строка package

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import petProject.pet.Service.UserService;
import petProject.pet.DTO.UserDTO.UserCreateDTO;
import petProject.pet.DTO.UserDTO.UserResponseDTO;
import petProject.pet.DTO.UserDTO.UserUpdateDTO;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserCreateDTO dto) {
        return userService.createUser(dto);
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PatchMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO dto) {
        return userService.updateUser(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
