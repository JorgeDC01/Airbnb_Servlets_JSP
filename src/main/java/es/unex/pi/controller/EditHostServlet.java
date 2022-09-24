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
import es.unex.pi.model.Hosting;
import es.unex.pi.model.HostingCategories;
import es.unex.pi.model.User;

@WebServlet("/EditHostServlet.do")

/**
 * Servlet implementation class EditHostServlet
 */
public class EditHostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditHostServlet() {
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
		
		// Realizamos las operaciones pertinentes con la BD
		Connection con = (Connection) getServletContext().getAttribute("dbConn");
		HostingDAO hostingDAO = new JDBCHostingDAOImpl();
		HostingCategoriesDAO hostingCategoriesDAO = new JDBCHostingCategoriesDAOImpl();
		hostingDAO.setConnection(con);
		hostingCategoriesDAO.setConnection(con);
		
		Hosting host = hostingDAO.get(id_long);
		
		if(host != null) {
			request.setAttribute("host", host);
			request.setAttribute("EditOrNewType", "Editar");
			
			// Obtenemos todas las tuplas del alojamiento con sus categorias
			List<HostingCategories> categories = hostingCategoriesDAO.getAllByHosting(id_long);
			request.setAttribute("categoriesVector", categories);
			
			// Obtenemos los servicios en un único String y se convierte en un vector de Strings
			String services = host.getServices();
			String [] servicesArray = services.split(",");
			request.setAttribute("servicesVector", servicesArray);
			
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/EditHost.jsp");
			view.forward(request, response);
		}
		else {
			logger.info("Error al editar el alojamiento");
			response.sendRedirect("ListHostsServlet.do");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("The request was made using POST ");
		
		// Obtenemos la sesión
		HttpSession session = request.getSession();
		session.removeAttribute("categoriesVector");
		
		// Creamos el objeto alojamiento donde se almacenarán los datos del formulario
		Hosting host = new Hosting();
		long id_host = (long) session.getAttribute("id_host");
		
		session.removeAttribute("id_host");
		
		host.setId(id_host);
		host.setTitle(request.getParameter("name"));
		host.setTelephone(request.getParameter("telephone"));
		host.setPrice(Integer.parseInt(request.getParameter("price")));
		host.setLocation(request.getParameter("located"));
		host.setDescription(request.getParameter("descrip"));
		host.setContactEmail(request.getParameter("email"));
		host.setRedSocial(request.getParameter("twitter"));
		
		// Establecemos la disponibilidad. 0: Disponible. 1: Reservado. Checkbox tendrá un valor si está disponible
		String [] disponibilidad = request.getParameterValues("available");
		
		if(disponibilidad == null){
			host.setAvailable(1);
		}
		else if(disponibilidad.length == 1){
			host.setAvailable(0);
		}

		// Establecemos el IDU del host
		User user = (User) session.getAttribute("user");
		host.setIdu((int) user.getId());		
		
		// Establecemos los servicios		
		String aux;
		String [] services = request.getParameterValues("services");
		
		if(services == null) {
			aux = " ";
		}
		else {
			aux = services[0] + ",";
			for(int i = 1; i < services.length; i++){
				aux = aux + services[i] + ",";
				logger.info(aux);
			}
		}
				
		host.setServices(aux);
		
		// Obtenemos el conjunto de valores (0,1,2,3) correspondientes a las categorias marcadas
		String [] categories = request.getParameterValues("categories");
		
		// Obtenemos la conexion y se la pasamos al DAO de alojamientos.
		Connection con = (Connection) getServletContext().getAttribute("dbConn");
		HostingDAO hostingDAO = new JDBCHostingDAOImpl();
		HostingCategoriesDAO hostingCategoriesDAO = new JDBCHostingCategoriesDAOImpl();
		hostingCategoriesDAO.setConnection(con);
		hostingDAO.setConnection(con);
		
		host.setLikes(hostingDAO.get(host.getId()).getLikes());
		if(hostingDAO.save(host)) {
			
			logger.info("Se ha actualizado correctamente el host en la tabla HOSTING de id: " + host.getId());

			// Se obtienen todas las tuplas de la tabla HostingCategories y se borran
			List<HostingCategories> hostingCategoriesList = hostingCategoriesDAO.getAllByHosting(host.getId());
			Iterator<HostingCategories> itHostingCategoriesList = hostingCategoriesList.iterator();
			
			while(itHostingCategoriesList.hasNext()) {
				HostingCategories host_cat_aux = itHostingCategoriesList.next();
				hostingCategoriesDAO.delete(host_cat_aux.getIdh(), host_cat_aux.getIdct());
			}
			
			if(categories.length != 0) {
				
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
					doGet(request,response);
				}
				else {
					response.sendRedirect("ListHostsServlet.do");
				}
			}
		}
		else {
			logger.info("Error al actualizar el host de id: " + host.getId());
		}
				
	}

}
