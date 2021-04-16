package ouhk.comps380f;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "servlet3", urlPatterns = {"/servlet3"})
public class Servlet3 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Entering Servlet3.doGet()");
        response.getWriter().write("Servlet 3");
        System.out.println("Leaving Servlet3.doGet()");
    }
}

