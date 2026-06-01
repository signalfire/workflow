package uk.signalfire.workflow.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.signalfire.workflow.dto.board.BoardResponse;
import uk.signalfire.workflow.dto.board.CreateBoardRequest;
import uk.signalfire.workflow.service.BoardService;

import java.util.List;


@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("")
    public List<BoardResponse> getBoards() {
        return boardService.getBoardsByUser();
    }

    @PostMapping("")
    public ResponseEntity<BoardResponse> createBoard(@Valid @RequestBody CreateBoardRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.createBoard(request));
    }

    @GetMapping("/{id}")
    public BoardResponse getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(@PathVariable Long id, @Valid @RequestBody CreateBoardRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.updateBoard(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id){
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }

}