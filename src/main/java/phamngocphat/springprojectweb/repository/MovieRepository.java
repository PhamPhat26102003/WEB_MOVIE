package phamngocphat.springprojectweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phamngocphat.springprojectweb.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
