package epam.db.dao.implementation;

import epam.connection.ConnectionProxy;
import epam.db.dao.interfaces.UserDao;
import epam.db.dto.User;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
    private static final String QUERY_ADD_USER = "INSERT INTO user (email, password, name, surname) VALUE (?,?,?,?)";
    private static final String QUERY_FIND_BY_ID = "SELECT * FROM user WHERE user_id = ?";
    private static final String QUERY_FIND_BY_EMAIL_AND_PASSWORD = "SELECT * FROM user WHERE email = ? AND password = ?";
    private static final String QUERY_IS_EXIST = "SELECT * FROM user WHERE email = ?";
    private static final String QUERY_FIND_ALL_USERS = "SELECT * FROM user";
    private static final String QUERY_FIND_BLOCKED_USERS = "SELECT * FROM user WHERE isBlocked = TRUE";
    private static final String QUERY_FIND_UNBLOCKED_USERS = "SELECT * FROM user WHERE isBlocked = FALSE";
    private static final String QUERY_BLOCK_USER = "UPDATE user SET isBlocked = TRUE WHERE user_id = ?";
    private static final String QUERY_UNBLOCK_USER = "UPDATE user SET isBlocked = FALSE WHERE user_id = ?";
    private static final String QUERY_DELETE_USER = "DELETE FROM user WHERE user_id = ?";
    private static final String QUERY_IS_BLOCKED = "SELECT isBlocked FROM user WHERE user_id = ?";

    @Override
    public User addUser(String email, String password, String name, String surname) {
        User user = null;

        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnectionProxy();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(QUERY_ADD_USER)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, surname);
            preparedStatement.execute();

            PreparedStatement pstm = connectionProxy.prepareStatement(QUERY_FIND_BY_EMAIL_AND_PASSWORD);
            pstm.setString(1, email);
            pstm.setString(2, password);

            ResultSet rs = pstm.executeQuery();
            rs.next();
            user = new User();
            user.setUser_id(rs.getInt("user_id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            user.setAdmin(rs.getBoolean("isAdmin"));
            user.setBlocked(rs.getBoolean("isBlocked"));
            user.setTicketCounter(rs.getInt("ticketCounter"));
            rs.close();

        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }
        return user;
    }

    @Override
    public User findById(int user_id) {

        User user = null;

        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnectionProxy();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(QUERY_FIND_BY_ID)) {

            preparedStatement.setInt(1, user_id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setTicketCounter(rs.getInt("ticketCounter"));
                user.setAdmin(rs.getBoolean("isAdmin"));
                user.setBlocked(rs.getBoolean("isBlocked"));
            }

            rs.close();
        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }
        return user;
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        User user = null;

        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnectionProxy();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(QUERY_FIND_BY_EMAIL_AND_PASSWORD)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setTicketCounter(rs.getInt("ticketCounter"));
                user.setAdmin(rs.getBoolean("isAdmin"));
                user.setBlocked(rs.getBoolean("isBlocked"));
            }

            rs.close();
        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();

        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnectionProxy();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(QUERY_FIND_ALL_USERS);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setTicketCounter(rs.getInt("ticketCounter"));
                user.setBlocked(rs.getBoolean("isBlocked"));
                user.setAdmin(rs.getBoolean("isAdmin"));
                users.add(user);
            }

        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }
        return users;
    }

    @Override
    public List<User> findBlockedUsers() {
        List<User> users = new ArrayList<>();

        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnectionProxy();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(QUERY_FIND_BLOCKED_USERS);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setBlocked(rs.getBoolean("isBlocked"));
                user.setTicketCounter(rs.getInt("ticketCounter"));
                user.setAdmin(rs.getBoolean("isAdmin"));
                users.add(user);
            }

        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }
        return users;
    }

    @Override
    public List<User> findUnblockedUsers() {
        List<User> users = new ArrayList<>();

        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnectionProxy();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(QUERY_FIND_UNBLOCKED_USERS);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setSurname(rs.getString("surname"));
                user.setName(rs.getString("name"));
                user.setTicketCounter(rs.getInt("ticketCounter"));
                user.setAdmin(rs.getBoolean("isAdmin"));
                user.setBlocked(rs.getBoolean("isBlocked"));
                users.add(user);
            }

        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }
        return users;
    }

    @Override
    public int blockUser(int user_id) {
        int rows = 0;

        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnectionProxy();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(QUERY_BLOCK_USER)) {

            preparedStatement.setInt(1, user_id);
            rows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }
        return rows;
    }

    @Override
    public int unblockUser(int user_id) {
        int rows = 0;

        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnectionProxy();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(QUERY_UNBLOCK_USER)) {

            preparedStatement.setInt(1, user_id);
            rows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }
        return rows;
    }

    @Override
    public int deleteUser(int user_id) {
        int rows = 0;

        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnectionProxy();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(QUERY_DELETE_USER)) {

            preparedStatement.setInt(1, user_id);
            rows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }
        return rows;
    }

    @Override
    public boolean isExist(String email) {

        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnectionProxy();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(QUERY_IS_EXIST)) {

            preparedStatement.setString(1, email);

            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }
        return false;
    }

    @Override
    public boolean isBlocked(int user_id) {

        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnectionProxy();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(QUERY_IS_BLOCKED)) {

            preparedStatement.setInt(1, user_id);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                return rs.getBoolean("isBlocked");
            }

        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }
        return false;
    }
}
