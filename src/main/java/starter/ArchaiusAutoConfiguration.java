package starter;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicConfiguration;
import com.netflix.config.FixedDelayPollingScheduler;
import com.netflix.config.PolledConfigurationSource;
import com.netflix.config.sources.JDBCConfigurationSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * @author wangjunxia
 * @date 2018-10-31
 */
@Configuration
@ConditionalOnClass({DataSource.class, EmbeddedDatabaseType.class})
@EnableConfigurationProperties({DataSourceProperties.class,ArchaiusProperties.class})
@Order
@Slf4j
public class ArchaiusAutoConfiguration {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private ArchaiusProperties archaiusProperties;

    private int delayMillis;
    private int initialDelayMillis ;

    private final String KEY_COLUMN_NAME = "key";
    private final String VALUE_COLUMN_NAME = "value";
    private String query = "select * from server_config where micro_server='%s'";

    @Bean
    public DynamicConfiguration dynamicConfiguration() {
        if(null == dataSource){
            return null;
        }

        this.delayMillis = archaiusProperties.getDelayMillis();
        this.initialDelayMillis = archaiusProperties.getInitialDelayMillis();
        this.query = String.format(query, archaiusProperties.getServerName());

        log.debug("Get Hystrix Property of SQL: {}",query);
        PolledConfigurationSource jdbcSource = new JDBCConfigurationSource(dataSource, query, KEY_COLUMN_NAME, VALUE_COLUMN_NAME);
        DynamicConfiguration configuration = new DynamicConfiguration(jdbcSource,
                new FixedDelayPollingScheduler(this.initialDelayMillis, this.delayMillis, false));

        ConfigurationManager.install(configuration);// Scheduler will be activated after installation, and DynamicConfigSource.poll () is updated regularly.
        return configuration;
    }

}
