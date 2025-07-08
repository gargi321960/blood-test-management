package in.sp.backend; // ← Make sure this matches your package

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/updateUser")
public class UpdateUser extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String contact = req.getParameter("contact");
        String gender = req.getParameter("gender");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blood_testing", "root", "root");
            PreparedStatement ps = con.prepareStatement("UPDATE register SET fullname=?, email=?, contact=?, gender=? WHERE id=?");
            ps.setString(1, fullname);
            ps.setString(2, email);
            ps.setString(3, contact);
            ps.setString(4, gender);
            ps.setInt(5, id);
            ps.executeUpdate();
            con.close();
            resp.sendRedirect("welcome.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("Error: " + e.getMessage());
        }
    }
}
