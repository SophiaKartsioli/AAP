package AAP_CF8_Project.AAP.domain;

import jakarta.persistence.*;


import java.util.Objects;

@Entity
@Table(name = "CATEGORIES")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "category_name", length = 30)
    private String categoryName;

    @Column(name = "description_text", length = 600)
    private String categoryText;


    public int getCategoryId() {
        return id;
    }

    public void setCategoryId(int categoryId) {
        this.id = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryText() {
        return categoryText;
    }

    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "categoryId=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", categoryText='" + categoryText + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Category categorie = (Category) o;
        return Objects.equals(id, categorie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
