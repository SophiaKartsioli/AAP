package AAP_CF8_Project.AAP.domain;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "THREADS")
public class Thread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "thread_id")
    private int id;

    @Column(name = "category_id", nullable = false)
    private String categoryId;

    @Column(name = "user_id", nullable = false)
    private String authorId;

    @Column(length = 255)
    private String title;

    @Column(name = "content_text", nullable = false)
    private String contentText;


    @Column(name = "created_date")
    private Date createdsDate;


    @Column(name = "edited_date")
    private Date editedDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
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

    public Date getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(Date editedDate) {
        this.editedDate = editedDate;
    }

    @Override
    public String toString() {
        return "Thread{" +
                "id=" + id +
                ", categoryId='" + categoryId + '\'' +
                ", authorId='" + authorId + '\'' +
                ", title='" + title + '\'' +
                ", contentText='" + contentText + '\'' +
                ", createwsDate=" + createdsDate +
                ", editedDate=" + editedDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Thread thread = (Thread) o;
        return Objects.equals(id, thread.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
