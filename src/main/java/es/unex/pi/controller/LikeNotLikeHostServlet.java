package es.unex.pi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import es.unex.pi.dao.HostingDAO;
import es.unex.pi.dao.JDBCHostingDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.JDBCUserHostingDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.dao.UserHostingDAO;
import es.unex.pi.model.Hosting;
import es.unex.pi.model.User;
import es.unex.pi.model.UserHosting;

@WebServlet("/LikeNotLikeHostServlet.do")

/**
 * Servlet implementation class LikeOrUnlikeHostServlet
 */
public class LikeNotLikeHostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());  

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikeNotLikeHostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("The request was made using GET ");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		// Obtenemos el ID del alojamiento
		String id_host_string = request.getParameter("id");
		long id_host = Long.parseLong(id_host_string);
		
		// Obtenemos la conexión de la BD
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");		
		UserHostingDAO userHostingDAO = new JDBCUserHostingDAOImpl();
		HostingDAO hostingDAO = new JDBCHostingDAOImpl();
		
		userHostingDAO.setConnection(conn);
		hostingDAO.setConnection(conn);
		
		UserHosting userHosting = userHostingDAO.get(id_host, (long)user.getId());
		
		if(userHosting == null) { // El usuario no ha dado todavía Like al alojamiento. Se tiene que añadir el Like
			
			// Insertamos en la tabla UserHosting
			userHosting = new UserHosting();
			userHosting.setIdh(id_host);
			userHosting.setIdu((long)user.getId());
			
			// Aumentamos el numero de Likes en 1 en el hosting
			Hosting hosting = hostingDAO.get(id_host);
			hosting.setLikes(hosting.getLikes()+1);
			
			if(!hostingDAO.save(hosting)) {
				logger.info("Error al incrementar los Likes en la tabla Hosting de la BD");
				response.sendRedirect("ListHostsServlet.do");
			}
			
			if(!userHostingDAO.add(userHosting)) {
				logger.info("Error al añadir el nuevo registro de Like en la tabla UserHosting de la BD");
				response.sendRedirect("ListHostsServlet.do");
			}
		}
		else {	// El usuario le ha dado Like anteriormente. Se tiene que eliminar el Like
			
			if(!userHostingDAO.delete(userHosting.getIdh(), userHosting.getIdu())) {
				logger.info("Error al eliminar el registro de Likes en la tabla UserHosting de la BD");
				response.sendRedirect("ListHostsServlet.do");
			}
			
			Hosting hosting = hostingDAO.get(id_host);
			hosting.setLikes(hosting.getLikes()-1);
			
			if(!hostingDAO.save(hosting)) {
				logger.info("Error al decrementar los Likes en la tabla Hosting de la BD");
				response.sendRedirect("ListHostsServlet.do");
			}
		}
		response.sendRedirect("ListHostsServlet.do");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("The request was made using POST ");
		doGet(request,response);
	}

}
