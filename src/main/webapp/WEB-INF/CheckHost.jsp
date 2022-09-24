<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css2/tailwind.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css2/background.css">
  <title>Crear nuevo alojamiento</title>
</head>

<body>
  <div class="flex flex-col items-center justify-center 
  fondoTarjetas bg-local">
    <div class=" 
    flex flex-col
    bg-white
    shadow-md
   	px-8
    pt-4
    pb-2
    mt-3
    mb-3
    rounded-3xl
    tarjetaHostDetails
    ">
      <div class="font-medium self-center text-xl text-gray-800">
        ${CheckType} Alojamiento
      </div>
      <div class="mt-6">
        <form name="host" action="?id=${host.id }" method="POST">

          <!-- NOMBRE --> 
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
              <textarea class="
              text-xsm
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
            <label for="categorias" class="mb-1 text-sm tracking-wide text-gray-600">Categorias:</label>
            <div class="relative text-sm placeholder-gray-500 w-auto justify-center mb-3">
            <c:forEach var="category" items="${categoriesVector}">
            	<c:choose>
            		<c:when test="${category == '0'}">
                		<input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" disabled="disabled" checked="checked" type="checkbox" name="categories" value="0"> Ciudad 
                	</c:when>
                	<c:when test="${category == '1'}">
                		<input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" disabled="disabled" checked="checked" type="checkbox" name="categories" value="1"> Playa
                	</c:when>
                	<c:when test="${category == '2'}">
                		<input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" disabled="disabled" checked="checked" type="checkbox" name="categories" value="2"> Pueblo
                	</c:when>
                	<c:when test="${category == '3'}">
                		<input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" disabled="disabled" checked="checked" type="checkbox" name="categories" value="3"> Montaña
                	</c:when>
                	<c:when test="${category == '4'}">
                		<input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" disabled="disabled" checked="checked" type="checkbox" name="categories" value="4"> Castillo
            		</c:when>
            	</c:choose>
            </c:forEach>
            </div>
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
            <label for="services" class="mb-1 text-sm tracking-wide text-gray-600">Servicios:</label>
            <div class="relative text-sm placeholder-gray-500 w-auto justify-center mb-3">
            <c:forEach var="service" items="${servicesVector}">
	            	<c:choose>
	      				<c:when test="${service == 'Cocina'}"> 
	      					<input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" disabled="disabled" checked="checked" type="checkbox" name="services" id="Cocina" value="Cocina"> Cocina 
	      				</c:when>
	      				<c:when test="${service == 'Limpieza'}"> 
	      					<input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" disabled="disabled" checked="checked" type="checkbox" name="services" id="Cocina" value="Cocina"> Limpieza 
	      				</c:when>
	      				<c:when test="${service == 'Desayuno'}"> 
	      					<input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" disabled="disabled" checked="checked" type="checkbox" name="services" id="Cocina" value="Cocina"> Desayuno 
	      				</c:when>
	      				<c:when test="${service == 'Wifi'}"> 
	      					<input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" disabled="disabled" checked="checked" type="checkbox" name="services" id="Cocina" value="Cocina"> Wifi 
	      				</c:when>
	      				<c:when test="${service == 'Lavadora'}"> 
	      					<input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" disabled="disabled" checked="checked" type="checkbox" name="services" id="Cocina" value="Cocina"> Lavadora 
	      				</c:when>
	    			</c:choose>
            </c:forEach>
            </div>
          </div>

			<!-- RED SOCIAL --> 
         	<div class="flex flex-col mb-2">
	            <label for="RedSocial" class="mb-1 text-sm tracking-wide text-gray-600">Twitter:</label>
	            <div class="relative">
	              <p class="
	              text-sm
	              placeholder-gray-500
	              pl-10
	              pr-4
	              rounded-2xl
	              border border-gray-400
	              w-full
	              py-3
	              focus:outline-none focus:border-blue-400 h-
	              ">${host.redSocial}</p>
	            </div>
          	</div>
          	
          	
		   <!-- DISPONIBILIDAD --> 
          <div class="flex flex-col mb-2">
            <label for="disponibilidad" class="mb-1 text-sm tracking-wide text-gray-600">Disponible:</label>
            <div class="relative text-sm placeholder-gray-500 w-auto justify-center mb-3">
	            	<c:choose>
	      				<c:when test="${host.available == '0'}"> 
	      					<input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" disabled="disabled" checked="checked" type="checkbox" name="available">
	      				</c:when>
	      				<c:when test="${host.available == '1'}"> 
	      					<input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" disabled="disabled" type="checkbox" name="available"> 
	      				</c:when>
	    			</c:choose>
            </div>
          </div>
		  
          <input type="submit" value="${CheckType}" class="
          flex uppercase
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
          ease-in
          ">
        </form>
    </div>
  </div>
 </div>
</body>

</html>