package petProject.pet.Mapper;

import org.mapstruct.*;
import petProject.pet.DTO.UserDTO.UserCreateDTO;
import petProject.pet.DTO.UserDTO.UserResponseDTO;
import petProject.pet.DTO.UserDTO.UserUpdateDTO;
import petProject.pet.Model.UserEntity;
import petProject.pet.Model.UserRole;  // Import enum — фиксит "Cannot resolve symbol 'UserRole'"

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", constant = "USER")  // Constant default, no dto.getRole() — фиксит resolve error
    @Mapping(target = "amount", expression = "java(dto.getAmount() != null ? dto.getAmount() : 0L)")  // Default amount 0L
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    UserEntity toEntity(UserCreateDTO dto);

    @Mapping(source = "entity.role", target = "role")
    @Mapping(source = "entity.amount", target = "amount")
    UserResponseDTO toDto(UserEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UserUpdateDTO dto, @MappingTarget UserEntity entity);

    @AfterMapping
    default void setDefaults(@MappingTarget UserEntity entity, UserCreateDTO dto) {
        if (entity.getRole() == null) {
            entity.setRole(UserRole.USER);  // Fallback default
        }
        if (entity.getAmount() == null) {
            entity.setAmount(0L);
        }
    }
}