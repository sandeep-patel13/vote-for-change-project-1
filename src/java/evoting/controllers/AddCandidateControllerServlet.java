
package evoting.controllers;

import evoting.dao.CandidateDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

public class AddCandidateControllerServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sess = request.getSession();
        String userid = (String)sess.getAttribute("userid");
        if(userid == null) {
            sess.invalidate();
            response.sendRedirect("accessdenied.html");
            return;
        }
        String candidate = (String)request.getParameter("id");
        String uid = (String)request.getParameter("uid");
        System.out.println("uid = "+uid);
        
        if(candidate != null && candidate.equals("getid")){
            try{
                String id = CandidateDao.getNewId();
                response.getWriter().println(id);
                return;
            }
            catch(SQLException se){
                se.printStackTrace();
                RequestDispatcher rd = request.getRequestDispatcher("showException.jsp");
                request.setAttribute("Exception", se);
                rd.forward(request, response);
            }
        }
        else if(uid != null) {
            try{
                
                
                
                String username = CandidateDao.getUserNameById(uid);
                System.out.println("username = "+username);
                List<String> city = CandidateDao.getCity();
                JSONObject jsonobj = new JSONObject();
                StringBuffer sb = new StringBuffer();
                for(String c : city){
                    sb.append("<option value='");
                    sb.append(c);
                    sb.append("'>");
                    sb.append(c);
                    sb.append("</option>");
                }
                System.out.println("cityoptions = "+sb);
                if(username == null)
                    username = "wrong";
                jsonobj.put("username", username);
                jsonobj.put("city", sb.toString());
                response.getWriter().println(jsonobj);
            }
            catch(SQLException se){
                
                se.printStackTrace();
                RequestDispatcher rd = request.getRequestDispatcher("showException.jsp");
                request.setAttribute("Exception", se);
                rd.forward(request, response);
                
            }    
         
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
