/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duongll.crawler;


/**
 *
 * @author duong
 */
public class CrawlHelper {

    public static void crawlDataBakingMad() throws Exception {
        String result = "";
        result = BakingMadCrawler.crawlCakesBakingMad();
        System.out.println("Result: " + result);
        result = BakingMadCrawler.crawlConfectioneryBakingMad();
        System.out.println("Result: " + result);
        result = BakingMadCrawler.crawlCookiesAndBiscuitBakingMad();
        System.out.println("Result: " + result);
        result = BakingMadCrawler.crawlCupcakesAndMuffinBakingMad();
        System.out.println("Result: " + result);
        result = BakingMadCrawler.crawlDessertBakingMad();
        System.out.println("Result: " + result);
    }    

    public static void crawlDataBakingAMoment() {
        String result = "";
        result = BakingAMomentCrawler.crawlCakeBakingAMoment();
        System.out.println("Result: " + result);
    }

}
