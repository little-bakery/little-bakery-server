/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duongll.controller;

import duongll.client.AccountClient;
import duongll.client.AnswerClient;
import duongll.client.CakeClient;
import duongll.client.CakeWeightClient;
import duongll.client.FavoriteClient;
import duongll.client.IngredientClient;
import duongll.client.MaterialClient;
import duongll.client.QuestionClient;
import duongll.dto.Account;
import duongll.dto.Answers;
import duongll.dto.Cake;
import duongll.dto.CakeWeight;
import duongll.dto.Favorite;
import duongll.dto.Ingredient;
import duongll.dto.Material;
import duongll.dto.Questions;
import duongll.utils.ConverterUtils;
import duongll.utils.XMLUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
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
        AccountClient accountClient = new AccountClient();
        CakeClient cakeClient = new CakeClient();
        FavoriteClient favoriteClient = new FavoriteClient();
        Account user = accountClient.checkLogin_XML(Account.class, "leduong", "leduong");
        Cake cake = cakeClient.findByCakeId_XML(Cake.class, "107");
        try {
            Favorite favorite = new Favorite();
            favorite.setAccount(user);
            favorite.setCakeid(cake);
            favorite.setAvailable(true);
            Favorite result = favoriteClient.addToFavorite_XML(request, Favorite.class);
            if (result != null) {
                System.out.println("sdasdasdasa");
            } else {
                System.out.println("aaaaaaaaaaaaaaaaaaa");
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
