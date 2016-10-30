
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
                
                if (action.equalsIgnoreCase("existing")) {
                    try {
                        int account = Integer.parseInt(request.getParameter("account"));
                        creditCard = new CreditCard(account, path);
                        
                        if (creditCard.getErrorStatus()) {
                            errorMessage += creditCard.getErrorMessage() + "<br>";
                        } else {
                            errorMessage += creditCard.getActionMsg() + "<br>";
                        }
                    } catch (Exception e) {
                        errorMessage += "Illegal or missing account number. <br>";
                    }
                }
                
                if (action.equalsIgnoreCase("charge")) {
                    try {
                        double charge = Double.parseDouble(request.getParameter("cAmt"));
                        double creditAmount = creditCard.getAvailableCreditAmount();
                        if (charge > creditAmount) {
                            errorMessage += "Amount entered exceeds the available credit amount of " +
                                    creditAmount + ". Please enter a valid amount.";
                        }
                        String description = request.getParameter("cDesc");
                        creditCard.setCharge(charge, description);
                    } catch (Exception e) {
                        errorMessage += "Please enter a valid charge amount.";
                    }
                }
                
                if (action.equalsIgnoreCase("payment")) {
                    try {
                        double amount = Double.parseDouble(request.getParameter("pAmt"));
                        if (amount < 0) {
                            errorMessage += "Payment input error. Amount must be a positive value.";
                        }
                        creditCard.setPayment(amount);
                    } catch (Exception e) {
                        errorMessage += "Please enter a valid payment amount";
                    }
                }
                
                if (action.equalsIgnoreCase("increase")) {
                    // Needs to create a client side validation
                    try {
                        double amount = Double.parseDouble(request.getParameter("cIncrease"));
                        if (amount < 0) {
                            errorMessage += "Credit increase cannot be negative. Minimum increase is $100";
                        }
                        else if (amount < 100) {
                            errorMessage += "Credit increase of $" + amount + " declined. Minimum increase is $100.";
                        }
                        else {
                            creditCard.setCreditIncrease(amount);
                        }
                    } catch (Exception e) {
                        errorMessage += "Input error. Please enter a valid amount.";
                    }
                }
                
                if (action.equalsIgnoreCase("interest")) {
                    try {
                        double amount = Double.parseDouble(request.getParameter("iRate"));
                        creditCard.setInterestCharge(amount);
                    } catch (Exception e) {
                        errorMessage += "Please enter a valid value.";
                    }
                }
                
                if (action.equalsIgnoreCase("history")) {
                    URL = "/History.jsp";
                    errorMessage = "History Request.";
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
