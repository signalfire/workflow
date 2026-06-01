package uk.signalfire.workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.signalfire.workflow.model.Card;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByBoardColumnId(Long boardColumnId);
}
