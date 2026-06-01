package uk.signalfire.workflow.model;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id", nullable=false)
    private Board board;

    @NotBlank(message="Name is required")
    @Size(max=100, min=3, message="Name must be between 3 and 100 characters")
    @Column(nullable=false, length=100)
    private String name;

    @NotBlank(message="Colour is required")
    @Size(min=7, max=7, message="Colour must be hex string of 7 characters")
    @Column(nullable=false, length=7)
    private String colour;

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
