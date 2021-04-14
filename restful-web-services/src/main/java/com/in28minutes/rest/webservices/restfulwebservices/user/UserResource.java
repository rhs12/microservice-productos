package com.in28minutes.rest.webservices.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/users")
    public List<User> retrieveAllUsers() {
        return userService.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        User user = userService.findOne(id);

        if (user == null) {
            throw new UserNotFoundException("id- " + id);
        }

        //HATEOAS
        // Add a link to user
        //        Link link = new Link("http://localhost:8080/spring-security-rest/api/customers/10A");
        //        user.add(link);
        //"all-users", SERVER_PATH + "/users"
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        user.add(link2.withRel("all-users"));
        return user;
    }


    // @GetMapping(path = "/users/{id}")
    //c public User findOne(@PathVariable Integer id) {
//        User user = userService.findOne(id);
//        if (user == null) {
//            throw new UserNotFoundException("id-" + id);
//        }
//        return userService.findOne(id);
//    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        User user = userService.deleteById(id);
        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Object> saveUser(@Valid @RequestBody User user) {
        User userSaved = userService.save(user);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userSaved.getId()).toUri()).build();
    }
}
