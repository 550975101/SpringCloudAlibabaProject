package com.zhx.contentcenter.configuration;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;
import ribbonconfiguration.RibbonConfiguration;

/**
 * @author 封心
 */
@RibbonClient(name = "user-center", configuration = RibbonConfiguration.class)
@Configuration
public class UserCenterRibbonConfiguration {
}
