package com.spring.consumerfeign.service;

import com.spring.consumerfeign.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "provider-user",fallback = HystricService.class)
public interface  UserService {
    @GetMapping("/user/hi")
    String sayHiFromClientOne(@RequestParam(value = "name") String name);
    @GetMapping("/user/userList")
    List<User> getUserList();
    @GetMapping("/user/add")
    String addUser(User user);
    @GetMapping("/user/updateUser")
    String updateUser(User user);
    @GetMapping("/user/delUser")
    String delUser(@RequestParam String id);
}
