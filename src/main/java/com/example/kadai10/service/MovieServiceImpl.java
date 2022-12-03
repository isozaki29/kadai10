package com.example.kadai10.service;

import com.example.kadai10.entity.Movie;
import com.example.kadai10.exception.ResourceNotFoundException;
import com.example.kadai10.form.MovieForm;
import com.example.kadai10.mapper.MovieMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieMapper movieMapper;

    public MovieServiceImpl(MovieMapper movieMapper) {
        this.movieMapper = movieMapper;
    }

    @Override
    public List<Movie> findAll() {
        return movieMapper.findAll();
    }

    @Override
    public Movie findById(int id) {
        return this.movieMapper.findById(id).orElseThrow(() -> new ResourceNotFoundException("resource not found"));
    }

    @Override
    public void createMovie(MovieForm form) {
        movieMapper.createMovie(form);
    }
}
