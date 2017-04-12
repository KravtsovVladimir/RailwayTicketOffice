package epam.db.dao.interfaces;

import epam.db.dto.User;

import java.util.List;

public interface UserDao {

    User addUser(String email, String password, String name, String surname);

    User findById(int user_id);

    User findByEmailAndPassword(String email, String password);

    List<User> findAllUsers();

    List<User> findBlockedUsers();

    List<User> findUnblockedUsers();

    int blockUser(int user_id);

    int unblockUser(int user_id);

    int deleteUser(int user_id);

    boolean isExist(String email);

    boolean isBlocked(int user_id);

}
