<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css2/tailwind.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css2/background.css"/>
  <title>Edición datos de la cuenta</title>
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
  mt-32
  mb-32
  rounded-3xl
  w-96  ">
      <div class="font-medium self-center text-xl text-gray-800">
        Editar los datos de la cuenta
      </div>
      <div class="mt-6">
        <form action="EditUserServlet.do" method="POST">
          <div class="flex flex-col mb-5">
            <label for="email" class="mb-1 text-sm tracking-wide text-gray-600">Nombre usuario:</label>
            <div class="relative">
              <div class="
          inline-flex
          items-center
          justify-center
          absolute
          left-0
          top-0
          h-full
          w-10
          text-gray-400
          ">
                <i class="fas fa-at text-blue-500"></i>
              </div>
              <input id="username" type="text" name="username" value="${user.username}" required class="
        text-sm
        placeholder-gray-500
        pl-10
        pr-4
        rounded-2xl
        border border-gray-400
        w-full
        py-2
        focus:outline-none focus:border-blue-400
        " placeholder="Usuario" autocomplete="off"/>
            </div>
          </div>
          <div class="flex flex-col mb-5">
            <label for="email" class="mb-1 text-sm tracking-wide text-gray-600">Correo electrónico:</label>
            <div class="relative">
              <div class="
          inline-flex
          items-center
          justify-center
          absolute
          left-0
          top-0
          h-full
          w-10
          text-gray-400
          ">
                <i class="fas fa-at text-blue-500"></i>
              </div>
              <input id="email" type="email" name="email" value="${user.email}" required class="
        text-sm
        placeholder-gray-500
        pl-10
        pr-4
        rounded-2xl
        border border-gray-400
        w-full
        py-2
        focus:outline-none focus:border-blue-400
        " placeholder="Correo electrónico" autocomplete="off"/>
            </div>
          </div>
          <div class="flex flex-col mb-6">
            <label for="password" class="mb-1 text-sm tracking-wide text-gray-600">Contraseña:</label>
            <div class="relative">
              <div class="
        inline-flex
        items-center
        justify-center
        absolute
        left-0
        top-0
        h-full
        w-10
        text-gray-400
        ">
                <span>
                  <i class="fas fa-lock text-blue-500"></i>
                </span>
              </div>

              <input id="password" type="text" name="password" value="${user.password}" required class="
      text-sm
      placeholder-gray-500
      pl-10
      pr-4
      rounded-2xl
      border border-gray-400
      w-full
      py-2
      focus:outline-none focus:border-blue-400
      " placeholder="Contraseña" autocomplete="off"/>
            </div>
          </div>

          <input type="submit" value="Editar" class="
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