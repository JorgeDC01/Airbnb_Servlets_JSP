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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import es.unex.pi.dao.HostingCategoriesDAO;
import es.unex.pi.dao.HostingDAO;
import es.unex.pi.dao.JDBCHostingCategoriesDAOImpl;
import es.unex.pi.dao.JDBCHostingDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.JDBCUserHostingDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.dao.UserHostingDAO;
import es.unex.pi.model.Hosting;
import es.unex.pi.model.HostingCategories;
import es.unex.pi.model.User;
import es.unex.pi.util.Triplet;

@WebServlet("/HostDetailsServlet.do")

/**
 * Servlet implementation class SocialNetworkUserHosting
 */
public class HostDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());  

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HostDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    /*
	 * This method return the hosting related to one given in the GET method with a ID
	 * @param request The request.
	 * @param id_host The host id 
	 * @return code List of Hosting 
	 */
    protected List<Hosting> getHostsRelated(HttpServletRequest request, long id_host) {
    	
    	// Obtenemos la conexión
    	Connection conn = (Connection) getServletContext().getAttribute("dbConn");
    	HostingDAO hostingDAO = new JDBCHostingDAOImpl();
    	hostingDAO.setConnection(conn);
    	
    	// Pasamos la conexion al DAO de alojamientos&categorias
    	HostingCategoriesDAO HostingsCategoriesDAO = new JDBCHostingCategoriesDAOImpl();
    	HostingsCategoriesDAO.setConnection(conn);
    			
    			
		List<Hosting> hostingList = new ArrayList<Hosting>();
		Hosting host = hostingDAO.get(id_host);
		// Obtenemos los servicios separados por comas en un vector
		String service = host.getServices();
		String servicesVector [] = service.split(",");
		
		
    	/* Primero se realizará la búsqueda por localidad similar*/		
		hostingList.addAll(hostingDAO.getAllByLocation(host.getLocation()));
    	
		
		/* En segundo lugar se realizará la búsqueda por precio*/
		hostingList.addAll(hostingDAO.getAllByPrice(host.getPrice()));
		
		
		/* En tercer lugar se realizará la búsqueda por servicios*/
		if(servicesVector.length != 0) {
			List<Hosting> aux = hostingDAO.getAll();
			Iterator<Hosting> itAux = aux.iterator();
			
			// Para cada host de la BD
			while(itAux.hasNext()) {
				Hosting hostAux = itAux.next();
				String serviceAux = hostAux.getServices();
				
				boolean encontrado = false;
				
				// Para cada servicio del host aux
				for(int i = 0; i < servicesVector.length && !encontrado; i++) {
					if(serviceAux.contains(servicesVector[i])) {
						encontrado = true;
						hostingList.add(hostAux);
					}
				
				}
				
			}
		}
		
		
		/* En cuarto lugar se realizará la búsqueda por categorías*/		
		List<HostingCategories> hostingCategoriesList = HostingsCategoriesDAO.getAllByHosting(id_host);
		List<HostingCategories> hostingCategoriesRelatedList = new ArrayList<>();

		Iterator<HostingCategories> itHostingCategoriesList = hostingCategoriesList.iterator();
		
		while(itHostingCategoriesList.hasNext()) {
			HostingCategories aux = itHostingCategoriesList.next();
			// Por cada categoria del Host detallado, se desean buscar el resto de alojamientos que la compartan
			hostingCategoriesRelatedList.addAll(HostingsCategoriesDAO.getAllByCategory(aux.getIdct()));
			
			Iterator<HostingCategories> itHostingCategoriesRelatedList = hostingCategoriesRelatedList.iterator();
			while(itHostingCategoriesRelatedList.hasNext()) {
				hostingList.add(hostingDAO.get(itHostingCategoriesRelatedList.next().getIdh()));
			}
		}
		
		
		// Se eliminan todos los host que coincidan con aquel sobre el que se desea realizar busqueda de relacionados
		Iterator<Hosting> itHostingList = hostingList.iterator();
		
		while(itHostingList.hasNext()) {
			if(itHostingList.next().getId() == host.getId()) {
				itHostingList.remove();
			}
		}
		
		// Hay que eliminar duplicados con respecto el ID
		Set<Hosting> set = new LinkedHashSet<>();
		set.addAll(hostingList);
		hostingList.clear();
		hostingList.addAll(set);
    	return hostingList;
    }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("The request was made using GET ");

		HttpSession session = request.getSession();		
		
		// Obtenemos el ID del host
		String id = request.getParameter("id");
		long id_long = Long.parseLong(id);

		
		// Realizamos las operaciones pertinentes con la BD
		Connection con = (Connection) getServletContext().getAttribute("dbConn");
		HostingDAO hostingDAO = new JDBCHostingDAOImpl();
		hostingDAO.setConnection(con);
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(con);
		HostingCategoriesDAO HostingsCategoriesDAO = new JDBCHostingCategoriesDAOImpl();
		HostingsCategoriesDAO.setConnection(con);
		UserHostingDAO userHostingDAO = new JDBCUserHostingDAOImpl();
		userHostingDAO.setConnection(con);
				
		
		// Obtenemos los datos relacionados con el host que debe ser detallado junto con las categorias
		Hosting host = hostingDAO.get(id_long);
		
		
		
		if(host != null) {
			
			// Insertamos el host detallado
			request.setAttribute("host", host);
			// Insertamos sus categorias en la request
			List<HostingCategories> HostingsCategories = HostingsCategoriesDAO.getAllByHosting(host.getId());
			request.setAttribute("categories", HostingsCategories);
			
			
			// Obtenemos los host relacionados con el detallado
	    	List<Hosting> HostingList = getHostsRelated(request, id_long);

			Iterator<Hosting> itHostingList = HostingList.iterator();
			
			// Parametro del request que contiene para cada alojamiento: usuario y categorias
			List<Triplet<Hosting, User, List<HostingCategories>>> HostingsUserList = new ArrayList<Triplet<Hosting, User, List<HostingCategories>>>();
			
			while(itHostingList.hasNext()) {
				
				Hosting Hosting = (Hosting) itHostingList.next();
				User user = userDAO.get(Hosting.getIdu());
				List<HostingCategories> HostingsCategories2 = HostingsCategoriesDAO.getAllByHosting(Hosting.getId());
				
				logger.info("User " + user.getUsername());
				
			
				// Buscamos en la tabla 'UserHosting' si existe un registro donde coincida el id del host y el del usuario
				User user_logged_in = (User) session.getAttribute("user");

				if(userHostingDAO.get(Hosting.getId(),(long) user_logged_in.getId()) != null) {
					Hosting.setIsFav("YES");
				}
				else {
					Hosting.setIsFav("NO");
				}
				HostingsUserList.add(new Triplet<Hosting, User, List<HostingCategories>>(Hosting,user,HostingsCategories2));
			}
			
			
			request.setAttribute("HostingRelated", HostingsUserList);
			
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/HostDetails.jsp");
			view.forward(request, response);
		}
		else {
			logger.info("Error al obtener el alojamiento");
			response.sendRedirect("ListHostsServlet.do");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
