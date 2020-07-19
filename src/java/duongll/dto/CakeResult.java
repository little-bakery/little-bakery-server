/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duongll.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author duong
 */
@XmlRootElement
public class CakeResult {

    private Cake cake;
    private Integer point;

    @XmlElement
    public Cake getCake() {
        return cake;
    }

    public void setCake(Cake cake) {
        this.cake = cake;
    }

    @XmlElement
    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
