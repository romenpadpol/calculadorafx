/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.iespuertodelacruz.romen.modelo;

import es.iespuertodelacruz.romen.controlador.FXMLVistaController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Romentoss
 * 
 */
/*Método para la llamada de la parte visual, tanto el fxml como una imagen png para el icono de nuestra app.*/
public class CalculadoraModel extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
       

        FXMLLoader loader = new  FXMLLoader(getClass().getResource("/es/iespuertodelacruz/romen/vista/FXMLVista_1.fxml"));
        loader.setController(new FXMLVistaController());
        Pane mainPane = loader.load(); 
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.getIcons().add(new Image("/imagenes/calc.png"));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    
    /*Método main donde nos encargamos de realizar la conexión con la base de datos y mostramos por consola las operaciones que estan registradas en la db. 
    antes de ejecutarse el programa. */ 
    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/historial","root","root");
            
            Statement mySt = con.createStatement();
            ResultSet myRt = mySt.executeQuery("select * from operaciones");
           
        
            while (myRt.next()) {
                System.out.println(myRt.getString("IdOperacion")+"."+myRt.getString("Operando1")+" "+myRt.getString("Operacion")+" "+
                        myRt.getString("Operando2")+ " = " + myRt.getString("Resultado"));                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        launch(args);
    }
    
}
