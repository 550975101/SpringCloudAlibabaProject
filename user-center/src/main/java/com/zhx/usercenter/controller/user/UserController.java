package com.zhx.usercenter.controller.user;

import com.zhx.usercenter.domain.entity.user.User;
import com.zhx.usercenter.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${server.port}")
    private String port;

    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id) {
        log.info("用户中心{}被调用了", port);
        return userService.findById(id);
    }
}
