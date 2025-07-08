<%@ page language="java" session="true" %>
<%
    // Invalidate the session
    session.invalidate();
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="refresh" content="2; URL=login.jsp" />
    <title>Logged Out</title>
    <style>
        body {
            font-family: Arial;
            background-color: #f4f4f4;
            text-align: center;
            padding-top: 100px;
        }
        .message {
            font-size: 20px;
            color: #333;
        }
    </style>
</head>
<body>
    <div class="message">
        <p>You have been successfully logged out.</p>
        <p>Redirecting to login page...</p>
    </div>
</body>
</html>
