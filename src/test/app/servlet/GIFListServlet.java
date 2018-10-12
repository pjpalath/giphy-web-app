/**
 * 
 */
package test.app.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import test.app.entities.AnimatedGIF;
import test.app.entities.GIFClassification;
import test.app.entities.UserAccount;
import test.app.util.DBUtils;
import test.app.util.SessionUtils;

/**
 * Servlet implementation class GIFListServlet
 * This is the servlet that takes requests to retrieve the list of
 * {@link AnimatedGIF}'s for this user
 */
@WebServlet(urlPatterns = { "/gifList" })
public class GIFListServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GIFListServlet()
    {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	Connection conn = SessionUtils.getStoredConnection(request);
    	
    	// Check if the user has logged on. If not redirect the user
        // to the log on page. If the user has logged on get the username
        HttpSession session = request.getSession();
     	UserAccount loginedUser = SessionUtils.getLoginedUser(session);
     	// Not logged in
     	if (loginedUser == null) {
     		// Redirect to login page.
     		response.sendRedirect(request.getContextPath() + "/login");
     		return;
     	}
     	String uname = loginedUser.getUserName();		
		
     	// Holds errors in processing this request to send back in the
     	// response to display on page
        String errorString = "";
        
        // Retrieve the list of AnimatedGif's for this user
        List<AnimatedGIF> list = null;
        try
        {        	
            list = DBUtils.queryGif(conn, uname);
        } catch (SQLException e)
        {
            e.printStackTrace();
            errorString = e.getMessage();
        }
        
        // Store info in request attribute, before forward to views
        request.setAttribute("errorString", errorString);        
        request.setAttribute("gifList", list);
        
        // Return the list of GIF classifications from the GIFClassification enum
        List<String> listCategory = Stream.of(GIFClassification.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        request.setAttribute("listCategory", listCategory);
        
        // Forward to /WEB-INF/views/gifListView.jsp
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/gifListView.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
