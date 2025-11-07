<%@page import="model.Rider"%>
<%@page import="dao.RiderDao"%>
<%@page import="connection.DbConn"%>
<%@page import="dao.DeliveryDao"%>
<%@page import="java.util.List"%>
<%@page import="model.Delivery"%>
<%
    DeliveryDao delivDao = new DeliveryDao(DbConn.getConnection());
    List<Delivery> deliveries = delivDao.getAllDeliveries();

    RiderDao riderDao = new RiderDao(DbConn.getConnection());
    List<Rider> riders = riderDao.getAllRiders();
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Delivery</title>
        <style>
            table {
                width: 100%;
                border-collapse: collapse;
            }
            th, td {
                border: 1px solid black;
                padding: 8px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
        </style>
    </head>
    <body>
        <h2>List of Delivery</h2>
        <table>
            <thead>
                <tr>
                    <th>Delivery ID</th>
                    <th>Order ID</th>
                    <th>Name</th>
                    <th>Phone</th>
                    <th>Order Address</th>
                    <th>Raider ID</th>
                    <th>Delivery Status</th>
                    <th>Order Shipping Date</th>
                    <th>Order Complete Date</th>
                    <th>Edit</th>
                </tr>
            </thead>
            <tbody>
                <%
                    if (deliveries != null && !deliveries.isEmpty()) {
                        for (Delivery delivery : deliveries) {
                %>
                <tr>
                    <form action="UpdateDeliveryServlet" method="post">
                        <td><%= delivery.getDeliveryID()%></td>
                        <td><%= delivery.getOrderID()%></td>
                        <td><%= delivery.getUserName()%></td>
                        <td><%= delivery.getUserPhone()%></td>
                        <td><%= delivery.getOrderAddress()%></td>
                        <td>
                            <select name="riderID">
                                <option value="" <%= (delivery.getRiderID() == 0) ? "selected" : ""%>>Select Rider</option>
                                <%
                                    if (riders != null && !riders.isEmpty()) {
                                        for (Rider rider : riders) {
                                %>
                                <option value="<%= rider.getRiderID()%>" <%= rider.getRiderID() == delivery.getRiderID() ? "selected" : ""%>>
                                    <%= rider.getRiderName()%>
                                </option>
                                <%
                                    }
                                } else {
                                %>
                                <option>No riders available</option>
                                <%
                                    }
                                %>
                            </select>
                        </td>
                        <td>
                            <select name="deliveryStatus">
                                <option value="preparing" <%= "preparing".equals(delivery.getDeliveryStatus()) ? "selected" : ""%>>Preparing</option>
                                <option value="ready" <%= "ready".equals(delivery.getDeliveryStatus()) ? "selected" : ""%>>Ready</option>
                                <option value="shipping" <%= "shipping".equals(delivery.getDeliveryStatus()) ? "selected" : ""%>>Shipping</option>
                                <option value="done" <%= "done".equals(delivery.getDeliveryStatus()) ? "selected" : ""%>>Done</option>
                                <option value="cancel" <%= "cancel".equals(delivery.getDeliveryStatus()) ? "selected" : ""%>>Cancel</option>
                            </select>
                        </td>
                        <td><%= delivery.getOrderShippingDate()%></td>
                        <td><%= delivery.getOrderCompleteDate()%></td>
                        <td>
                            <input type="hidden" name="deliveryID" value="<%= delivery.getDeliveryID() %>">
                            <input type="hidden" name="orderID" value="<%= delivery.getOrderID() %>">
                            <button type="submit">Update</button>
                        </td>
                    </form>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="9">No deliveries found.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </body>
</html>
