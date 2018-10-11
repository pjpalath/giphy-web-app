package test.app.util;

import java.sql.Connection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import test.app.entities.UserAccount;

public class SessionUtils {

	public SessionUtils() {
	}

	public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";
	 
    public static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_USER_NAME_IN_COOKIE";
 
    // Store Connection in request attribute.
    public static void storeConnection(HttpServletRequest request, Connection conn) {
        request.setAttribute(ATT_NAME_CONNECTION, conn);
    }
 
    // Get the Connection object has been stored in attribute of the request.
    public static Connection getStoredConnection(HttpServletRequest request) {
        Connection conn = (Connection) request.getAttribute(ATT_NAME_CONNECTION);
        return conn;
    }
 
    // Store user info in Session.
    public static void storeLoginedUser(HttpSession session, UserAccount loginedUser) {
        // On the JSP can access via ${loginedUser}
        session.setAttribute("loginedUser", loginedUser);
    }
 
    // Get the user information stored in the session.
    public static UserAccount getLoginedUser(HttpSession session) {
        UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");
        return loginedUser;
    }
 
    // Store info in Cookie
    public static void storeUserCookie(HttpServletResponse response, UserAccount user) {
        System.out.println("Store user cookie");
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, user.getUserName());
        // 1 day (Converted to seconds)
        cookieUserName.setMaxAge(24 * 60 * 60);
        response.addCookie(cookieUserName);
    }
 
    public static String getUserNameInCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ATT_NAME_USER_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
 
    // Delete cookie.
    public static void deleteUserCookie(HttpServletResponse response) {
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);
        // 0 seconds (This cookie will expire immediately)
        cookieUserName.setMaxAge(0);
        response.addCookie(cookieUserName);
    }
    
    public static String getUnameFromRequest(HttpServletRequest request)
    {
    	String uname = null;
    	String rawCookie = request.getHeader("Cookie");
    	System.out.println(rawCookie);
		String[] rawCookieParams = rawCookie.split(";");
		for(String rawCookieNameAndValue :rawCookieParams)
		{
		  String[] rawCookieNameAndValuePair = rawCookieNameAndValue.split("=");
		  if (rawCookieNameAndValuePair[0].trim().equals(ATT_NAME_USER_NAME))
		  {
			  uname = rawCookieNameAndValuePair[1];
		  }
		}
		return uname;
    }
}
