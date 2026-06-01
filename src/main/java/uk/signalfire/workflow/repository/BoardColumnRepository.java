package uk.signalfire.workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.signalfire.workflow.model.BoardColumn;

import java.util.List;

public interface BoardColumnRepository extends JpaRepository<BoardColumn, Long> {
    List<BoardColumn> findByBoardId(Long boardId);
}
