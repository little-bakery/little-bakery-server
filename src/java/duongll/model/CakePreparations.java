/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duongll.model;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author duong
 */
@XmlRootElement(name = "cakepreparations")
@XmlAccessorType(XmlAccessType.FIELD)
public class CakePreparations {
    
    @XmlElement(name = "cakepreparation")
    private List<duongll.dto.CakePreparation> cakepreparations;

    public List<duongll.dto.CakePreparation> getCakepreparations() {
        return cakepreparations;
    }

    public void setCakepreparations(List<duongll.dto.CakePreparation> cakepreparations) {
        this.cakepreparations = cakepreparations;
    }
        
}
