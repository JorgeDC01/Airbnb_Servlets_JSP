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
import java.util.logging.Logger;

import es.unex.pi.dao.HostingCategoriesDAO;
import es.unex.pi.dao.HostingDAO;
import es.unex.pi.dao.JDBCHostingCategoriesDAOImpl;
import es.unex.pi.dao.JDBCHostingDAOImpl;
import es.unex.pi.model.Hosting;
import es.unex.pi.model.HostingCategories;

@WebServlet("/CheckHostServlet.do")

/**
 * Servlet implementation class CheckHostServlet
 */
public class CheckHostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckHostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("The request was made using GET ");

		request.setAttribute("CheckType", "Confirmar");
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/CheckHost.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("The request was made using POST ");
		
		HttpSession session = request.getSession();
		
		// Obtenemos el objeto alojamiento de la sesión y el vector que contiene las categorias
		Hosting host = (Hosting) session.getAttribute("host");
		String [] categories = (String[]) session.getAttribute("categoriesVector");
		
		session.removeAttribute("host");
		session.removeAttribute("servicesVector");
		session.removeAttribute("categoriesVector");
		
		if(host != null) {
			
			// Obtenemos la conexion y se la pasamos al DAO de alojamientos.
			Connection con = (Connection) getServletContext().getAttribute("dbConn");
			HostingDAO hostingDAO = new JDBCHostingDAOImpl();
			hostingDAO.setConnection(con);
			
			long id_host = hostingDAO.add(host);

			if(id_host == -1) {
				logger.info("Error al añadir el host a la BD");
				response.sendRedirect("ListHostsServlet.do");
			}			
			else {
				
				// Si el alojamiento se ha insertado en la BD y tiene categoria
				if(categories != null) {
					
					boolean insertCategories = true;
					HostingCategoriesDAO h_c_DAO = new JDBCHostingCategoriesDAOImpl();
					h_c_DAO.setConnection(con);
					
					for(int i = 0; i < categories.length && insertCategories; i++) {
						
						// Creamos el objeto que unifica los id del 'host' y la categoria
						HostingCategories hostingCategories = new HostingCategories();
						
						hostingCategories.setIdct((Long.parseLong(categories[i])));
						hostingCategories.setIdh(id_host);
						
						if(!h_c_DAO.add(hostingCategories)) {
							insertCategories = false;
						}
					}
					
					if(!insertCategories) {
						logger.info("Error al añadir el hostingCategory a la BD.");
						response.sendRedirect("HostServlet.do");
					}
					else {
						response.sendRedirect("ListHostsServlet.do");
					}
				}
				else {
					// Si no tiene categoria alguna, no se tiene que insertar nada en HostingCategories
					response.sendRedirect("ListHostsServlet.do");
				}
			}
		}
		else {
			logger.info("El alojamiento obtenido de la BD está vacío");
			response.sendRedirect("HostServlet.do");
		}
		
	}

}
