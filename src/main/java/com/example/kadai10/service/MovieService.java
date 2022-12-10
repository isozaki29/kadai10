package com.example.kadai10.service;

import com.example.kadai10.entity.Movie;
import com.example.kadai10.form.MovieForm;

import java.util.List;

public interface MovieService {
    List<Movie> findAll();

    Movie findById(int id) throws Exception;

    void createMovie(MovieForm form);
}
