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
@Table(name = "Cake_Preparation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CakePreparation.findAll", query = "SELECT c FROM CakePreparation c")
    , @NamedQuery(name = "CakePreparation.findById", query = "SELECT c FROM CakePreparation c WHERE c.id = :id")
    , @NamedQuery(name = "CakePreparation.findByContentprepare", query = "SELECT c FROM CakePreparation c WHERE c.contentprepare = :contentprepare")
    , @NamedQuery(name = "CakePreparation.findByOrderprepare", query = "SELECT c FROM CakePreparation c WHERE c.orderprepare = :orderprepare")})
public class CakePreparation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Size(max = 1073741823)
    @Column(name = "contentprepare")
    private String contentprepare;
    @Column(name = "orderprepare")
    private Integer orderprepare;
    @JoinColumn(name = "cakeid", referencedColumnName = "id")
    @ManyToOne
    private Cake cakeid;

    public CakePreparation() {
    }

    public CakePreparation(Long id) {
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
    public String getContentprepare() {
        return contentprepare;
    }

    public void setContentprepare(String contentprepare) {
        this.contentprepare = contentprepare;
    }

    @XmlElement
    public Integer getOrderprepare() {
        return orderprepare;
    }

    public void setOrderprepare(Integer orderprepare) {
        this.orderprepare = orderprepare;
    }

    @XmlElement
    public Cake getCakeid() {
        return cakeid;
    }

    public void setCakeid(Cake cakeid) {
        this.cakeid = cakeid;
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
        if (!(object instanceof CakePreparation)) {
            return false;
        }
        CakePreparation other = (CakePreparation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "duongll.dto.CakePreparation[ id=" + id + " ]";
    }

}
