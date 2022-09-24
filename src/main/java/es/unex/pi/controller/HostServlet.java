package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Logger;

import es.unex.pi.model.Hosting;
import es.unex.pi.model.User;

@WebServlet("/HostServlet.do")

/**
 * Servlet implementation class HostServlet
 */
public class HostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("The request was made using GET ");
		
		request.setAttribute("EditOrNewType", "Crear");
		
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/EditHost.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("The request was made using POST ");
		
		// Obtenemos la sesión
		HttpSession session = request.getSession();
		
		// Creamos el objeto alojamiento donde se almacenarán los datos del formulario
		Hosting host = new Hosting();
		
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
		session.setAttribute("categoriesVector", categories);	
		
		session.setAttribute("host", host);
		
		// servicesVector sirve para el JSP recorrer con un bucle los servicios e insertar checkbox
		session.setAttribute("servicesVector",request.getParameterValues("services"));			
		
		response.sendRedirect("CheckHostServlet.do");
		
		
		// Información en la consola
		logger.info("Alojamiento: " + host.getTitle() + ". Servicios: " + host.getServices() + ". Categoria: " + host.getIdu());
	}
}
