package petProject.pet.Mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import petProject.pet.DTO.BookDTO.BookCreateDTO;
import petProject.pet.DTO.BookDTO.BookResponseDTO;
import petProject.pet.Model.BookEntity;
import petProject.pet.Model.UserEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-29T23:17:46+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookResponseDTO toDto(BookEntity entity) {
        if ( entity == null ) {
            return null;
        }

        BookResponseDTO bookResponseDTO = new BookResponseDTO();

        bookResponseDTO.setUserId( entityUserId( entity ) );
        bookResponseDTO.setId( entity.getId() );
        bookResponseDTO.setTitle( entity.getTitle() );
        bookResponseDTO.setAuthor( entity.getAuthor() );
        bookResponseDTO.setYear( entity.getYear() );
        bookResponseDTO.setIsAvailable( entity.getIsAvailable() );

        return bookResponseDTO;
    }

    @Override
    public BookEntity toEntity(BookCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        BookEntity bookEntity = new BookEntity();

        bookEntity.setTitle( dto.getTitle() );
        bookEntity.setAuthor( dto.getAuthor() );
        bookEntity.setYear( dto.getYear() );

        return bookEntity;
    }

    private Long entityUserId(BookEntity bookEntity) {
        if ( bookEntity == null ) {
            return null;
        }
        UserEntity user = bookEntity.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
