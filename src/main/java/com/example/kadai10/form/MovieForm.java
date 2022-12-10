package com.example.kadai10.form;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class MovieForm {
    private int id;

    @NotBlank
    private String name;

    @NotBlank
    private String director;

    @NotNull
    @Positive
    private int publishedYear;
}
