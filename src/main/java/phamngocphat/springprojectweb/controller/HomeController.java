package phamngocphat.springprojectweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import phamngocphat.springprojectweb.model.Movie;
import phamngocphat.springprojectweb.repository.MovieRepository;

import java.util.List;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("")
    public ModelAndView ViewHomePage(){
        List<Movie> lastMovie = movieRepository.findAll(PageRequest.of(0, 4, Sort.by("releaseDate").descending())).toList();
        return new ModelAndView("index")
                .addObject("lastMovie", lastMovie);
    }

    @GetMapping("/films")
    public ModelAndView ListMovies(@PageableDefault(sort = "releaseDate", size = 8,
            direction = Sort.Direction.DESC)Pageable pageable){
        Page<Movie> movies = movieRepository.findAll(pageable);
        return new ModelAndView("movies")
                .addObject("movies", movies);
    }

    @GetMapping("/films/{id}")
    public ModelAndView DisplayDetailMovie(@PathVariable Long id){
        Movie movie = movieRepository.getOne(id);
        return new ModelAndView("movie").addObject("movie", movie);
    }
}
