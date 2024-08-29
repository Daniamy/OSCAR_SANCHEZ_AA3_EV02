package com.mycompany.tiendagorritos;

import com.mycompany.tiendagorritos.conexion.conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "RegistrarClienteServlet", urlPatterns = {"/RegistrarClienteServlet"})
public class RegistrarClienteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("registro.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarClienteServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("registroFallido.jsp");
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validación de campos obligatorios
        if (nombre == null || nombre.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
            response.sendRedirect("registro.jsp?error=Todos los campos son obligatorios");
            return;
        }
        

        // Validación del nombre (solo letras)
        
        
        if (!nombre.matches("[a-zA-Z]+")) {
            response.sendRedirect("registro.jsp?error=El nombre solo debe contener letras");
            return;
        }
        

        // Validación del formato del email
        
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            response.sendRedirect("registro.jsp?error=Formato de email incorrecto");
            return;
        }

        // Conexión a la base de datos
        
        
        conexion con = new conexion();
        try (Connection cn = con.getConnection()) {
            String sql = "INSERT INTO clientes (nombre, email, password) VALUES ('"+nombre+"','"+email+"','"+password+"')";
            try (PreparedStatement pst = cn.prepareStatement(sql)) {
                pst.setString(1, nombre);
                pst.setString(2, email);
                pst.setString(3, password);

                int rowCount = pst.executeUpdate();

                if (rowCount > 0) {
                    // Registro exitoso
                    response.sendRedirect("registroFallido.jsp");
                } else {
                    // Error en el registro
                    response.sendRedirect("registroFallido.jsp");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarClienteServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("registroFallido.jsp");
        }
    }
}