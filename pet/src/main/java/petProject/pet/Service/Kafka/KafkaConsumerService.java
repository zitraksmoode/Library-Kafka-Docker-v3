package petProject.pet.Service.Kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "pet-topic", groupId = "pet-group")
    public void listen(String message) {
        System.out.println("📩 Received message from Kafka: " + message);
    }
}
