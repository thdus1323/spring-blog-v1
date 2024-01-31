package shop.mtcoding.blog.user;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
public class UserRepository {

    private EntityManager em;

    public UserRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    //기본기
    public void save(UserRequest.JoinDTO requestDTO) {
        Query query = em.createNativeQuery("insert into user_tb(username, password, email) values(?, ?, ?)");
        query.setParameter(1, requestDTO.getUsername());
        query.setParameter(2, requestDTO.getPassword());
        query.setParameter(3, requestDTO.getEmail());

        query.executeUpdate();

    }

    @Transactional
    //하이버네이트 기술
    public void saveV2(UserRequest.JoinDTO requestDTO) {
        User user = new User();
        user.setUsername(requestDTO.getUsername());
        user.setPassword(requestDTO.getPassword());
        user.setEmail(requestDTO.getEmail());

        em.persist(user);
    }

    public User findByUsernameAndPassword(String username) {
        Query query = em.createNativeQuery("select * from user_tb where username=?", User.class);
        query.setParameter(1, username);

        try {
            User user = (User) query.getSingleResult();
            return true;
        }catch (Exception e){
            return false;
        }
        //single = 한건 리턴될때
        //resultList = 여러건 리턴될 때

    }

    public User findByUsernameAndPassword(String username) {
    }
}