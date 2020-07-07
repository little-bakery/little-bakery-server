/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duongll.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author duong
 */
@XmlRootElement(name = "times")
public class Times {

    private String prepare;
    private String cook;

    @XmlElement
    public String getPrepare() {
        return prepare;
    }

    public void setPrepare(String prepare) {
        this.prepare = prepare;
    }

    @XmlElement
    public String getCook() {
        return cook;
    }

    public void setCook(String cook) {
        this.cook = cook;
    }

}
