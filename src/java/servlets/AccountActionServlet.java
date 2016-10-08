
package servlets;

import business.CreditCard;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author josepharcelo
 */
public class AccountActionServlet extends HttpServlet
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");

        CreditCard creditCard;
        String URL = "/CardTrans.jsp";
        String errorMessage = "";
        String path = getServletContext().getRealPath("/WEB-INF/") + "\\";
        
        try {
            String action = request.getParameter("actiontype");
            creditCard = (CreditCard) request.getSession().getAttribute("card");
            
            if (creditCard == null
                    && !action.equalsIgnoreCase("new")
                    && !action.equalsIgnoreCase("existing")) {
                errorMessage += "No active account. Please create or open.<br>";
            }
            else {
                if (action.equalsIgnoreCase("new")) {
                    creditCard = new CreditCard(path);
                    if (creditCard.getErrorStatus()) {
                        errorMessage += "New account error: " + creditCard.getErrorMessage() + "<br>";
                    }
                    else {
                        errorMessage += creditCard.getActionMsg() + "<br>";
                    }
                }
                
                if (action.equalsIgnoreCase("history")) {
                    URL = "/History.jsp";
                }
                
                request.getSession().setAttribute("card", creditCard);
               
                Cookie accountCookie = new Cookie("acct", String.valueOf(creditCard.getAccountId()));
                accountCookie.setMaxAge(60*2);
                accountCookie.setPath("/");
                
                response.addCookie(accountCookie);
            }
        } catch (Exception e) {
            errorMessage += "Application error. " + e.getMessage();
        }
        
        request.setAttribute("errorMessage", errorMessage);
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(URL);
        dispatcher.forward(request, response);
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
            throws ServletException, IOException
    {
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
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
