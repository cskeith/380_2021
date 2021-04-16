package ouhk.comps380f;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "servlet2", urlPatterns = {"/servlet2"})
public class Servlet2 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Entering Servlet2.doGet()");
        response.getWriter().write("Servlet 2");
        System.out.println("Leaving Servlet2.doGet()");
    }
}

