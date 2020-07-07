/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duongll.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author duong
 */
@XmlRootElement(name = "ingredients")
@XmlAccessorType(XmlAccessType.FIELD)
public class Ingredients {

    @XmlElement(name = "ingredient")
    private List<duongll.dto.Ingredient> ingredients;

    public List<duongll.dto.Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<duongll.dto.Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

}
