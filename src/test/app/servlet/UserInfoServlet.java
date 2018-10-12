/**
 * 
 */
package test.app.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import test.app.entities.UserAccount;
import test.app.util.SessionUtils;

/**
 * Servlet implementation class UserInfoServlet
 * This servlet is used to retrieve the user info for the
 * logged in user
 */
@WebServlet(urlPatterns = { "/userInfo" })
public class UserInfoServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserInfoServlet()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
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
     	
		// Store info to the request attribute before forwarding.
		request.setAttribute("user", loginedUser);

		// If the user has logged in, then forward to the page
		// /WEB-INF/views/userInfoView.jsp
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/userInfoView.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{	
		doGet(request, response);
	}
}
