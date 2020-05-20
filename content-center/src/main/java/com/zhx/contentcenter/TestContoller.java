package com.zhx.contentcenter;

import com.zhx.contentcenter.dao.content.ShareMapper;
import com.zhx.contentcenter.domain.entity.content.Share;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 测试mybatis是否好使
 */
@RestController
public class TestContoller {

    @Autowired
    private ShareMapper shareMapper;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/test")
    public List<Share> testInsert() {
        //1、做插入
        Share share = new Share();
        share.setCreateTime(new Date());
        share.setUpdateTime(new Date());
        share.setTitle("xxx");
        share.setCover("xxx");
        share.setAuthor("大目");
        share.setBuyCount(1);
        this.shareMapper.insertSelective(share);
        //2、做查询
        List<Share> shares = this.shareMapper.selectAll();
        return shares;
    }

    /**
     * 测试 服务发现 证明内容中心总能找到用户中心
     * @return
     */
    @GetMapping("/test2")
    public List<ServiceInstance> discoveryClient() {
        //查詢指定服務的所有实例的信息
        //通用组件 不管是zookeeper 还是euraka 还是nacous  都可以使用这个接口  是springCloud提供
        return this.discoveryClient.getInstances("user-center");

    }
}
