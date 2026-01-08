package AAP_CF8_Project.AAP.domain;

import jakarta.persistence.*;


import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "POSTS")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "post_id")
    private int id;

    @Column(name = "category_id", nullable = false)
    private int ctegoryId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "content_text", length = 600)
    private String contentText;


    @Column(name = "created_date")
    private Date createdDate;

    @OneToOne
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCtegoryId() {
        return ctegoryId;
    }

    public void setCtegoryId(int ctegoryId) {
        this.ctegoryId = ctegoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", ctegoryId=" + ctegoryId +
                ", userId=" + userId +
                ", contentText='" + contentText + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
