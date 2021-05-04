package seance1.bo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DBUpdateService {
    private int bo_num;

    public DBUpdateService(int bo_num) {
        this.bo_num = bo_num;
        this.url = "jdbc:postgresql://localhost:5432/bo" + Integer.toString(bo_num);
    }
    //Coordonnées de la base
    public String url;
    public String user="postgres";
    public String password = "root";
    //Requete pour la mise en TRUE de l'attribut sent
    public String query = "UPDATE product_sale set sent = TRUE where id = ?";
    //Methode pour la mise en TRUE de l'attribut sent
    public void update(List<Product> productList) throws SQLException {
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pst = connection.prepareStatement(query)
        ){
            for(int i=0; i<productList.size(); i++) {
                pst.setInt(1, productList.get(i).getId());
                pst.executeUpdate();
            }
        }
    }
}
