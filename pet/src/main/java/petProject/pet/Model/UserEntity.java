package petProject.pet.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_users_phone", columnNames = "phone_number")
        },
        indexes = {
                @Index(name = "idx_users_name", columnList = "name")
        }
)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Имя пользователя */
    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно содержать от 2 до 100 символов")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /** Пароль (хранить только захэшированным!) */
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
    @Column(name = "password", nullable = false)
    private String password;

    /** Баланс/сумма на счёте */
    @NotNull(message = "Сумма не может быть null")
    @Min(value = 0, message = "Сумма должна быть не меньше 0")
    @Column(name = "amount", nullable = false)
    private Long amount = 0L;

    /** Уникальный номер телефона */
    @NotBlank(message = "Телефон не может быть пустым")
    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Номер телефона должен быть в формате +79991234567")
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    /** Когда запись была создана */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /** Когда запись последний раз изменялась */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /** Мягкое удаление (soft delete) */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

}
