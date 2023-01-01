package com.example.kadai10.service;

import com.example.kadai10.entity.Movie;
import com.example.kadai10.exception.NotFoundURLException;
import com.example.kadai10.form.MovieForm;
import com.example.kadai10.mapper.MovieMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {
    @InjectMocks
    MovieServiceImpl movieServiceImpl;

    @Mock
    MovieMapper movieMapper;
    List movieList = List.of(new Movie(1, "ショーシャンクの空に", "フランク・ダラボン", 1994),
            new Movie(2, "この世界の片隅に", "片渕須直", 2020),
            new Movie(3, "アルマゲドン", "ジェリー・ブラッカイマー", 1998));
    Movie movie = new Movie(1, "ショーシャンクの空に", "フランク・ダラボン", 1994);

    @Test
    public void 映画情報を全件取得できること() {
        doReturn(movieList).when(movieMapper).findAll();

        List<Movie> actual = movieServiceImpl.findAll();
        assertThat(actual).isEqualTo(movieList);
    }

    @Test
    public void 指定したIDの映画情報を取得できること() throws Exception {
        doReturn(Optional.of(movie)).when(movieMapper).findById(1);

        Movie actual = movieServiceImpl.findById(1);
        assertThat(actual).isEqualTo(movie);
    }

    @Test
    public void 存在しない映画情報のIDを指定したときにNotFoundURLExceptionが発生すること() throws Exception {
        doReturn(Optional.empty()).when(movieMapper).findById(1);

        assertThatThrownBy(() -> movieServiceImpl.findById(1))
                .isInstanceOf(NotFoundURLException.class);
    }

    @Test
    public void 映画情報を登録できること() {
        doNothing().when(movieMapper).createMovie(any(MovieForm.class));

        movieServiceImpl.createMovie(new MovieForm());
        verify(movieMapper).createMovie(any(MovieForm.class));
    }

    @Test
    public void 指定したIDの映画情報を更新できること() {
        doReturn(Optional.of(movie)).when(movieMapper).findById(1);

        movieServiceImpl.updateMovie(1, new MovieForm());
        verify(movieMapper).updateMovie(any(MovieForm.class));
    }

    @Test
    public void 存在しない映画情報のIDを指定して更新したときにNotFoundURLExceptionが発生すること() {
        doReturn(Optional.empty()).when(movieMapper).findById(1);

        assertThatThrownBy(() -> movieServiceImpl.updateMovie(1, any(MovieForm.class)))
                .isInstanceOf(NotFoundURLException.class);
    }

    @Test
    public void 指定したIDの映画情報を削除できること() {
        doReturn(Optional.of(movie)).when(movieMapper).findById(1);

        movieServiceImpl.deleteMovie(1);
        verify(movieMapper).deleteMovie(1);
    }

    @Test
    public void 存在しない映画情報のIDを指定して削除したときにNotFoundURLExceptionが発生すること() {
        doReturn(Optional.empty()).when(movieMapper).findById(1);

        assertThatThrownBy(() -> movieServiceImpl.deleteMovie(1))
                .isInstanceOf(NotFoundURLException.class);
    }
}
