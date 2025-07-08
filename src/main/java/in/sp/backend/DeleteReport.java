package in.sp.backend;

import java.io.IOException;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/deleteReport")
public class DeleteReport extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("id")); // register.id

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/blood_testing", "root", "root"
            );

            // Delete blood report(s) for the given user
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM blood_test_report WHERE register_id=?"
            );
            ps.setInt(1, userId);
            ps.executeUpdate();

            // Optionally delete the user as well (only if needed)
            // PreparedStatement ps2 = con.prepareStatement("DELETE FROM register WHERE id=?");
            // ps2.setInt(1, userId);
            // ps2.executeUpdate();

            con.close();
            resp.sendRedirect("welcome.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            resp.setContentType("text/html");
            resp.getWriter().println("<h3>Error deleting report: " + e.getMessage() + "</h3>");
        }
    }
}
