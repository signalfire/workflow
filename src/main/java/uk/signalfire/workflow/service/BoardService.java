package uk.signalfire.workflow.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uk.signalfire.workflow.dto.board.BoardResponse;
import uk.signalfire.workflow.dto.board.CreateBoardRequest;
import uk.signalfire.workflow.dto.user.UserSummaryResponse;
import uk.signalfire.workflow.model.Board;
import uk.signalfire.workflow.model.User;
import uk.signalfire.workflow.repository.BoardRepository;

import java.util.List;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardResponse createBoard(CreateBoardRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Board board = new Board();
        board.setTitle(request.title());
        board.setOwner(user);

        Board saved = boardRepository.save(board);

        return mapToBoardResponse(saved);
    }

    @Transactional
    public List<BoardResponse> getBoardsByUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Board> ownedBoards = boardRepository.findByOwnerId(user.getId());
        List<Board> memberBoards = boardRepository.findByMemberId(user.getId());

        return Stream.concat(ownedBoards.stream(), memberBoards.stream())
                .distinct()
                .map(this::mapToBoardResponse)
                .toList();
    }

    @Transactional
    public BoardResponse getBoard(Long boardId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));
        if (!board.getOwner().getId().equals(user.getId()) &&
                board.getMembers().stream().noneMatch(m -> m.getId().equals(user.getId()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        return mapToBoardResponse(board);
    }

    @Transactional
    public BoardResponse updateBoard(Long boardId, CreateBoardRequest request){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));
        if (!board.getOwner().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        board.setTitle(request.title());
        Board saved = boardRepository.save(board);

        return mapToBoardResponse(saved);
    }

    @Transactional
    public void deleteBoard(Long boardId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));
        if (!board.getOwner().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        boardRepository.delete(board);
    }

    private BoardResponse mapToBoardResponse(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getMembers().stream().map( member -> new UserSummaryResponse(
                        member.getId(),
                        member.getEmail(),
                        member.getFirstName(),
                        member.getLastName()
                )).toList(),
                board.getTitle(),
                new UserSummaryResponse(
                        board.getOwner().getId(),
                        board.getOwner().getEmail(),
                        board.getOwner().getFirstName(),
                        board.getOwner().getLastName()
                ),
                board.getCreatedAt(),
                board.getUpdatedAt()
        );
    }
}