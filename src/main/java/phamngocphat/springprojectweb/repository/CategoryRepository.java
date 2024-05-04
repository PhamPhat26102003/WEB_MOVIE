package phamngocphat.springprojectweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phamngocphat.springprojectweb.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
