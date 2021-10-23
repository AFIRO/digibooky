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
        User userAdmin = new User("12345", "John", "Admin",
                "test@test.be",
                new Address("Street", "10", "3000", "Leuven"));
        User librarian = new User("2354235323", "John", "Librarian",
                "string@test.be",
                new Address("Street", "10", "3000", "Leuven"));
        userAdmin.setAdmin(true);
        librarian.setLibrarian(true);
        users.put(userAdmin.getId(), userAdmin);
        users.put(librarian.getId(), librarian);
        System.out.println("admin for testing: " + userAdmin.getId());
        System.out.println("librarian for testing: " + librarian.getId());
    }

    public User addUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    public void removeUser(String id) {
        if (users.containsKey(id))
            users.remove(id);
        else
            throw new NullPointerException("This member does not exist.");
    }

    public void updateUser(String id, User user) {
        if (users.containsKey(id))
            users.put(id, user);
        else
            throw new NullPointerException("This member does not exist.");
    }

    public Collection<User> getUsers() {
        return users.values();
    }
}
