package cn.sgx.zyqd.datasource;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RoutingDataSourceConfig {
    @Resource(name = "dataSource")
    DataSource dataSource;
    @Resource(name = "dataSource195")
    DataSource dataSource195;
    @Resource(name = "dataSource196")
    DataSource dataSource196;
    @Resource(name = "dataSource197")
    DataSource dataSource197;
    @Resource(name = "dataSource198")
    DataSource dataSource198;

    @Bean(autowire = Autowire.BY_NAME, value = "routingDataSource")
    public AbstractRoutingDataSource createMapperScannerConfigurer() {
        AbstractRoutingDataSource routingDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return DataSourceHolder.getDataSourceType();
            }
        };
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("dataSource195", dataSource195);
        targetDataSources.put("dataSource196", dataSource196);
        targetDataSources.put("dataSource197", dataSource197);
        targetDataSources.put("dataSource198", dataSource198);
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(dataSource);
        return routingDataSource;
    }

}
