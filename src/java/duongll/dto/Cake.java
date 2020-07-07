/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duongll.dto;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author duong
 */
@Entity
@Table(name = "Cake")
@XmlRootElement(name = "cake")
@NamedQueries({
    @NamedQuery(name = "Cake.findAll", query = "SELECT c FROM Cake c")
    , @NamedQuery(name = "Cake.findById", query = "SELECT c FROM Cake c WHERE c.id = :id")
    , @NamedQuery(name = "Cake.findByName", query = "SELECT c FROM Cake c WHERE c.name = :name")
    , @NamedQuery(name = "Cake.findByDescription", query = "SELECT c FROM Cake c WHERE c.description = :description")
    , @NamedQuery(name = "Cake.findByImage", query = "SELECT c FROM Cake c WHERE c.image = :image")
    , @NamedQuery(name = "Cake.findByLink", query = "SELECT c FROM Cake c WHERE c.link = :link")
    , @NamedQuery(name = "Cake.findByTime", query = "SELECT c FROM Cake c WHERE c.time = :time")
    , @NamedQuery(name = "Cake.findByServes", query = "SELECT c FROM Cake c WHERE c.serves = :serves")})
public class Cake implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Size(max = 200)
    @Column(name = "name")
    private String name;
    @Size(max = 1073741823)
    @Column(name = "description")
    private String description;
    @Size(max = 225)
    @Column(name = "image")
    private String image;
    @Size(max = 225)
    @Column(name = "link")
    private String link;
    @Size(max = 20)
    @Column(name = "time")
    private String time;
    @Column(name = "serves")
    private Integer serves;
    @OneToMany(mappedBy = "cakeid")
    private Collection<CakePreparation> cakePreparationCollection;
    @OneToMany(mappedBy = "cakeid")
    private Collection<Ingredient> ingredientCollection;
    @JoinColumn(name = "categoryid", referencedColumnName = "id")
    @ManyToOne
    private Category categoryid;

    public Cake() {
    }

    public Cake(Long id) {
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
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @XmlElement
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @XmlElement
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @XmlElement
    public Integer getServes() {
        return serves;
    }

    public void setServes(Integer serves) {
        this.serves = serves;
    }

    @XmlTransient
    public Collection<CakePreparation> getCakePreparationCollection() {
        return cakePreparationCollection;
    }

    public void setCakePreparationCollection(Collection<CakePreparation> cakePreparationCollection) {
        this.cakePreparationCollection = cakePreparationCollection;
    }

    @XmlTransient
    public Collection<Ingredient> getIngredientCollection() {
        return ingredientCollection;
    }

    public void setIngredientCollection(Collection<Ingredient> ingredientCollection) {
        this.ingredientCollection = ingredientCollection;
    }

    @XmlElement
    public Category getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Category categoryid) {
        this.categoryid = categoryid;
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
        if (!(object instanceof Cake)) {
            return false;
        }
        Cake other = (Cake) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "duongll.dto.Cake[ id=" + id + " ]";
    }

}
