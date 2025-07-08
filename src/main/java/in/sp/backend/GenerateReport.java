package in.sp.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/generateReport")
public class GenerateReport extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blood_testing", "root", "root");

            PreparedStatement ps = con.prepareStatement(
                "SELECT r.fullname, r.email, r.gender, b.test_date, b.hemoglobin, b.wbc_count, b.platelets_count, b.blood_group, b.doctor_remarks " +
                "FROM register r LEFT JOIN blood_test_report b ON r.id = b.register_id WHERE r.id = ?"
            );
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            out.println("<html><head><title>Blood Test Report</title>");
            out.println("<style>");
            out.println("body { font-family: Arial; background: #fff; color: #000; padding: 40px; }");
            out.println(".report-box { max-width: 800px; margin: auto; padding: 20px; border: 1px solid #ccc; box-shadow: 0 0 5px rgba(0,0,0,0.1); background-color: #fff; }");
            out.println(".center-header { text-align: center; }");
            out.println(".center-header h1 { margin: 0; color: #d9534f; }");
            out.println(".center-header p { margin: 2px 0; font-size: 14px; }");
            out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
            out.println("th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }");
            out.println("th { background-color: #f2f2f2; }");
            out.println(".footer { margin-top: 40px; display: flex; justify-content: space-between; align-items: center; }");
            out.println(".footer img { height: 80px; }");
            out.println(".print-btn { margin-top: 20px; display: block; text-align: center; }");
            out.println(".print-btn button { padding: 10px 20px; background-color: #4CAF50; color: white; border: none; cursor: pointer; border-radius: 5px; }");
            out.println("</style>");
            out.println("<script>function printPage() { window.print(); }</script>");
            out.println("</head><body>");

            out.println("<div class='report-box'>");

            // Diagnostic Center Header
            out.println("<div class='center-header'>");
            out.println("<h1>HealthPlus Diagnostic Center</h1>");
            out.println("<p>123 Medical Street, Kolkata - 700001</p>");
            out.println("<p>Contact: +91-9876543210 | Email: info@healthplus.com</p>");
            out.println("<hr>");
            out.println("<h2>Blood Test Report</h2>");
            out.println("</div>");

            if (rs.next()) {
                out.println("<table>");
                out.println("<tr><th>Full Name</th><td>" + rs.getString("fullname") + "</td></tr>");
                out.println("<tr><th>Email</th><td>" + rs.getString("email") + "</td></tr>");
                out.println("<tr><th>Gender</th><td>" + rs.getString("gender") + "</td></tr>");
                out.println("<tr><th>Blood Group</th><td>" + rs.getString("blood_group") + "</td></tr>");
                out.println("<tr><th>Test Date</th><td>" + rs.getDate("test_date") + "</td></tr>");
                out.println("<tr><th>Hemoglobin</th><td>" + rs.getFloat("hemoglobin") + " g/dL</td></tr>");
                out.println("<tr><th>WBC Count</th><td>" + rs.getFloat("wbc_count") + " cells/mcL</td></tr>");
                out.println("<tr><th>Platelets Count</th><td>" + rs.getFloat("platelets_count") + " platelets/mcL</td></tr>");
                out.println("<tr><th>Doctor's Remarks</th><td>" + rs.getString("doctor_remarks") + "</td></tr>");
                out.println("</table>");

                // Footer with signature & stamp
                out.println("<div class='footer'>");
                out.println("<div>");
                out.println("<p><strong>Authorized Doctor</strong></p>");
                out.println("<img src='images/signature2.jpg' alt='Doctor Signature'>");
                out.println("</div>");
                out.println("<div>");
                out.println("<img src='images/stamp.png' alt='Center Stamp'>");
                out.println("</div>");
                out.println("</div>");

                // Print Button
                out.println("<div class='print-btn'><button onclick='printPage()'>Print Report</button></div>");

            } else {
                out.println("<p style='color:red;text-align:center;'>No report found for this user.</p>");
            }

            out.println("</div>"); // report-box
            out.println("</body></html>");

            con.close();
        } catch (Exception e) {
            out.println("<p style='color:red;text-align:center;'>Error generating report: " + e.getMessage() + "</p>");
        }
    }
}
