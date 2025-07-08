package in.sp.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/sendEmail")
public class SendReportEmail extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("id"));
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String toEmail = "";
        String subject = "Blood Test Report";
        StringBuilder emailBody = new StringBuilder();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blood_testing", "root", "root");

            PreparedStatement ps = con.prepareStatement(
                "SELECT r.fullname, r.email, r.gender, b.test_date, b.hemoglobin, b.wbc_count, b.platelets_count, b.blood_group, b.doctor_remarks " +
                "FROM register r LEFT JOIN blood_test_report b ON r.id = b.register_id WHERE r.id = ?"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                toEmail = rs.getString("email");
                emailBody.append("Dear ").append(rs.getString("fullname")).append(",\n\n");
                emailBody.append("Your Blood Test Report:\n");
                emailBody.append("Test Date: ").append(rs.getString("test_date")).append("\n");
                emailBody.append("Hemoglobin: ").append(rs.getString("hemoglobin")).append("\n");
                emailBody.append("WBC Count: ").append(rs.getString("wbc_count")).append("\n");
                emailBody.append("Platelets Count: ").append(rs.getString("platelets_count")).append("\n");
                emailBody.append("Blood Group: ").append(rs.getString("blood_group")).append("\n");
                emailBody.append("Doctor Remarks: ").append(rs.getString("doctor_remarks")).append("\n\n");
                emailBody.append("Regards,\nBlood Test Center");
            } else {
                out.println("<h3>No report found for this user.</h3>");
                return;
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Error fetching report: " + e.getMessage() + "</h3>");
            return;
        }

        // Email configuration
        final String fromEmail = "gargighosh365@gmail.com";
        final String password = "metujidzdneblxbt";          

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session mailSession = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(emailBody.toString());

            Transport.send(message);

            out.println("<h3>Email sent successfully to: " + toEmail + "</h3>");
        } catch (MessagingException e) {
            e.printStackTrace();
            out.println("<h3>Error sending email: " + e.getMessage() + "</h3>");
        }
    }
}
