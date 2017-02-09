/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.lifelets.rps;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author zeroadmin
 */
public class RockPaperScissorsServlet extends HttpServlet {

    

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
        
        // session is required in all the conditions.
        HttpSession session = request.getSession();

        // clear history
        if(request.getParameter("history").equals("clear")){
            //ResultHistory resulthistory = (ResultHistory) session.getAttribute("result");
            session.invalidate();
            //session.setAttribute("result", null);
            response.sendRedirect("index.html");
        }
        
        if(request.getParameter("history").equals("save")){
            // Get the history map.
            // Get database connection.
            // take the session
            ResultHistory resulthistory = (ResultHistory) session.getAttribute("result");
            Connection conn = getSqlConnection();
            saveHistory(conn,session,resulthistory);
            if(resulthistory != null){
                response.sendRedirect("showhistory.jsp");
            }
            else{
                response.sendRedirect("error.jsp");
            }
        }
        
        // clear saved History.
        if(request.getParameter("history").equals("saveclear")){
            Connection conn = getSqlConnection();
            deleteSavedHistory(conn,request,response);
            response.sendRedirect("showhistory.jsp");
        }
    }
    
    private void deleteSavedHistory(Connection conn, HttpServletRequest req, HttpServletResponse res){
        String sql = "DELETE FROM Rps WHERE username = ?";
        HttpSession session = req.getSession();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, (String) session.getAttribute("username"));
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(RockPaperScissorsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(RockPaperScissorsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void saveHistory(Connection conn, HttpSession session, ResultHistory resulthistory){
        
        String sql = "INSERT INTO Rps(username,userchoice,syschoice,winner) VALUES (?, ?, ?, ?)";
        List<Result> results = resulthistory.getResults();
        String username = (String) session.getAttribute("username");
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for(Result result : results){
                pstmt.setString(1, username);
                if(result.getWinner().equals("user")){
                    pstmt.setString(2, result.getWinnerChoice());
                    pstmt.setString(3, result.getLoserChoice());
                }
                else {
                    pstmt.setString(2, result.getLoserChoice());
                    pstmt.setString(3, result.getWinnerChoice());
                }
                pstmt.setString(4, result.getWinner());
                pstmt.executeUpdate();
                
            }
            
        }catch(SQLException e){System.out.println("Exception : "+e.getMessage());}
        
        finally{
            
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(RockPaperScissorsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        
        // Random system option (Rock/Paper/Scissor);
        String playerPick = pickRandomly();
        
        // decideTheWinnerAndForward
        decideTheWinnerAndForward(request,response,playerPick);
        
    }
    
    public void decideTheWinnerAndForward(HttpServletRequest request, HttpServletResponse response,
            String playerPick) throws IOException{
        
        // Process the choice.
        String userPick = request.getParameter("pick");
        HttpSession session = request.getSession();
        String winner;
        
        if(userPick.equals(playerPick)){
            winner="tie";
        }
        else if(userPick.equals("rock") && playerPick.equals("scissors")){
            winner="user";
        }
        else if(userPick.equals("scissors") && playerPick.equals("rock")){
            winner="system";
        }
        else if(userPick.equals("scissors") && playerPick.equals("paper")){
            winner = "user";
        }
        else if(userPick.equals("paper") && playerPick.equals("scissors")){
            winner = "system";
        }
        else if(userPick.equals("paper") && playerPick.equals("rock")){
            winner = "user";
        }
        else if(userPick.equals("rock") && playerPick.equals("paper")){
            winner = "system";
        }
        else {
            winner = "none";
        }
        
        // Record History of Records.
        recordGameHistory(session,userPick,playerPick,winner);
           
        
        // Redirect the output to JSP template.
        session.setAttribute("winner", winner);
        response.sendRedirect("display.jsp");
        
    }
    
    private void recordGameHistory(HttpSession session, String userPick, String playerPick, String winner){
       // check already a session is available.
       ResultHistory resulthistory = new ResultHistory();
       
       if(session.getAttribute("result") != null){
           ResultHistory existingHistory = (ResultHistory) session.getAttribute("result");
           // copy existing records first.
           for(Result result : existingHistory.getResults()){
               resulthistory.addResult(result);
           }
       
       }
       
        resulthistory.printRecords(); // debug.
       
        // copy latest record into history.
        resulthistory.addRecord(winner, userPick, playerPick);
        session.setAttribute("result", resulthistory);
       
    }
    
    private String pickRandomly(){
        
        //1. Make a 'random' choice using Random in range of Rock(0), Paper(1), Scissors(2).
        Random random = new Random();
        int choice = random.nextInt(3);
        
        //2. corelate the choice.
        if(choice == 0){
           return "rock";
        }
        else if(choice == 1){
            return "paper";
        }
        else{
            return "scissors";
        }
        
    }

}
