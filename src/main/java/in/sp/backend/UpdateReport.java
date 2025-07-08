// File: UpdateReport.java (Servlet)
package in.sp.backend;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/updateReport")
public class UpdateReport extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int registerId = Integer.parseInt(req.getParameter("register_id"));
        String testDate = req.getParameter("test_date");
        float hemoglobin = Float.parseFloat(req.getParameter("hemoglobin"));
        float wbc = Float.parseFloat(req.getParameter("wbc_count"));
        float platelets = Float.parseFloat(req.getParameter("platelets_count"));
        String bloodGroup = req.getParameter("blood_group");
        String remarks = req.getParameter("doctor_remarks");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blood_testing", "root", "root");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM blood_test_report WHERE register_id=?");
            ps.setInt(1, registerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                PreparedStatement updateStmt = con.prepareStatement(
                    "UPDATE blood_test_report SET test_date=?, hemoglobin=?, wbc_count=?, platelets_count=?, blood_group=?, doctor_remarks=? WHERE register_id=?"
                );
                updateStmt.setString(1, testDate);
                updateStmt.setFloat(2, hemoglobin);
                updateStmt.setFloat(3, wbc);
                updateStmt.setFloat(4, platelets);
                updateStmt.setString(5, bloodGroup);
                updateStmt.setString(6, remarks);
                updateStmt.setInt(7, registerId);
                updateStmt.executeUpdate();
            } else {
                PreparedStatement insertStmt = con.prepareStatement(
                    "INSERT INTO blood_test_report (register_id, test_date, hemoglobin, wbc_count, platelets_count, blood_group, doctor_remarks) VALUES (?, ?, ?, ?, ?, ?, ?)"
                );
                insertStmt.setInt(1, registerId);
                insertStmt.setString(2, testDate);
                insertStmt.setFloat(3, hemoglobin);
                insertStmt.setFloat(4, wbc);
                insertStmt.setFloat(5, platelets);
                insertStmt.setString(6, bloodGroup);
                insertStmt.setString(7, remarks);
                insertStmt.executeUpdate();
            }

            con.close();
            resp.sendRedirect("welcome.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            resp.setContentType("text/html");
            resp.getWriter().println("<h3>Error updating report: " + e.getMessage() + "</h3>");
        }
    }
}
