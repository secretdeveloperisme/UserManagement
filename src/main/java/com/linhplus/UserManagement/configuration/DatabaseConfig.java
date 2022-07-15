package com.linhplus.UserManagement.configuration;

import org.springframework.context.annotation.Configuration;
;
import java.util.logging.Logger;

@Configuration
public class DatabaseConfig {
    Logger log = Logger.getLogger(DatabaseConfig.class.getName());
//    @Autowired
//    Environment environment;
//    @Bean
//    Connection getConnection() throws ClassNotFoundException, SQLException {
//        Class.forName(environment.getProperty("spring.datasource.driver-class-name"));
//        Connection connection = DriverManager(environment.getProperty("spring.datasource.url")
////                ,environment.getProperty("spring.datasource.username")
////                .getConnection,environment.getProperty("spring.datasource.password"));
//        return connection;
//    }
}
