package services;

import models.User;
import dao.UserDAOImpl;

public class UserService {

    private final UserDAOImpl usersDao = new UserDAOImpl();

    public User getUserByLogin(String login) {
        return usersDao.getByLogin(login);
    }

    public User getUserByAuth(String login, String password) {
        return usersDao.getByAuth(login, password);
    }

    public void saveUser(User user) {
        usersDao.save(user);
    }

    public void updateUser(User user) {
        usersDao.update(user);
    }

    public void deleteUser(User user) {
        usersDao.delete(user);
    }

}
