package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import shop.mtcoding.blog._core.Constant;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;
    private final JdbcTemplate jdbcTemplate;

    public int count(){

        return 1;
    }

    public List<Board> findAll(int page){
        final int COUNT = 3;
        int value = page*COUNT;
        int value2 = page* Constant.PAGING_COUNT; // 체크
        Query query = em.createNativeQuery("select * from board_tb order by id desc limit ?,?", Board.class);
        query.setParameter(1, value);
        query.setParameter(2, COUNT);
        query.setParameter(2, Constant.PAGING_COUNT);

        List<Board> boardList = query.getResultList();
        return boardList;
    }
}