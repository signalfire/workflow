package uk.signalfire.workflow.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uk.signalfire.workflow.dto.boardcolumn.BoardColumnResponse;
import uk.signalfire.workflow.dto.boardcolumn.CreateBoardColumnRequest;
import uk.signalfire.workflow.model.Board;
import uk.signalfire.workflow.model.BoardColumn;
import uk.signalfire.workflow.model.User;
import uk.signalfire.workflow.repository.BoardColumnRepository;
import uk.signalfire.workflow.repository.BoardRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardColumnService {

    private final BoardRepository boardRepository;
    private final BoardColumnRepository boardColumnRepository;

    @Transactional
    public BoardColumnResponse createBoardColumn(Long boardId, CreateBoardColumnRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));

        if (!board.getOwner().getId().equals(user.getId()) &&
                board.getMembers().stream().noneMatch(m -> m.getId().equals(user.getId()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        BoardColumn column = new BoardColumn();
        column.setBoard(board);
        column.setTitle(request.title());
        column.setPosition(request.position());

        BoardColumn saved = boardColumnRepository.save(column);

        return mapToBoardColumnResponse(saved);
    }

    @Transactional
    public List<BoardColumnResponse> getBoardColumns(Long boardId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));
        if (!board.getOwner().getId().equals(user.getId()) &&
                board.getMembers().stream().noneMatch(m -> m.getId().equals(user.getId()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        List<BoardColumn> columns = boardColumnRepository.findByBoardId(boardId);

        return columns.stream()
                .map(this::mapToBoardColumnResponse)
                .toList();
    }

    @Transactional
    public BoardColumnResponse updateBoardColumn(Long boardId, Long boardColumnId, CreateBoardColumnRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));

        if (!board.getOwner().getId().equals(user.getId()) &&
                board.getMembers().stream().noneMatch(m -> m.getId().equals(user.getId()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        BoardColumn column = boardColumnRepository.findById(boardColumnId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board Column not found"));

        if (!column.getBoard().getId().equals(board.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        column.setTitle(request.title());
        column.setPosition(request.position());

        BoardColumn saved = boardColumnRepository.save(column);

        return mapToBoardColumnResponse(saved);
    }

    @Transactional
    public void deleteBoardColumn(Long boardId, Long boardColumnId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));
        if (!board.getOwner().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        BoardColumn column = boardColumnRepository.findById(boardColumnId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board Column not found"));

        if (!column.getBoard().getId().equals(board.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        boardColumnRepository.delete(column);
    }

    private BoardColumnResponse mapToBoardColumnResponse(BoardColumn column) {
        return new BoardColumnResponse(
                column.getId(),
                column.getBoard().getId(),
                column.getTitle(),
                column.getPosition(),
                column.getCreatedAt(),
                column.getUpdatedAt()
        );
    }
}