package site.joshua.acs.mapper;

import site.joshua.acs.domain.Board;

import java.util.List;

public interface BoardMapper {

    int boardCount();

    List<Board> getList();

    Board getBoard(Long boardId);

    void uploadBoard(Board board);

    void updateBoard(Board board);

    void deleteBoard(Long boardId);

    void viewCount(Long boardId);
}
