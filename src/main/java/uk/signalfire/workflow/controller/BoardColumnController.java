package uk.signalfire.workflow.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.signalfire.workflow.dto.boardcolumn.BoardColumnResponse;
import uk.signalfire.workflow.dto.boardcolumn.CreateBoardColumnRequest;
import uk.signalfire.workflow.service.BoardColumnService;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardColumnController {

    private final BoardColumnService boardColumnService;

    @GetMapping("/{boardId}/columns")
    public List<BoardColumnResponse> getBoardColumns(@PathVariable Long boardId) {
        return boardColumnService.getBoardColumns(boardId);
    }

    @PostMapping("/{boardId}/columns")
    public ResponseEntity<BoardColumnResponse> createBoardColumn(@PathVariable Long boardId, @Valid @RequestBody CreateBoardColumnRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boardColumnService.createBoardColumn(boardId, request));
    }

    @PutMapping("/{boardId}/columns/{columnId}")
    public ResponseEntity<BoardColumnResponse> updateBoardColumn(@PathVariable Long boardId, @PathVariable Long columnId, @Valid @RequestBody CreateBoardColumnRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(boardColumnService.updateBoardColumn(boardId, columnId, request));
    }

    @DeleteMapping("/{boardId}/columns/{columnId}")
    public ResponseEntity<Void> deleteBoardColumn(@PathVariable Long boardId, @PathVariable Long columnId){
        boardColumnService.deleteBoardColumn(boardId, columnId);
        return ResponseEntity.noContent().build();
    }

}