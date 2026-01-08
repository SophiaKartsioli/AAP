package AAP_CF8_Project.AAP.domain;

import jakarta.persistence.*;


import java.util.Objects;

@Entity
@Table(name = "POST_LIKES")
public class PostLike {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "user_id",  nullable = false)
    private int userId;

    @Column(name = "post_id",  nullable = false)
    private int postId;

    @OneToOne
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "PostLikes{" +
                "likeId=" + id +
                ", userId=" + userId +
                ", postId=" + postId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PostLike postLikes = (PostLike) o;
        return Objects.equals(id, postLikes.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
