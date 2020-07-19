/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duongll.dto.service;

import duongll.dto.Cake;
import duongll.dto.CakeDto;
import duongll.dto.CakeResult;
import duongll.dto.Category;
import duongll.dto.Page;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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

    @GET
    @Path("findResult")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CakeResult> findResultForUser(@QueryParam("answerid") List<String> answerId) {
        String param = answerId.stream().collect(Collectors.joining(","));
        List<Object[]> result = em.createNativeQuery("SELECT sum(a.point) as point, c.id as cakeid FROM CakeWeight cw "
                + "join Answers a on cw.answerid = a.id "
                + "join Cake c on c.id = cw.cakeid "
                + "WHERE a.id in (" + param + ")"
                + "group by cw.answerid, c.id ORDER BY point DESC").getResultList();
        List<CakeResult> cakeResultList = new ArrayList<>();
        for (Object[] object : result) {
            CakeResult cakeResult = new CakeResult();
            Cake cake = em.find(Cake.class, object[1]);
            cakeResult.setCake(cake);
            cakeResult.setPoint(Integer.parseInt(object[0] + ""));
            cakeResultList.add(cakeResult);
        }
        return cakeResultList;
    }

    @GET
    @Path("find/paging")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CakeDto> listCakePagination() {
        List<CakeDto> cakeDtoResult = new ArrayList<>();
        List<Object[]> resultQuery = em.createNativeQuery("SELECT c.id, c.name, cate.name, c.categoryid, c.views, c.image "
                + "FROM Cake c, Category cate "
                + "WHERE cate.id = c.categoryid").getResultList();
        for (Object[] objects : resultQuery) {
            CakeDto cakeDto = new CakeDto();
            cakeDto.setId(Long.parseLong(objects[0] + ""));
            cakeDto.setImage(objects[5].toString());
            cakeDto.setViews(Long.parseLong(objects[4] + ""));
            cakeDto.setName(objects[1].toString());
            Category category = new Category();
            category.setId(Long.parseLong(objects[3].toString()));
            category.setName(objects[2].toString());
            cakeDto.setCategoryid(category);
            cakeDtoResult.add(cakeDto);
        }
        return cakeDtoResult;
    }

    @GET
    @Path("find/page")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Page calculatePageRender(@DefaultValue("10") @QueryParam("offset") String offset, @QueryParam("category") String categoryId) {
        Page result = new Page();
        Object resultQuery;
        if (categoryId.isEmpty()) {
            resultQuery = em.createNativeQuery("SELECT (SELECT COUNT(id) as records FROM Cake) / ? as pages")
                    .setParameter(1, Long.parseLong(offset)).getSingleResult();
        } else {
            resultQuery = em.createNativeQuery("SELECT (SELECT COUNT(c.id) as records FROM Cake c WHERE c.categoryid = ?) / ? as pages")
                    .setParameter(2, Long.parseLong(offset))
                    .setParameter(1, Long.parseLong(categoryId))
                    .getSingleResult();
        }
        result.setMaxPageNumber(Long.parseLong(resultQuery.toString()));
        return result;
    }

    @GET
    @Path("search")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CakeDto> listCakeBasedOnCategoryOrName(@QueryParam("categoryId") String categoryId, @QueryParam("name") String cakeName) {
        List<CakeDto> cakeDtoResult = new ArrayList<>();
        Query query = null;
        String sql = "";
        if (!categoryId.isEmpty() && !cakeName.isEmpty()) {
            sql = "SELECT c.id, c.name, cate.name, c.categoryid, c.views, c.image "
                    + "FROM Cake c, Category cate "
                    + "WHERE cate.id = c.categoryid AND c.categoryid = ? AND c.name LIKE ?";
            query = em.createNativeQuery(sql)
                    .setParameter(1, Long.parseLong(categoryId))
                    .setParameter(2, "%" + cakeName + "%");
        } else if (!categoryId.isEmpty() && cakeName.isEmpty()) {
            sql = "SELECT c.id, c.name, cate.name, c.categoryid, c.views, c.image "
                    + "FROM Cake c, Category cate "
                    + "WHERE cate.id = c.categoryid AND c.categoryid = ?";
            query = em.createNativeQuery(sql)
                    .setParameter(1, Long.parseLong(categoryId));
        } else if (categoryId.isEmpty() && !cakeName.isEmpty()) {
            sql = "SELECT c.id, c.name, cate.name, c.categoryid, c.views, c.image "
                    + "FROM Cake c, Category cate "
                    + "WHERE cate.id = c.categoryid AND c.name LIKE ?";
            query = em.createNativeQuery(sql)
                    .setParameter(1, "%" + cakeName + "%");
        }
        List<Object[]> resultQuery = query.getResultList();
        for (Object[] objects : resultQuery) {
            CakeDto cakeDto = new CakeDto();
            cakeDto.setId(Long.parseLong(objects[0] + ""));
            cakeDto.setImage(objects[5].toString());
            cakeDto.setViews(Long.parseLong(objects[4] + ""));
            cakeDto.setName(objects[1].toString());
            Category category = new Category();
            category.setId(Long.parseLong(objects[3].toString()));
            category.setName(objects[2].toString());
            cakeDto.setCategoryid(category);
            cakeDtoResult.add(cakeDto);
        }
        return cakeDtoResult;
    }

}
