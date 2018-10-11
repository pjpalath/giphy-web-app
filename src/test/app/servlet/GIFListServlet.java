package test.app.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import test.app.entities.AnimatedGIF;
import test.app.entities.UserAccount;
import test.app.util.DBUtils;
import test.app.util.SessionUtils;

/**
 * Servlet implementation class GIFListServlet
 */
@WebServlet(urlPatterns = { "/gifList" })
public class GIFListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GIFListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	HttpSession session = request.getSession();
    	// Check User has logged on
    	UserAccount loginedUser = SessionUtils.getLoginedUser(session);

    	// Not logged in
    	if (loginedUser == null) {
    		// Redirect to login page.
    		response.sendRedirect(request.getContextPath() + "/login");
    		return;
    	}
    	String uname = loginedUser.getUserName();

    	Connection conn = SessionUtils.getStoredConnection(request);		
		
        String errorString = null;
        List<AnimatedGIF> list = null;
        try {        	
            list = DBUtils.queryGif(conn, uname);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }
        // Store info in request attribute, before forward to views
        request.setAttribute("errorString", errorString);        
        request.setAttribute("gifList", list);
        
        // Forward to /WEB-INF/views/productListView.jsp
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/gifListView.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
