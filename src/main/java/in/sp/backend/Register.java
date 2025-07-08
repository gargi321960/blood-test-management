package in.sp.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.apache.jasper.tagplugins.jstl.core.Out;
import org.apache.tomcat.dbcp.dbcp2.DelegatingPreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/regForm")
public class Register extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String fullname1=req.getParameter("fullname");
		String email1=req.getParameter("email");
		String password1=req.getParameter("password");
		String repassword1=req.getParameter("repassword");
		String contact1=req.getParameter("mobile");
		String address1=req.getParameter("address");
		String gender1=req.getParameter("gender");
		
		PrintWriter out = resp.getWriter();
		
		try {
			   
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/blood_testing?useSSL=false","root","root");			
			PreparedStatement ps= con.prepareStatement("INSERT INTO register (fullname, email, password, contact, address, gender) VALUES (?, ?, ?, ?, ?, ?)");
			ps.setString(1, fullname1);
			ps.setString(2, email1);
			ps.setString(3, password1);
			ps.setString(4, contact1);
			ps.setString(5, address1);
			ps.setString(6, gender1);
			
			int count=ps.executeUpdate();
			
				if(count > 0) {
					resp.setContentType("text/html");
				    req.setAttribute("message", "Registration Successful!");
					RequestDispatcher rd= req.getRequestDispatcher("/register.jsp");

					rd.include(req, resp);

				} else {
					resp.setContentType("text/html");
				    req.setAttribute("message", "Registration Failed. Please try again.");
					RequestDispatcher rd= req.getRequestDispatcher("/register.jsp");

					rd.include(req, resp);

				}

			

		} catch (Exception e) {
			// TODO: handle exception
			resp.setContentType("text/html");
			out.print("<h3>Registration not Successfull due to some error:"+e.getMessage()+"</h3>");
			RequestDispatcher rd= req.getRequestDispatcher("/register.jsp");
			rd.include(req, resp);
			e.printStackTrace();
		}

	}

}
