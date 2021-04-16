package ouhk.comps380f;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "servlet1", urlPatterns = {"/servlet1"})
public class Servlet1 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Entering Servlet1.doGet()");
        response.getWriter().write("Servlet 1");
        System.out.println("Leaving Servlet1.doGet()");
    }
}

