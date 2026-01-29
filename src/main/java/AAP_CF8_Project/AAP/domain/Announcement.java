package AAP_CF8_Project.AAP.domain;

import jakarta.persistence.*;

/**
 * This entity represents the announcements of the application.
 *
 * This class is mapped to a database table and stores the title and
 * the message of each announcement where admin is submitting to the application.
 * Announcements can be marked as active or inactive.
 */


@Entity
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Column(length = 2000)
    private String message;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    private boolean isActive = true;

    public Announcement() {}

    public Announcement(String title, String message, Admin admin) {
        this.title = title;
        this.message = message;
        this.admin = admin;
        this.isActive = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}