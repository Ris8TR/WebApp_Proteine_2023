package it.unical.mat.progettoweb2023.persistenza.rowmappers;

import it.unical.mat.progettoweb2023.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("passw"));
        user.setName(rs.getString("nome"));
        user.setLastname(rs.getString("cognome"));
        user.setStreet_1(rs.getString("via_1"));
        user.setStreet_2(rs.getString("via_2"));
        user.setCap(rs.getInt("cap"));
        user.setCity(rs.getString("citta"));
        user.setCountry(rs.getString("nazione"));
        user.setCellphone(rs.getString("cellulare"));
        user.setAdmin(rs.getBoolean("admin"));
        return user;
    }
}
