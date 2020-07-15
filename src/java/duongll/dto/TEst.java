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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author duong
 */
@Entity
@Table(name = "TEst")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TEst.findAll", query = "SELECT t FROM TEst t")
    , @NamedQuery(name = "TEst.findById", query = "SELECT t FROM TEst t WHERE t.id = :id")
    , @NamedQuery(name = "TEst.findByAsdsd", query = "SELECT t FROM TEst t WHERE t.asdsd = :asdsd")})
public class TEst implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Column(name = "asdsd")
    private Boolean asdsd;

    public TEst() {
    }

    public TEst(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAsdsd() {
        return asdsd;
    }

    public void setAsdsd(Boolean asdsd) {
        this.asdsd = asdsd;
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
        if (!(object instanceof TEst)) {
            return false;
        }
        TEst other = (TEst) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "duongll.dto.TEst[ id=" + id + " ]";
    }
    
}
