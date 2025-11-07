/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import connection.DbConn;
import dao.DeliveryDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "AddDeliveryApi", urlPatterns = {"/AddDeliveryApi"})
public class AddDeliveryApi extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String userName = request.getParameter("userName");
        String userPhone = request.getParameter("userPhone");
        String orderID = request.getParameter("orderID");
        String orderAddress = request.getParameter("orderAddress");
        String deliveryStatus = request.getParameter("deliveryStatus");
        String jsonResponse;

        if (userName == null || orderID == null || orderAddress == null || deliveryStatus == null
                || userName.isEmpty() || orderID.isEmpty() || orderAddress.isEmpty() || deliveryStatus.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            jsonResponse = "{\"message\": \"Error occurs!\"}";
        } else {

            try {
                DeliveryDao deliveryDao = new DeliveryDao(DbConn.getConnection());

                Delivery delivery = new Delivery();
                delivery.setUserName(userName);
                delivery.setUserPhone(userPhone);
                delivery.setOrderID(new BigInteger(orderID));
                delivery.setOrderAddress(orderAddress);
                delivery.setDeliveryStatus(deliveryStatus);
                
                deliveryDao.addDelivery(delivery);
                
                jsonResponse = "{\"message\": \"Delivery add successfully\"}";
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (ClassNotFoundException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                jsonResponse = "{\"message\": \"Error occurs!\"}";
            } catch (SQLException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                jsonResponse = "{\"message\": \"Error occurs!\"}";
            }
        }

        out.println(jsonResponse);
        out.flush();
        out.close();
    }
}
