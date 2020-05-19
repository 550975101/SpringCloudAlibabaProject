package com.zhx.usercenter.service.user;

import com.zhx.usercenter.dao.user.UserMapper;
import com.zhx.usercenter.domain.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findById(Integer id) {
        //相當於select * from user where id = #{id}
        return userMapper.selectByPrimaryKey(id);
    }
}
