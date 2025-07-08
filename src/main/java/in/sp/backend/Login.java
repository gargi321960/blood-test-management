package in.sp.backend;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/login")  // <== THIS IS CRITICAL
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/blood_testing", "root", "root");

            String query = "SELECT * FROM register WHERE (email=? OR contact=?) AND password=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, username);
            ps.setString(3, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = req.getSession();
                session.setAttribute("userId", rs.getInt("id"));
                session.setAttribute("userName", rs.getString("fullname"));
                resp.sendRedirect("welcome.jsp");
            } else {
                PrintWriter out = resp.getWriter();
                resp.setContentType("text/html");
                out.println("<script>alert('Invalid login'); window.location='login.jsp';</script>");
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
