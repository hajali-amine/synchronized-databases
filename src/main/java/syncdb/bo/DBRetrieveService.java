package syncdb.bo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBRetrieveService {
    private int bo_num;

    public DBRetrieveService(int bo_num, boolean sent) {
        this.bo_num = bo_num;
        this.url = "jdbc:postgresql://localhost:543" + Integer.toString(bo_num) + "/bo" + Integer.toString(bo_num);
        query = "SELECT * FROM product_sale" + (sent ? "" : " where sent=FALSE");
    }

    //Coordonnées de la base
    public String url;
    public String user="postgres";
    public String password = "root";
    //Requete pour recuperer les données
    public String query;
    //Methodes pour recuperer les produits
    public List<Product> retrieve() throws SQLException{
        System.out.println(this.url);
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
                product.setBo_num(bo_num);
                res.add(product);
            }

            return res;
        }

    }
}
