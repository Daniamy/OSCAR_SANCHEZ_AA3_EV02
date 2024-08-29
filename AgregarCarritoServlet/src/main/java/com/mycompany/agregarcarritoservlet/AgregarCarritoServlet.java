/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.agregarcarritoservlet;

import com.mycompany.agregarcarritoservlet.conexion.conexion;
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

@WebServlet(name = "AgregarCarritoServlet", urlPatterns = {"/agregarCarrito"})
public class AgregarCarritoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String Id = request.getParameter("Id");
        conexion con = new conexion();
        Connection cn = con.getConnection();

        try {
            // Consulta SQL para verificar la cantidad del producto
            String sql = "SELECT cantidad FROM productos WHERE Id = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, Id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int cantidad = rs.getInt("cantidad");

                if (cantidad > 0) {
                    // Redirige a la página para seguir comprando
                    response.sendRedirect("seguirComprando.jsp");
                } else {
                    // Redirige a la página que indica que el producto está agotado
                    response.sendRedirect("productoAgotado.jsp");
                }
            } else {
                // Si no encuentra el producto, redirige a una página de error o manejo adecuado
                response.sendRedirect("productoAgotado.jsp");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("errorConexion.jsp");
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.html");
    }
}