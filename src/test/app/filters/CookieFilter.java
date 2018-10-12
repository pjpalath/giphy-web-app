/**
 * 
 */
package test.app.filters;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import test.app.entities.UserAccount;
import test.app.util.DBUtils;
import test.app.util.SessionUtils;

/**
 * @author paulp
 *
 * This filter class is used to hold the cookies for the user
 * per session.
 */
@WebFilter(filterName = "cookieFilter", urlPatterns = { "/*" })
public class CookieFilter implements Filter
{
	/**
	 * 
	 */
	public CookieFilter()
	{
    }
 
    @Override
    public void init(FilterConfig fConfig) throws ServletException
    {
    }
 
    @Override
    public void destroy()
    {
    }
 
    /**
     * This is the filtering method that sets applicable cookies to the 
     * users session
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
 
        // Retrieve the currently logged in user if any
        UserAccount userInSession = SessionUtils.getLoginedUser(session); 
        if (userInSession != null) {
            session.setAttribute("COOKIE_CHECKED", "CHECKED");
            chain.doFilter(request, response);
            return;
        }
 
        // Return the connection attribute stored in the session
        Connection conn = SessionUtils.getStoredConnection(req);
 
        // Check the flag for whether the cookie has been checked. If it 
        // has not (No user details in session), use the connection attribute from the
        // cookie to retrieve the user details for the username in the session 
        String checked = (String) session.getAttribute("COOKIE_CHECKED");
        if (checked == null && conn != null)
        {
            String userName = SessionUtils.getUserNameInCookie(req);
            try
            {
                UserAccount user = DBUtils.findUser(conn, userName);
                SessionUtils.storeLoginedUser(session, user);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            // Mark checked Cookies.
            session.setAttribute("COOKIE_CHECKED", "CHECKED");
        }
 
        chain.doFilter(request, response);
    }
}
