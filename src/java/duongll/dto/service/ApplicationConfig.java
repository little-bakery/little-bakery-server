/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duongll.dto.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author duong
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(duongll.dto.service.CakeFacadeREST.class);
        resources.add(duongll.dto.service.CakePreparationFacadeREST.class);
        resources.add(duongll.dto.service.CategoryFacadeREST.class);
        resources.add(duongll.dto.service.IngredientFacadeREST.class);
        resources.add(duongll.dto.service.MaterialFacadeREST.class);
        resources.add(duongll.dto.service.SysdiagramsFacadeREST.class);
        resources.add(duongll.dto.service.UserFacadeREST.class);
    }
    
}
