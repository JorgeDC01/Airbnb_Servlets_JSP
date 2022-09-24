<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta http-equiv="Content-Type" content="text/html" />
<title>Alojamiento ${host.title}</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css2/tailwind.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css2/background.css">
</head>
<body>
  <div class="flex flex-row flex-wrap space-x-4 justify-center 
  	fondoTarjetas bg-local">
    <div class=" tarjetaHostDetails
    flex flex-col
    bg-white
    shadow-md
   	px-8
    pt-4
    pb-4
    mt-4
    mb-4
    rounded-3xl
    ">
    	<div class="font-medium self-center text-xl text-gray-800 pb-4">
        	Detalles del alojamiento: ${host.title}
      	</div>
      
	    <div class="flex flex-col mb-2">
	    	<label for="name" class="mb-1 text-sm tracking-wide text-gray-600">Nombre de alojamiento:</label>
	    	<div class="relative">
	        <p type="text" readonly class="
	      		  text-sm
	              placeholder-gray-500
	              pl-10
	              pr-4
	              rounded-2xl
	              border border-gray-400
	              w-full
	              py-1
	              focus:outline-none focus:border-blue-400
	              ">${host.title}</p>
	        </div>
	    </div>
      
      	<!-- DESCRIPCIÓN --> 
        <div class="flex flex-col mb-2">
        	<label for="descripcion" class="mb-1 text-sm tracking-wide text-gray-600">Descripción:</label>
            <div class="relative">
              <textarea class="text-sm
              placeholder-gray-500
              pl-10
              pr-4
              rounded-2xl
              border border-gray-400
              w-full
              py-1
              focus:outline-none focus:border-blue-400" name="descrip" cols="50" rows="3" readonly>${host.description}</textarea><br>
            </div>
        </div>

        <!-- TELÉFONO -->
        <div class="flex flex-col mb-2">
        	<label for="telephone" class="mb-1 text-sm tracking-wide text-gray-600">Teléfono:</label>
            <div class="relative">
              <p class="
              text-sm
              placeholder-gray-500
              pl-10
              pr-4
              rounded-2xl
              border border-gray-400
              w-full
              py-1
              focus:outline-none focus:border-blue-400" 
              >${host.telephone}</p>
            </div>
        </div>

        <!-- EMAIL -->
        <div class="flex flex-col mb-2">
            <label for="email" class="mb-1 text-sm tracking-wide text-gray-600">Email:</label>
            <div class="relative">
              <p class="
              text-sm
              placeholder-gray-500
              pl-10
              pr-4
              rounded-2xl
              border border-gray-400
              w-full
              py-1
              focus:outline-none focus:border-blue-400" 
              >${host.contactEmail}</p>
            </div>
        </div>

        <!-- PRECIO -->
        <div class="flex flex-col mb-2">
            <label for="precio" class="mb-1 text-sm tracking-wide text-gray-600">Precio:</label>
            <div class="relative">
              <p class="
              text-sm
              placeholder-gray-500
              pl-10
              pr-4
              rounded-2xl
              border border-gray-400
              w-full
              py-1
              focus:outline-none focus:border-blue-400" 
              >${host.price}</p>
            </div>
        </div>

        <!-- CATEGORIA -->
        <div class="flex flex-col mb-2">
            <label for="categorias" class="mb-1 text-sm tracking-wide text-gray-600">Categorias:
            
            <c:choose>
            	<c:when test="${empty categories}">Sin categorías
            	</c:when>
            	<c:otherwise>
            		<c:forEach var="category" items="${categories}">
								${category.idct} - 		    	
					</c:forEach>
            	</c:otherwise>
            </c:choose>
            
            </label>
        </div>

        <!-- LOCALIDAD --> 
        <div class="flex flex-col mb-2">
            <label for="localidad" class="mb-1 text-sm tracking-wide text-gray-600">Localidad:</label>
            <div class="relative">
              <p class="
              text-sm
              placeholder-gray-500
              pl-10
              pr-4
              rounded-2xl
              border border-gray-400
              w-full
              py-1
              focus:outline-none focus:border-blue-400
              ">${host.location}</p>
            </div>
        </div>

        <!-- SERVICIOS --> 
        <div class="flex flex-col mb-2">
            <label for="services" class="mb-1 text-sm tracking-wide text-gray-600">Servicios:
                        
            <c:choose>
            	<c:when test="${empty host.services}">Sin servicios
            	</c:when>
            	<c:otherwise>
            		${host.services}
            	</c:otherwise>
            </c:choose>
            
            </label>
        </div>
        
        <!-- DISPONIBILIDAD -->
        <c:if test="${host.available == '0'}"><p class="mb-1 text-gray-600 text-sm">Estado del alojamiento: Disponible</p></c:if>
		<c:if test="${host.available == '1'}"><p class="mb-1 text-gray-600 text-sm">Estado del alojamiento: Reservado</p></c:if>
		
      <!-- RED SOCIAL -->
      <c:if test="${not empty host.redSocial}">
	      <div class="mt-2">
	      	<a class="twitter-timeline" data-width="450" data-height="400" data-theme="dark" href="<c:url value="https://twitter.com/${host.redSocial}?ref_src=twsrc%5Etfw"/>">Tweets de ${host.redSocial}</a> 
	      	<script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>              
	      </div>
      </c:if>
      
      <!-- BOTÓN VOLVER ATRÁS -->
      <form action="ListHostsServlet.do" method="GET" class="mt-2">
	      <input type="submit" class="flex uppercase
	          mt-2
	          items-center
	          justify-center
	          focus:outline-none
	          text-white text-sm
	          bg-blue-500
	          hover:bg-blue-600
	          rounded-2xl
	          py-2
	          w-full
	          transition
	          duration-150
	          ease-in" value="Volver a la página principal"/>
	  </form>
    </div>
    
    <div class=" tarjetaHostDetails
	    flex flex-col
	    bg-white
	    shadow-md
	   	px-8
	    pt-4
	    pb-4
	    mt-4
	    mb-4
	    rounded-3xl
	    h-full
	    ">
	    
	    <div class="font-medium self-center text-xl text-gray-800 pb-4">
        	Alojamientos relacionados
      	</div>
      	
    	<div class="max-w-8xl shadow-md sm:rounded-lg overflow-y-auto ">
    		<table
				class="w-full text-sm text-left text-gray-500 dark:text-gray-400 scroll-">
                <thead
					class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                    <tr>
                        <th class="px-4 py-3">Título</th>
                        <th scope="col" class="px-6 py-3">Categorias</th>
                        <th class="px-4 py-3">Servicios</th>
                        <th class="px-4 py-3">Localizacion</th>
                        <th class="px-4 py-3">Precio</th>
                                            
                        <th class="px-4 py-3">NºLikes</th>

                        <th class="px-4 py-3">Like</th>
                    </tr>
                </thead>
                <tbody class="text-xs">
                	<c:forEach var="Hosting" items="${HostingRelated}">
                    	<tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
							<td class="px-4 py-4 text-black-700 font-bold"><a href="<c:url value='HostDetailsServlet.do?id=${Hosting.first.id }'/>">${Hosting.first.title}</a></td>
							<td class="px-4 py-4"> <c:forEach var="category" items="${Hosting.third}">
								${category.idct} - 		    	
								</c:forEach>
							</td>
							<td class="px-4 py-4">${Hosting.first.services}</td>
							<td class="px-4 py-4">${Hosting.first.location}</td>
							<td class="px-4 py-4">${Hosting.first.price}</td>
																
							<td class="px-4 py-4">${Hosting.first.likes}</td>
							
							<c:if test="${Hosting.first.isFav == 'YES'}">
								<td class="px-4 py-4"><a href="<c:url value='LikeNotLikeHostServlet.do?id=${Hosting.first.id }'/>"> <svg aria-hidden="true" class="w-5 h-5 text-yellow-400" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><title>First star</title><path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path></svg></a></td>						
							</c:if>
							<c:if test="${Hosting.first.isFav == 'NO'}">
								<td class="px-4 py-4"><a href="<c:url value='LikeNotLikeHostServlet.do?id=${Hosting.first.id }'/>"><svg aria-hidden="true" class="w-5 h-5 text-gray-300 dark:text-gray-500" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><title>Fifth star</title><path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path></svg></a></td>
							</c:if>
                    	</tr>
                    </c:forEach>
                </tbody>
            </table>
          </div>
    </div>
  </div>
</body>
</html>