package petProject.pet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import petProject.pet.Model.BookEntity;
import petProject.pet.Model.UserEntity;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    default Optional<UserEntity> findUserByBookId(Long bookId) {
        return findById(bookId).map(BookEntity::getUser);
    }

}
