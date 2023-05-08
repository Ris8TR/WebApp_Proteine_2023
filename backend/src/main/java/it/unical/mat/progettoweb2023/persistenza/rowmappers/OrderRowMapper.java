package it.unical.mat.progettoweb2023.persistenza.rowmappers;


import it.unical.mat.progettoweb2023.model.Ordine;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
public class OrderRowMapper implements RowMapper<Ordine>{
    @Override
    public Ordine mapRow(ResultSet rs, int rowNum) throws SQLException {
        Ordine order = new Ordine();
        order.setN_ordine(rs.getInt("n_ordine"));
        order.setEmail(rs.getString("email"));
        order.setData(rs.getString("data"));
        order.setTotale(rs.getInt("totale"));
        return order;
    }
}
