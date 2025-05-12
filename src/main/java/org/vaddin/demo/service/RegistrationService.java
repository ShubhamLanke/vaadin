package org.vaddin.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.vaddin.demo.model.User;
import org.vaddin.demo.repository.UserRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RegistrationService {

//    private final List<User> users = new ArrayList<>();
//
//    public void save(User user) {
//        users.add(user);
//    }
//
//    public List<User> findAll() {
//        return new ArrayList<>(users);
//    }
//
//    public void deleteById(Integer id){
//        Iterator<User> iterator = users.iterator();
//        while (iterator.hasNext()) {
//            if (iterator.next().getId().equals(id)) {
//                iterator.remove();
//                break;
//            }
//        }
//    }
//
//    public List<User> getAllUsers() {
//        users.add(createUser("Amit Sharma", "amit.sharma@example.com", "password123", "9876543210",
//                "123 MG Road", "Maharashtra", "Mumbai"));
//
////        users.add(createUser("Priya Iyer", "priya.iyer@example.com", "securepass", "9123456780",
////                "56 Anna Nagar", "Tamil Nadu", "Chennai"));
////
////        users.add(createUser("Ravi Verma", "ravi.verma@example.com", "ravi2024", "9988776655",
////                "Plot 9, Sector 15", "Uttar Pradesh", "Lucknow"));
////
////        users.add(createUser("Sneha Kulkarni", "sneha.kulkarni@example.com", "kulkarni@456", "9090909090",
////                "Flat 2B, Koregaon Park", "Maharashtra", "Pune"));
////
////        users.add(createUser("Ankit Jain", "ankit.jain@example.com", "ankitpass", "9345678912",
////                "44 Civil Lines", "Madhya Pradesh", "Bhopal"));
//
//        return users;
//    }
//
//    private static User createUser(String name, String email, String password, String mobile,
//                                   String address, String state, String city) {
//        User user = new User();
//        user.setName(name);
//        user.setEmailId(email);
//        user.setPassword(password);
//        user.setConfirmedPassword(password);
//        user.setMobileNumber(mobile);
//        user.setAddress(address);
//        user.setState(state);
//        user.setCity(city);
//        user.setCreationDate(Instant.now());
//        return user;
//    }

    /**
     * Data JPA Implementation
     */

    private final UserRepository userRepository;

    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

}
