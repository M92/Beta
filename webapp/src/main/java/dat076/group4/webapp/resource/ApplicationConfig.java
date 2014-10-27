package dat076.group4.webapp.resource;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 * Configuration for JAX-RS resources.
 */
@javax.ws.rs.ApplicationPath("api")
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
        resources.add(dat076.group4.webapp.auth.UserFilter.class);
        resources.add(dat076.group4.webapp.resource.InitDataResource.class);
        resources.add(dat076.group4.webapp.resource.ListCatalogueResource.class);
        resources.add(dat076.group4.webapp.resource.SessionResource.class);
        resources.add(dat076.group4.webapp.resource.UserRegistryResource.class);
    }
}
