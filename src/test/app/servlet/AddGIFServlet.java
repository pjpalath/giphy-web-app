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
 * Servlet implementation class AddGIFServlet
 */
@WebServlet(urlPatterns = { "/addGif" })
public class AddGIFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddGIFServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/addGifView.jsp");
        dispatcher.forward(request, response);
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        
        String errorString = "";
        
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
        try {
        	if (errorString == null || errorString.trim().compareTo("") == 0) {
        		DBUtils.insertGif(conn, uname, url, type);
        	}
		} catch (SQLException e) { 
			e.printStackTrace();
			errorString = "Unable to insert gif details to DB";
		}
        
        AnimatedGIF aGif = new AnimatedGIF();
        aGif.setUrl(url);        
        aGif.setType(typeEnum);
 
        // Store infomation to request attribute, before forward to views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("animatedgif", aGif);
 
        // If error, forward to Edit page.
        if (errorString != null && errorString.trim().compareTo("") != 0) {
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/addGifView.jsp");
            dispatcher.forward(request, response);
        }
        // If everything nice.
        // Redirect to the product listing page.
        else {
            response.sendRedirect(request.getContextPath() + "/gifList");
        }
    }

}
