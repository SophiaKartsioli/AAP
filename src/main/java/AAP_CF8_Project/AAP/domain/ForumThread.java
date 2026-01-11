package AAP_CF8_Project.AAP.domain;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "THREADS")
public class ForumThread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "thread_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @Column(length = 255)
    private String title;

    @Column(name = "content_text", nullable = false)
    private String contentText;

    @Column(name = "created_date")
    private Date createdsDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public Date getCreatedsDate() {
        return createdsDate;
    }

    public void setCreatedsDate(Date createdsDate) {
        this.createdsDate = createdsDate;
    }

    @Override
    public String toString() {
        return "Thread{" +
                "id=" + id +
                ", category=" + category +
                ", author=" + author +
                ", title='" + title + '\'' +
                ", contentText='" + contentText + '\'' +
                ", createdsDate=" + createdsDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ForumThread forumThread = (ForumThread) o;
        return Objects.equals(id, forumThread.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
