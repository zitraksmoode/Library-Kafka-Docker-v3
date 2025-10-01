package petProject.pet.DTO.BookDTO;

import lombok.Data;

@Data
public class BookUpdateDTO {
    private String title;
    private String author;
    private Integer year;
    private Boolean isAvailable;
    private Long userId;

}
