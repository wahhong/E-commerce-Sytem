package controller;

import connection.DbConn;
import dao.DeliveryDao;
import dao.RiderDao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Delivery;
import model.Rider;

@WebServlet(name = "UpdateDeliveryServlet", urlPatterns = {"/UpdateDeliveryServlet"})
public class UpdateDeliveryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PrintWriter out = response.getWriter();

            int deliveryID = Integer.parseInt(request.getParameter("deliveryID"));
            String orderID = request.getParameter("orderID");
            int riderID = request.getParameter("riderID").isEmpty() ? 0 : Integer.parseInt(request.getParameter("riderID"));
            String deliveryStatus = request.getParameter("deliveryStatus");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            if (orderID == null || deliveryStatus == null) {
                throw new ServletException("Order ID or Delivery Status is missing.");
            }

            String orderShippingDate;
            String orderCompleteDate;
            String riderName = "";
            String riderPlateNum = "";
            String riderPhone = "";

            if (riderID != 0) {
                RiderDao riderDao = new RiderDao(DbConn.getConnection());
                Rider rider = riderDao.getRiderById(riderID);

                if (rider != null) {
                    riderName = rider.getRiderName() != null ? rider.getRiderName() : "";
                    riderPlateNum = rider.getRiderPlateNumber() != null ? rider.getRiderPlateNumber() : "";
                    riderPhone = rider.getRiderPhone() != null ? rider.getRiderPhone() : "";
                } else {
                    throw new ServletException("Rider not found.");
                }
            }

            Delivery delivery = new Delivery();
            delivery.setDeliveryID(deliveryID);
            delivery.setRiderID(riderID);
            delivery.setDeliveryStatus(deliveryStatus);
            
            if (deliveryStatus.equals("done")) {
                delivery.setOrderCompleteDate(now.format(formatter));
            } else if (deliveryStatus.equals("shipping")) {
                delivery.setOrderShippingDate(now.format(formatter));
            }

            out.println("Delivery ID: " + delivery.getDeliveryID());
            out.println("Rider ID: " + delivery.getRiderID());
            out.println("Delivery Status: " + delivery.getDeliveryStatus());
            out.println("Order Shipping Date: " + delivery.getOrderShippingDate());
            out.println("Order Complete Date: " + delivery.getOrderCompleteDate());

            DeliveryDao deliveryDao = new DeliveryDao(DbConn.getConnection());
            boolean success = deliveryDao.updateDelivery(delivery);
            if (success) {
                String apiUrl = "http://127.0.0.1:8000/api/updateOrderDetail";
                String urlParameters = "orderID=" + URLEncoder.encode(orderID.toString(), "UTF-8")
                        + "&riderName=" + URLEncoder.encode(riderName, "UTF-8")
                        + "&riderPlateNum=" + URLEncoder.encode(riderPlateNum, "UTF-8")
                        + "&riderPhone=" + URLEncoder.encode(riderPhone, "UTF-8")
                        + "&orderStatus=" + URLEncoder.encode(deliveryStatus, "UTF-8");

                HttpURLConnection urlConn = null;
                BufferedReader buffRead = null;

                try {
                    URL url = new URL(apiUrl);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setRequestMethod("POST");
                    urlConn.setDoOutput(true);
                    urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    try (OutputStream os = urlConn.getOutputStream()) {
                        os.write(urlParameters.getBytes("UTF-8"));
                        os.flush();
                    }

                    int responseCode = urlConn.getResponseCode();
                    out.println("Response Code: " + responseCode);

                    buffRead = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                    String inputLine;
                    StringBuilder apiResponse = new StringBuilder();

                    while ((inputLine = buffRead.readLine()) != null) {
                        apiResponse.append(inputLine);
                    }
                    out.println("API Response: " + apiResponse.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (buffRead != null) {
                        buffRead.close();
                    }
                    if (urlConn != null) {
                        urlConn.disconnect();
                    }
                }

                response.sendRedirect("delivery.jsp");
            } else {
                throw new ServletException("Failed to update delivery.");
            }
        } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UpdateDeliveryServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException("Error updating delivery", ex);
        }
    }
}
