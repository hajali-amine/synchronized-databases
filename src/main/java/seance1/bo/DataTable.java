package seance1.bo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataTable{
    final Object[] column = {"Date","Region","Product","Quantity","Cost","AMT","Tax","Total"};
    private JScrollPane scrollPane;
    private JTable dataTable;
    DefaultTableModel dtm;

    private Connection connection = null;
    private Statement statement = null;
    int i;
    public DataTable(int i){
        Object[][] data = {};
        this.i = i;
        this.dtm = new DefaultTableModel(data, this.column);
        this.dataTable =new JTable(dtm);
        this.dataTable.setBounds(30,40,200,300);
        this.scrollPane = new JScrollPane(this.dataTable);
        try {
            this.fillTable();
        } catch (SQLException sqlException){

        }

    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void fillTable() throws SQLException {
        dtm.setRowCount(0);
        DBRetrieveService dbRetrieveService = new DBRetrieveService(i, true);
        List<Product> productList = dbRetrieveService.retrieve();
        for (Product p : productList){
            dtm.addRow(new Object[]{p.getDate().toString(),
                    p.getRegion(),
                    p.getProduct(),
                    Integer.toString(p.getQty()),
                    Float.toString(p.getCost()),
                    Double.toString(p.getAmt()),
                    Float.toString(p.getTax()),
                    Double.toString(p.getTotal()),
            });
        }

    }
}
