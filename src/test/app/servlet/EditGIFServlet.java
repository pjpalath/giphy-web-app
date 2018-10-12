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
 * Servlet implementation class EditGIFServlet
 * This is the servlet that takes requests to edit
 * {@link AnimatedGIF} to the database
 */
@WebServlet(urlPatterns = { "/editGif" })
public class EditGIFServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditGIFServlet()
    {
        super();
    }
  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        Connection conn = SessionUtils.getStoredConnection(request);
 
        // Holds errors in processing this request to send back in the
     	// response to display on page
        String errorString = "";
        
        // Get parameters from request and process
        String gifUrl = (String) request.getParameter("url");
 
        // Get the AnimatedGif object for this gifUrl
        AnimatedGIF aGif = null;
        try
        {
        	aGif = DBUtils.retrieveGif(conn, gifUrl);
        } catch (SQLException e)
        {
            e.printStackTrace();
            errorString = e.getMessage();
        }
        
        // If there is an error go back to the gif list page and
        // display the error
        if (errorString != null && aGif == null)
        {
            response.sendRedirect(request.getServletPath() + "/gifList");
            return;
        }
 
        // Store errorString in request attribute, before forward to views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("animatedgif", aGif);
 
        // Return the list of GIF classifications from the GIFClassification enum
        List<String> listCategory = Stream.of(GIFClassification.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        request.setAttribute("listType", listCategory);
        
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/editGifView.jsp");
        dispatcher.forward(request, response);
    }
 
    // After the user modifies the gif information, and click Submit.
    // This method will be executed.
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
        String url = (String) request.getParameter("url");
        String type = (String) request.getParameter("type");
        GIFClassification typeEnum = null;
        try
        {
        	typeEnum = GIFClassification.valueOf(type);
        } catch (Exception e)
        {
        	errorString = errorString + "Incorrect classification type";
        }
        // If there are no errors to this point update the modified GIF to the database
        try {
        	if (errorString == null || errorString.trim().compareTo("") == 0)
        	{
        		DBUtils.updateGif(conn, uname, url, type);
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
 
        // Store infomation to request attribute, before forward to views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("animatedgif", aGif);
 
        // If error, forward to Edit page.
        if (errorString != null && errorString.trim().compareTo("") != 0)
        {
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/editGifView.jsp");
            dispatcher.forward(request, response);
        }
        // If everything nice.
        // Redirect to the gif listing page.
        else
        {
            response.sendRedirect(request.getContextPath() + "/gifList");
        }
    }

}
