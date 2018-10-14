package cn.sgx.zyqd;

import com.jolbox.bonecp.BoneCPDataSource;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig195 {

    @Value(value = "${jdbc195.driver-class-name}")
    private String jdbcDriverClassName;

    @Value(value = "${jdbc195.url}")
    private String jdbcUrl;

    @Value(value = "${jdbc195.userName}")
    private String userName;

    @Value(value = "${jdbc195.password}")
    private String password;


    @Bean(autowire = Autowire.BY_NAME,value = "dataSource195")
    @Primary
    public DataSource generateDataSource(){

        BoneCPDataSource boneCPDataSource = new BoneCPDataSource();
        // 数据库驱动
        boneCPDataSource.setDriverClass(jdbcDriverClassName);
        // 相应驱动的jdbcUrl
        boneCPDataSource.setJdbcUrl(jdbcUrl);
        // 数据库的用户名
        boneCPDataSource.setUsername(userName);
        // 数据库的密码
        boneCPDataSource.setPassword(password);
        // 检查数据库连接池中空闲连接的间隔时间，单位是分，默认值：240，如果要取消则设置为0
        boneCPDataSource.setIdleConnectionTestPeriodInMinutes(60);
        // 连接池中未使用的链接最大存活时间，单位是分，默认值：60，如果要永远存活设置为0
        boneCPDataSource.setIdleMaxAgeInMinutes(30);
        // 每个分区最大的连接数
        boneCPDataSource.setMaxConnectionsPerPartition(100);
        // 每个分区最小的连接数
        boneCPDataSource.setMinConnectionsPerPartition(5);

        return boneCPDataSource;
    }
}
