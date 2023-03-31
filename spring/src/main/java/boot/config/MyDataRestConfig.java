package boot.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import boot.AppProperties;
import boot.entity.Country;
import boot.entity.Order;
import boot.entity.Product;
import boot.entity.ProductCategory;
import boot.entity.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private AppProperties properties;
    private EntityManager entityManager;

    @Autowired
    MyDataRestConfig(EntityManager theEntityManager, AppProperties properties) {
        this.properties = properties;
        this.entityManager = theEntityManager;
    }


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};

        // disable HTTP methods for ProductCategory: PUT, POST, DELETE and PATCH
        this.disableHttpMethods(Product.class, config, theUnsupportedActions);
        this.disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);
        this.disableHttpMethods(Country.class, config, theUnsupportedActions);
        this.disableHttpMethods(State.class, config, theUnsupportedActions);
        this.disableHttpMethods(Order.class, config, theUnsupportedActions);

        // call an internal helper method
        this.exposeIds(config);

        // configure cors mapping
        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(this.properties.getCors());
    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config) {

        // expose entity ids
        //

        // - get a list of all entity classes from the entity manager
        Set<EntityType<?>> entities = this.entityManager.getMetamodel().getEntities();

        // - create an array of the entity types
        List<Class> entityClasses = new ArrayList<>();

        // - get the entity types for the entities
        for (EntityType tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        // - expose the entity ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}









