package es.unex.pi.filter;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import es.unex.pi.dao.HostingDAO;
import es.unex.pi.dao.JDBCHostingDAOImpl;
import es.unex.pi.model.Hosting;
import es.unex.pi.model.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



/**
 * Servlet Filter implementation class EditDeleteFilter
 */
public class EditDeleteFilter extends HttpFilter {
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    /**
     * @see HttpFilter#HttpFilter()
     */
    public EditDeleteFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		logger.info("------- FILTER EDIT_DELETE ------");

		HttpServletRequest req= (HttpServletRequest) request;
		HttpServletResponse res= (HttpServletResponse) response;
		String path = ((HttpServletRequest) request).getRequestURI();
		HttpSession session = req.getSession(false);
		logger.info("PATH: " + path);

		
		User user = (User) session.getAttribute("user");
		
		String id_host = req.getParameter("id");
		long id_long = Long.parseLong(id_host);
			
		Connection con = (Connection) req.getSession().getServletContext().getAttribute("dbConn");
		HostingDAO hostingDAO = new JDBCHostingDAOImpl();
		hostingDAO.setConnection(con);
				
		Hosting host = hostingDAO.get(id_long);
		if(host == null) {
			logger.info("Error al modificar los datos del alojamiento porque no existe en la BD");
			res.sendRedirect("ListHostsServlet.do");
		}
		else {
			
			if((long)host.getIdu() != user.getId()) {
				logger.info("Error al modificar los datos del alojamiento porque no eres el propietario");
				res.sendRedirect("ListHostsServlet.do");
			}
			else {
				chain.doFilter(req, res);						
			}
		}
	}

	
	

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
