package com.getdonuts.digibooky.repository;
import com.getdonuts.digibooky.domain.Address;
import com.getdonuts.digibooky.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    Map<String, User> users;

    public UserRepository() {
        users = new ConcurrentHashMap<>();
        User userAdmin = new User("12345", "John", "Wick",
                "test@test.be",
                new Address("Street", "10", "3000", "Leuven"));
        users.put(userAdmin.getId(), userAdmin);
        userAdmin.setAdmin(true);
        System.out.println(userAdmin.getId());
    }

    public User addMember(User user) {
        users.put(user.getId(), user);
        return user;
    }

    public void removeMember(String id) {
        if (users.containsKey(id))
            users.remove(id);
        else
            throw new NullPointerException("This member does not exist.");
    }

    public void updateMember(String id, User user) {
        if (users.containsKey(id))
            users.put(id, user);
        else
            throw new NullPointerException("This member does not exist.");
    }

    public Collection<User> getUsers() {
        return users.values();
    }
}
