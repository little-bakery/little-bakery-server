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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author duong
 */
@Entity
@Table(name = "CakeWeight")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CakeWeight.findAll", query = "SELECT c FROM CakeWeight c")
    , @NamedQuery(name = "CakeWeight.findById", query = "SELECT c FROM CakeWeight c WHERE c.id = :id")})
public class CakeWeight implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "answerid", referencedColumnName = "id")
    @ManyToOne
    private Answers answerid;
    @JoinColumn(name = "cakeid", referencedColumnName = "id")
    @ManyToOne
    private Cake cakeid;

    public CakeWeight() {
    }

    public CakeWeight(Long id) {
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
    public Answers getAnswerid() {
        return answerid;
    }

    public void setAnswerid(Answers answerid) {
        this.answerid = answerid;
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
        if (!(object instanceof CakeWeight)) {
            return false;
        }
        CakeWeight other = (CakeWeight) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "duongll.dto.CakeWeight[ id=" + id + " ]";
    }

}
