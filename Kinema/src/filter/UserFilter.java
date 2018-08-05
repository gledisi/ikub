package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import entitete.User;
import bean.UserBean;

@WebFilter(filterName = "/UserFilter", urlPatterns = { "/*" })
public class UserFilter implements Filter {

	public UserFilter() {}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest httpRequest= (HttpServletRequest) request;
		HttpSession session = ((HttpServletRequest) request).getSession(false);
		UserBean userBean = (session != null) ? (UserBean) session.getAttribute("userBean") : null;
		User user = (userBean != null) ? userBean.getUser() : null;
		String currentPath = ((HttpServletRequest) request).getRequestURL().toString();
		if (user != null) {
			if (user.getRoli().getRoli().equals("Admin")) {
				if (!currentPath.contains("admin") && !allowed(currentPath)) {
					res.sendRedirect("admin/movie.xhtml");
				} else {
					chain.doFilter(request, response);
				}
			} else if (user.getRoli().getRoli().equals("Client")) {
				if (!currentPath.contains("client") && !allowed(currentPath)) {
					res.sendRedirect("client/clientHome.xhtml");
				} else {
					chain.doFilter(request, response);
				}
			}
		} else {
			if (!allowed(currentPath) && !currentPath.contains("login")) {
				if (!currentPath.contains("sign-up")) {
					
					res.sendRedirect(httpRequest.getContextPath()+"/login.xhtml");

				} else {
					chain.doFilter(request, response);
				}
			}

			else {
				chain.doFilter(request, response);
			}

		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	private boolean allowed(String path) {
		return path.contains("javax.faces.resource") || path.contains(".png") || path.contains("resources");
	}

}
