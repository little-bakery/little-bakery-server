/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duongll.dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author duong
 */
@Entity
@Table(name = "Material")
@XmlRootElement(name = "material")
@NamedQueries({
    @NamedQuery(name = "Material.findAll", query = "SELECT m FROM Material m")
    , @NamedQuery(name = "Material.findById", query = "SELECT m FROM Material m WHERE m.id = :id")
    , @NamedQuery(name = "Material.findByName", query = "SELECT m FROM Material m WHERE m.name = :name")
    , @NamedQuery(name = "Material.findByUnit", query = "SELECT m FROM Material m WHERE m.unit = :unit")
    , @NamedQuery(name = "Material.findByQuantity", query = "SELECT m FROM Material m WHERE m.quantity = :quantity")})
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Size(max = 225)
    @Column(name = "name")
    private String name;
    @Size(max = 50)
    @Column(name = "unit")
    private String unit;
    @Size(max = 50)
    @Column(name = "quantity")
    private String quantity;
    @JoinColumn(name = "ingredientid", referencedColumnName = "id")
    @ManyToOne
    private Ingredient ingredientid;

    public Material() {
    }

    public Material(Long id) {
        this.id = id;
    }

    @XmlElement
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @XmlElement
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @XmlElement
    public Ingredient getIngredientid() {
        return ingredientid;
    }

    public void setIngredientid(Ingredient ingredientid) {
        this.ingredientid = ingredientid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Material)) {
            return false;
        }
        Material other = (Material) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "duongll.dto.Material[ id=" + id + " ]";
    }

}
