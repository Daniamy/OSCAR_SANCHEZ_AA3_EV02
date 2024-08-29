package com.mycompany.login.servlet;

import com.mycompany.login.conexion.conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("usuario");
        String contraseña = request.getParameter("contraseña");

        conexion con = new conexion();
        Connection cn = con.getConnection();
 
        
        // la consulta para verificar usuario y contraseña
      

        try {
            
            String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contraseña = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, usuario);
            pst.setString(2, contraseña);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                
         // Si existe el usuario,
         
                response.sendRedirect("panel.jsp");
                
                
          // Si no existe, redirige a login.jsp con mensaje de error
                
            } else {
                
                response.sendRedirect("loginfallido.jsp?error=Usuario o contraseña incorrectos");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("loginfallido.jsp?error=Error de conexión");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}