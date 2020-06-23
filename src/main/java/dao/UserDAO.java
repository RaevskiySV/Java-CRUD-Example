package dao;

import models.User;
import java.util.List;

public interface UserDAO {

    User getByLogin(String login);

    User getByAuth(String password, String login);

    void save(User user);

    void update(User user);

    void delete(User user);
}
