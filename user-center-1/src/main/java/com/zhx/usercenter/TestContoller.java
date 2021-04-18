package com.zhx.usercenter;

import com.zhx.usercenter.dao.user.UserMapper;
import com.zhx.usercenter.domain.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserMapper userMapper;

    @GetMapping("/test")
    public List<User> testInsert() {
        User user = new User();
        user.setAvatarUrl("xxxxx");
        user.setBonus(100);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        this.userMapper.insertSelective(user);
        List<User> users = this.userMapper.selectAll();
        return users;
    }

}
