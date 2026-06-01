package uk.signalfire.workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.signalfire.workflow.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCardId(Long cardId);
}
