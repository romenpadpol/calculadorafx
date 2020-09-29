/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.iespuertodelacruz.romen.controlador;

import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Romentoss
 */
/*
En el controlador podemos encontrar todos los botones que usaremos en nuestra calculadora.
*/
public class FXMLVistaController implements Initializable {
    
    public String operadorr = " ";
    public boolean Digito = false;
    public boolean Punto = false;
    public int numOperandos= 0;
    public double Operando1, Operando2,Resultado;
    public char Operador=' ';
    /*
    Label donde se va a mostrar el dígito y los resultados de las operaciones. 
    */
    @FXML
    private Label LabPantalla;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
    }    

    @FXML
    private void clickCero(ActionEvent event) {
        digitoPantalla("0");
    }

    @FXML
    private void clickPunto(ActionEvent event) {
        if (!Punto && !Digito) {
            LabPantalla.setText("0.");
            Digito = true;  
        }
        else if(!Punto){
            String valActual= LabPantalla.getText();
            LabPantalla.setText(valActual+".");
            
        }
        Punto = true;
    }

    @FXML
    private void clickIgual(ActionEvent event) {
        evaluarOperador("=");
    }

    @FXML
    private void clickTres(ActionEvent event) {
        digitoPantalla("3");
    }

    @FXML
    private void clickDos(ActionEvent event) {
        digitoPantalla("2");
    }

    @FXML
    private void clickUno(ActionEvent event) {
        digitoPantalla("1");
    }

    @FXML
    private void clickMas(ActionEvent event) {
        evaluarOperador("+");
    }

    @FXML
    private void clickMenos(ActionEvent event) {
        evaluarOperador("-");
    }

    @FXML
    private void clickSeis(ActionEvent event) {
        digitoPantalla("6");
    }

    @FXML
    private void clickCinco(ActionEvent event) {
        digitoPantalla("5");
    }

    @FXML
    private void clickCuatro(ActionEvent event) {
        digitoPantalla("4");
    }

    @FXML
    private void clickNueve(ActionEvent event) {
        digitoPantalla("9");
    }

    @FXML
    private void clickOcho(ActionEvent event) {
        digitoPantalla("8");
    }

    @FXML
    private void clickSiete(ActionEvent event) {
        digitoPantalla("7");
    }

    @FXML
    private void clickPor(ActionEvent event) {
        evaluarOperador("*");
    }

    @FXML
    private void clickEntre(ActionEvent event) {
        evaluarOperador("/");
        
    }
    /*
    Boton encargado de subir los datos a la base de datos mysql. 
    */
    @FXML
    private void clickHistorial(ActionEvent event) {
        historialOperacion(Operando1, Operando2, operadorr, Resultado);
       
        
        
    }
    /*
    Boton encargado de limpiar la pantalla y digitos que estubieran en memoria. 
    */
    @FXML
    private void clickC(ActionEvent event) {
        Digito = false;
        Punto = false;
        numOperandos = 0;
        Operando1= 0;
        Operando2 = 0;
        Resultado = 0;
        Operador = ' '; 
        LabPantalla.setText("0");
    }
    /*Método encargado de la conexión con la db y la subida de datos.*/
    public void historialOperacion(double Operando11,double Operando22, String Operadorr,double ResultadoH){
        this.Operando1 = Operando11;
        this.Operando2 = Operando22;
        this.operadorr=Operadorr;
        this.Resultado = ResultadoH;
        String ultimaOperacion = "Resultado:  " + ResultadoH+" Operación: "+Operadorr+" numero1: "+Operando11+ " numero2: "+Operando22;
        System.out.println(ultimaOperacion);
       
        
        try {
            java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/historial","root","root");
            PreparedStatement pr = con.prepareStatement("Insert into operaciones values (null,?,?,?,?)");
            
            pr.setDouble(1, Operando11);
            pr.setDouble(2, Operando22);
            pr.setString(3, Operadorr);
            pr.setDouble(4, ResultadoH);
            pr.executeUpdate();
            
           
        
//            while (myRt.next()) {
//                System.out.println(myRt.getString("IdOperacion")+"."+myRt.getString("Operando1")+" "+myRt.getString("Operacion")+" "+
//                        myRt.getString("Operando2")+ " = " + myRt.getString("Resultado"));                
//            }
        } 
        catch (SQLException e) {
            System.out.println(e.toString());
        } 
        
        
        
    }
    /*Método encargado de la introducción de dígitos*/
    public void digitoPantalla(String numero){
        if(!Digito && numero.equals("0"))
            return;
        
        if(!Digito)
        {
            LabPantalla.setText("");
            Punto = false;
        }
        
        String valActual= LabPantalla.getText();
        LabPantalla.setText(valActual+numero);
        Digito = true;
    }
    /*Método encargado de la evaluación de operadores, es decir, se encarga de seleccionar el operador con el que se va a realizar el calculo. */
    private void evaluarOperador(String Operador){
        
        if(Digito)
            numOperandos++;
        
        if(numOperandos == 1)
            Operando1 = Double.parseDouble(LabPantalla.getText());
        
         if(numOperandos == 2){
              Operando2 = Double.parseDouble(LabPantalla.getText());
              switch(this.Operador){
                  
                  case '+':
                      Resultado = Operando1 + Operando2;
                      operadorr = "+";
                      break;
                  case '-':
                      Resultado= Operando1 - Operando2;
                      operadorr = "-";
                      break;
                  case '*':
                      Resultado = Operando1 * Operando2;
                      operadorr = "*";
                      break;
                  case '/':
                      Resultado = Operando1 / Operando2;
                      operadorr = "/";
                      break;
                  case '=':
                      Resultado = Operando2;
                      break;
              }
              LabPantalla.setText(String.valueOf(Resultado));
              numOperandos = 1;
              Punto = false;
         }
         Digito = false;
         this.Operador = Operador.charAt(0);
           
    }
}
