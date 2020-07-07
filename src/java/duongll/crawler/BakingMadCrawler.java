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
import duongll.utils.ConverterUtils;
import duongll.utils.XMLUtils;
import duongll.xslt.XSLTApplier;
import java.util.ArrayList;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author duong
 */
public class BakingMadCrawler {

    private final static String BAKING_MAD_URL = "https://www.bakingmad.com";
    private final static XSLTApplier XSLTA = new XSLTApplier();

    public static String crawlCakesBakingMad() {
        String source = XMLUtils.getHTMLSourceFromUrl(BAKING_MAD_URL + "/");
        Document document = XMLUtils.parseHTMLSourceToXMLDOM(source);
        XPath xPath = XMLUtils.createXPath();
        try {
            String urlCakesRecipes = BAKING_MAD_URL + xPath.evaluate("//*[@class = 'main-nav__child']//a[text()='Cake Recipes']/@href", document);
            String htmlCakesRecipesSource = XMLUtils.getHTMLSourceFromUrl(urlCakesRecipes);
            CategoryClient categoryClient = new CategoryClient();
            CakeClient cakeClient = new CakeClient();
            IngredientClient ingredientClient = new IngredientClient();
            CakePreparationClient cakePreparationClient = new CakePreparationClient();
            MaterialClient materialClient = new MaterialClient();
            Category category = (Category) categoryClient.findByName(Category.class, "Cake");
            document = XMLUtils.parseHTMLSourceToXMLDOM(htmlCakesRecipesSource);
            while (xPath.evaluate("//li[@class='pagination__next is-disabled']", document).isEmpty()
                    && !xPath.evaluate("//li[@class='pagination__next']", document).isEmpty()) {
                String paginationPage = BAKING_MAD_URL + xPath.evaluate("//li[@class='pagination__next']/a/@href", document);
                NodeList cakeRecipesList = (NodeList) xPath.evaluate(
                        "//*[@class = 'summary summary--recipe']//a[@class='summary__block-link']",
                        document, XPathConstants.NODESET);
                document = XMLUtils.parseHTMLSourceToXMLDOM(XMLUtils.getHTMLSourceFromUrl(paginationPage.replaceAll("\'", "")));
                for (int i = 0; i < cakeRecipesList.getLength(); i++) {
                    String cakeLink = BAKING_MAD_URL + cakeRecipesList.item(i).getAttributes().getNamedItem("href").getNodeValue();
                    String cakeLinkHTMLSource = XMLUtils.getHTMLSourceFromUrl(cakeLink);

                    String cakeXML = XSLTA.applyStylesheet("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\cake-baking-mad.xsl", cakeLinkHTMLSource);
                    Cake c = XMLUtils.unmarshal(cakeXML,
                            "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\cake.xsd", Cake.class);
                    c.setCategoryid(category);
                    Cake cakeResult = cakeClient.createCake_XML(c, Cake.class);
                    String ingredientXML = XSLTA.applyStylesheet("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\ingredient-baking-mad.xsl", cakeLinkHTMLSource);
                    Ingredients ingredientList = XMLUtils.unmarshal(ingredientXML,
                            "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\ingredient.xsd", Ingredients.class
                    );

                    for (Ingredient ingredient : ingredientList.getIngredients()) {
                        ingredient.setCakeid(cakeResult);
                        Ingredient tmp = ingredientClient.createIngredient_XML(ingredient, Ingredient.class);
                        ingredient.setId(tmp.getId());
                        ingredient.setMaterialCollection(new ArrayList<>());
                        ingredient.setCakeid(null);
                    }

                    String preparationXML = XSLTA.applyStylesheet("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\preparation-baking-mad.xsl", cakeLinkHTMLSource);
                    CakePreparations cakePreparationsList = XMLUtils.unmarshal(preparationXML,
                            "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\preparation.xsd", CakePreparations.class);
                    for (CakePreparation preparation : cakePreparationsList.getCakepreparations()) {
                        preparation.setCakeid(cakeResult);
                        cakePreparationClient.createCakePreparation_XML(preparation, CakePreparation.class);
                    }
                    String materialXML = XSLTA.applyStylesheet("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\material-baking-mad.xsl", cakeLinkHTMLSource);
                    Materials materialList = XMLUtils.unmarshal(materialXML,
                            "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\material.xsd", Materials.class);
                    System.out.println("New MAterial List");
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
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something Error");
        }
        return "Crawl Cake Recipes Finished";
    }

    public static String crawlConfectioneryBakingMad() {
        String source = XMLUtils.getHTMLSourceFromUrl(BAKING_MAD_URL + "/");
        Document document = XMLUtils.parseHTMLSourceToXMLDOM(source);
        XPath xPath = XMLUtils.createXPath();
        try {
            String urlCakesRecipes = BAKING_MAD_URL + xPath.evaluate("//*[@class = 'main-nav__child']//a[text()='Confectionery Recipes']/@href", document);
            String htmlCakesRecipesSource = XMLUtils.getHTMLSourceFromUrl(urlCakesRecipes);
            CategoryClient categoryClient = new CategoryClient();
            CakeClient cakeClient = new CakeClient();
            IngredientClient ingredientClient = new IngredientClient();
            CakePreparationClient cakePreparationClient = new CakePreparationClient();
            MaterialClient materialClient = new MaterialClient();
            Category category = (Category) categoryClient.findByName(Category.class, "Confectionery");
            document = XMLUtils.parseHTMLSourceToXMLDOM(htmlCakesRecipesSource);
            while (xPath.evaluate("//li[@class='pagination__next is-disabled']", document).isEmpty()
                    && !xPath.evaluate("//li[@class='pagination__next']", document).isEmpty()) {
                String paginationPage = BAKING_MAD_URL + xPath.evaluate("//li[@class='pagination__next']/a/@href", document);
                NodeList cakeRecipesList = (NodeList) xPath.evaluate(
                        "//*[@class = 'summary summary--recipe']//a[@class='summary__block-link']",
                        document, XPathConstants.NODESET);
                document = XMLUtils.parseHTMLSourceToXMLDOM(XMLUtils.getHTMLSourceFromUrl(paginationPage.replaceAll("\'", "")));
                for (int i = 0; i < cakeRecipesList.getLength(); i++) {
                    String cakeLink = BAKING_MAD_URL + cakeRecipesList.item(i).getAttributes().getNamedItem("href").getNodeValue();
                    String cakeLinkHTMLSource = XMLUtils.getHTMLSourceFromUrl(cakeLink);

                    String cakeXML = XSLTA.applyStylesheet("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\cake-baking-mad.xsl", cakeLinkHTMLSource);
                    Cake c = XMLUtils.unmarshal(cakeXML,
                            "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\cake.xsd", Cake.class);
                    c.setCategoryid(category);
                    Cake cakeResult = cakeClient.createCake_XML(c, Cake.class);
                    String ingredientXML = XSLTA.applyStylesheet("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\ingredient-baking-mad.xsl", cakeLinkHTMLSource);
                    Ingredients ingredientList = XMLUtils.unmarshal(ingredientXML,
                            "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\ingredient.xsd", Ingredients.class
                    );                   
                    for (Ingredient ingredient : ingredientList.getIngredients()) {
                        ingredient.setCakeid(cakeResult);
                        Ingredient tmp = ingredientClient.createIngredient_XML(ingredient, Ingredient.class);
                        ingredient.setId(tmp.getId());
                        ingredient.setMaterialCollection(new ArrayList<>());
                        ingredient.setCakeid(null);
                    }

                    String preparationXML = XSLTA.applyStylesheet("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\preparation-baking-mad.xsl", cakeLinkHTMLSource);
                    CakePreparations cakePreparationsList = XMLUtils.unmarshal(preparationXML,
                            "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\preparation.xsd", CakePreparations.class);
                    for (CakePreparation preparation : cakePreparationsList.getCakepreparations()) {
                        preparation.setCakeid(cakeResult);
                        cakePreparationClient.createCakePreparation_XML(preparation, CakePreparation.class);
                    }
                    String materialXML = XSLTA.applyStylesheet("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\material-baking-mad.xsl", cakeLinkHTMLSource);
                    Materials materialList = XMLUtils.unmarshal(materialXML,
                            "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\material.xsd", Materials.class);
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
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something Error In Confectionery");
        }
        return "Crawl Confectionery Recipes Finished";
    }

    public static String crawlCookiesAndBiscuitBakingMad() {
        String source = XMLUtils.getHTMLSourceFromUrl(BAKING_MAD_URL + "/");
        Document document = XMLUtils.parseHTMLSourceToXMLDOM(source);
        XPath xPath = XMLUtils.createXPath();
        try {
            String urlCakesRecipes = BAKING_MAD_URL + xPath.evaluate("//*[@class = 'main-nav__child']//a[text()='Cookies & Biscuit Recipes']/@href", document);
            String htmlCakesRecipesSource = XMLUtils.getHTMLSourceFromUrl(urlCakesRecipes);
            CategoryClient categoryClient = new CategoryClient();
            CakeClient cakeClient = new CakeClient();
            IngredientClient ingredientClient = new IngredientClient();
            CakePreparationClient cakePreparationClient = new CakePreparationClient();
            MaterialClient materialClient = new MaterialClient();
            Category category = (Category) categoryClient.findByName(Category.class, "Biscuit & Cookies");
            document = XMLUtils.parseHTMLSourceToXMLDOM(htmlCakesRecipesSource);
            while (xPath.evaluate("//li[@class='pagination__next is-disabled']", document).isEmpty()
                    && !xPath.evaluate("//li[@class='pagination__next']", document).isEmpty()) {
                String paginationPage = BAKING_MAD_URL + xPath.evaluate("//li[@class='pagination__next']/a/@href", document);
                NodeList cakeRecipesList = (NodeList) xPath.evaluate(
                        "//*[@class = 'summary summary--recipe']//a[@class='summary__block-link']",
                        document, XPathConstants.NODESET);
                document = XMLUtils.parseHTMLSourceToXMLDOM(XMLUtils.getHTMLSourceFromUrl(paginationPage.replaceAll("\'", "")));
                for (int i = 0; i < cakeRecipesList.getLength(); i++) {
                    String cakeLink = BAKING_MAD_URL + cakeRecipesList.item(i).getAttributes().getNamedItem("href").getNodeValue();
                    String cakeLinkHTMLSource = XMLUtils.getHTMLSourceFromUrl(cakeLink);

                    String cakeXML = XSLTA.applyStylesheet("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\cake-baking-mad.xsl", cakeLinkHTMLSource);
                    Cake c = XMLUtils.unmarshal(cakeXML,
                            "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\cake.xsd", Cake.class);
                    c.setCategoryid(category);
                    Cake cakeResult = cakeClient.createCake_XML(c, Cake.class);
                    String ingredientXML = XSLTA.applyStylesheet("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\ingredient-baking-mad.xsl", cakeLinkHTMLSource);
                    Ingredients ingredientList = XMLUtils.unmarshal(ingredientXML,
                            "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\ingredient.xsd", Ingredients.class
                    );
                    for (Ingredient ingredient : ingredientList.getIngredients()) {
                        ingredient.setCakeid(cakeResult);
                        Ingredient tmp = ingredientClient.createIngredient_XML(ingredient, Ingredient.class);
                        ingredient.setId(tmp.getId());
                        ingredient.setMaterialCollection(new ArrayList<>());
                        ingredient.setCakeid(null);
                    }

                    String preparationXML = XSLTA.applyStylesheet("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\preparation-baking-mad.xsl", cakeLinkHTMLSource);
                    CakePreparations cakePreparationsList = XMLUtils.unmarshal(preparationXML,
                            "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\preparation.xsd", CakePreparations.class);
                    for (CakePreparation preparation : cakePreparationsList.getCakepreparations()) {
                        preparation.setCakeid(cakeResult);
                        cakePreparationClient.createCakePreparation_XML(preparation, CakePreparation.class);
                    }
                    String materialXML = XSLTA.applyStylesheet("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\material-baking-mad.xsl", cakeLinkHTMLSource);
                    Materials materialList = XMLUtils.unmarshal(materialXML,
                            "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\material.xsd", Materials.class);
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
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something Error at Cookies");
        }
        return "Crawl Cookies And Biscuit Recipes Finished";
    }

    public static String crawlCupcakesAndMuffinBakingMad() {
        String source = XMLUtils.getHTMLSourceFromUrl(BAKING_MAD_URL + "/");
        Document document = XMLUtils.parseHTMLSourceToXMLDOM(source);
        XPath xPath = XMLUtils.createXPath();
        try {
            String urlCakesRecipes = BAKING_MAD_URL + xPath.evaluate("//*[@class = 'main-nav__child']//a[text()='Cupcakes & Muffin Recipes']/@href", document);
            String htmlCakesRecipesSource = XMLUtils.getHTMLSourceFromUrl(urlCakesRecipes);
            CategoryClient categoryClient = new CategoryClient();
            CakeClient cakeClient = new CakeClient();
            IngredientClient ingredientClient = new IngredientClient();
            CakePreparationClient cakePreparationClient = new CakePreparationClient();
            MaterialClient materialClient = new MaterialClient();
            Category category = (Category) categoryClient.findByName(Category.class, "Cupcakes & Muffin");
            document = XMLUtils.parseHTMLSourceToXMLDOM(htmlCakesRecipesSource);
            while (xPath.evaluate("//li[@class='pagination__next is-disabled']", document).isEmpty()
                    && !xPath.evaluate("//li[@class='pagination__next']", document).isEmpty()) {
                String paginationPage = BAKING_MAD_URL + xPath.evaluate("//li[@class='pagination__next']/a/@href", document);
                NodeList cakeRecipesList = (NodeList) xPath.evaluate(
                        "//*[@class = 'summary summary--recipe']//a[@class='summary__block-link']",
                        document, XPathConstants.NODESET);
                document = XMLUtils.parseHTMLSourceToXMLDOM(XMLUtils.getHTMLSourceFromUrl(paginationPage.replaceAll("\'", "")));
                for (int i = 0; i < cakeRecipesList.getLength(); i++) {
                    String cakeLink = BAKING_MAD_URL + cakeRecipesList.item(i).getAttributes().getNamedItem("href").getNodeValue();
                    String cakeLinkHTMLSource = XMLUtils.getHTMLSourceFromUrl(cakeLink);

                    String cakeXML = XSLTA.applyStylesheet("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\cake-baking-mad.xsl", cakeLinkHTMLSource);
                    Cake c = XMLUtils.unmarshal(cakeXML,
                            "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\cake.xsd", Cake.class);
                    c.setCategoryid(category);
                    Cake cakeResult = cakeClient.createCake_XML(c, Cake.class);
                    String ingredientXML = XSLTA.applyStylesheet("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\ingredient-baking-mad.xsl", cakeLinkHTMLSource);
                    Ingredients ingredientList = XMLUtils.unmarshal(ingredientXML,
                            "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\ingredient.xsd", Ingredients.class
                    );                   

                    for (Ingredient ingredient : ingredientList.getIngredients()) {
                        ingredient.setCakeid(cakeResult);
                        Ingredient tmp = ingredientClient.createIngredient_XML(ingredient, Ingredient.class);
                        ingredient.setId(tmp.getId());
                        ingredient.setMaterialCollection(new ArrayList<>());
                        ingredient.setCakeid(null);
                    }

                    String preparationXML = XSLTA.applyStylesheet("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\preparation-baking-mad.xsl", cakeLinkHTMLSource);
                    CakePreparations cakePreparationsList = XMLUtils.unmarshal(preparationXML,
                            "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\preparation.xsd", CakePreparations.class);
                    for (CakePreparation preparation : cakePreparationsList.getCakepreparations()) {
                        preparation.setCakeid(cakeResult);
                        cakePreparationClient.createCakePreparation_XML(preparation, CakePreparation.class);
                    }
                    String materialXML = XSLTA.applyStylesheet("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\material-baking-mad.xsl", cakeLinkHTMLSource);
                    Materials materialList = XMLUtils.unmarshal(materialXML,
                            "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\material.xsd", Materials.class);
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
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something Error at Cupcakes and Muffin");
        }
        return "Crawl Cupcakes And Muffin Recipes Finished";
    }

    public static String crawlDessertBakingMad() {
        String source = XMLUtils.getHTMLSourceFromUrl(BAKING_MAD_URL + "/");
        Document document = XMLUtils.parseHTMLSourceToXMLDOM(source);
        XPath xPath = XMLUtils.createXPath();
        try {
            String urlCakesRecipes = BAKING_MAD_URL + xPath.evaluate("//*[@class = 'main-nav__child']//a[text()='Dessert Recipes']/@href", document);
            String htmlCakesRecipesSource = XMLUtils.getHTMLSourceFromUrl(urlCakesRecipes);
            CategoryClient categoryClient = new CategoryClient();
            CakeClient cakeClient = new CakeClient();
            IngredientClient ingredientClient = new IngredientClient();
            CakePreparationClient cakePreparationClient = new CakePreparationClient();
            MaterialClient materialClient = new MaterialClient();
            Category category = (Category) categoryClient.findByName(Category.class, "Dessert");
            document = XMLUtils.parseHTMLSourceToXMLDOM(htmlCakesRecipesSource);
            while (xPath.evaluate("//li[@class='pagination__next is-disabled']", document).isEmpty()
                    && !xPath.evaluate("//li[@class='pagination__next']", document).isEmpty()) {
                String paginationPage = BAKING_MAD_URL + xPath.evaluate("//li[@class='pagination__next']/a/@href", document);
                NodeList cakeRecipesList = (NodeList) xPath.evaluate(
                        "//*[@class = 'summary summary--recipe']//a[@class='summary__block-link']",
                        document, XPathConstants.NODESET);
                document = XMLUtils.parseHTMLSourceToXMLDOM(XMLUtils.getHTMLSourceFromUrl(paginationPage.replaceAll("\'", "")));
                for (int i = 0; i < cakeRecipesList.getLength(); i++) {
                    String cakeLink = BAKING_MAD_URL + cakeRecipesList.item(i).getAttributes().getNamedItem("href").getNodeValue();
                    String cakeLinkHTMLSource = XMLUtils.getHTMLSourceFromUrl(cakeLink);

                    String cakeXML = XSLTA.applyStylesheet("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\cake-baking-mad.xsl", cakeLinkHTMLSource);
                    Cake c = XMLUtils.unmarshal(cakeXML,
                            "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\cake.xsd", Cake.class);
                    c.setCategoryid(category);
                    Cake cakeResult = cakeClient.createCake_XML(c, Cake.class);
                    String ingredientXML = XSLTA.applyStylesheet("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\ingredient-baking-mad.xsl", cakeLinkHTMLSource);
                    Ingredients ingredientList = XMLUtils.unmarshal(ingredientXML,
                            "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\ingredient.xsd", Ingredients.class
                    );

                    for (Ingredient ingredient : ingredientList.getIngredients()) {
                        ingredient.setCakeid(cakeResult);
                        Ingredient tmp = ingredientClient.createIngredient_XML(ingredient, Ingredient.class);
                        ingredient.setId(tmp.getId());
                        ingredient.setMaterialCollection(new ArrayList<>());
                        ingredient.setCakeid(null);
                    }

                    String preparationXML = XSLTA.applyStylesheet("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\preparation-baking-mad.xsl", cakeLinkHTMLSource);
                    CakePreparations cakePreparationsList = XMLUtils.unmarshal(preparationXML,
                            "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\preparation.xsd", CakePreparations.class);
                    for (CakePreparation preparation : cakePreparationsList.getCakepreparations()) {
                        preparation.setCakeid(cakeResult);
                        cakePreparationClient.createCakePreparation_XML(preparation, CakePreparation.class);
                    }
                    String materialXML = XSLTA.applyStylesheet("F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\xslt\\material-baking-mad.xsl", cakeLinkHTMLSource);
                    Materials materialList = XMLUtils.unmarshal(materialXML,
                            "F:\\Semester 8\\PRX\\SE1303\\LittleBakery_Server\\src\\java\\duongll\\schema\\material.xsd", Materials.class);
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
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something Error at Dessert");
        }
        return "Crawl Dessert Recipes Finished";
    }
}
