package uk.signalfire.workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uk.signalfire.workflow.model.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByOwnerId(Long ownerId);

    @Query("SELECT b FROM Board b JOIN b.members m WHERE m.id = :userId")
    List<Board> findByMemberId(@Param("userId") Long userId);
}
