package test.app.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.app.util.DBUtils;
import test.app.util.SessionUtils;

/**
 * Servlet implementation class UserRegistrationServlet
 */
@WebServlet(urlPatterns = { "/addUser" })
public class UserRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserRegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/addUserView.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = SessionUtils.getStoredConnection(request);
		 
        String errorString = "";
        
        String uname = (String) request.getParameter("uname");
        String pwd = (String) request.getParameter("pwd");        
        try {
        	if (errorString == null || errorString.trim().compareTo("") == 0) {
        		DBUtils.insertUser(conn, uname, pwd);
        	}
		} catch (SQLException e) { 
			e.printStackTrace();
			errorString = "Unable to add new user to DB";
		}        
 
        // Store infomation to request attribute, before forward to views.
        request.setAttribute("errorString", errorString);        
 
        // If error, forward to Edit page.
        if (errorString != null && errorString.trim().compareTo("") != 0) {
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/addUserView.jsp");
            dispatcher.forward(request, response);
        }
        // If everything nice.
        // Redirect to the product listing page.
        else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
	}

}
