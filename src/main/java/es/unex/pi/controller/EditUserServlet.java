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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;

@WebServlet("/EditUserServlet.do")

/**
 * Servlet implementation class EditUserServlet
 */
public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("The request was made using GET ");
		
		// En este momento disponemos los datos de la cuenta en el objeto "user" de la sesión, datos que serán 
		// accedidos desde el .JSP
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/EditUser.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("The request was made using POST ");
		
		// Obtenemos la sesion del usuario que debe ser actualizada con los nuevos datos introducidos.
		HttpSession session = request.getSession();
		User user_ID = (User) session.getAttribute("user");
	
		// Establecemos la conexion con la base de datos
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		
		// Creamos el objeto usuario
		User user = new User();
		user.setUsername(request.getParameter("username"));
		user.setEmail(request.getParameter("email"));
		user.setPassword(request.getParameter("password"));
		user.setId(user_ID.getId());
		
		Map<String, String> messages = new HashMap<String, String>();
		
		if (user.validate(messages) && userDAO.save(user)) {
			
			// Una vez actualizado los nuevos datos del usuario en la BD, se actualiza el de la sesión
			session.removeAttribute("user");
			session.setAttribute("user", user);
			response.sendRedirect("ListHostsServlet.do");
		}
		else {
			
			if(messages.isEmpty()) {logger.info("Error al editar en el acceso a la BD");}
			else {logger.info("Error al editar los datos por inválidos");}
			
			doGet(request,response);
		}
	}

}
