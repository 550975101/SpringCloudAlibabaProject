package com.zhx.contentcenter.domain.dto.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
public class UserDTO {


    private Integer id;


    private String wxId;


    private String wxNickname;


    private String roles;


    private String avatarUrl;


    private Date createTime;


    private Date updateTime;

    private Integer bonus;
}
