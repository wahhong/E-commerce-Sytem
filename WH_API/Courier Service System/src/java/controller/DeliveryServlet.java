/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import connection.DbConn;
import dao.DeliveryDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Delivery;

/**
 *
 * @author chanw
 */
@WebServlet(name = "DeliveryServlet", urlPatterns = {"/DeliveryServlet"})
public class DeliveryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");

            DeliveryDao delivDao = new DeliveryDao(DbConn.getConnection());
            List<Delivery> deliveries = delivDao.getAllDeliveries();

            if (deliveries != null && !deliveries.isEmpty()) {
                request.setAttribute("deliveries", deliveries);
            } else {
                request.setAttribute("error", "No deliveries found.");
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("/delivery.jsp");
            dispatcher.forward(request, response);

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Error retrieving deliveries: " + ex.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}

