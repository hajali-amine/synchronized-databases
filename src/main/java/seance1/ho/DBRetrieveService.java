package seance1.ho;

import seance1.ho.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBRetrieveService {
    //Coordonnées de la base
    public String user="postgres";
    public String password = "root";
    public String url = "jdbc:postgresql://localhost:5432/ho";

//Requete pour recuperer les données
    public String query = "SELECT * FROM product_sale";
    //Methodes pour recuperer les produits
    public List<Product> retrieve() throws SQLException {
        List<Product> res = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery()
        ) {

            while(rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setDate(rs.getDate("date"));
                product.setRegion(rs.getString("region"));
                product.setProduct(rs.getString("product"));
                product.setQty(rs.getInt("qty"));
                product.setCost(rs.getFloat("cost"));
                product.setAmt(rs.getDouble("amt"));
                product.setTax(rs.getFloat("tax"));
                product.setTotal(rs.getDouble("total"));
                product.setBo_num(rs.getInt("bo_num"));
                res.add(product);
            }

            return res;
        }
    }
}

