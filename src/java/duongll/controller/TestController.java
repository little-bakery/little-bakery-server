/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duongll.controller;

import duongll.client.AccountClient;
import duongll.client.CakeClient;
import duongll.client.FavoriteClient;
import duongll.dto.Account;
import duongll.dto.Cake;
import duongll.dto.Favorite;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author duong
 */
@WebServlet(name = "TestController", urlPatterns = {"/TestController"})
public class TestController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        CakeClient cakeClient = new CakeClient();
        FavoriteClient favoriteClient = new FavoriteClient();
        try {
            String username = "leduong";
            String password = "leduong";
            AccountClient accountClient = new AccountClient();
            Account result = accountClient.checkLogin_XML(Account.class, username, password);
            if (result != null) {
                System.out.println("result: " + result.getRole());
            }
            Cake cake = cakeClient.findByCakeId_XML(Cake.class, "121");
            Favorite favorite = new Favorite();
            favorite.setId(new Long(0));
            favorite.setCakeid(cake);
            favorite.setAccount(result);
            favorite.setAvailable(true);
            Favorite f = favoriteClient.addToFavorite_XML(favorite, Favorite.class);
            if (f == null) {
                System.out.println("null");
            } else {
                System.out.println("Not null");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
