package petProject.pet.Mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import petProject.pet.DTO.UserDTO.UserCreateDTO;
import petProject.pet.DTO.UserDTO.UserResponseDTO;
import petProject.pet.DTO.UserDTO.UserUpdateDTO;
import petProject.pet.Model.UserEntity;
import petProject.pet.Model.UserRole;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-29T23:17:46+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity toEntity(UserCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.name( dto.getName() );
        userEntity.password( dto.getPassword() );
        userEntity.phoneNumber( dto.getPhoneNumber() );

        userEntity.role( UserRole.USER );
        userEntity.amount( dto.getAmount() != null ? dto.getAmount() : 0L );
        userEntity.isActive( true );

        return userEntity.build();
    }

    @Override
    public UserResponseDTO toDto(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setRole( entity.getRole() );
        userResponseDTO.setAmount( entity.getAmount() );
        userResponseDTO.setId( entity.getId() );
        userResponseDTO.setName( entity.getName() );
        userResponseDTO.setPhoneNumber( entity.getPhoneNumber() );

        return userResponseDTO;
    }

    @Override
    public void updateFromDto(UserUpdateDTO dto, UserEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getPassword() != null ) {
            entity.setPassword( dto.getPassword() );
        }
        if ( dto.getPhoneNumber() != null ) {
            entity.setPhoneNumber( dto.getPhoneNumber() );
        }
    }
}
