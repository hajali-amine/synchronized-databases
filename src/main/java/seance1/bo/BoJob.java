package seance1.bo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;

public class BoJob {

    //Définir sa queue
    public final static String QUEUE_NAME="product_sale_queue";
    //Definir son DAO
    public static DBRetrieveService dbRetrieveService;
    public static DBUpdateService dbUpdateService;

    public static void main(String[] args) throws IOException, SQLException {
        int i = Integer.parseInt(args[0]);

        //Préparation de l'interface
        JFrame insertionFrame = new JFrame();
        insertionFrame.setVisible(true);
        insertionFrame.setTitle("Branch Office " + args[0]);

        DataTable dataTable = new DataTable(i);

        InsertPanel insertPanel = new InsertPanel(i, dataTable);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                insertPanel, dataTable.getScrollPane());
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(300);

        insertionFrame.add(splitPane);
        insertionFrame.setSize(700,450);
        insertionFrame.setLocation(500,250);

        //Instancier son DAO
        dbRetrieveService = new DBRetrieveService(i, false);
        dbUpdateService = new DBUpdateService(i);

        //Preparation necessaire pour le sender de RabbitMQ
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        //Definir le job
        TimerTask task = new TimerTask() {
            public void run(){
                try {
                    //Recuperer ses produits
                    List<Product> productList = dbRetrieveService.retrieve();
                    System.out.println(productList);
                    //Serialiser ses produits en mode JSON
                    String message = serialize(productList);

                    try (Connection connection = connectionFactory.newConnection()) {
                        Channel channel = connection.createChannel();
                        channel.queueDeclare(QUEUE_NAME  + Integer.toString(i), false, false, false, null);

                        channel.basicPublish("", QUEUE_NAME  + Integer.toString(i), null, message.getBytes());
                        System.out.println(" [x] sent '" + message + " '" + LocalDateTime.now().toString());
                        //Mise en TRUE de l'attribut sent dans la table de la base de données
                        dbUpdateService.update(productList);
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e){ }
            }
        };
        Timer timer = new Timer("Timer");

        //Ce job s'executera chaque minute
        long delay = 60*1000L;
        timer.schedule(task,0, delay);

    }

    public static String serialize(List<Product> productList) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(productList);
    }
}
