package it.unical.mat.progettoweb2023.persistenza.rowmappers;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import it.unical.mat.progettoweb2023.model.Prodotto;
public class ProductRowMapper implements RowMapper<Prodotto> {
    @Override
    public Prodotto mapRow(ResultSet rs, int rowNum) throws SQLException {
        Prodotto product = new Prodotto();
        product.setId_prodotto(rs.getInt("id_prodotto"));
        product.setNome(rs.getString("nome"));
        product.setMarchio(rs.getString("marchio"));
        product.setSize_cps(rs.getInt("size_cps"));
        product.setSize_gr(rs.getInt("size_gr"));
        product.setVegan(rs.getBoolean("vegan"));
        product.setAvailable(rs.getBoolean("available"));
        product.setLactose_free(rs.getBoolean("lactose_free"));
        product.setPrezzo(rs.getInt("prezzo"));
        product.setDescrizione(rs.getString("descrizione"));
        product.setVal_nutr(rs.getBytes("valori_nutrizionali"));
        product.setCategoria(rs.getString("categoria"));
        return product;
    }
}
