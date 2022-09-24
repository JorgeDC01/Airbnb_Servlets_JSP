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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.Map;

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
import es.unex.pi.model.UserHosting;
import es.unex.pi.util.LikeComparator;
import es.unex.pi.util.Triplet;

@WebServlet("/ListHostsServlet.do")

/**
 * Servlet implementation class ListHostsServlet
 */
public class ListHostsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());  
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListHostsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /*
	 * This method return the hosting depending on the code given in the request.
	 * @param request The request.
	 * int code:
	 * 1: Available hosting.
	 * 2: Reserved hosting.
	 * 3: Hosting Ordered by likes.
	 * 4: Hosting Ordered with a MIN.Likes.	
	 * @return code Int
	 */
	protected List<Hosting> getHostsByParameter(HttpServletRequest request) {
		
		// Obtenemos la conexión
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		HostingDAO hostingDAO = new JDBCHostingDAOImpl();
		hostingDAO.setConnection(conn);
		
		// Establecemos el código para filtrar los Hosting
		int code;
		if(request.getParameter("code") == null) {
			code = 0;
		}
		else {
			code = Integer.parseInt(request.getParameter("code"));
		}
		
		List<Hosting> hostingList = new ArrayList<Hosting>();
		switch(code) {
		case 0:
			hostingList = hostingDAO.getAll();
			break;
		case 1:
			hostingList = hostingDAO.getAll();
			Iterator<Hosting> itHostingList = hostingList.iterator();
			
			while(itHostingList.hasNext()) {
				Hosting aux = itHostingList.next();
				if(aux.getAvailable() == 1) {
					itHostingList.remove();
				}
			}
			break;
		case 2:
			hostingList = hostingDAO.getAll();
			Iterator<Hosting> itHostingReservedList = hostingList.iterator();
			
			while(itHostingReservedList.hasNext()) {
				Hosting aux = itHostingReservedList.next();
				if(aux.getAvailable() == 0) {
					itHostingReservedList.remove();
				}
			}
			break;
		case 3:
			hostingList = hostingDAO.getAll();
			hostingList.sort(new LikeComparator());
			break;
		case 4:
			int min_likes = Integer.parseInt(request.getParameter("min_likes"));
			hostingList = hostingDAO.getAllByMinLikes(min_likes);
			hostingList.sort(new LikeComparator());
			break;
		
		case 5:
			String search = request.getParameter("search");
			hostingList.addAll(hostingDAO.getAllBySearchAll(search));
			break;
		}	
		return hostingList;
	}
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("The request was made using GET ");
		
		HttpSession session = request.getSession();
		// Eliminamos la información introducida en el EditHost.jsp cuando retornamos del CheckHost.jsp
		session.removeAttribute("host");
		session.removeAttribute("servicesVector");
		session.removeAttribute("categoriesVector");
		
		// Obtenemos la conexion a la BD, se la pasamos al DAO de usuarios para obtener el usuario de cada alojamiento
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");		
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		
		// Pasamos la conexion al DAO de los alojamientos
		HostingDAO hostingDAO = new JDBCHostingDAOImpl();
		hostingDAO.setConnection(conn);
		
		// Pasamos la conexion al DAO de alojamientos&categorias
		HostingCategoriesDAO HostingsCategoriesDAO = new JDBCHostingCategoriesDAOImpl();
		HostingsCategoriesDAO.setConnection(conn);
		
		// Pasamos la conexión al DAO de User&Hosting
		UserHostingDAO userHostingDAO = new JDBCUserHostingDAOImpl();
		userHostingDAO.setConnection(conn);
		
		// Método que aglutina código para obtener los hosting según el tipo de búsqueda
		List<Hosting> HostingList = getHostsByParameter(request);
		
		Iterator<Hosting> itHostingList = HostingList.iterator();
		
		// Parametro del request que contiene para cada alojamiento: usuario y categorias
		List<Triplet<Hosting, User, List<HostingCategories>>> HostingsUserList = new ArrayList<Triplet<Hosting, User, List<HostingCategories>>>();
		
		while(itHostingList.hasNext()) {
			
			Hosting Hosting = (Hosting) itHostingList.next();
			User user = userDAO.get(Hosting.getIdu());
			List<HostingCategories> HostingsCategories = HostingsCategoriesDAO.getAllByHosting(Hosting.getId());
			
			logger.info("User " + user.getUsername());
			
		
			// Buscamos en la tabla 'UserHosting' si existe un registro donde coincida el id del host y el del usuario
			User user_logged_in = (User) session.getAttribute("user");

			if(userHostingDAO.get(Hosting.getId(),(long) user_logged_in.getId()) != null) {
				Hosting.setIsFav("YES");
			}
			else {
				Hosting.setIsFav("NO");
			}
			HostingsUserList.add(new Triplet<Hosting, User, List<HostingCategories>>(Hosting,user,HostingsCategories));
		}
		
		request.setAttribute("HostingsList", HostingsUserList);
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/ListHosts.jsp");
		
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	
}
