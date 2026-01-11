package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.Category;
import AAP_CF8_Project.AAP.repository.CategoryRepository;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }
}
