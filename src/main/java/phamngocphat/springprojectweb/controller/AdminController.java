package phamngocphat.springprojectweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import phamngocphat.springprojectweb.repository.MovieRepository;
import phamngocphat.springprojectweb.service.impl.StoreService;
import org.springframework.web.servlet.ModelAndView;
import phamngocphat.springprojectweb.model.Category;
import phamngocphat.springprojectweb.model.Movie;
import phamngocphat.springprojectweb.repository.CategoryRepository;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StoreService storeService;

    @GetMapping("")
    public ModelAndView ViewHomePage(@PageableDefault(sort = "qualification", size = 5)Pageable pageable){
        Page<Movie> movies = movieRepository.findAll(pageable);
        return new ModelAndView("admin/index").addObject("movies", movies);
    }

    @GetMapping("/films/new")
    public ModelAndView DisplayNewMovieForm(){
        List<Category> categories = categoryRepository.findAll(Sort.by("qualification"));
        return new ModelAndView("admin/new-films")
                .addObject("movie", new Movie())
                .addObject("categories", categories);
    }

    @PostMapping("/films/new")
    public ModelAndView AddMovie(@Validated Movie movie, BindingResult bindingResult){
        if(bindingResult.hasErrors() || movie.getFrontPage().isEmpty()){
            if(movie.getFrontPage().isEmpty()){
                bindingResult.rejectValue("frontPage", "MultipartNotEmpty");
            }
            List<Category> categories = categoryRepository.findAll(Sort.by("qualification"));
            return new ModelAndView("admin/new-films")
                    .addObject("movie", movie)
                    .addObject("categories", categories);
        }

        String routeFrontPage = storeService.storeFile(movie.getFrontPage());
        movie.setRouteFrontPage(routeFrontPage);
        movieRepository.save(movie);
        return new ModelAndView("redirect:/admin");
    }

    @GetMapping("/films/{id}/edit")
    public ModelAndView DisplayEditInfoMovie(@PathVariable Long id){
        Movie movie = movieRepository.getOne(id);
        List<Category> categories = categoryRepository.findAll(Sort.by("qualification"));

        return new ModelAndView("admin/edit-films")
                .addObject("movie", movie)
                .addObject("categories", categories);
    }

    @PostMapping("/films/{id}/edit")
    public ModelAndView UpdateInfoMovie(@PathVariable Long id, @Validated Movie movie, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<Category> categories = categoryRepository.findAll(Sort.by("qualification"));
            return new ModelAndView("admin/edit-films")
                    .addObject("movie", movie)
                    .addObject("categories", categories);
        }

        Movie movie1 = movieRepository.getOne(id);
        movie1.setQualification(movie.getQualification());
        movie1.setSynopsis(movie.getSynopsis());
        movie1.setReleaseDate(movie.getReleaseDate());
        movie1.setYoutubeTrailerId(movie.getYoutubeTrailerId());
        movie1.setCategories(movie.getCategories());

        if(!movie.getFrontPage().isEmpty()){
            storeService.deleteFile(movie1.getRouteFrontPage());
            String routerFrontPage = storeService.storeFile(movie.getFrontPage());
            movie1.setRouteFrontPage(routerFrontPage);
        }
        movieRepository.save(movie1);
        return new ModelAndView("redirect:/admin");
    }

    @PostMapping("/films/{id}/delete")
    public String deleteMovie(@PathVariable Long id){
        Movie movie = movieRepository.getById(id);
        movieRepository.delete(movie);
        storeService.deleteFile(movie.getRouteFrontPage());
        return "redirect:/admin";
    }
}
