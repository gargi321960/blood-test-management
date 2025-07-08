<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int userId = Integer.parseInt(request.getParameter("id"));
    String fullName = "", email = "", contact = "", address = "", gender = "";
    String testDate = "", hemoglobin = "", wbc = "", platelets = "", bloodGroup = "", remarks = "";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blood_testing", "root", "root");

        PreparedStatement ps = con.prepareStatement(
            "SELECT r.fullname, r.email, r.contact, r.address, r.gender, " +
            "b.test_date, b.hemoglobin, b.wbc_count, b.platelets_count, b.blood_group, b.doctor_remarks " +
            "FROM register r LEFT JOIN blood_test_report b ON r.id = b.register_id WHERE r.id = ?"
        );
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            fullName = rs.getString("fullname");
            email = rs.getString("email");
            contact = rs.getString("contact");
            address = rs.getString("address");
            gender = rs.getString("gender");
            testDate = rs.getString("test_date");
            hemoglobin = rs.getString("hemoglobin");
            wbc = rs.getString("wbc_count");
            platelets = rs.getString("platelets_count");
            bloodGroup = rs.getString("blood_group");
            remarks = rs.getString("doctor_remarks");
        }

        con.close();
    } catch (Exception e) {
        out.println("<h3>Error: " + e.getMessage() + "</h3>");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Blood Test Report</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
        }
        .report-container {
            background: #fff;
            width: 850px;
            margin: 30px auto;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
        }
        .center-header {
            text-align: center;
            margin-bottom: 30px;
        }
        .center-header h2 {
            color: #2c3e50;
            margin-bottom: 10px;
        }
        .center-header p {
            margin: 0;
            font-size: 14px;
            color: #777;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 25px;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            vertical-align: top;
        }
        th {
            background-color: #f2f2f2;
            width: 200px;
        }
        .footer {
            display: flex;
            justify-content: space-between;
            margin-top: 40px;
        }
        .footer .stamp img, .footer .signature img {
            height: 80px;
        }
        .footer .label {
            font-weight: bold;
            margin-top: 10px;
        }
        .print-btn {
            display: block;
            margin: 30px auto 0;
            padding: 12px 30px;
            background-color: #28a745;
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 6px;
            cursor: pointer;
        }
        .print-btn:hover {
            background-color: #218838;
        }

        @media print {
            .print-btn {
                display: none;
            }
        }
    </style>
</head>
<body>
    <div class="report-container">
        <div class="center-header">
            <h2>LifeCare Diagnostic Center</h2>
            <p>123 Medical Street, Health City, India | Phone: 9876543210</p>
            <hr>
            <h3>Blood Test Report</h3>
        </div>

        <table>
            <tr><th>Full Name</th><td><%= fullName %></td></tr>
            <tr><th>Email</th><td><%= email %></td></tr>
            <tr><th>Contact</th><td><%= contact %></td></tr>
            <tr><th>Gender</th><td><%= gender %></td></tr>
            <tr><th>Address</th><td><%= address %></td></tr>
            <tr><th>Test Date</th><td><%= testDate %></td></tr>
            <tr><th>Hemoglobin</th><td><%= hemoglobin %> g/dL</td></tr>
            <tr><th>WBC Count</th><td><%= wbc %> cells/mcL</td></tr>
            <tr><th>Platelets Count</th><td><%= platelets %> platelets/mcL</td></tr>
            <tr><th>Blood Group</th><td><%= bloodGroup %></td></tr>
            <tr><th>Doctor's Remarks</th><td><%= remarks %></td></tr>
        </table>

        <div class="footer">
            <div class="stamp">
                <img src="images/stamp.png" alt="Center Stamp">
                <div class="label">Center Stamp</div>
            </div>
            <div class="signature">
                <img src="images/signature2.jpg" alt="Doctor Signature">
                <div class="label">Doctor's Signature</div>
            </div>
        </div>

        <button class="print-btn" onclick="window.print()">Print Report</button>
    </div>
</body>
</html>
