package com.example.kadai10.controller;

import com.example.kadai10.entity.Movie;
import com.example.kadai10.exception.NotFoundURLException;
import com.example.kadai10.form.MovieForm;
import com.example.kadai10.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    
    @GetMapping
    public List<Movie> getMovie() {
        return movieService.findAll();
    }

    @GetMapping("{id}")
    public Movie getMovie(@PathVariable("id") int id) throws Exception {
        return movieService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> postMovie(@RequestBody @Validated MovieForm form, UriComponentsBuilder uriBuilder) {
        movieService.createMovie(form);
        URI url = uriBuilder.path("/movies/" + form.getId()).
                build().
                toUri();
        return ResponseEntity.created(url).body(Map.of("message", "映画を登録しました。"));
    }

    @PatchMapping("{id}")
    public ResponseEntity<Map<String, String>> patchMovie(@PathVariable("id") int id,@RequestBody @Validated MovieForm form) {
        movieService.updateMovie(id,form);
        return ResponseEntity.ok(Map.of("message","映画を更新しました。"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, String>> deleteMovie(@PathVariable("id") int id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok(Map.of("message","映画を削除しました。"));
    }

    @ExceptionHandler(value = NotFoundURLException.class)
    public ResponseEntity<Map<String, String>> handleNoResourceFound(
            NotFoundURLException e, HttpServletRequest request) {
        Map<String, String> body = Map.of(
                "timestamp", ZonedDateTime.now().toString(),
                "status", String.valueOf(HttpStatus.NOT_FOUND.value()),
                "error", HttpStatus.NOT_FOUND.getReasonPhrase(),
                "message", e.getMessage(),
                "path", request.getRequestURI());
        return new ResponseEntity(body, HttpStatus.NOT_FOUND);
    }
}
