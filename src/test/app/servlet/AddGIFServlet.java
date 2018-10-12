/**
 * 
 */
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

import test.app.entities.AnimatedGIF;
import test.app.entities.GIFClassification;
import test.app.entities.UserAccount;
import test.app.util.DBUtils;
import test.app.util.SessionUtils;

/**
 * Servlet implementation class AddGIFServlet.
 * This is the servlet that takes requests to add
 * {@link AnimatedGIF} to the database
 */
@WebServlet(urlPatterns = { "/addGif" })
public class AddGIFServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddGIFServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/addGifView.jsp");
        dispatcher.forward(request, response);
    }
 
    /**
     * 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
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
        
        // Get parameters from request and process
        String url = (String) request.getParameter("gifurl");
        String type = (String) request.getParameter("category");
        GIFClassification typeEnum = null;
        try
        {
        	typeEnum = GIFClassification.valueOf(type);
        } catch (Exception e)
        {
        	errorString = errorString + "Incorrect classification type";
        }
        // If there are no errors to this point insert the GIF to the database
        try {
        	if (errorString == null || errorString.trim().compareTo("") == 0) {
        		DBUtils.insertGif(conn, uname, url, type);
        	}
		} catch (SQLException e)
        { 
			e.printStackTrace();
			errorString = "Unable to insert gif details to DB";
		}
        
        // Create an instance of AnimatedGif to send back in the response
        AnimatedGIF aGif = new AnimatedGIF();
        aGif.setUrl(url);        
        aGif.setType(typeEnum);
 
        // Store infomation to request attribute, before forwarded to views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("animatedgif", aGif);        
    }
}
