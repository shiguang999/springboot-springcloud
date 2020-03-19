package com.spring.consumerfeign.controller;

import com.spring.consumerfeign.entity.User;
import com.spring.consumerfeign.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags ="用户信息管理")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @ApiOperation(value ="测试负载均衡")
    @GetMapping(value = "/hi")
    public String sayHi(@RequestParam String name) {
        return userService.sayHiFromClientOne( name );
    }

    @ApiOperation(value ="查询所有用户信息")
    @GetMapping(value = "/userList")
    public List<User> userList() {
        return userService.getUserList();
    }
    @GetMapping(value = "/addUser")
    @ApiOperation(value ="新增用户信息")
    public String addUser(@RequestParam String id,String username){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        return userService.addUser(user);
    }

    @ApiOperation(value ="修改用户信息")
    @GetMapping(value = "/updateUser")
    public String updateUser(@RequestParam String id,String username){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        return userService.updateUser(user);
    }

    @ApiOperation(value ="删除用户信息")
    @GetMapping(value = "/delUser")
    public String delUser(@RequestParam String id){ return userService.delUser(id);}
}
