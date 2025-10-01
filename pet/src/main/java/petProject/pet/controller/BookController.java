package petProject.pet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import petProject.pet.DTO.BookDTO.BookCreateDTO;
import petProject.pet.DTO.BookDTO.BookResponseDTO;
import petProject.pet.DTO.BookDTO.BookUpdateDTO;
import petProject.pet.DTO.UserDTO.UserResponseDTO;
import petProject.pet.Service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books")  // Добавь /api
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public BookResponseDTO createBook(@RequestBody BookCreateDTO dto) {
        return bookService.createBook(dto);
    }

    @GetMapping("/{id}")
    public BookResponseDTO getBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PatchMapping("/{id}")
    public BookResponseDTO updateBook(@PathVariable Long id, @RequestBody BookUpdateDTO dto) {
        return bookService.updateBook(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping
    public List<BookResponseDTO> getAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}/user")
    public UserResponseDTO getBookUser(@PathVariable Long id) {
        return bookService.getUserByBookId(id);
    }

}
