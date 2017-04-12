package epam.db.service;

import epam.db.dao.DaoFactory;
import epam.db.dao.interfaces.UserDao;
import epam.db.dto.User;

import java.util.List;

public class UserService {

    private final UserDao userDao;

    public UserService() {
        this.userDao = DaoFactory.getInstance().getUserDao();
    }

    public User addUser(String email, String password, String name, String surname) {
        return userDao.addUser(email, password, name, surname);
    }

    public User findUser(final Integer userId) {
        return userDao.findById(userId);
    }

    public User findUser(final String email, final String password) {
        return userDao.findByEmailAndPassword(email, password);
    }

    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }

    public boolean isExist(String email) {
        return userDao.isExist(email);
    }

    public int deleteUsers(List<Integer> list) {
        int rows = 0;
        for (int i = 0; i < list.size(); i++) {
            rows += userDao.deleteUser(list.get(i));
        }
        return rows;
    }

    public int blockUsers(List<Integer> list) {
        int rows = 0;
        for (int i = 0; i < list.size(); i++) {
            rows += userDao.blockUser(list.get(i));
        }
        return rows;
    }

    public int unblockUsers(List<Integer> list) {
        int rows = 0;
        for (int i = 0; i < list.size(); i++) {
            rows += userDao.unblockUser(list.get(i));
        }
        return rows;
    }

    public boolean isBlocked(int user_id) {
        return userDao.isBlocked(user_id);
    }

}
