
package evoting.controllers;

import evoting.dao.CandidateDao;
import evoting.dto.CandidateDetails;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowCandidateControllerServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = null;
        HttpSession sess = request.getSession();
        String userid = (String)sess.getAttribute("userid");
        if(userid == null) {
            sess.invalidate();
            response.sendRedirect("accessdenied.html");
            return;
        }
        String data = request.getParameter("data");
        try{
            
            if(data != null && data.equalsIgnoreCase("cid")){
                ArrayList<String> candidatelist = CandidateDao.getCandidateIds();
                request.setAttribute("candidateid", candidatelist);
                request.setAttribute("result", "candidatelist");
            }
            else{
                CandidateDetails candidate = CandidateDao.getDetailsById(data);
                request.setAttribute("candidate", candidate);
                request.setAttribute("result", "details");
            }
            rd = request.getRequestDispatcher("adminShowCandidate.jsp");
        }
        
        catch(Exception ex) {
            ex.printStackTrace();
            rd = request.getRequestDispatcher("showException.jsp");
            rd.forward(request, response);
            return;
        }
        finally{
      
            rd.forward(request, response);
        }
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
