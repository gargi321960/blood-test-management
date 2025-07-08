<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
    <style>
        body {
            background: #f2f2f2;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            font-family: Arial, sans-serif;
        }
        .login-box {
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 0 10px #aaa;
            width: 350px;
        }
        .login-box h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #d9534f;
        }
        .login-box input {
            width: 100%;
            padding: 12px;
            margin-bottom: 15px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        .login-box button {
            width: 100%;
            padding: 12px;
            background: #d9534f;
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 5px;
        }
        .login-box a {
            text-align: center;
            display: block;
            margin-top: 10px;
            color: #007bff;
            text-decoration: none;
        }
    </style>
</head>
<body>

<div class="login-box">
    <h2>User Login</h2>
    <form action="login" method="post">
        <input type="text" name="username" placeholder="Email or Contact Number" required />
        <input type="password" name="password" placeholder="Password" required />
        <button type="submit">Login</button>
        <a href="forgotPassword.jsp">Forgot Password?</a>
    </form>
</div>

</body>
</html>
