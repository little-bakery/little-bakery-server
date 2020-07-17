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
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
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
@Table(name = "Favorite")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Favorite.findAll", query = "SELECT f FROM Favorite f")
    , @NamedQuery(name = "Favorite.findById", query = "SELECT f FROM Favorite f WHERE f.id = :id")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "findUserFavoriteCollection", query = "SELECT CAST ((SELECT f.cakeid, c.name, cate.name as category, c.image, c.views "
            + "FROM Favorite f, Cake c, Category cate "
            + "WHERE f.username = ? AND f.available = ? "
            + "AND c.id = f.cakeid AND c.categoryid = cate.id "
            + "FOR XML PATH('favorite'), Root('favorites')) "
            + "AS VARCHAR(MAX)) AS XmlData")})
public class Favorite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne
    private Account account;
    @JoinColumn(name = "cakeid", referencedColumnName = "id")
    @ManyToOne
    private Cake cakeid;
    @Column(name = "available")
    private boolean available;

    public Favorite() {
    }

    public Favorite(Long id) {
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
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    @XmlElement
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Favorite)) {
            return false;
        }
        Favorite other = (Favorite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "duongll.dto.Favorite[ id=" + id + " ]";
    }

}
