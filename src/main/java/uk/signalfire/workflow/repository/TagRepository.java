package uk.signalfire.workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.signalfire.workflow.model.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByBoardId(Long boardId);
}
