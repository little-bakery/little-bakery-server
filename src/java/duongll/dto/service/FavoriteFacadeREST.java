/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duongll.dto.service;

import duongll.dto.Favorite;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author duong
 */
@Stateless
@Path("duongll.dto.favorite")
public class FavoriteFacadeREST extends AbstractFacade<Favorite> {

    @PersistenceContext(unitName = "Little_Bakery_ServerPU")
    private EntityManager em;

    public FavoriteFacadeREST() {
        super(Favorite.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Favorite entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Favorite entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Favorite find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Favorite> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Favorite> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Path("find")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Favorite findFavoriteFromUser(@QueryParam("username") String username, @QueryParam("cakeid") String cakeid) {
        try {
            Query query = em.createQuery("SELECT f FROM Favorite f WHERE f.account.username = :username AND f.cakeid.id = :id")
                    .setParameter("username", username)
                    .setParameter("id", Long.parseLong(cakeid));
            return (Favorite) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @POST
    @Path("add")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Favorite addToFavorite(Favorite favorite) {
        super.create(favorite);
        return favorite;
    }

    @PUT
    @Path("/update/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Favorite updateAvailableFavorite(@PathParam("id") Long id, Favorite entity) {
        super.edit(entity);
        return entity;
    }

    @GET
    @Path("/getAll/{username}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Favorite> getUserFavoriteCollection(@PathParam("username") String user, @QueryParam("available") Boolean available) {
        Query query = em.createQuery("SELECT f FROM Favorite f WHERE f.account.username = :username AND f.available = :available")
                .setParameter("username", user)
                .setParameter("available", available);
        return query.getResultList();
    }

    @GET
    @Path("/getAll")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getUserFavoriteCollectionXML(@PathParam("username") String user, @QueryParam("available") Boolean available) {
        try {
            Query query = em.createQuery("SELECT CAST ((SELECT f.cakeid, f.id FROM Favorite f "
                    + "WHERE f.account.username = :username AND f.available = :avalable"
                    + "FOR XML PATH('favorite'), Root('favorites'))"
                    + "AS NVARCHAR(MAX)) AS XmlData")
                    .setParameter("username", user)
                    .setParameter("available", available);
            return (String) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
