/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tiendagorritos.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author sanch
 */
public class conexion {
    
Connection con;
    
 
public conexion(){
    
    
    
         try{
        Class.forName ("com.mysql.jdbc.Driver");
        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda_gorritos","root","Camila2018");
        }catch (ClassNotFoundException | SQLException e){
          System.out.println("No conectado"); 
          
        }
} 

public Connection getConnection(){
    return con;        
            
     
        }

    public PreparedStatement prepareStatement(String query) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}