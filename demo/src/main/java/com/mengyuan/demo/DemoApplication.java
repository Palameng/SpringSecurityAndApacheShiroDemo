package com.mengyuan.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

    /**
     * 在pom文件中加入/取消 spring security对这里的请求进行权限拦截/不拦截
     * @return string
     */
	@RequestMapping("/")
	public String home(){
		return "hello spring root";
	}

    /**
     * 在pom文件中加入/取消 spring security对这里的请求进行权限拦截/不拦截
     * @return string
     */
    @RequestMapping("/hello")
    public String hello(){
        return "hello world";
    }

    /**
     * PreAuthorize 在访问前检查（权限，参数， ...）
     * PostAuthorize 在访问后检查（权限，返回值， ...）
     * PreFilter 对集合类型的参数进行过滤
     * PostFilter 对集合类型的返回值进行过滤
     * @return string
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostAuthorize("hasRole('')")
    @PreFilter("")
    @PostFilter("")
    @RequestMapping("/roleAuth")
    public String role(){
        return "hello admin";
    }


    /**
     * 测试PreAuthorize和PostAuthorize
     * @param id id
     * @param username username
     * @param user user
     * @return id
     */
    @PreAuthorize("#id<10 or principal.username.equals(#username) and #user.username.equals('abc')")
    @PostAuthorize("returnObject%2==0")
    @RequestMapping("/test")
    public Integer test(Integer id, String username, User user){
        return id;
    }


    /**
     * 测试PreFilter和PostFilter，其中filterObject指代集合里的单个对象
     * @param idList id list
     * @return list
     */
    @PreFilter("filterObject%2==0")
    @PostFilter("filterObject%4==0")
    @RequestMapping("/test")
    public List<Integer> test(List<Integer> idList){
        return idList;
    }
}
