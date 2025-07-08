<%@page import="java.io.PrintWriter"%>
<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page session="true" %>
<%
    if (session == null || session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<html>
<head>
    <title>Blood Test Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
        }
        h2 {
            text-align: center;
            color: #d9534f;
        }
        table {
            border-collapse: collapse;
            width: 95%;
            margin: 30px auto;
            background-color: #fff;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 12px;
            text-align: center;
        }
        th {
            background-color: #ff9999;
            color: white;
        }
        .btn {
            padding: 6px 10px;
            margin: 3px 2px;
            text-decoration: none;
            border-radius: 5px;
            color: white;
            font-size: 13px;
            display: inline-block;
        }
        .edit { background-color: #4CAF50; }
        .delete { background-color: #f44336; }
        .report { background-color: #2196F3; }
        .email { background-color: #ff9800; }
        .actions-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
        }
        .logout {
            display: block;
            text-align: right;
            margin: 10px;
            color: #333;
        }
        .log{
        background-color: #f44336;
      
        }
    </style>
</head>
<body>

<div class="logout">
    Welcome, <%= session.getAttribute("userName") %> | <button class="btn log"><a style="color:white;" href="logout.jsp">Logout</a></button>
</div>

<h2>Blood Test Users Dashboard</h2>

<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Contact</th>
        <th>Gender</th>
        <th>Test Date</th>
        <th>Hemoglobin</th>
        <th>WBC</th>
        <th>Platelets</th>
        <th>Blood Group</th>
        <th>Doctor Remarks</th>
        <th>Actions</th>
    </tr>
<%

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blood_testing", "root", "root");

        String query = "SELECT r.id, r.fullname, r.email, r.contact, r.gender, " +
                       "b.test_date, b.hemoglobin, b.wbc_count, b.platelets_count, b.blood_group AS report_bg, b.doctor_remarks " +
                       "FROM register r LEFT JOIN blood_test_report b ON r.id = b.register_id";

        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
%>
    <tr>
        <td><%= rs.getInt("id") %></td>
        <td><%= rs.getString("fullname") %></td>
        <td><%= rs.getString("email") %></td>
        <td><%= rs.getString("contact") %></td>
        <td><%= rs.getString("gender") %></td>
        <td><%= rs.getDate("test_date") != null ? rs.getDate("test_date") : "-" %></td>
        <td><%= rs.getObject("hemoglobin") != null ? rs.getFloat("hemoglobin") : "-" %></td>
        <td><%= rs.getObject("wbc_count") != null ? rs.getFloat("wbc_count") : "-" %></td>
        <td><%= rs.getObject("platelets_count") != null ? rs.getFloat("platelets_count") : "-" %></td>
        <td><%= rs.getString("report_bg") != null ? rs.getString("report_bg") : "-" %></td>
        <td><%= rs.getString("doctor_remarks") != null ? rs.getString("doctor_remarks") : "-" %></td>
        <td>
            <div class="actions-container">
                <a href="editUser.jsp?id=<%=rs.getInt("id")%>" class="btn edit">Edit User</a>
                <a href="editReport.jsp?id=<%=rs.getInt("id")%>" class="btn edit">Edit Report</a>
                <a href="deleteUser?id=<%=rs.getInt("id")%>" class="btn delete" onclick="return confirm('Are you sure?')">Delete</a>
                <a href="generateReport?id=<%=rs.getInt("id")%>" class="btn report">Generate Report</a>
                <a href="sendEmail?id=<%=rs.getInt("id")%>" class="btn email">Send Email</a>
            </div>
        </td>
    </tr>
<%
        }
        
        
        con.close();
    } catch(Exception e) {
        out.println("<tr><td colspan='12'>Error: " + e.getMessage() + "</td></tr>");
    }
%>
</table>

</body>
</html>
