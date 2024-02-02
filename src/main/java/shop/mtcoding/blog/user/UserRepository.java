package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class UserRepository { //= dao

    private EntityManager em;

    public UserRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional//org뭐시기 넣어야함
    public void save(UserRequest.JoinDTO requestDTO){
        Query query=em.createNativeQuery("insert into user_tb(username, password, email) values(?, ?, ?)"); //내가 쿼리를 직접짰고
        query.setParameter(1, requestDTO.getUsername());
        query.setParameter(2, requestDTO.getPassword());
        query.setParameter(3, requestDTO.getEmail());

        query.executeUpdate();
    }

    @Transactional //org뭐시기 넣어야함
    public void saveV2(UserRequest.JoinDTO requestDTO){
        User user= new User(); //엔티티클래스에 옮겼음 //하이퍼네이트한테 클래스를 줌 하이퍼네이트가 insert문을 만들어서 넣어줌
        user.setUsername(requestDTO.getUsername());
        user.setPassword(requestDTO.getPassword());
        user.setEmail(requestDTO.getEmail());
        em.persist(user);
    }

    //Transactional select 할땐 필요없음 : 바뀌는 행이 없으므로
    public User findByUsernameAndPassword(UserRequest.LoginDTO requestDTO) { //조회
        Query query=em.createNativeQuery("select * from user_tb where username=? and password=?", User.class); //user타입(엔티티)으로 바로 맵핑할 수 있음, 엔티티=테이블
        query.setParameter(1, requestDTO.getUsername());
        query.setParameter(2, requestDTO.getPassword());

        try{
            User user=(User)query.getSingleResult(); //하나의 행을 리턴할때 여러개는 resultlist
            return user;
        }catch (Exception e){
            return null;
        }
        //query.executeUpdate();업데이트할게 아님

    }

    public User findByUsername(UserRequest.JoinDTO requestDTO) { //조회
        Query query=em.createNativeQuery("select * from user_tb where username=? ", User.class); //user타입(엔티티)으로 바로 맵핑할 수 있음, 엔티티=테이블
        query.setParameter(1, requestDTO.getUsername());

        try{
            User user=(User)query.getSingleResult(); //하나의 행을 리턴할때 여러개는 resultlist
            return user;
        }catch (Exception e){
            return null;
        }
        //query.executeUpdate();업데이트할게 아님

    }
}