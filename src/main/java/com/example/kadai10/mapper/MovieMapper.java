package com.example.kadai10.mapper;

import com.example.kadai10.entity.Movie;
import com.example.kadai10.form.MovieForm;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MovieMapper {

    @Select("SELECT * from movies")
    List<Movie> findAll();

    @Select("SELECT * from movies WHERE id = #{id}")
    Optional<Movie> findById(int id);

    @Insert("INSERT INTO movies (name, director, published_year) VALUES (#{name},#{director},#{publishedYear});")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createMovie(MovieForm form);
}
