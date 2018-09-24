package cn.aezo.springboot.springdatarest.config;

import cn.aezo.springboot.springdatarest.domain.Person;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;

import javax.persistence.Entity;
import java.util.Set;

@Configuration
public class SpringDataRestConfig {

    // 解决Spring Data Rest不暴露ID字段的问题
    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return new RepositoryRestConfigurerAdapter() {
            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
                // 根据实体类名暴露ID
                // config.exposeIdsFor(Person.class, User.class);

                // 通过反射暴露ID
                final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
                provider.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
                final Set<BeanDefinition> beans = provider.findCandidateComponents("cn.aezo.springboot.springdatarest");
                for (final BeanDefinition bean : beans) {
                    try {
                        config.exposeIdsFor(Class.forName(bean.getBeanClassName()));

                        // 可进行其他配置
                        // config.setDefaultMediaType(org.springframework.http.MediaType.APPLICATION_JSON);
                        // config.setDefaultPageSize(2);
                    } catch (final ClassNotFoundException e) {
                        // Can't throw ClassNotFoundException due to the method signature. Need to cast it
                        throw new IllegalStateException("Failed to expose `id` field due to", e);
                    }
                }
            }
        };
    }

    // 给某个资源增加自定链接
    @Bean
    public ResourceProcessor<Resource<Person>> personProcessor() {
        return new ResourceProcessor<Resource<Person>>() {
            @Override
            public Resource<Person> process(Resource<Person> resource) {
                resource.add(new Link("http://blog.aezo.cn", "my_like"));
                return resource;
            }
        };
    }
}
