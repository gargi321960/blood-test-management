<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BloodTestRegisterForm</title>

<style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #fff55;
            
        }

        .main {
            background-color: #ffd0c8;
            border-radius: 15px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
            padding: 40px;
            width: 400px;
        }

        .main h2 {
            color: #4caf50;
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            color: #555;
            font-weight: bold;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"],
        select {
            width: 100%;
            margin-bottom: 15px;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        button[type="submit"] {
            padding: 15px;
            border-radius: 10px;
            border: none;
            background-color: #4caf50;
            color: white;
            cursor: pointer;
            width: 100%;
            font-size: 16px;
        }
    </style>


</head>
<body>
<div class="main">
<% String message = (String) request.getAttribute("message"); %>
    <% if (message != null) { %>
        <div style="background-color: #d4edda; color: #155724; padding: 10px; border-radius: 5px; margin-bottom: 20px; text-align: center; font-weight: bold;">
            <%= message %>
        </div>
    <% } %>
        <h2>Registration Form</h2>
        <form action="regForm" method="post">
            <label for="first">Full Name:</label>
            <input type="text" id="fullname" name="fullname" required />
            
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required />

            <label for="password">Password:</label>
            <input type="password" id="password" name="password"
                   pattern="^(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9])\S{8,}$" 
                   title="Password must contain at least one number, 
                           one alphabet, one symbol, and be at 
                           least 8 characters long" required />

            <label for="repassword">Re-type Password:</label>
            <input type="password" id="repassword" name="repassword" required />

            <label for="mobile">Contact:</label>
            <input type="text" id="mobile" name="mobile" maxlength="10" required />
            
              <label for="last">Address:</label>
            <input type="text" id="address" name="address" required />

            <label for="gender">Gender:</label>
            <select id="gender" name="gender" required>
                <option value="male">
                    Male
                </option>
                <option value="female">
                    Female
                </option>
                <option value="other">
                    Other
                </option>
            </select>

            <button type="submit">
                Submit
            </button>
        </form>
    </div>

</body>
</html>