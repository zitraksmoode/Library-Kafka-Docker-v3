package petProject.pet.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import petProject.pet.DTO.BookDTO.BookCreateDTO;
import petProject.pet.DTO.BookDTO.BookResponseDTO;
import petProject.pet.DTO.BookDTO.BookUpdateDTO;
import petProject.pet.DTO.UserDTO.UserResponseDTO;
import petProject.pet.Exception.BookNotFoundException;
import petProject.pet.Exception.UserNotFoundException;
import petProject.pet.Mapper.BookMapper;
import petProject.pet.Mapper.UserMapper;
import petProject.pet.Model.BookEntity;
import petProject.pet.Model.UserEntity;
import petProject.pet.Repository.BookRepository;
import petProject.pet.Repository.UserRepository;
import petProject.pet.Service.Kafka.KafkaProducerService;  // Твой Producer

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookMapper bookMapper;
    private final UserMapper userMapper;

    @Autowired
    private final KafkaProducerService kafkaProducer;  // Внедряем Producer для событий

    @Transactional
    public BookResponseDTO createBook(BookCreateDTO dto) {
        BookEntity entity = bookMapper.toEntity(dto);
        BookEntity savedEntity = bookRepository.save(entity);

        // Kafka: шлём событие "книга создана"
        String event = String.format("{\"event\":\"book_created\", \"id\":%d, \"title\":\"%s\", \"author\":\"%s\"}",
                savedEntity.getId(), savedEntity.getTitle(), savedEntity.getAuthor());
        kafkaProducer.sendMessage("pet-topic", event);  // Твой топик, Consumer поймает

        return bookMapper.toDto(savedEntity);
    }

    public BookResponseDTO findById(Long id) {
        BookEntity entity = bookRepository.findById(id)
                .filter(BookEntity::getIsAvailable) // учитываем мягкое удаление
                .orElseThrow(() -> new BookNotFoundException("Такой книги нет!"));
        return bookMapper.toDto(entity);
    }

    public List<BookResponseDTO> findAllBooks() {
        List<BookEntity> entities = bookRepository.findAll();
        return entities.stream().filter(BookEntity::getIsAvailable).map(bookMapper::toDto).toList();
    }

    @Transactional
    public BookResponseDTO updateBook(Long id, BookUpdateDTO bookUpdateDTO) {
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Такой книги не существует!"));

        bookEntity.setTitle(bookUpdateDTO.getTitle());
        bookEntity.setAuthor(bookUpdateDTO.getAuthor());
        bookEntity.setYear(bookUpdateDTO.getYear());
        bookEntity.setIsAvailable(bookUpdateDTO.getIsAvailable());

        if (bookUpdateDTO.getUserId() != null) {
            UserEntity user = userRepository.findById(bookUpdateDTO.getUserId())
                    .orElseThrow(() -> new UserNotFoundException("Пользователь не найден!"));
            bookEntity.setUser(user);
        } else {
            bookEntity.setUser(null);
        }

        BookEntity saved = bookRepository.save(bookEntity);

        // Kafka: шлём событие "книга обновлена"
        String event = String.format("{\"event\":\"book_updated\", \"id\":%d, \"title\":\"%s\", \"available\":%s}",
                saved.getId(), saved.getTitle(), saved.getIsAvailable());
        kafkaProducer.sendMessage("pet-topic", event);

        return bookMapper.toDto(saved);
    }

    @Transactional
    public void deleteBook(Long id) {
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Такой книги не существует!"));

        bookEntity.setIsAvailable(false);
        bookRepository.save(bookEntity);  // Добавил save() для @UpdateTimestamp

        // Kafka: шлём событие "книга удалена" (soft delete)
        String event = String.format("{\"event\":\"book_deleted\", \"id\":%d}", id);
        kafkaProducer.sendMessage("pet-topic", event);
    }

    public UserResponseDTO getUserByBookId(Long bookId) {
        UserEntity user = bookRepository.findUserByBookId(bookId)
                .orElseThrow(() -> new RuntimeException("Пользователь для этой книги не найден"));
        return userMapper.toDto(user);
    }
}