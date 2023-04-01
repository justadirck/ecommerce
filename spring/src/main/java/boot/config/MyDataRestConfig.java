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
    MyDataRestConfig(EntityManager entityManager, AppProperties properties) {
        this.properties = properties;
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] unsupportedMethods = { HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH };

        this.disableHttpMethods(Product.class, config, unsupportedMethods);
        this.disableHttpMethods(ProductCategory.class, config, unsupportedMethods);
        this.disableHttpMethods(Country.class, config, unsupportedMethods);
        this.disableHttpMethods(State.class, config, unsupportedMethods);
        this.disableHttpMethods(Order.class, config, unsupportedMethods);

        this.exposeIds(config);

        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(this.properties.getCors());
        
    }

    private void disableHttpMethods(Class<?> cls, RepositoryRestConfiguration config, HttpMethod[] unsupportedMethods) {

        config.getExposureConfiguration()
                .forDomainType(cls)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedMethods))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedMethods));

    }

    private void exposeIds(RepositoryRestConfiguration config) {

        Set<EntityType<?>> entities = this.entityManager.getMetamodel().getEntities();

        List<Class<?>> entityClasses = new ArrayList<>();

        for (EntityType<?> tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        Class<?>[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
