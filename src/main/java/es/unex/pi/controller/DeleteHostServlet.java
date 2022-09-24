package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.dao.HostingCategoriesDAO;
import es.unex.pi.dao.HostingDAO;
import es.unex.pi.dao.JDBCHostingCategoriesDAOImpl;
import es.unex.pi.dao.JDBCHostingDAOImpl;
import es.unex.pi.dao.JDBCUserHostingDAOImpl;
import es.unex.pi.dao.UserHostingDAO;
import es.unex.pi.model.Hosting;
import es.unex.pi.model.HostingCategories;
import es.unex.pi.model.UserHosting;

@WebServlet("/DeleteHostServlet.do")


/**
 * Servlet implementation class DeleteHostServlet
 */
public class DeleteHostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteHostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("The request was made using GET ");
		
		// Obtenemos el ID del host
		String id = request.getParameter("id");
		long id_long = Long.parseLong(id);		
		
		//Guardamos el id del host en la sesión para el POST
		HttpSession session = request.getSession();
		session.setAttribute("id_host", id_long);
				
		// Obtenemos la conexión y los DAO
		Connection con = (Connection) getServletContext().getAttribute("dbConn");
		HostingDAO hostingDAO = new JDBCHostingDAOImpl();
		HostingCategoriesDAO hostingCategoriesDAO = new JDBCHostingCategoriesDAOImpl();
		hostingDAO.setConnection(con);
		hostingCategoriesDAO.setConnection(con);
		
		Hosting host = hostingDAO.get(id_long);
		
		if(host != null) {
			
			request.setAttribute("host", host);
			request.setAttribute("CheckType", "Eliminar");

			// Obtenemos todas las tuplas del alojamiento con sus categorias
			List<HostingCategories> categories = hostingCategoriesDAO.getAllByHosting(id_long);
			request.setAttribute("categoriesVector", categories);
			
			// Obtenemos los servicios en un único String y se convierte en un vector de Strings
			String services = host.getServices();
			String [] servicesArray = services.split(",");
			request.setAttribute("servicesVector", servicesArray);
			
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/CheckHost.jsp");
			view.forward(request, response);
		}
		else {
			response.sendRedirect("ListHostsServlet.do");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("The request was made using POST ");

		HttpSession session = request.getSession();
		long id_host = (long) session.getAttribute("id_host");
		session.removeAttribute("id_host");
	
		// Obtenemos la conexión a la BD. El objetivo es eliminar todas las categorias del alojamiento y el mismo
		Connection con = (Connection) getServletContext().getAttribute("dbConn");
		HostingDAO hostingDAO = new JDBCHostingDAOImpl();
		HostingCategoriesDAO hostingCategoriesDAO = new JDBCHostingCategoriesDAOImpl();
		UserHostingDAO userHostingDAO = new JDBCUserHostingDAOImpl();
		hostingCategoriesDAO.setConnection(con);
		hostingDAO.setConnection(con);
		userHostingDAO.setConnection(con);
		Hosting host = hostingDAO.get(id_host);
		
		if(host != null) {
			
			List<HostingCategories> hostingCategoriesList = hostingCategoriesDAO.getAllByHosting(id_host);
			Iterator<HostingCategories> itHostingCategoriesList = hostingCategoriesList.iterator();
			
			// Eliminamos todas las tuplas de la tabla HostingCategories relacionadas con el alojamiento a borrar
			while(itHostingCategoriesList.hasNext()) {
				HostingCategories hostingCategories = itHostingCategoriesList.next();
				if(!hostingCategoriesDAO.delete(hostingCategories.getIdh(), hostingCategories.getIdct())) {
					logger.info("Error al borrar la tupla <Host,category> de la BD");
				}
			}
			
			List<UserHosting> userHostingList = userHostingDAO.getAllByHost(id_host);
			Iterator<UserHosting> itUserHostingList = userHostingList.iterator();

			// Eliminamos todas las tuplas de la tabla UserHosting relacionadas con los Likes del alojamiento
			while(itUserHostingList.hasNext()) {
				UserHosting userHosting = itUserHostingList.next();
				if(!userHostingDAO.delete(userHosting.getIdh(), userHosting.getIdu())) {
					logger.info("Error al borrar la tupla <Host,User> de la BD");
				}
			}
			
			if(!hostingDAO.delete(id_host)) {
				logger.info("Error al borrar el alojamiento en la BD");
			}
			
		}
		response.sendRedirect("ListHostsServlet.do");
	}
}
