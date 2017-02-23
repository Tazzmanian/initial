package com.example.user;

import com.example.mail.SendMailTLS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private RoleRepository roleRepo;

    public User saveUser(User user) {
        user.setRole(roleRepo.findByName("USER"));
        return repo.save(user);
    }

    public User saveAdmin(User user) {
        user.setRole(roleRepo.findByName("ADMIN"));
        return repo.save(user);
    }

    public User save(User user, String token) {
        if (!user.getEmail().isEmpty()) {
            SendMailTLS.send(user.getEmail(), token);
        }

        user.setEnabled(false);
        user.setRole(roleRepo.findByName("USER"));

        return repo.save(user);
    }

    public Page<UserDTO> getAllUsers(Pageable pageRequest) {
        Page<User> resultPage = repo.findAll(pageRequest);
        return UserMapper.mapEntityPageIntoDTOPage(pageRequest, resultPage);
    }

    public User remove(Long id) throws Exception {
        User user = repo.findByUserId(id); // for some reason findOne doesn't work (user == null)
        if (user == null) {
            throw new Exception("User not found");
        }
        repo.delete(user);
        return user;
    }

    public User update(Long id, User user) throws Exception {
        User dbUser = repo.findByUserId(id);
        if (user == null) {
            throw new Exception("User not found");
        }
        dbUser.setUserName(user.getUsername());
        dbUser.setFirstName(user.getFirstName());
        dbUser.setLastName(user.getLastName());
        dbUser.setBirthDate(user.getBirthDate());
        dbUser.setPhoneNumber(user.getPhoneNumber());
        dbUser.setEmail(user.getEmail());
        return repo.save(dbUser);
    }

    public Page<UserDTO> findByFirstName(String firstName, Pageable pageRequest) {
        Page<User> resultPage = repo.findByFirstNameLike(firstName, pageRequest);
        return UserMapper.mapEntityPageIntoDTOPage(pageRequest, resultPage);
    }

    public Page<User> getAllFlaggedUsers(Pageable pageRequest) {
        return repo.findByEnabledTrue(pageRequest);
    }

    public Page<User> getAllUnflaggedUsers(Pageable pageRequest) {
        return repo.findByEnabledFalse(pageRequest);
    }

    public User changeActive(Long id) {
        User user = repo.findByUserId(id);
        user.setEnabled(!user.isEnabled());
        repo.save(user);
        return user;
    }
}
