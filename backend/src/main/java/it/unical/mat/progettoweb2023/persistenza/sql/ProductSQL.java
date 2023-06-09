package it.unical.mat.progettoweb2023.persistenza.sql;


import it.unical.mat.progettoweb2023.persistenza.rowmappers.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Random;

import it.unical.mat.progettoweb2023.model.Prodotto;
import it.unical.mat.progettoweb2023.persistenza.DAO.ProductDAO;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin("http://localhost:4200")
public class ProductSQL implements ProductDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductSQL() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/progetto2023");
        dataSource.setUsername(Data.USER);
        dataSource.setPassword(Data.PASS);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    @CrossOrigin("http://localhost:4200")
    public List<Prodotto> getAllProducts() {
        String sql = "SELECT * FROM prodotti";
        List<Prodotto> products = jdbcTemplate.query(sql, new ProductRowMapper());
        return products;
    }

    @Override
    @CrossOrigin("http://localhost:4200")
    public List<Prodotto> getAllProductsByCategory(String Category) {
        String sql = "SELECT * FROM prodotti WHERE categoria = ?";
        List<Prodotto> products = jdbcTemplate.query(sql, new Object[]{Category}, new ProductRowMapper());
        return products;
    }

    @Override
    @CrossOrigin("http://localhost:4200")
    public List<Prodotto> getProductsBySearch(String search) {
        String sql = "SELECT * FROM prodotti WHERE LOWER(prodotti.nome) ILIKE LOWER(?) OR LOWER(prodotti.marchio) ILIKE LOWER(?)";
        String searchTerm = "%" + search.toLowerCase() + "%";
        List<Prodotto> products = jdbcTemplate.query(sql, new Object[]{searchTerm, searchTerm}, new ProductRowMapper());
        return products;
    }




    @Override
    @CrossOrigin("http://localhost:4200")
    public Prodotto getProductbyId(Integer id_prodotto) {
        String sql = "SELECT * FROM prodotti WHERE id_prodotto = ?";
        try{Prodotto product = jdbcTemplate.queryForObject(sql, new Object[]{id_prodotto}, new ProductRowMapper());
            return product;}
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public Integer getPrezzo(Integer id_prodotto) {
        String sql = "SELECT prezzo FROM prodotti WHERE id_prodotto = ?";
        try{Integer prezzo = jdbcTemplate.queryForObject(sql, new Object[]{id_prodotto}, Integer.class);
            return prezzo;}
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Integer GenID(){
        Random random = new Random(System.currentTimeMillis());
        while(true){
            int num = random.nextInt(Integer.MAX_VALUE);
            String sql = "SELECT prezzo FROM prodotti WHERE id_prodotto = ?";
            try{Integer prezzo = jdbcTemplate.queryForObject(sql, new Object[]{num}, Integer.class);}
            catch (EmptyResultDataAccessException e){
                return num;
            }
        }

    }

    @Override
    public void AddProduct(Prodotto prodotto) {
        String sql = "INSERT INTO prodotti(id_prodotto,nome,marchio,size_cps,size_gr," +
                "vegan,available,lactose_free,prezzo,descrizione,valori_nutrizionali," +
                "categoria,foto_prod) " +
                "VALUES (?, ?,?, ?,?, ?,?, ?,?,?,?,?,?)";
        jdbcTemplate.update(sql,prodotto.getId_prodotto(),prodotto.getNome(),prodotto.getMarchio(),
                prodotto.getSize_cps(),prodotto.getSize_gr(),prodotto.getVegan(),
                prodotto.getAvailable(),prodotto.getLactose_free(),prodotto.getPrezzo(),
                prodotto.getDescrizione(),prodotto.getVal_nutr().getBytes(),prodotto.getCategoria(),prodotto.getFoto().getBytes());
    }

    @Override
    public void DeleteProduct(Integer ID) {
        String sql = "DELETE FROM prodotti WHERE id_prodotto = ?";
        jdbcTemplate.update(sql, ID);
    }

}
