package in.sp.backend;

import java.io.IOException;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/deleteUser")
public class DeleteUser extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        int userId = Integer.parseInt(req.getParameter("id")); // coming from URL: ?id=xyz

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/blood_testing", "root", "root"
            );

            // Delete from child table first (foreign key constraint)
            PreparedStatement ps1 = con.prepareStatement("DELETE FROM blood_test_report WHERE register_id=?");
            ps1.setInt(1, userId);
            ps1.executeUpdate();

            // Then delete from main table
            PreparedStatement ps2 = con.prepareStatement("DELETE FROM register WHERE id=?");
            ps2.setInt(1, userId);
            ps2.executeUpdate();

            con.close();
            resp.sendRedirect("welcome.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            resp.setContentType("text/html");
            resp.getWriter().println("<h3>Error deleting user: " + e.getMessage() + "</h3>");
        }
    }
}
