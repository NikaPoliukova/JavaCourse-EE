package example.repository;


import example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

  List<User> findUserWithSearch(String name);

  User findUserByNameAndPassword(String name, String password);

  String findUserByName(String userName);

}

