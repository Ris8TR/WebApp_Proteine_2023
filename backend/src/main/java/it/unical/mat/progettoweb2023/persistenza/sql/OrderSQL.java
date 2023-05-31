package it.unical.mat.progettoweb2023.persistenza.sql;

import it.unical.mat.progettoweb2023.model.Ordine;
import it.unical.mat.progettoweb2023.model.Prodotto;
import it.unical.mat.progettoweb2023.persistenza.DAO.OrderDAO;
import it.unical.mat.progettoweb2023.persistenza.rowmappers.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderSQL implements OrderDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderSQL() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/progetto2023");
        dataSource.setUsername("postgres");
        dataSource.setPassword("toor");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Ordine> getOrdersbyEmail(String email) {
        String sql = "SELECT * FROM ordini WHERE email = ?";
        List<Ordine> orders = jdbcTemplate.query(sql, new Object[]{email}, new OrderRowMapper());
        for (Ordine order : orders) {
            Map<Prodotto, Integer> prodquant = new HashMap<>();
            sql = "SELECT p.nome, p2.quantita FROM prodotti p JOIN prodord p2 ON p2.id_prodotto = p.id_prodotto WHERE p2.n_ordine = ?";
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, order.getN_ordine());
            for (Map<String, Object> row : rows) {
                Prodotto prodotto = new Prodotto();
                prodotto.setNome((String) row.get("nome"));
                int quantita = (Integer) row.get("quantita");
                prodquant.put(prodotto, quantita);
            }
            order.setProdottoQuantita(prodquant);
        }
        return orders;
    }



    @Override
    public Ordine getOrderbyN(Integer n_ordine) {
        String sql = "SELECT * FROM ordini WHERE n_ordine = ?";
        try{
            Ordine order = jdbcTemplate.queryForObject(sql, new Object[]{n_ordine}, new OrderRowMapper());
            return order;}
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Ordine> getAllOrders() {
        String sql = "SELECT * FROM ordini";
        List<Ordine> orders = jdbcTemplate.query(sql, new OrderRowMapper());
        for (Ordine order : orders) {
            Map<Prodotto, Integer> prodquant = new HashMap<>();
            sql = "SELECT p.nome, p2.quantita FROM prodotti p JOIN prodord p2 ON p2.id_prodotto = p.id_prodotto WHERE p2.n_ordine = ?";
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, order.getN_ordine());
            for (Map<String, Object> row : rows) {
                Prodotto prodotto = new Prodotto();
                prodotto.setNome((String) row.get("nome"));
                int quantita = (Integer) row.get("quantita");
                prodquant.put(prodotto, quantita);
            }
            order.setProdottoQuantita(prodquant);
        }
        return orders;
    }

    @Override
    public Integer AddOrder(Ordine order) {
        String get = "SELECT MAX(n_ordine) FROM ordini";
        int maxorder = 0;
        boolean first = false;
        try{
            maxorder=jdbcTemplate.queryForObject("SELECT MAX(n_ordine) FROM ordini",Integer.class);
            }
        catch (EmptyResultDataAccessException e){
            maxorder = 0;
            first = true;
        }
        if(first){
            String sql = "INSERT INTO ordini(n_ordine,email,data,totale) " +
                    "VALUES (?, ?,?,?)";
            jdbcTemplate.update(sql,maxorder,
                    order.getEmail(),order.getData(),order.getTotale());
        }
        else{
            maxorder+=1;
            String sql = "INSERT INTO ordini(n_ordine,email,data,totale) " +
                    "VALUES (?, ?,?,?)";
            jdbcTemplate.update(sql,maxorder,
                    order.getEmail(),order.getData(),order.getTotale());
        }
        first = false;
        return maxorder;
    }

    @Override
    public void DeleteOrder(Ordine order) {
        String sql = "DELETE FROM ordini WHERE n_ordine = ?";
        jdbcTemplate.update(sql, order.getN_ordine());
    }
}
