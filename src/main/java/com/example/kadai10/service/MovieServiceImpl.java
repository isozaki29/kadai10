package com.example.kadai10.service;

import com.example.kadai10.entity.Movie;
import com.example.kadai10.exception.NotFoundURLException;
import com.example.kadai10.form.MovieForm;
import com.example.kadai10.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieMapper movieMapper;

    @Override
    public List<Movie> findAll() {
        return movieMapper.findAll();
    }

    @Override
    public Movie findById(int id) {
        return this.movieMapper.findById(id).orElseThrow(() -> new NotFoundURLException("resource not found"));
    }

    @Override
    public void createMovie(MovieForm form) {
        movieMapper.createMovie(form);
    }
}
