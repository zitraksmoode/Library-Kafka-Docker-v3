package petProject.pet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
// Отключаем автоконфигурации, чтобы Kafka и БД не мешали
@ImportAutoConfiguration({})
class PetApplicationTests {

    @Test
    void contextLoads() {
        // Тест проверяет, что контекст Spring грузится без Kafka/DB
    }
}
