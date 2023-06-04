package it.unical.mat.progettoweb2023.persistenza.sql;

import it.unical.mat.progettoweb2023.model.User;
import it.unical.mat.progettoweb2023.persistenza.DAO.UserDAO;
import it.unical.mat.progettoweb2023.persistenza.rowmappers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import java.util.List;
@Repository
public class UserSQL implements UserDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserSQL() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/progetto2023");
        dataSource.setUsername(Data.USER);
        dataSource.setPassword(Data.PASS);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM utenti";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper());
        return users;
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM utenti WHERE email = ?";
        try{User user = jdbcTemplate.queryForObject(sql, new Object[]{email}, new UserRowMapper());
            return user;}
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public void createUser(User user) {
        String sql = "INSERT INTO utenti(email,passw,nome,cognome,via_1,via_2,cap,citta,nazione,cellulare,admin) " +
                "VALUES (?, ?,?, ?,?, ?,?, ?,?,?,?)";
        jdbcTemplate.update(sql,user.getEmail(),user.getPassword(),
                user.getName(),user.getLastname(),user.getStreet_1(),user.getStreet_2(),
                user.getCap(),user.getCity(),user.getCountry(),user.getCellphone(),user.getAdmin());
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE utenti SET email = ?,passw = ?,nome = ?,cognome = ?,via_1 = ?," +
                "via_2 = ?,cap = ?,citta = ?,nazione = ?,cellulare = ?,admin = ? " +
                "WHERE email = ?";
        jdbcTemplate.update(sql,user.getEmail(),user.getPassword(),
                user.getName(),user.getLastname(),user.getStreet_1(),user.getStreet_2(),
                user.getCap(),user.getCity(),user.getCountry(),user.getCellphone(),user.getAdmin(),user.getEmail());
    }

    @Override
    public void deleteUser(String email) {
        String sql = "DELETE FROM utenti WHERE email = ?";
        jdbcTemplate.update(sql, email);
    }
}

