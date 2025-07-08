<%-- editUser.jsp --%>
<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page session="true" %>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    String fullname = "", email = "", contact = "", gender = "";

    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blood_testing", "root", "root");
    PreparedStatement ps = con.prepareStatement("SELECT * FROM register WHERE id=?");
    ps.setInt(1, id);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        fullname = rs.getString("fullname");
        email = rs.getString("email");
        contact = rs.getString("contact");
        gender = rs.getString("gender");
    }
    con.close();
%>
<html>
<head>
    <title>Edit User</title>
    <style>
        body {
            font-family: Arial;
            background: #f4f4f4;
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
        input[type=text], input[type=email] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        input[type=submit] {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
        }
        input[type=submit]:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Edit User Details</h2>
        <form action="updateUser" method="post">
            <input type="hidden" name="id" value="<%=id%>" />
            Full Name: <input type="text" name="fullname" value="<%=fullname%>" required /><br>
            Email: <input type="email" name="email" value="<%=email%>" required /><br>
            Contact: <input type="text" name="contact" value="<%=contact%>" required /><br>
            Gender: <input type="text" name="gender" value="<%=gender%>" required /><br>
            <input type="submit" value="Update User" />
        </form>
    </div>
</body>
</html>