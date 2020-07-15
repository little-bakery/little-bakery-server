/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duongll.dto.service;

import duongll.dto.Cake;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author duong
 */
@Stateless
@Path("duongll.dto.cake")
public class CakeFacadeREST extends AbstractFacade<Cake> {

    @PersistenceContext(unitName = "Little_Bakery_ServerPU")
    private EntityManager em;

    public CakeFacadeREST() {
        super(Cake.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Cake entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Cake entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Cake find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cake> findAll() {
        try {
            return super.findAll();
        } catch (Exception e) {
            System.out.println("Log at Cake Facade: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cake> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    @POST
    @Path("cake")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Cake createCake(Cake entity) {
        try {
            super.create(entity);
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return entity;
    }

    @GET
    @Path("/find/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cake> findByCategoryId(@PathParam("id") String categoryId) {
        Query query = em.createQuery("SELECT c FROM Cake c WHERE c.categoryid.id = :id")
                .setParameter("id", Long.parseLong(categoryId));
        return query.getResultList();
    }
    
    @GET
    @Path("{id}/find")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Cake findByCakeId(@PathParam("id") String cakeId) {
        Query query = em.createQuery("SELECT c FROM Cake c Where c.id = :id")
                .setParameter("id", Long.parseLong(cakeId));
        return (Cake) query.getSingleResult();
    }
}
