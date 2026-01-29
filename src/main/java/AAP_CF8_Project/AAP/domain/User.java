package AAP_CF8_Project.AAP.domain;

import AAP_CF8_Project.AAP.security.Role;
import jakarta.persistence.*;
import java.util.Objects;
import java.time.LocalDateTime;

/**
 * This entity represents the users of the application.
 *
 *This class is mapped to a database table and stores the name, email, password, bio,
 * the time when the account was created and when it was last logged in, location and website.
 * The user entity is assigned with the default role of USER.
 */

@Entity
@Table (name="USERS")
public class User {

    @Id
    @GeneratedValue  (strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(unique = true, nullable = false, length = 20)
    private String username;

    @Column(unique = true, nullable = false, length = 120)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 100)
    private String passwordHash;

    @Column(name = "bio_text", length = 600)
    private String bioText;


    @Column(name = "created_date")
    private LocalDateTime createdDate;


    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "website" , length = 255)
    private String website;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    public User(){};


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBioText() {
        return bioText;
    }

    public void setBioText(String bioText) {
        this.bioText = bioText;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", bioText='" + bioText + '\'' +
                ", createdDate=" + createdDate +
                ", lastLogin=" + lastLogin +
                ", location='" + location + '\'' +
                ", website='" + website + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

