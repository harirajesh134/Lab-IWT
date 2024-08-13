import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class NewServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        // Initialize the PrintWriter
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession(true);

            // Get session creation time and last accessed time
            Date createTime = new Date(session.getCreationTime());
            Date lastAccessTime = new Date(session.getLastAccessedTime());

            // Initialize or retrieve session attributes
            String title = "Welcome Back to the Site";
            Integer visitCount = (Integer) session.getAttribute("visitCount");
            String userID = (String) session.getAttribute("userID");

            if (session.isNew()) {
                title = "Welcome to the Site";
                visitCount = 1; // Set the visit count for a new session
                userID = "User123"; // Example user ID
                session.setAttribute("userID", userID);
            } else {
                visitCount = (visitCount == null) ? 1 : visitCount + 1;
            }

            session.setAttribute("visitCount", visitCount);

            // Output the response
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>" + title + "</title></head>");
            out.println("<body>");
            out.println("<h1>" + title + "</h1>");
            out.println("<p>Session ID: " + session.getId() + "</p>");
            out.println("<p>Creation Time: " + createTime + "</p>");
            out.println("<p>Last Access Time: " + lastAccessTime + "</p>");
            out.println("<p>User ID: " + userID + "</p>");
            out.println("<p>Number of visits: " + visitCount + "</p>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close(); // Ensure the writer is closed
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}