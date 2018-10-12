/**
 * 
 */
package test.app.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/** 
 * @author paulp
 *
 * This filter sets the appropriate encoding to the request
 * before forwarding it (In this case UTF-8 encoding)
 */
@WebFilter(filterName = "encodingFilter", urlPatterns = { "/*" })
public class EncodingFilter implements Filter {

	public EncodingFilter()
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
	 * 
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		request.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}
}
