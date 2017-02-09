package in.lifelets.rps;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author zeroadmin
 */
public class LoginManagerServlet extends HttpServlet {

    

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
        // Yes, Against REST, But just forward.
        doPost(request,response);
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
        // get a db connection.
        // verify if the user exists in db.
        // If exists, set the session and forward to dashboard.jsp
        Connection conn = getSqlConnection();
        // check user has db entry.
        HttpSession session = request.getSession();
        
        if(checkUserExists(conn,request)){
            session.setAttribute("history", "yes");
            
        }
        
        else{
            session.setAttribute("history", "no");

        }
        
        session.setAttribute("username", request.getParameter("username"));
        response.sendRedirect("dashboard.jsp");
    }
    
    
    private boolean checkUserExists(Connection conn, HttpServletRequest req){
        
        String sql = "SELECT Count(*) AS COUNT FROM Rps where username = ?";
        //System.out.println("connection is "+conn);
        
        // pass the parameter from request to pstmt.
        try {
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, req.getParameter("username"));
            ResultSet rs = pstmt.executeQuery();
            //System.out.println("Result set count value = "+rs.getInt("COUNT"));
            if(rs.getInt("COUNT") == 0){
                return false;
            }
            
            return true;
            
        } catch (SQLException ex) {
            System.out.println("SQL Error "+ex.getMessage());
            return false;
        }
        finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
            
    private Connection getSqlConnection(){
        Connection conn = null;
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:/tmp/test.db");
        }
        catch(ClassNotFoundException | SQLException  | NullPointerException e ){
            System.out.println("Gotcha " + e.getMessage());
        }
        return conn;
    }

    
}
