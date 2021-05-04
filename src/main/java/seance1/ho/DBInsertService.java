package seance1.ho;

import java.sql.*;
import java.util.List;

public class DBInsertService {
    //coordonnées de la base
    public String url = "jdbc:postgresql://localhost:5432/ho";
    public String user="postgres";
    public String password = "root";
    //Requete pour inserer dans la base
    public String query = "INSERT INTO product_sale(date, region, product, qty, cost, amt, tax, total, bo_num) values(?,?,?,?,?,?,?,?,?)";

    //Methode pour inserer dans la base
    public void insert(List<Product> productList) throws SQLException {
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pst = connection.prepareStatement(query)
        ){
            for(int i=0; i<productList.size(); i++) {
                System.out.println(i);
                Product p = productList.get(i);
                pst.setDate(1, new Date(p.getDate().getTime()));
                pst.setString(2, p.getRegion());
                pst.setString(3, p.getProduct());
                pst.setInt(4, p.getQty());
                pst.setFloat(5, p.getCost());
                pst.setDouble(6, p.getAmt());
                pst.setFloat(7, p.getTax());
                pst.setDouble(8, p.getTotal());
                pst.setInt(9,p.getBo_num());
                pst.executeUpdate();
            }
        }
    }
}
