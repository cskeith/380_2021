package ouhk.comps380f.controller;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import ouhk.comps380f.model.Attachment;
import ouhk.comps380f.model.Ticket;

@WebServlet(
        name = "ticketServlet",
        urlPatterns = {"/tickets"},
        loadOnStartup = 1
)
@MultipartConfig(
        fileSizeThreshold = 5_242_880, //5MB
        maxFileSize = 20_971_520L, //20MB
        maxRequestSize = 41_943_040L //40MB
)
public class TicketServlet extends HttpServlet {

    private volatile int TICKET_ID_SEQUENCE = 1;

    private Map<Integer, Ticket> ticketDatabase = new Hashtable<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "create":
                this.showTicketForm(request, response);
                break;
            case "view":
                this.viewTicket(request, response);
                break;
            case "download":
                this.downloadAttachment(request, response);
                break;
            case "list":
            default:
                this.listTickets(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "create":
                this.createTicket(request, response);
                break;
            case "list":
            default:
                response.sendRedirect("tickets");
                break;
        }
    }

    private void showTicketForm(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/view/ticketForm.jsp")
                .forward(request, response);
    }

    private void viewTicket(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idString = request.getParameter("ticketId");
        Ticket ticket = this.getTicket(idString, response);
        if (ticket == null) {
            return;
        }

        request.setAttribute("ticketId", idString);
        request.setAttribute("ticket", ticket);

        request.getRequestDispatcher("/WEB-INF/jsp/view/viewTicket.jsp")
                .forward(request, response);
    }

    private void listTickets(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("ticketDatabase", this.ticketDatabase);

        request.getRequestDispatcher("/WEB-INF/jsp/view/listTickets.jsp")
                .forward(request, response);
    }

    private void createTicket(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Ticket ticket = new Ticket();
        ticket.setCustomerName(request.getParameter("customerName"));
        ticket.setSubject(request.getParameter("subject"));
        ticket.setBody(request.getParameter("body"));

        Part filePart = request.getPart("file1");
        if (filePart != null && filePart.getSize() > 0) {
            Attachment attachment = this.processAttachment(filePart);
            if (attachment != null) {
                ticket.addAttachment(attachment);
            }
        }

        int id;
        synchronized (this) {
            id = this.TICKET_ID_SEQUENCE++;
            this.ticketDatabase.put(id, ticket);
        }

        response.sendRedirect("tickets?action=view&ticketId=" + id);
    }

    private Attachment processAttachment(Part filePart)
            throws IOException {
        InputStream inputStream = filePart.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int read;
        final byte[] bytes = new byte[1024];

        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }

        Attachment attachment = new Attachment();
        attachment.setName(filePart.getSubmittedFileName());
        attachment.setContents(outputStream.toByteArray());

        return attachment;
    }

    private Ticket getTicket(String idString, HttpServletResponse response)
            throws ServletException, IOException {
        if (idString == null || idString.length() == 0) {
            response.sendRedirect("tickets");
            return null;
        }

        try {
            Ticket ticket = this.ticketDatabase.get(Integer.parseInt(idString));
            if (ticket == null) {
                response.sendRedirect("tickets");
                return null;
            }
            return ticket;
        } catch (Exception e) {
            response.sendRedirect("tickets");
            return null;
        }
    }

    private void downloadAttachment(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String idString = request.getParameter("ticketId");
        Ticket ticket = this.getTicket(idString, response);
        if (ticket == null) {
            return;
        }

        String name = request.getParameter("attachment");
        if (name == null) {
            response.sendRedirect("tickets?action=view&ticketId=" + idString);
            return;
        }

        Attachment attachment = ticket.getAttachment(name);
        if (attachment == null) {
            response.sendRedirect("tickets?action=view&ticketId=" + idString);
            return;
        }

        response.setHeader("Content-Disposition",
                "attachment; filename=" + attachment.getName());
        response.setContentType("application/octet-stream");

        ServletOutputStream stream = response.getOutputStream();
        stream.write(attachment.getContents());
    }
}
