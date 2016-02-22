<%-- 
    Document   : index
    Created on : 31/03/2010, 12:19:18 AM
    Author     : Rockberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="myStyle.css" rel="stylesheet" type="text/css" />
        <title>Juego del Ahorcado en SERVLET DOS </title>
    </head>
    <body>
        <h1>Link al Juego AHORCADO EN servlet</h1>
        <%
         out.println("<a href=\"AhorcadoServlet\">Jugar</a>");
        %>
    </body>
</html>
