package it.unical.mat.progettoweb2023.persistenza.sql;

import it.unical.mat.progettoweb2023.model.ProdOrd;
import it.unical.mat.progettoweb2023.persistenza.DAO.ProdOrdDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProdOrdSQL implements ProdOrdDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProdOrdSQL() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/progetto2023");
        dataSource.setUsername("postgres");
        dataSource.setPassword("progetto2023");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void AddProdOrd(ProdOrd order, int id) {
      List<Integer> prodotti = order.getId_prodotti();
      List<Integer> quantita = order.getQuantita();
      String sql = "INSERT INTO prodord(n_ordine,id_prodotto,quantita) VALUES (?,?,?)";
      for(int i=0;i<prodotti.size();i++){
          jdbcTemplate.update(sql,id,prodotti.get(i),quantita.get(i));
      }
    }

    @Override
    public void DelProdOrd(int id) {
        String sql = "DELETE FROM prodord WHERE n_ordine = ?";
        jdbcTemplate.update(sql, id);
    }
}
