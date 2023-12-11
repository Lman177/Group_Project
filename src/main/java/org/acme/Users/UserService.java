package org.acme.Users;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.JWT.UserRepository;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserService implements PanacheRepository<User> {

    @Transactional
    public void createUser(User user) {
        // Tự động tạo UUID mới
        user.userId = UUID.randomUUID();

        // Lưu thông tin người dùng vào cơ sở dữ liệu
        persist(user);
    }

    public Optional<User> findUserByUsername(String username) {
        return find("username", username).firstResultOptional();
    }
    // This method should perform the validation logic

    @Inject
    UserRepository userRepository;

    public boolean isValidUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && user.password.equals(password); // In a real application, compare hashed passwords
    }

}

