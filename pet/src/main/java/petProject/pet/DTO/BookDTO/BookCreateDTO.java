package petProject.pet.DTO.BookDTO;

import lombok.Data;

@Data
public class BookCreateDTO {
    private String title;
    private String author;
    private Integer year;
}
