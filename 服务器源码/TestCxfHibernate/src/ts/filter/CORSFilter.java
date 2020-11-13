package ts.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletResponse;

public class CORSFilter implements Filter {
	private FilterConfig filterConfig;
	
	@Override
	public void destroy() {
		
	}
	
	@Override
	public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain chain)
	        throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) sresponse;
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE,OPTIONS");
		response.addHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header");
		response.addHeader("Access-Control-Max-Age", "1800");// 30 min
		chain.doFilter(srequest, sresponse);
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
}
