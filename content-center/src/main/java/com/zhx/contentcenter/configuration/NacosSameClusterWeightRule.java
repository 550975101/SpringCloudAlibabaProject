package com.zhx.contentcenter.configuration;

import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.alibaba.nacos.NacosDiscoveryProperties;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 封心
 */
@Slf4j
public class NacosSameClusterWeightRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @SneakyThrows
    @Override
    public Server choose(Object o) {
        //拿到配置文件中的集群名称
        String clusterName = nacosDiscoveryProperties.getClusterName();
        BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
        //想要的微服务名字
        String name = loadBalancer.getName();
        //拿到服务发现的相关API
        NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
        //nacos自动通过基于权重的负载均衡算法 给我选择一个健康的实例
        Instance instance = namingService.selectOneHealthyInstance(name);
        log.info("选的实例是:port = {},instance = {} ", instance.getPort(), instance);
        // 找到指定服务的所有实例
        List<Instance> instances = namingService.selectInstances(name, true);
        //过滤出相同集群下的所有实例
        List<Instance> sameCluster = instances.stream().filter(instance1 -> Objects.equals(clusterName, instance1.getClusterName())).collect(Collectors.toList());
        List<Instance> instancesToBeChosen;
        //如果B是空 则用A
        if (CollectionUtils.isEmpty(sameCluster)) {
            instancesToBeChosen = instances;
            log.warn("发生了跨集群调用 name = {} ,cluserName = {}, instances = {}", name, clusterName, instance);
        } else {
            instancesToBeChosen = sameCluster;
        }
        //基于权重的负载均衡算法，返回1个实例
        Instance instanceChosen = ExtendBalancer.getHostByRandomWeight2(instancesToBeChosen);
        log.info("选择的实例是 port={} Instance ={}",instance.getPort(),instance);
        return new NacosServer(instanceChosen);
    }

    static class ExtendBalancer extends Balancer {
        public static Instance getHostByRandomWeight2(List<Instance> hosts) {
            return getHostByRandomWeight(hosts);
        }
    }
}
