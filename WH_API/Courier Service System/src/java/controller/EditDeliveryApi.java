package controller;

import connection.DbConn;
import dao.DeliveryDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Delivery;

@WebServlet(name = "EditDeliveryApi", urlPatterns = {"/EditDeliveryApi"})
public class EditDeliveryApi extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String orderID = request.getParameter("orderID");
        String orderAddress = request.getParameter("orderAddress");
        String deliveryStatus = request.getParameter("deliveryStatus");
        String orderShippingDate = request.getParameter("orderShippingDate");
        String orderCompleteDate = request.getParameter("orderCompleteDate");
        String jsonResponse;

        if (orderID == null || orderAddress == null || deliveryStatus == null || orderID.isEmpty() || orderAddress.isEmpty() || deliveryStatus.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            jsonResponse = "{\"message\": \"Error occurs!\"}";
        } else {
            try {
                DeliveryDao deliveryDao = new DeliveryDao(DbConn.getConnection());

                Delivery delivery = new Delivery();
                delivery.setOrderID(new BigInteger(orderID));
                delivery.setOrderAddress(orderAddress);
                delivery.setDeliveryStatus(deliveryStatus);
                delivery.setOrderShippingDate(orderShippingDate);
                delivery.setOrderCompleteDate(orderCompleteDate);

                deliveryDao.updateDeliveryApi(delivery);
                
                jsonResponse = "{\"message\": \"Delivery edit successfully\"}";
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (ClassNotFoundException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                jsonResponse = "{\"message\": \"Class not found: " + ex.getMessage() + "\"}";
            } catch (SQLException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                jsonResponse = "{\"message\": \"SQL Error: " + ex.getMessage() + "\"}";
            }
        }

        out.println(jsonResponse);
        out.flush();
        out.close();
    }

}
