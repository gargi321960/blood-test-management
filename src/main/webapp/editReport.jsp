<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page session="true" %>
<%
    int userId = Integer.parseInt(request.getParameter("id"));
    String testDate = "", hemoglobin = "", wbc = "", platelets = "", bloodGroup = "", remarks = "";

    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blood_testing", "root", "root");
    PreparedStatement ps = con.prepareStatement("SELECT * FROM blood_test_report WHERE register_id=?");
    ps.setInt(1, userId);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        testDate = rs.getString("test_date");
        hemoglobin = rs.getString("hemoglobin");
        wbc = rs.getString("wbc_count");
        platelets = rs.getString("platelets_count");
        bloodGroup = rs.getString("blood_group");
        remarks = rs.getString("doctor_remarks");
    }
    con.close();
%>
<html>
<head>
    <title>Edit Blood Test Report</title>
    <style>
        body {
            font-family: Arial;
            background: #eef2f3;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        input[type=text], input[type=date] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        input[type=submit] {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
        }
        input[type=submit]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Edit Blood Test Report</h2>
        <form action="updateReport" method="post">
            <input type="hidden" name="register_id" value="<%=userId%>" />
            Test Date: <input type="date" name="test_date" value="<%=testDate%>" required /><br>
            Hemoglobin: <input type="text" name="hemoglobin" value="<%=hemoglobin%>" required /><br>
            WBC Count: <input type="text" name="wbc_count" value="<%=wbc%>" required /><br>
            Platelets Count: <input type="text" name="platelets_count" value="<%=platelets%>" required /><br>
            Blood Group: <input type="text" name="blood_group" value="<%=bloodGroup%>" required /><br>
            Doctor Remarks: <input type="text" name="doctor_remarks" value="<%=remarks%>" /><br>
            <input type="submit" value="Update Report" />
        </form>
    </div>
</body>
</html>
