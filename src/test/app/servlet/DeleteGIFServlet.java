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
import javax.servlet.http.HttpSession;

import test.app.entities.UserAccount;
import test.app.util.DBUtils;
import test.app.util.SessionUtils;

/**
 * Servlet implementation class DeleteGIFServlet
 */
@WebServlet(urlPatterns = { "/deleteGif" })
public class DeleteGIFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteGIFServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = SessionUtils.getStoredConnection(request);
		
		// Check User has logged on
        HttpSession session = request.getSession();
     	UserAccount loginedUser = SessionUtils.getLoginedUser(session);
     	// Not logged in
     	if (loginedUser == null) {
     		// Redirect to login page.
     		response.sendRedirect(request.getContextPath() + "/login");
     		return;
     	}
     	String uname = loginedUser.getUserName();
		
		String gifUrl = (String) request.getParameter("url");		
        String errorString = "";
 
        try {
			DBUtils.deleteGif(conn, uname, gifUrl);
		} catch (SQLException e) { 
			e.printStackTrace();
			errorString = "Unable to insert gif details to DB";
		}
        
        if (errorString != null && errorString.trim().compareTo("") != 0) {
        	request.setAttribute("errorString", errorString);
        	RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/deleteGifErrorView.jsp");
        	dispatcher.forward(request, response);
        }
        else{
        	response.sendRedirect(request.getContextPath() + "/gifList");
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
