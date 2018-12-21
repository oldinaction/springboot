package cn.aezo.springboot.oauth2.sso.resource.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class ResController {

    @RequestMapping("/api/read")
    public String read(@RequestParam("id") String id,
                       @RequestParam("access_token") String accessToken, Principal principal) {
        System.out.println("accessToken = " + accessToken + ", principal = " + principal);
        return "read resource: " + id;
    }

    @PostMapping("/api/write/{id}")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // @PreAuthorize("#oauth2.hasScope('write')")
    public String write(@PathVariable("id") String id) {
        return "write resource: " + id;
    }
}
