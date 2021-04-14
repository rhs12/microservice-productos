package com.in28minutes.rest.webservices.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserService {

    private static List<User> users = new ArrayList<>();
    int userCount = 3;

    static {
        users.add(new User(1, "Adam", new Date()));
        users.add(new User(2, "Peter", new Date()));
        users.add(new User(3, "Griffing", new Date()));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++userCount);
        }
        users.add(user);
        return user;
    }

    public User findOne(Integer id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    public User deleteById(int id) {
        User userFound = users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
        if (userFound != null) {
            users.remove(userFound);
        }
        return userFound;
    }
}
