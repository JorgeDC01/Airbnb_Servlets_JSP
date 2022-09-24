<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css2/tailwind.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css2/background.css">
  <title>${EditOrNewType} nuevo alojamiento</title>
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
        ${EditOrNewType} Alojamiento
      </div>
      <div class="mt-6">
        <form  action="?id=${host.id }" method="POST">

          <!-- NOMBRE --> 
          <div class="flex flex-col mb-2">
            <label for="name" class="mb-1 text-sm tracking-wide text-gray-600">Nombre de alojamiento:</label>
            <div class="relative">
              <input id="name" type="text" name="name" class="
              text-sm
              placeholder-gray-500
              pl-10
              pr-4
              rounded-2xl
              border border-gray-400
              w-full
              py-1
              focus:outline-none focus:border-blue-400
              " placeholder="Nombre" required value="${host.title}" autocomplete="off"/>
            </div>
          </div>

          <!-- DESCRIPCIÓN --> 
          <div id="descDIV" class="flex flex-col mb-2">
            <label for="descripcion" class="mb-1 text-sm tracking-wide text-gray-600">Descripción:</label>
            <div class="relative">
              <textarea id="description" required autocomplete="off" class="text-sm 
              placeholder-gray-500
              pl-10
              pr-4
              rounded-2xl
              border border-gray-400
              w-full
              py-1
              focus:outline-none focus:border-blue-400" name="descrip" cols="50" rows="3">${host.description}</textarea>
            </div>
          </div>

          <!-- TELÉFONO -->
          <div id="telephoneDIV" class="flex flex-col mb-2">
            <label for="telephone" class="mb-1 text-sm tracking-wide text-gray-600">Teléfono:</label>
            <div class="relative">
              <input id="telephone" type="tel" name="telephone" required class="
              text-sm
              placeholder-gray-500
              pl-10
              pr-4
              rounded-2xl
              border border-gray-400
              w-full
              py-1
              focus:outline-none focus:border-blue-400" 
              placeholder="666666666" value="${host.telephone}" autocomplete="off"/>
            </div>
          </div>

          <!-- EMAIL -->
          <div id="email" class="flex flex-col mb-2">
            <label for="email" class="mb-1 text-sm tracking-wide text-gray-600">Email:</label>
            <div class="relative">
              <input type="email" name="email" required class="
              text-sm
              placeholder-gray-500
              pl-10
              pr-4
              rounded-2xl
              border border-gray-400
              w-full
              py-1
              focus:outline-none focus:border-blue-400" 
              placeholder="nombre@uex.es" value="${host.contactEmail}" autocomplete="off"/>
            </div>
          </div>

          <!-- PRECIO -->
          <div class="flex flex-col mb-2">
            <label for="precio" class="mb-1 text-sm tracking-wide text-gray-600">Precio:</label>
            <div class="relative">
              <input min="0" id="precio" type="number" name="price" required class="
              text-sm
              placeholder-gray-500
              pl-10
              pr-4
              rounded-2xl
              border border-gray-400
              w-full
              py-1
              focus:outline-none focus:border-blue-400" 
              placeholder="€" value="${host.price}"/>
            </div>
          </div>

          <!-- CATEGORIA -->
          <div class="flex flex-col mb-2">
            <label for="categorias" class="mb-1 text-sm tracking-wide text-gray-600">Categorias:</label>
            <div class="relative text-sm placeholder-gray-500 w-auto justify-center mb-3">
                <input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" type="checkbox" name="categories" value="0"
                	<c:forEach var="category" items="${categoriesVector}">
                		<c:if test="${category.idct == '0'}"> checked </c:if>
                	</c:forEach>/> Ciudad
                <input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" type="checkbox" name="categories" value="1"
                	<c:forEach var="category" items="${categoriesVector}">
                		<c:if test="${category.idct == '1'}"> checked </c:if>
                	</c:forEach>/> Playa
                <input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" type="checkbox" name="categories" value="2"
                	<c:forEach var="category" items="${categoriesVector}">
                		<c:if test="${category.idct == '2'}"> checked </c:if>
                	</c:forEach>/> Pueblo
                <input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" type="checkbox" name="categories" value="3"
                	<c:forEach var="category" items="${categoriesVector}">
                		<c:if test="${category.idct == '3'}"> checked </c:if>
                	</c:forEach>/> Montaña
                <input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" type="checkbox" name="categories" value="4"
                	<c:forEach var="category" items="${categoriesVector}">
                		<c:if test="${category.idct == '4'}"> checked </c:if>
                	</c:forEach>/> Castillo
            </div>
          </div>

          <!-- LOCALIDAD --> 
          <div class="flex flex-col mb-2">
            <label for="localidad" class="mb-1 text-sm tracking-wide text-gray-600">Localidad:</label>
            <div class="relative">
              <input id="localidad" type="text" name="located" required class="
              text-sm
              placeholder-gray-500
              pl-10
              pr-4
              rounded-2xl
              border border-gray-400
              w-full
              py-1
              focus:outline-none focus:border-blue-400
              " placeholder="Ejemplo: Murcia" value="${host.location}" autocomplete="off"/>
            </div>
          </div>

          <!-- SERVICIOS --> 
          <div id="serviceDIV" class="flex flex-col mb-2">
            <label for="services" class="mb-1 text-sm tracking-wide text-gray-600">Servicios:</label>
            <div class="relative text-sm placeholder-gray-500 w-auto justify-center mb-3">
                <input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" type="checkbox" name="services" id="Cocina" value="Cocina"
                	<c:forEach var="service" items="${servicesVector}">
                		<c:if test="${service == 'Cocina'}"> checked </c:if>
                	</c:forEach>/> Cocina 
                <input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" type="checkbox" name="services" id="Limpieza" value="Limpieza"
                	<c:forEach var="service" items="${servicesVector}">
                		<c:if test="${service == 'Limpieza'}"> checked </c:if>
                	</c:forEach>/> Limpieza
                <input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" type="checkbox" name="services" id="Desayuno" value="Desayuno"
                	<c:forEach var="service" items="${servicesVector}">
                		<c:if test="${service == 'Desayuno'}"> checked </c:if>
                	</c:forEach>/> Desayuno
                <input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" type="checkbox" name="services" id="Wifi" value="Wifi"
                	<c:forEach var="service" items="${servicesVector}">
                		<c:if test="${service == 'Wifi'}"> checked </c:if>
                	</c:forEach>/> Wifi
                <input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" type="checkbox" name="services" id="Lavadora" value="Lavadora"
                	<c:forEach var="service" items="${servicesVector}">
                		<c:if test="${service == 'Lavadora'}"> checked </c:if>
                	</c:forEach>/> Lavadora
            </div>
          </div>
          
          <!-- RED SOCIAL --> 
          <div class="flex flex-col mb-2">
            <label for="RedSocial" class="mb-1 text-sm tracking-wide text-gray-600">Twitter:</label>
            <div class="relative">
              <input id="twitter" type="text" name="twitter" class="
              text-sm
              placeholder-gray-500
              pl-10
              pr-4
              rounded-2xl
              border border-gray-400
              w-full
              py-1
              focus:outline-none focus:border-blue-400
              " placeholder="@Perfil" value="${host.redSocial}" autocomplete="off"/>
            </div>
          </div>
          
          <!-- DISPONIBILIDAD -->
          <div class="flex flex-col mb-2">
          	<label for="disponibilidad" class="mb-1 text-sm tracking-wide text-gray-600">Disponible:</label>
           	<div class="relative text-sm placeholder-gray-500 w-auto justify-center mb-3">
               	<input class="h-4 w-4 border border-gray-300 rounded-sm bg-white checked:bg-blue-600 checked:border-blue-600 transition mt-1 align-top mr-1 cursor-pointer" type="checkbox" name="available" value="0" 
               	<c:if test="${host.available == '0'}"> checked </c:if>/> 
           	</div>
		  </div>
		  
		  
          
          <input id="button" type="submit" value="${EditOrNewType}" class="
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
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"> </script>
</body>

</html>