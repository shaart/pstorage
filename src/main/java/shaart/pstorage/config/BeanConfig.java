package shaart.pstorage.config;

import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shaart.pstorage.util.ExceptionUtil;
import shaart.pstorage.util.OperationUtil;

@Configuration
public class BeanConfig {

  @Bean
  public SpringLiquibase liquibase(DataSource dataSource) {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setChangeLog("classpath:db/liquibase-changelog.xml");
    liquibase.setDataSource(dataSource);
    return liquibase;
  }

  @Bean
  public OperationUtil operationUtil() {
    return OperationUtil.getInstance();
  }

  @Bean
  public ExceptionUtil exceptionUtil() {
    return ExceptionUtil.getInstance();
  }
}
