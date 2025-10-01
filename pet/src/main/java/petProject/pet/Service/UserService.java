package petProject.pet.Service;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;  // Убрал, если не нужен — это Tomcat User, заменил на твой UserEntity
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petProject.pet.DTO.UserDTO.UserCreateDTO;
import petProject.pet.DTO.UserDTO.UserResponseDTO;
import petProject.pet.DTO.UserDTO.UserUpdateDTO;
import petProject.pet.Exception.UserAlreadyExistsException;
import petProject.pet.Exception.UserNotFoundException;
import petProject.pet.Mapper.UserMapper;
import petProject.pet.Model.UserEntity;
import petProject.pet.Model.UserRole;
import petProject.pet.Repository.UserRepository;
import petProject.pet.Service.Kafka.KafkaProducerService;  // Твой Producer

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    private final KafkaProducerService kafkaProducer;  // Внедряем Producer для событий

    @Transactional
    public UserResponseDTO createUser(UserCreateDTO dto) {
        userRepository.findByPhoneNumber(dto.getPhoneNumber())
                .ifPresent(u -> {
                    throw new UserAlreadyExistsException(
                            "Пользователь с телефоном " + dto.getPhoneNumber() + " уже существует"
                    );
                });

        UserEntity entity = userMapper.toEntity(dto);
        if (entity.getRole() == null) {
            entity.setRole(UserRole.USER);
        }
        UserEntity saved = userRepository.save(entity);

        // Kafka: шлём событие "юзер создан"
        String event = String.format("{\"event\":\"user_created\", \"id\":%d, \"name\":\"%s\", \"phone\":\"%s\", \"role\":\"%s\"}",
                saved.getId(), saved.getName(), saved.getPhoneNumber(), saved.getRole());
        kafkaProducer.sendMessage("pet-topic", event);

        return userMapper.toDto(saved);
    }

    public UserResponseDTO getUserById(Long id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Этого человека нет"));
        return userMapper.toDto(entity);
    }

    public UserResponseDTO getUserByName(String name){
        UserEntity entity = userRepository.findByName(name).
                orElseThrow(() -> new UserNotFoundException("Этого человека нет!"));
        return userMapper.toDto(entity);
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserUpdateDTO dto) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя не существует"));
        userRepository.findByPhoneNumber(dto.getPhoneNumber())
                .filter(u -> !u.getId().equals(id))
                .ifPresent(u -> {
                    throw new UserAlreadyExistsException(
                            "Пользователь с телефоном " + dto.getPhoneNumber() + " уже существует");
                });
        entity.setName(dto.getName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setPassword(dto.getPassword());
        UserEntity saved = userRepository.save(entity);

        // Kafka: шлём событие "юзер обновлён"
        String event = String.format("{\"event\":\"user_updated\", \"id\":%d, \"name\":\"%s\", \"phone\":\"%s\"}",
                saved.getId(), saved.getName(), saved.getPhoneNumber());
        kafkaProducer.sendMessage("pet-topic", event);

        return userMapper.toDto(saved);
    }

    @Transactional
    public void deleteUser(Long id) {
        UserEntity entity = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("Пользователя не существует!"));

        entity.setIsActive(false);
        userRepository.save(entity);  // Для @UpdateTimestamp

        // Kafka: шлём событие "юзер удалён" (soft delete)
        String event = String.format("{\"event\":\"user_deleted\", \"id\":%d}", id);
        kafkaProducer.sendMessage("pet-topic", event);
    }

    public List<UserResponseDTO> getAllUsers() {
        List<UserEntity> entities = userRepository.findAll();

        return entities.stream()
                .filter(UserEntity::getIsActive)
                .map(userMapper::toDto)
                .toList();
    }

    public UserResponseDTO getByPhoneNumber(String number) {
        UserEntity entity = userRepository.findByPhoneNumber(number)
                .orElseThrow(() -> new RuntimeException("Пользователя с таким номером нет"));
        return userMapper.toDto(entity);
    }
}