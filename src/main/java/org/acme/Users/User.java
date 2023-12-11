package org.acme.Users;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
public class User extends PanacheEntityBase {
    @NotBlank(message = "Username may not be blank")
    public String username;

    @NotBlank(message = "Password may not be blank")
    public String password; // Lưu ý: Trong thực tế, bạn nên lưu trữ mật khẩu đã được mã hóa

    @Id
    @Column(columnDefinition = "BINARY(16)")
    public UUID userId;;



public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}