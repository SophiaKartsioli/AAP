package AAP_CF8_Project.AAP.domain;

import jakarta.persistence.*;


import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "CATEGORIES")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", length = 30, nullable = false)
    private String categoryName;

    @Column(name = "description_text", length = 600)
    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<ForumThread> forumThreads;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ForumThread> getThreads() {
        return forumThreads;
    }

    public void setThreads(List<ForumThread> forumThreads) {
        this.forumThreads = forumThreads;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                ", threads=" + forumThreads +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(categoryName, category.categoryName) && Objects.equals(description, category.description) && Objects.equals(forumThreads, category.forumThreads);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
