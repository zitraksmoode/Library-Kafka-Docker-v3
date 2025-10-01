package petProject.pet.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import petProject.pet.DTO.BookDTO.BookCreateDTO;
import petProject.pet.DTO.BookDTO.BookResponseDTO;
import petProject.pet.Model.BookEntity;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "userId", source = "user.id")
    BookResponseDTO toDto(BookEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    BookEntity toEntity(BookCreateDTO dto);

}
