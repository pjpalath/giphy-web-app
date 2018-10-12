/**
 * 
 */
package test.app.filters;

import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import test.app.conn.DBConnectionUtils;
import test.app.util.SessionUtils;

/**
 * @author paulp
 * 
 * This filter creates the connection to the database and sets
 * it in the session attribute before forwarding the request
 */
@WebFilter(filterName = "jdbcFilter", urlPatterns = { "/*" })
public class JDBCFilter implements Filter
{
	public JDBCFilter()
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
	 * This method checks to see if the request is a 
	 * request to a servlet. If so then that request
	 * needs access to the connection attribute
	 * 
	 * @param request
	 * @return
	 */
	private boolean needJDBC(HttpServletRequest request)
	{
		// Get servlet URL path
		String servletPath = request.getServletPath();
		String pathInfo = request.getPathInfo();
		String urlPattern = servletPath;

		if (pathInfo != null)
		{
			urlPattern = servletPath + "/*";
		}

		// Get a map of <ServletName, ServletRegistration>
		Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext()
				.getServletRegistrations();

		// Collection of all servlets in your Webapp.
		Collection<? extends ServletRegistration> values = servletRegistrations.values();
		for (ServletRegistration sr : values)
		{
			Collection<String> mappings = sr.getMappings();
			if (mappings.contains(urlPattern))
			{
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Only open database connection for valid requests. (For example, the path to the servlet, JSP, ..)
	 * Avoid opening connections for common requests (For example: image, css, javascript,... )
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		if (this.needJDBC(req))
		{			
			Connection conn = null;
			try
			{
				conn = DBConnectionUtils.getMySQLConnection();			
				conn.setAutoCommit(false);
				SessionUtils.storeConnection(req, conn);				
				chain.doFilter(request, response);
				conn.commit();
			} catch (Exception e)
			{
				e.printStackTrace();				
				throw new ServletException();
			} finally
			{				
			}
		}
		else
		{
			chain.doFilter(request, response);
		}
	}
}
