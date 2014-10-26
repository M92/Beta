package dat076.group4.webapp.auth;

import dat076.group4.model.core.User;
import dat076.group4.model.dao.IUserRegistry;

import java.io.IOException;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.util.SocialAuthUtil;

/**
 * The OAuth provider redirects the client here.
 */
@WebServlet("/callback")
public class CallbackServlet extends HttpServlet {

    @EJB
    private IUserRegistry userRegistry;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        try {
            SocialAuthManager manager = (SocialAuthManager)session.getAttribute("authManager");
            Map<String, String> paramsMap = SocialAuthUtil.getRequestParametersMap(request);
            AuthProvider provider = manager.connect(paramsMap);   
            String key = provider.getAccessGrant().getKey();

            Profile profile = provider.getUserProfile();
            Long providerID = Long.parseLong(profile.getValidatedId());
            String username = profile.getDisplayName().toLowerCase();

            if (userRegistry.getByOAuth(providerID) == null) {
                userRegistry.create(new User(providerID, username));
            }
            session.setAttribute("key", key);
            session.setAttribute("username", username);

        } catch (Exception e) {
            response.sendError(500);
        }
        response.sendRedirect("/webapp/");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
