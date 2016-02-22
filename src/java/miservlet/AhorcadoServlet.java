/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package miservlet;

/**
 *
 * @author Rockberto
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AhorcadoServlet extends HttpServlet
{
  //Las palabras disponibles para jugar
private static final String[] PALABRAS= {"GATO","CAPILLA","BABOR","MURCIELAGO","VENTANAL","HAMACA","SEXY","NIRVANA"};

protected void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
{
  doPost(req,res);
}

protected void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
{
    HttpSession sesion = req.getSession();
    int maxIntentos = Integer.parseInt(getInitParameter("maxIntentos"));
    //la palabra sobre la que se esta adivinando
    String palabra = (String) sesion.getAttribute("palabra") ;
    //Las Letras que acerto
    String aciertos;
    //Las letras que no acerto
    String errados;
    String loQueSeForma="";
    //Primera vez del usuario, no tiene palabra asignada
       if(palabra == null)
       {
           Random rand = new Random();
           //Le agregamos una palabra al azar al usuario
           palabra = PALABRAS[rand.nextInt(PALABRAS.length)];
           aciertos="";
           errados ="";
           //Guardamos los datos iniciales en sesion
           sesion.setAttribute("palabra",palabra);
           sesion.setAttribute("acierto",aciertos);
           sesion.setAttribute("errados",errados);
        }
       else
       {
          //Obtenemos los datos de este usuario de session
         aciertos= (String) sesion.getAttribute("acierto");
         errados = (String) sesion.getAttribute("errados");
         //Verificamos si la letra que ariesgo pertenece a la palabra o no
         String letra=req.getParameter("letra");

         if(palabra.indexOf(letra) >=0)
         {  aciertos += letra; }
         else
         { errados += letra; }

         //Guardamos los datos actualizados en session
         sesion.setAttribute("acierto",aciertos);
         sesion.setAttribute("errados",errados);

       }


    //Imprimimos el resultado
    PrintWriter out= res.getWriter();
    out.println("<html>");
    out.println("<head>");
    out.print("<link href='myStyle.css' rel='stylesheet' type='text/css' /> ");
    out.println("<title>AHORCADO</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1 align='center'>");
   
   
    //Iteramos por las letras de la palabra. SI ya la certo,la mostramos,si no, mostramos un "_"
       for(int i=0; i < palabra.length();i++)
       {
            String letra = palabra.substring(i, i +1);
           if(aciertos.indexOf(letra) >= 0)
           {
               out.println("" + letra);
               loQueSeForma+=letra;
           }
           else
           {
              out.println("_");
           }
       }
       out.println("</h1>");
       /*
       out.println("ESTO SON SOLO PRUEBAS");
       out.println("</br>");
       out.println("PALABRA ASIGNADA: "+palabra);
       out.println("</br>");
       out.println("AHORCADO: "+loQueSeForma);
       out.println("</br>");
       out.println("IGUALES?: "+yaTermino(palabra,loQueSeForma));
       */
        out.println("</br>");

       //Nos fijamos si yerro mas de los intentos
       if(maxIntentos > errados.length() && yaTermino(palabra,loQueSeForma) == false)
       {
         //Todavia esta en juego
            out.println("<h2 align='center'>");
          out.println("Intentos: " + (maxIntentos - errados.length()));
          out.println("</br>");
         
          //Las chances restantes
          for(char c='A'; c <= 'Z';c++)
          {
             if(aciertos.indexOf(Character.toString(c)) == -1 && errados.indexOf(Character.toString(c)) == -1)
             {
             //Mostramos letra como opcion si no fue arriesgada aun
             out.println("<a href=\"AhorcadoServlet?letra="+c+"\">" +c+"</a>");
             }
          }
           out.println("</h2>");

           switch(maxIntentos - errados.length())
           { case 5:
             out.println("<center><img src='img/1.png' alt='Ahorcado' width='250' height='250' border='0' /></cente>"); 
             break;
             case 4:
             out.println("<center><img src='img/2.png' alt='Ahorcado' width='250' height='250' border='0' /></cente>"); 
             break;
             case 3:
             out.println("<center><img src='img/3.png' alt='Ahorcado' width='250' height='250' border='0' /></cente>"); 
             break;
             case 2:
             out.println("<center><img src='img/4.png' alt='Ahorcado' width='250' height='250' border='0' /></cente>"); 
             break;
             case 1:
             out.println("<center><img src='img/5.png' alt='Ahorcado' width='250' height='250' border='0' /></cente>"); 
             break;
          }


      }
       else
       {
       //Juego Terminado
       sesion.invalidate();
       out.println("<h2 align='center'>Juego Terminado</h2>");
       if(yaTermino(palabra,loQueSeForma) == true)
       {
        out.println("<h1 align='center'>WINNER!!</h1></BR>");
        out.println("<center><img src='img/7.png' alt='Ahorcado' width='250' height='250' border='0' /></cente>");
        out.println("</br>");
       }
       else
       {
        out.println("<h1 align='center'>LOOSER!!</h1></BR>");
        out.println("<center><img src='img/6.png' alt='Ahorcado' width='250' height='250' border='0' /></cente>");
        out.println("</br>");
       }
       out.println("<a href=\"AhorcadoServlet\">Jugar de Nuevo</a>");

       }
       out.println("</body>");
       out.println("</html>");

}
//Se requeria un metodo para SABER cuando el usuario haya adivinado la palabra,
//sin la necesidad de usar todas las chances.
protected boolean yaTermino(String pal,String acie)
{
        boolean iguales=false;
          int temp=0;
          String uno=pal;
          String dos=acie;
          char mtz[] = dos.toCharArray();
          for(int i=0;i < dos.length();i++ )
          {
             if(uno.indexOf(mtz[i]) >=0)
             {
              temp++;
             }

          }
          if(temp == uno.length())
              iguales=true;

          return iguales;
}
     
}




