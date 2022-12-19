package com.example.kadai10.mapper;

import com.example.kadai10.entity.Movie;
import com.example.kadai10.form.MovieForm;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MovieMapper {

    @Select("SELECT * FROM movies")
    List<Movie> findAll();

    @Select("SELECT * FROM movies WHERE id = #{id}")
    Optional<Movie> findById(int id);

    @Insert("INSERT INTO movies (name, director, published_year) VALUES (#{name}, #{director}, #{publishedYear})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createMovie(MovieForm form);

    @Update("UPDATE movies SET name = #{name}, director= #{director}, published_year = #{publishedYear} WHERE id = #{id}")
    void updateMovie(MovieForm form);

    @Delete("DELETE FROM movies where id = #{id}")
    void deleteMovie(int id);
}
