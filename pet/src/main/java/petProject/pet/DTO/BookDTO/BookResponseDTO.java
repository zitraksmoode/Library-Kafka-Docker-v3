package petProject.pet.DTO.BookDTO;

import lombok.Data;

@Data
public class BookResponseDTO {
    private Long id;
    private String title;
    private String author;
    private Integer year;
    private Boolean isAvailable;
    private Long userId;  // id пользователя, взявшего книгу
}
