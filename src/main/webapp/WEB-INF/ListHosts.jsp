<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html id=list>
<head>
<meta charset="UTF-8" />
<meta http-equiv="Content-Type" content="text/html" />
<title>Página principal Alojamientos</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css2/tailwind.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css2/background.css">
</head>

<body>
<body>
	<!-- Navbar  -->
    <div id="container" class="w-100% h-100% flex flex-col">
        <nav
			class="bg-white h-16 flex-initial flex gap-5 shadow-md">
            <div>
                <!-- Navbar principal -->
                <div class="w-44">
                    <!-- Logo Airbnb -->
                    <div class="flex items-center py-4 px-6">
                        <img src="${pageContext.request.contextPath}/img/logo2.png" alt="Logo"
							class="h-8 w-8">
                        <span
							class="font-semibold text-gray-500 text-lg">Alojamientos</span>
                    </div>
                </div>
            </div>

            <p class="flex-none py-5  text-gray-500 border-b-4 border-blue-500 font-semibold ">Cuenta :</p>
            
			<p class="flex-none py-5  text-red-500 font-bold ">${user.username}</p>
            <a href="EditUserServlet.do"
				class="flex-none self-center px-2 py-2  text-white bg-blue-500 rounded-lg hover:bg-blue-400  transition duration-300">Editar
                cuenta
            </a>
            <a href="DeleteUserServlet.do"
				class="flex-none self-center ml-2 py-2 px-2 text-white bg-blue-500 rounded-lg hover:bg-blue-400 transition duration-300">Borrar
                cuenta</a>
            <a href="LogoutUserServlet.do"
				class="mr-4 ml-auto flex-none self-center inline-flex px-3 py-2 text-white bg-red-500 rounded-lg hover:bg-red-400  transition duration-300">Cerrar
                sesión</a>
        </nav>

        <!-- Tarjetas de alojamientos -->
        <div id="container2"
			class="flex-1  max-w-8xl relative shadow-md sm:rounded-lg overflow-y-auto h-screen flex flex-col">
			<div id="contenedor3" class=" flex flex-row-">
			
	            <form class="flex items-center p-4 mt-1 flex-none" action="ListHostsServlet.do" method="GET">   
    				<label for="simple-search" class="sr-only">Search</label>
    				<div class="relative flex-none">
        				<input type="text" autocomplete="off" name="search" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Search" required>
    					<input type="hidden" value="5" name="code"/>
    				</div>
    				<button type="submit" class="p-2.5 ml-2 text-sm font-medium text-white bg-blue-500 rounded-lg border border-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
      				  <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path></svg>
      				  <span class="sr-only">Search</span>
  				  </button>
				</form>
	            
	            <div class="relative p-4 mt-1 flex-none">
	                    <a href="HostServlet.do"
							class="flex-none px-2 text-center bg-gray-50 border border-gray-300 hover:border-gray-700 text-gray-500 text-sm rounded-lg block w-46 p-2.5 "
						>Crear Alojamiento</a>	
	            </div>
	            
	            
	            <div class="dropdown  p-4 mt-1 flex-none">
				  <button onclick="myFunction()" class="dropbtn bg-blue-500 px-2 text-center border border-gray-300 hover:border-gray-700 text-gray-500 text-sm rounded-lg block w-46 p-2.5">Estado Alojamiento</button>
				  <div id="myDropdown" class="dropdown-content text-sm">
				    <a href="ListHostsServlet.do?code=0">Todos</a>
				    <a href="ListHostsServlet.do?code=1">Disponibles</a>
				    <a href="ListHostsServlet.do?code=2">Reservados</a>
				  </div>
				</div>
				
	            <div class="p-4 mt-1 flex-none">
	                    <a href="ListHostsServlet.do?code=3"
							class=" px-2 text-center bg-gray-50 border border-gray-300 hover:border-gray-700 text-gray-500 text-sm rounded-lg block w-46 p-2.5 "
						>Ordenar por Likes</a>	
	            </div>
	            
	            	<form class="p-4 mt-1 flex-none" action="ListHostsServlet.do" method="GET">
						<div class="relative inline-flex text-center bg-gray-50 border border-gray-300 hover:border-gray-700 text-gray-500 text-sm rounded-lg block w-28 p-2.5 ">
	            			<input type="submit" class="mr-2   bg-transparent 	" value="Min"/>
	            			<input type="number" min="0" class="text-center w-full text-gray-500 text-sm" placeholder="likes" name="min_likes" required/>
	            			<input type="hidden" value="4" name="code"/>
	            		</div>
	            	</form>
	             
            </div>
            
            <table
				class="w-full text-sm text-left text-gray-500 dark:text-gray-400 scroll-">
                <thead
					class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                    <tr>
                        <th class="px-4 py-3">Título</th>
                        <th class="px-4 py-3">Descripción</th>
                        <th class="px-4 py-3">Teléfono</th>
                        <th scope="col" class="px-4 py-3">Categorias</th>
                        <th class="px-4 py-3">Servicios</th>
                        <th class="px-4 py-3">Localizacion</th>
                        <th class="px-4 py-3">Precio</th>
                        <th class="px-4 py-3">Contacto</th>
                        <th class="px-4 py-3">Usuario</th>
                        <th class="px-4 py-3">Disponible</th>   
                                            
                        <th class="px-4 py-3">NºLike</th>
                        
                        <th class="px-4 py-3">Like</th>
                        <th class="px-4 py-3"><span class="sr-only">Editar</span></th>
                        <th class="px-4 py-3"><span class="sr-only">Eliminar</span></th>
                    </tr>
                </thead>
                <tbody class="text-xs">
                	<c:forEach var="Hosting" items="${HostingsList}">
                    	<tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                    	
                    	
							<td class="px-4 py-4 text-black-700 font-bold"><a href="<c:url value='HostDetailsServlet.do?id=${Hosting.first.id }'/>">${Hosting.first.title}</a></td>
							<td class="px-4 py-4">${Hosting.first.description}</td>
							<td class="px-4 py-4">${Hosting.first.telephone}</td>
							<td class="px-4 py-4"> <c:forEach var="category" items="${Hosting.third}">
								${category.idct} - 		    	
								</c:forEach>
							</td>
							<td class="px-4 py-4">${Hosting.first.services}</td>
							<td class="px-4 py-4">${Hosting.first.location}</td>
							<td class="px-4 py-4">${Hosting.first.price}</td>
							<td class="px-4 py-4">${Hosting.first.contactEmail}</td>
							<td class="px-4 py-4">${Hosting.second.username}</td>
			
							<c:if test="${Hosting.first.available == '0'}"><td class="px-4 py-4">Si</td></c:if>
							<c:if test="${Hosting.first.available == '1'}"><td class="px-4 py-4">No</td></c:if>		
												
							<td class="px-4 py-4">${Hosting.first.likes}</td>
						
							<c:if test="${Hosting.first.isFav == 'YES'}">
								<td class="px-4 py-4"><a href="<c:url value='LikeNotLikeHostServlet.do?id=${Hosting.first.id }'/>"> <svg aria-hidden="true" class="w-5 h-5 text-yellow-400" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><title>First star</title><path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path></svg></a></td>						
							</c:if>
							<c:if test="${Hosting.first.isFav == 'NO'}">
								<td class="px-4 py-4"><a href="<c:url value='LikeNotLikeHostServlet.do?id=${Hosting.first.id }'/>"><svg aria-hidden="true" class="w-5 h-5 text-gray-300 dark:text-gray-500" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><title>Fifth star</title><path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path></svg></a></td>
							</c:if>
							<c:if test="${user.id == Hosting.first.idu}">
									<td class="icono"><a href="<c:url value='EditHostServlet.do?id=${Hosting.first.id }'/>"><img src="${pageContext.request.contextPath}/img/edit.png"/></a></td>
    								<td class="icono"><a href="<c:url value='DeleteHostServlet.do?id=${Hosting.first.id }'/>"><img src="${pageContext.request.contextPath}/img/delete.png"/></a></td>
							</c:if>
                    	</tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    
	<script>
		/* When the user clicks on the button, 
		toggle between hiding and showing the dropdown content */
		function myFunction() {
		  document.getElementById("myDropdown").classList.toggle("show");
		}
		
		// Close the dropdown if the user clicks outside of it
		window.onclick = function(event) {
		  if (!event.target.matches('.dropbtn')) {
		    var dropdowns = document.getElementsByClassName("dropdown-content");
		    var i;
		    for (i = 0; i < dropdowns.length; i++) {
		      var openDropdown = dropdowns[i];
		      if (openDropdown.classList.contains('show')) {
		        openDropdown.classList.remove('show');
		      }
		    }
		  }
		}
	</script>
	
</body>
</html>