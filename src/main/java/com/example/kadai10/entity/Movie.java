package com.example.kadai10.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Movie {
    
    private int id;
    private String name;
    private String director;
    private int publishedYear;
}
