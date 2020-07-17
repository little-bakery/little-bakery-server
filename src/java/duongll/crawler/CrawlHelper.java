/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duongll.crawler;

import duongll.client.AnswerClient;
import duongll.client.CakeClient;
import duongll.client.CakeWeightClient;
import duongll.client.FavoriteClient;
import duongll.client.IngredientClient;
import duongll.client.MaterialClient;
import duongll.client.QuestionClient;
import duongll.dto.Answers;
import duongll.dto.Cake;
import duongll.dto.CakeWeight;
import duongll.dto.Material;
import duongll.dto.Questions;
import duongll.utils.ConverterUtils;
import java.util.HashMap;
import java.util.List;

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

    public static void mappingCakeWithAnswer() {
        HashMap<Questions, List<Answers>> result = new HashMap<Questions, List<Answers>>();
        CakeClient cakeClient = new CakeClient();
        CakeWeight cakeWeight = new CakeWeight();
        CakeWeightClient cakeWeightClient = new CakeWeightClient();
        MaterialClient materialClient = new MaterialClient();
        List<Questions> listQuestion;
        List<Material> materialList;
        try {
            QuestionClient questionClient = new QuestionClient();
            AnswerClient answerClient = new AnswerClient();
            listQuestion = questionClient.findALlQuestion_XML(List.class);
            for (Questions questions : listQuestion) {
                result.put(questions, answerClient.findByQuestionId_XML(List.class, questions.getId() + ""));
            }
            List<Cake> cakeList = cakeClient.findAll_XML(List.class);
            for (Questions questions : result.keySet()) {
                if (questions.getId() == 1) {
                    for (Answers answers : result.get(questions)) {
                        if (answers.getId().intValue() == 1) {
                            for (Cake cake : cakeList) {
                                if (cake.getCategoryid().getId() == 1) {
                                    cakeWeight.setId(new Long(0));
                                    cakeWeight.setCakeid(cake);
                                    cakeWeight.setAnswerid(answers);
                                    cakeWeightClient.mappingAnswerWithCake_XML(cakeWeight, CakeWeight.class);
                                }
                            }
                        } else if (answers.getId().intValue() == 2) {
                            for (Cake cake : cakeList) {
                                if (cake.getCategoryid().getId() == 2) {
                                    cakeWeight.setId(new Long(0));
                                    cakeWeight.setCakeid(cake);
                                    cakeWeight.setAnswerid(answers);
                                    cakeWeightClient.mappingAnswerWithCake_XML(cakeWeight, CakeWeight.class);
                                }
                            }
                        } else if (answers.getId().intValue() == 3) {
                            for (Cake cake : cakeList) {
                                if (cake.getCategoryid().getId() == 3) {
                                    cakeWeight.setId(new Long(0));
                                    cakeWeight.setCakeid(cake);
                                    cakeWeight.setAnswerid(answers);
                                    cakeWeightClient.mappingAnswerWithCake_XML(cakeWeight, CakeWeight.class);
                                }
                            }
                        } else if (answers.getId().intValue() == 4) {
                            for (Cake cake : cakeList) {
                                if (cake.getCategoryid().getId() == 4) {
                                    cakeWeight.setId(new Long(0));
                                    cakeWeight.setCakeid(cake);
                                    cakeWeight.setAnswerid(answers);
                                    cakeWeightClient.mappingAnswerWithCake_XML(cakeWeight, CakeWeight.class);
                                }
                            }
                        } else if (answers.getId().intValue() == 5) {
                            for (Cake cake : cakeList) {
                                if (cake.getCategoryid().getId() == 6) {
                                    cakeWeight.setId(new Long(0));
                                    cakeWeight.setCakeid(cake);
                                    cakeWeight.setAnswerid(answers);
                                    cakeWeightClient.mappingAnswerWithCake_XML(cakeWeight, CakeWeight.class);
                                }
                            }
                        }
                    }
                } else if (questions.getId() == 2) {
                    for (Answers answers : result.get(questions)) {
                        materialList = materialClient.getMaterialByName_XML(List.class, answers.getName());
                        for (Material material : materialList) {
                            cakeWeight.setId(new Long(0));
                            cakeWeight.setCakeid(material.getIngredientid().getCakeid());
                            cakeWeight.setAnswerid(answers);
                            cakeWeightClient.mappingAnswerWithCake_XML(cakeWeight, CakeWeight.class);
                        }
                    }
                } else if (questions.getId() == 3) {
                    for (Answers answers : result.get(questions)) {
                        materialList = materialClient.getMaterialByName_XML(List.class, answers.getName());
                        for (Material material : materialList) {
                            cakeWeight.setId(new Long(0));
                            cakeWeight.setCakeid(material.getIngredientid().getCakeid());
                            cakeWeight.setAnswerid(answers);
                            cakeWeightClient.mappingAnswerWithCake_XML(cakeWeight, CakeWeight.class);
                        }
                    }
                } else if (questions.getId() == 4) {
                    for (Answers answers : result.get(questions)) {
                        materialList = materialClient.getMaterialByName_XML(List.class, answers.getName());
                        for (Material material : materialList) {
                            cakeWeight.setId(new Long(0));
                            cakeWeight.setCakeid(material.getIngredientid().getCakeid());
                            cakeWeight.setAnswerid(answers);
                            cakeWeightClient.mappingAnswerWithCake_XML(cakeWeight, CakeWeight.class);
                        }
                    }
                } else if (questions.getId() == 5) {
                    for (Answers answers : result.get(questions)) {
                        if (answers.getId() == 15) {
                            for (Cake cake : cakeList) {
                                if (ConverterUtils.convertTimeToMinute(cake.getTime()) < 30) {
                                    cakeWeight.setId(new Long(0));
                                    cakeWeight.setCakeid(cake);
                                    cakeWeight.setAnswerid(answers);
                                    cakeWeightClient.mappingAnswerWithCake_XML(cakeWeight, CakeWeight.class);
                                }
                            }
                        } else if (answers.getId() == 16) {
                            for (Cake cake : cakeList) {
                                if (ConverterUtils.convertTimeToMinute(cake.getTime()) < 60 && ConverterUtils.convertTimeToMinute(cake.getTime()) >= 30) {
                                    cakeWeight.setId(new Long(0));
                                    cakeWeight.setCakeid(cake);
                                    cakeWeight.setAnswerid(answers);
                                    cakeWeightClient.mappingAnswerWithCake_XML(cakeWeight, CakeWeight.class);
                                }
                            }
                        } else if (answers.getId() == 17) {
                            for (Cake cake : cakeList) {
                                if (ConverterUtils.convertTimeToMinute(cake.getTime()) < 180 && ConverterUtils.convertTimeToMinute(cake.getTime()) >= 60) {
                                    cakeWeight.setId(new Long(0));
                                    cakeWeight.setCakeid(cake);
                                    cakeWeight.setAnswerid(answers);
                                    cakeWeightClient.mappingAnswerWithCake_XML(cakeWeight, CakeWeight.class);
                                }
                            }
                        } else if (answers.getId() == 18) {
                            for (Cake cake : cakeList) {
                                if (ConverterUtils.convertTimeToMinute(cake.getTime()) > 180) {
                                    cakeWeight.setId(new Long(0));
                                    cakeWeight.setCakeid(cake);
                                    cakeWeight.setAnswerid(answers);
                                    cakeWeightClient.mappingAnswerWithCake_XML(cakeWeight, CakeWeight.class);
                                }
                            }
                        }
                    }
                } else if (questions.getId() == 7) {
                    for (Answers answers : result.get(questions)) {
                        materialList = materialClient.getMaterialByName_XML(List.class, answers.getName());
                        for (Material material : materialList) {
                            cakeWeight.setId(new Long(0));
                            cakeWeight.setCakeid(material.getIngredientid().getCakeid());
                            cakeWeight.setAnswerid(answers);
                            cakeWeightClient.mappingAnswerWithCake_XML(cakeWeight, CakeWeight.class);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
