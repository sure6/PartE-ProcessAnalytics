package com.pandemica.parteprocessanalytics.config;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.common.api.delegate.event.FlowableEventListener;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configurable
@PropertySource("classpath:application.yml")
public class FlowableConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String url;

    @Bean
    public ProcessEngine getProcessEngine() {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration().
                setJdbcUrl(url).setEnableDatabaseEventLogging(true).setJdbcDriver(driver).setJdbcUsername(username).setJdbcPassword(password).setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        ProcessEngine processEngine = cfg.buildProcessEngine();
        return processEngine;
    }

}
