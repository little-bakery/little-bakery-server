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
@XmlRootElement(name = "materials")
@XmlAccessorType(XmlAccessType.FIELD)
public class Materials {

    @XmlElement(name = "material")
    private List<duongll.dto.Material> materials;

    public List<duongll.dto.Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<duongll.dto.Material> materials) {
        this.materials = materials;
    }

}
