package it.unical.mat.progettoweb2023.persistenza.DAO;

import it.unical.mat.progettoweb2023.model.User;

import java.util.List;

public interface UserDAO {

    List<User> getAllUsers();

    User getUserByEmail(String email);

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(String email);
}
