/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duongll.crawler;

import duongll.client.CakeClient;
import duongll.client.CakePreparationClient;
import duongll.client.CategoryClient;
import duongll.client.IngredientClient;
import duongll.client.MaterialClient;
import duongll.dto.Cake;
import duongll.dto.CakePreparation;
import duongll.dto.Category;
import duongll.dto.Ingredient;
import duongll.dto.Material;
import duongll.model.CakePreparations;
import duongll.model.Ingredients;
import duongll.model.Materials;
import duongll.model.Times;
import duongll.utils.ConverterUtils;
import duongll.utils.MyUtils;
import duongll.utils.XMLUtils;
import duongll.xslt.XSLTApplier;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import javax.xml.xpath.XPath;
import org.w3c.dom.Document;

/**
 *
 * @author duong
 */
public class BakingAMomentCrawler {

    private final static String BAKING_A_MOMENT = "https://bakingamoment.com";
    private final static XSLTApplier XSLTA = new XSLTApplier();

    public static String crawlCakeBakingAMoment() {
        try {
            String url = BAKING_A_MOMENT + "/";
            String homePageSource = XMLUtils.getHTMLSourceFromUrl(url);
            Document document = XMLUtils.parseHTMLSourceToXMLDOM(homePageSource);
            XPath xPath = XMLUtils.createXPath();
            url = xPath.evaluate("(//a[text()='Bake'])[1]/@href", document);
            String categorySource = XMLUtils.getHTMLSourceFromUrl(url);
            document = XMLUtils.parseHTMLSourceToXMLDOM(categorySource);
            url = xPath.evaluate("//div[@class='archives recipe-cats']/div[4]/a/@href", document);
            String cakesSource = XMLUtils.getHTMLSourceFromUrl(url);
            document = XMLUtils.parseHTMLSourceToXMLDOM(cakesSource);
            String cakeLink = xPath.evaluate("//div[@class='archive-post'][23]/a/@href", document);
            CategoryClient categoryClient = new CategoryClient();
            Category category = categoryClient.findByName(Category.class, "Cake");
            CakeClient cakeClient = new CakeClient();
            IngredientClient ingredientClient = new IngredientClient();
            MaterialClient materialClient = new MaterialClient();
            CakePreparationClient cakePreparationClient = new CakePreparationClient();

            String cakeLinkSource = XMLUtils.getHTMLSourceFromUrl(cakeLink);
            String timesXML = XSLTA.applyStylesheet(
                    MyUtils.getXSLTFile("F:\\Semester 8\\PRX\\SE1303\\Little_Bakery_Server\\src\\java\\duongll\\xslt\\time-baking-a-moment.xsl"),
                    cakeLinkSource);
            Times times = XMLUtils.unmarshal(timesXML, "", Times.class);

            String cakeXML = XSLTA.applyStylesheet(
                    MyUtils.getXSLTFile("F:\\Semester 8\\PRX\\SE1303\\Little_Bakery_Server\\src\\java\\duongll\\xslt\\cake-baking-a-moment.xsl"),
                    cakeLinkSource);
            ByteArrayOutputStream cakeStreamForDTD = XSLTA.applyStylesheetWithDtd("F:\\Semester 8\\PRX\\SE1303\\Little_Bakery_Server\\src\\java\\duongll\\dtd\\cake.dtd",
                    "F:\\Semester 8\\PRX\\SE1303\\Little_Bakery_Server\\src\\java\\duongll\\xslt\\cake-baking-a-moment.xsl",
                    cakeXML);
            if (XMLUtils.validateXMLUsingDtd(new ByteArrayInputStream(cakeStreamForDTD.toByteArray(), 0, cakeStreamForDTD.size()))) {
                Cake cake = XMLUtils.unmarshal(cakeXML,
                        "F:\\Semester 8\\PRX\\SE1303\\Little_Bakery_Server\\src\\java\\duongll\\schema\\cake.xsd", Cake.class);
                cake.setTime(ConverterUtils.convertTime(times.getPrepare(), times.getCook()));
                cake.setViews(ConverterUtils.randomViews());
                cake.setCategoryid(category);
                cake.setImage("https://bakingamoment.com/wp-content/uploads/2019/01/IMG_2714-best-german-chocolate-cake-recipe-720x720.jpg");
                cake.setServes(new Integer(10));

                Cake c = cakeClient.createCake_XML(cake, Cake.class);

                String ingredientXML = XSLTA.applyStylesheet(
                        MyUtils.getXSLTFile("F:\\Semester 8\\PRX\\SE1303\\Little_Bakery_Server\\src\\java\\duongll\\xslt\\ingredient-baking-a-moment.xsl"),
                        cakeLinkSource);
                Ingredients ingredientList = XMLUtils.unmarshal(ingredientXML,
                        "F:\\Semester 8\\PRX\\SE1303\\Little_Bakery_Server\\src\\java\\duongll\\schema\\ingredient.xsd",
                        Ingredients.class);
                for (Ingredient ingredient : ingredientList.getIngredients()) {
                    ingredient.setCakeid(c);
                    Ingredient tmp = ingredientClient.createIngredient_XML(ingredient, Ingredient.class);
                    ingredient.setId(tmp.getId());
                }

                String preparationXML = XSLTA.applyStylesheet(
                        MyUtils.getXSLTFile("F:\\Semester 8\\PRX\\SE1303\\Little_Bakery_Server\\src\\java\\duongll\\xslt\\preparation-baking-a-moment.xsl"),
                        cakeLinkSource);
                CakePreparations preparationList = XMLUtils.unmarshal(preparationXML,
                        "F:\\Semester 8\\PRX\\SE1303\\Little_Bakery_Server\\src\\java\\duongll\\schema\\preparation.xsd",
                        CakePreparations.class);
                for (CakePreparation cakepreparation : preparationList.getCakepreparations()) {
                    cakepreparation.setCakeid(c);
                    cakePreparationClient.createCakePreparation_XML(cakepreparation, CakePreparation.class);
                }

                new BufferedWriter(new OutputStreamWriter(new FileOutputStream("test.xml"), "UTF-8")).write(cakeLinkSource);

                String materialXML = XSLTA.applyStylesheet(
                        MyUtils.getXSLTFile("F:\\Semester 8\\PRX\\SE1303\\Little_Bakery_Server\\src\\java\\duongll\\xslt\\material-baking-a-moment.xsl"),
                        cakeLinkSource);
                Materials materialList = XMLUtils.unmarshal(materialXML,
                        "F:\\Semester 8\\PRX\\SE1303\\Little_Bakery_Server\\src\\java\\duongll\\schema\\material.xsd",
                        Materials.class);
                for (Material material : materialList.getMaterials()) {
                    material.setUnit(ConverterUtils.convertQuantity(material.getUnit()));
                    if (material.getIngredientid().getName().equals("For the Food")) {
                        for (Ingredient ingredient : ingredientList.getIngredients()) {
                            material.setIngredientid(ingredient);
                        }
                    } else {
                        for (Ingredient ingredient : ingredientList.getIngredients()) {
                            if (material.getIngredientid().getName().equals(ingredient.getName())) {
                                material.setIngredientid(ingredient);
                            }
                        }
                    }
                    materialClient.createMaterial_XML(material, Material.class);
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return "Crawl Cake Baking A Moment Finished";
    }

    private void crawlAllCakeBakingAMoment() {
//        String url = BAKING_A_MOMENT + "/";
//            String homePageSource = XMLUtils.getHTMLSourceFromUrl(url);
//            Document document = XMLUtils.parseHTMLSourceToXMLDOM(homePageSource);
//            XPath xPath = XMLUtils.createXPath();
//            url = xPath.evaluate("(//a[text()='Bake'])[1]/@href", document);
//            String categorySource = XMLUtils.getHTMLSourceFromUrl(url);
//            document = XMLUtils.parseHTMLSourceToXMLDOM(categorySource);
        //            Cake cake = XMLUtils.unmarshal(cakeXML,
//                    "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\cake.xsd", Cake.class);
//            cake.setTime(ConverterUtils.convertTime(times.getPrepare(), times.getCook()));
//            cake.setImage(imgSrc);
//            cake.setCategoryid(category);

//            String ingredientXML = XSLTA.applyStylesheet(
//                    MyUtils.getXSLTFile("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\ingredient-baking-a-moment.xsl"),
//                    cakeLinkSource);
//            System.out.println("Ingredient XML: " + ingredientXML);
//
//            String preparationXML = XSLTA.applyStylesheet(
//                    MyUtils.getXSLTFile("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\preparation-baking-a-moment.xsl"),
//                    cakeLinkSource);
//            System.out.println("Preparation XML: " + preparationXML);
//
//            String materialXML = XSLTA.applyStylesheet(
//                    MyUtils.getXSLTFile("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\material-baking-a-moment.xsl"),
//                    cakeLinkSource);
//
//            System.out.println("Material List: " + materialXML);
//            CategoryClient categoryClient = new CategoryClient();
//            Category category = categoryClient.findByName(Category.class, "Cake");
//            CakeClient cakeClient = new CakeClient();
//            IngredientClient ingredientClient = new IngredientClient();
//            MaterialClient materialClient = new MaterialClient();
//            CakePreparationClient cakePreparationClient = new CakePreparationClient();
//            do {
//                NodeList cakeUrlList = (NodeList) xPath.evaluate("//div[@class='archive-post']",
//                        document, XPathConstants.NODESET);
//                for (int i = 0; i < cakeUrlList.getLength(); i++) {
//                    String cakeLink = cakeUrlList.item(i).getFirstChild().getAttributes().getNamedItem("href").getNodeValue();
//                    String cakeLinkSource = XMLUtils.getHTMLSourceFromUrl(cakeLink);
//
//                    //get image link for your cake;
//                    String img = cakeUrlList.item(i).getFirstChild().getFirstChild().getAttributes().getNamedItem("src").getNodeValue();
//
//                    String timesXML = XSLTA.applyStylesheet(
//                            MyUtils.getXSLTFile("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\time-baking-a-moment.xsl"),
//                            cakeLinkSource);
//                    Times times = XMLUtils.unmarshal(timesXML, "", Times.class);
//
////                    String cakeXML = XSLTA.applyStylesheet(
////                            MyUtils.getXSLTFile("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\cake-baking-a-moment.xsl"),
////                            cakeLinkSource);
////                    System.out.println("CakeXML: " + cakeXML);
////                    Cake cake = XMLUtils.unmarshal(cakeXML,
////                            "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\cake.xsd", Cake.class);
////                    cake.setTime(ConverterUtils.convertTime(times.getPrepare(), times.getCook()));
////                    cake.setImage(img);
////                    cake.setCategoryid(category);
//
//                    String ingredientXML = XSLTA.applyStylesheet(
//                            MyUtils.getXSLTFile("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\ingredient-baking-a-moment.xsl"),
//                            cakeLinkSource);
//                    System.out.println("Ingredient XML: " + ingredientXML);
//
////                    String preparationXML = XSLTA.applyStylesheet(
////                            MyUtils.getXSLTFile("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\preparation-baking-a-moment.xsl"),
////                            cakeLinkSource);
////                    System.out.println("Preparation XML: " + preparationXML);
//
////                    String materialXML = XSLTA.applyStylesheet(
////                            MyUtils.getXSLTFile("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\material-baking-a-moment.xsl"),
////                            cakeLinkSource);
////
////                    System.out.println("Material List: " + materialXML);
//                }
//                String nextPage = xPath.evaluate("//a[@class='next page-numbers']/@href", document);
//                document = XMLUtils.parseHTMLSourceToXMLDOM(XMLUtils.getHTMLSourceFromUrl(nextPage));
//            } while (!xPath.evaluate("//a[@class='next page-numbers']", document).isEmpty());
    }
}
