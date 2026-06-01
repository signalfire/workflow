package uk.signalfire.workflow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_column_id", nullable=false)
    private BoardColumn boardColumn;

    @NotBlank(message="Title is required")
    @Size(max=100, min=3, message="Title must be between 3 and 100 characters")
    @Column(nullable=false, length=100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message="Position is required")
    @Column(nullable=false)
    private Integer position;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="card_users",
            joinColumns = @JoinColumn(name="card_id"),
            inverseJoinColumns = @JoinColumn(name="user_id")
    )
    private List<User> assignees = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "card_tags",
            joinColumns = @JoinColumn(name = "card_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
