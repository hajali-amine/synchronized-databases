package seance1.bo;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Calendar;

import javax.swing.*;

//This GUI interface is a place holder to be finished by students.

public class InsertPanel extends JPanel{
    private Connection connection = null;
    private Statement statement = null;
    public JTextField regionTextFld;
    public JTextField productTextFld;
    public JTextField quantityTextFld;
    public JTextField costTextFld;
    public JTextField amtTextFld;
    public JTextField taxTextFld;
    public JTextField totalTextFld;
    public JTextArea textArea;
    public JButton submitBtn;

    DataTable dataTable;

    public InsertPanel(int i, DataTable dataTable) {
        switch (i) {
            case 1: setBackground(Color.DARK_GRAY);
            break;
            case 2: setBackground(new Color(0x7e4bb4));
            break;
            case 3: setBackground(new Color(0x7c1b1b));
            break;
            default:setBackground(Color.black);
        }
        this.dataTable=dataTable;
        setPreferredSize(new Dimension(1000, 1000));

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1000, 1000));

        JLabel regionLabel = new JLabel("Region: ");
        regionTextFld = new JTextField(15);

        JLabel productLabel = new JLabel("Product: ");
        productTextFld = new JTextField(20);

        JLabel quantityLabel = new JLabel("Quantity: ");
        quantityTextFld = new JTextField(10);

        JLabel costLabel = new JLabel("Cost: ");
        costTextFld = new JTextField(10);

        JLabel amtLabel = new JLabel("AMT: ");
        amtTextFld = new JTextField(10);

        JLabel taxLabel = new JLabel("Tax: ");
        taxTextFld = new JTextField(10);

        JLabel totalLabel = new JLabel("Total: ");
        totalTextFld = new JTextField(10);

        submitBtn = new JButton("Submit");
        ButtonListener buttonListener = new ButtonListener(i);
        submitBtn.addActionListener(buttonListener);

        textArea = new JTextArea(10, 30);

       //add current date
        p.add(regionLabel);
        p.add(regionTextFld);
        p.add(productLabel);
        p.add(productTextFld);
        p.add(quantityLabel);
        p.add(quantityTextFld);
        p.add(costLabel);
        p.add(costTextFld);
        p.add(amtLabel);
        p.add(amtTextFld);
        p.add(taxLabel);
        p.add(taxTextFld);
        p.add(totalLabel);
        p.add(totalTextFld);
        p.add(submitBtn);
        p.add(textArea);
        add(p, BorderLayout.NORTH);
        add(textArea, BorderLayout.CENTER);
    }

    public class ButtonListener implements ActionListener{
        int i;
        public ButtonListener(int i){
            this.i=i;
        }
        public void actionPerformed(ActionEvent e){
            try {
                Calendar calendar = Calendar.getInstance();
                java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

                String url = "jdbc:postgresql://localhost:5432/bo" + Integer.toString(i);
                String user="postgres";
                String password = "root";
                connection = DriverManager.getConnection(
                        url, user, password);

                    String region = regionTextFld.getText();
                    String product = productTextFld.getText();
                    int qty = Integer.parseInt(quantityTextFld.getText());
                    float cost = Float.parseFloat(costTextFld.getText());
                    double amt = Double.parseDouble(amtTextFld.getText());
                    float tax = Float.parseFloat(taxTextFld.getText());
                    double total = Double.parseDouble(totalTextFld.getText());


                // the mysql insert statement
                String query = " INSERT INTO product_sale(date, region, product, qty, cost, amt, tax, total)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setDate(1, startDate);
                preparedStmt.setString(2, region);
                preparedStmt.setString(3, product);
                preparedStmt.setInt(4, qty);
                preparedStmt.setFloat(5, cost);
                preparedStmt.setDouble(6, amt);
                preparedStmt.setFloat(7, tax);
                preparedStmt.setDouble(8, total);

                // execute the preparedstatement
                preparedStmt.execute();

                System.out.println(preparedStmt);

                connection.close();

                regionTextFld.setText("");
                productTextFld.setText("");
                quantityTextFld.setText("");
                costTextFld.setText("");
                amtTextFld.setText("");
                taxTextFld.setText("");
                totalTextFld.setText("");
                System.out.println("ajout table succes");
               dataTable.fillTable();
                System.out.println("ajout table succes");

            } catch (Exception exception){

            }
        }
    }
}
