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
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.JDBCUserHostingDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.dao.UserHostingDAO;
import es.unex.pi.model.Hosting;
import es.unex.pi.model.HostingCategories;
import es.unex.pi.model.User;
import es.unex.pi.model.UserHosting;

@WebServlet("/DeleteUserServlet.do")

/**
 * Servlet implementation class DeleteUserServlet
 */
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("The request was made using GET ");
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/DeleteUser.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("The request was made using POST ");

		// Obtenemos la conexión con la base de datos
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		
		UserDAO userDAO = new JDBCUserDAOImpl();
		HostingDAO hostingDAO = new JDBCHostingDAOImpl();
		HostingCategoriesDAO hostingCategoriesDAO = new JDBCHostingCategoriesDAOImpl();
		UserHostingDAO userHostingDAO = new JDBCUserHostingDAOImpl();

		hostingCategoriesDAO.setConnection(conn);
		hostingDAO.setConnection(conn);
		userDAO.setConnection(conn);
		userHostingDAO.setConnection(conn);

		HttpSession session = request.getSession();
		User user_ID = (User) session.getAttribute("user");
		
		
		// Obtenemos los alojamientos de la cuenta del usuario a borrar
		List<Hosting> hostingList = hostingDAO.getAllByUser(user_ID.getId());
		Iterator<Hosting> itHostingList = hostingList.iterator();
		
		while(itHostingList.hasNext()) {
			
			Hosting host = itHostingList.next();
			// Obtenemos las categorias asociadas a cada alojamiento
			List<HostingCategories> hostingCategoriesList = hostingCategoriesDAO.getAllByHosting(host.getId());
			Iterator<HostingCategories> itHostingCategoriesList = hostingCategoriesList.iterator();
			
			while(itHostingCategoriesList.hasNext()) {
				
				HostingCategories hostingCategory = itHostingCategoriesList.next();
				
				// Se borra cada tupla de la tabla HostingCategories
				if(hostingCategoriesDAO.delete(hostingCategory.getIdh() ,hostingCategory.getIdct())) {
					logger.info("La tupla de la tabla HostingCategories (host:" + hostingCategory.getIdh() + ", category:" + hostingCategory.getIdct() + " se ha borrado correctamente.");
				}
				else {
					logger.info("La tupla de la tabla HostingCategories (host: " + hostingCategory.getIdh() + ", category:" + hostingCategory.getIdct() + " no se ha podido borrar.");
				}
			}
			
			// Obtenemos las tuplas de la tabla UserHosting asociadas a cada alojamiento del usuario
			List<UserHosting> userHostingList = userHostingDAO.getAllByHost(host.getId());
			Iterator<UserHosting> itUserHostingList = userHostingList.iterator();

			while(itUserHostingList.hasNext()) {
				UserHosting userHosting = itUserHostingList.next();
				
				// Se borran los Likes de cada alojamiento
				if(!userHostingDAO.delete(userHosting.getIdh(), userHosting.getIdu())) {
					logger.info("Error al borrar la tupla <Host: " + userHosting.getIdh() + ",User: " + userHosting.getIdu() + "> de la BD");
				}
			}
			
			// Se borra cada alojamiento
			hostingDAO.delete(host.getId());
		}
		
		
		// Obtenemos todos los Likes que el usuario ha otorgado al resto de alojamientos
		List<UserHosting> UserhostingList = userHostingDAO.getAllByUser(user_ID.getId());
		Iterator<UserHosting> itUserhostingList = UserhostingList.iterator();

		while(itUserhostingList.hasNext()) {
			
			UserHosting userHosting = itUserhostingList.next();
			
			// Se ha de decrementar el numero de Likes del alojamiento al que le dio like el usuario a borrar
			Hosting hostLiked = hostingDAO.get(userHosting.getIdh());
			
			hostLiked.setLikes(hostLiked.getLikes()-1);
			if(!hostingDAO.save(hostLiked)) {
				logger.info("Error al actualizar el alojamiento con id: " + hostLiked.getId() + " 'liked' por el usuario a borrar");
			}
			
			// Se borran los Likes del usuario al resto de alojamientos
			if(!userHostingDAO.delete(userHosting.getIdh(), userHosting.getIdu())) {
				logger.info("Error al borrar la tupla <Host: " + userHosting.getIdh() + ",User: " + userHosting.getIdu() + "> de la BD");
			}
		}
		
		
		// Se borra la cuenta del usuario
		if(userDAO.delete(user_ID.getId())) {
			
			logger.info("The account with ID: " + user_ID + " has been removed from the DataBase");
			session.removeAttribute("user");
			session.invalidate();
			response.sendRedirect("LoginUserServlet.do");
		}
		else {
			logger.info("Error at removing the account " + user_ID);
			response.sendRedirect("ListHostsServlet.do");
		}
	}

}
