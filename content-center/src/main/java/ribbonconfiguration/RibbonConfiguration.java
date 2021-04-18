package ribbonconfiguration;

import com.netflix.loadbalancer.IRule;
import com.zhx.contentcenter.configuration.NacosSameClusterWeightRule;
import com.zhx.contentcenter.configuration.NacosWeightedRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 封心
 */
@Configuration
public class RibbonConfiguration {

    @Bean
    public IRule ribbonRule() {
        return new NacosSameClusterWeightRule();
    }
}
