package com.zhx.contentcenter.service.content;

import com.zhx.contentcenter.dao.content.ShareMapper;
import com.zhx.contentcenter.domain.dto.content.ShareDTO;
import com.zhx.contentcenter.domain.entity.content.Share;
import com.zhx.contentcenter.domain.dto.user.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShareService {

    @Autowired
    private ShareMapper shareMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;



    public ShareDTO findById(Integer id) {
        //获取分享详情
        Share share = shareMapper.selectByPrimaryKey(id);
        //发布人id
        Integer userId = share.getUserId();
//        RestTemplate restTemplate = new RestTemplate();
        //怎么样调用用户微服务的 users/id
        UserDTO userDTO = restTemplate.getForObject("http://user-center/users/{id}", UserDTO.class, userId);
        //消息的装配
        ShareDTO shareDTO = new ShareDTO();
        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickname(userDTO.getWxNickname());
        return shareDTO;
    }

//    /**
//     * 手写负载均衡
//     * @param id
//     * @return
//     */
//    public ShareDTO findById(Integer id) {
//        //获取分享详情
//        Share share = shareMapper.selectByPrimaryKey(id);
//        //发布人id
//        Integer userId = share.getUserId();
////        RestTemplate restTemplate = new RestTemplate();
//        //怎么样调用用户微服务的 users/id
//        //所有用户中心的请求地址
//        List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
//        List<String> targetURLS = instances.stream()
//                .map(instance -> instance.getUri().toString() + "/users/{id}")
//                .collect(Collectors.toList());
//        int i = ThreadLocalRandom.current().nextInt(targetURLS.size());
//        String url = targetURLS.get(i);
//        log.info("请求的目标地址{}" + url);
//        UserDTO userDTO = restTemplate.getForObject(url, UserDTO.class, userId);
//        //消息的装配
//        ShareDTO shareDTO = new ShareDTO();
//        BeanUtils.copyProperties(share, shareDTO);
//        shareDTO.setWxNickname(userDTO.getWxNickname());
//        return shareDTO;
//    }

//    public ShareDTO findById(Integer id) {
//        //获取分享详情
//        Share share = shareMapper.selectByPrimaryKey(id);
//        //发布人id
//        Integer userId = share.getUserId();
////        RestTemplate restTemplate = new RestTemplate();
//        //怎么样调用用户微服务的 users/id
//        List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
//        String targetURL = instances.stream()
//                .map(instance -> instance.getUri().toString() + "/users/{id}")
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("当前没有实例"));
//        log.info("请求的目标地址{}" + targetURL);
//        UserDTO userDTO = restTemplate.getForObject(targetURL, UserDTO.class, userId);
//        //消息的装配
//        ShareDTO shareDTO = new ShareDTO();
//        BeanUtils.copyProperties(share, shareDTO);
//        shareDTO.setWxNickname(userDTO.getWxNickname());
//        return shareDTO;
//    }


    /**
     * restTemplate版本
     */
//    public ShareDTO findById(Integer id) {
//        //获取分享详情
//        Share share = shareMapper.selectByPrimaryKey(id);
//        //发布人id
//        Integer userId = share.getUserId();
////        RestTemplate restTemplate = new RestTemplate();
//        //怎么样调用用户微服务的 users/id
//        UserDTO userDTO = restTemplate.getForObject("http://localhost:8080/users/{id}", UserDTO.class, userId);
//        //消息的装配
//        ShareDTO shareDTO = new ShareDTO();
//        BeanUtils.copyProperties(share, shareDTO);
//        shareDTO.setWxNickname(userDTO.getWxNickname());
//        return shareDTO;
//    }



    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        //用Http get方法去请求 并且返回一个对象
        //String forObject = restTemplate.getForObject("http://localhost:8080/users/1", String.class);
        //支持rest风格
       //String forObject = restTemplate.getForObject("http://localhost:8080/users/{id}", String.class,1);
        //getForEntity 能获取响应码啥的那些  功能更强 一般情况等价
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:8080/users/{id}", String.class, 1);
        if (forEntity.getStatusCodeValue() == 200) {
            System.out.println(forEntity.getBody());
        }
    }
}