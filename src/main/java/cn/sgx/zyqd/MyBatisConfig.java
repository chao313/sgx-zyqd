package cn.sgx.zyqd;

import cn.sgx.zyqd.datasource.RoutingDataSourceConfig;
import com.jolbox.bonecp.BoneCPDataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class MyBatisConfig {

    @Bean
    public SqlSessionFactoryBean generateSqlSession(@Qualifier("routingDataSource") DataSource routingDataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(routingDataSource);//设置数据源

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        sqlSessionFactoryBean.setConfigLocation(
                resolver.getResource("classpath:/mybatis/myBatis-config.xml"));
        try {
            sqlSessionFactoryBean.setMapperLocations(
                    resolver.getResources("classpath:/mybatis/mappers/mssql/**/*.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sqlSessionFactoryBean;
    }

    @Bean
    public MapperScannerConfigurer createMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("cn.sgx.zyqd.mybatis.dao");
        return mapperScannerConfigurer;
    }
}
