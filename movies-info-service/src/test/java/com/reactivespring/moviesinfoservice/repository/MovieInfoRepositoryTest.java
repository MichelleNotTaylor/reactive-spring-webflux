package com.reactivespring.moviesinfoservice.repository;

import com.reactivespring.moviesinfoservice.domain.MovieInfo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
class MovieInfoRepositoryTest {

    @Autowired
    MovieInfoRepository movieInfoRepository;

    @BeforeEach
    void setUp() {
        var movieInfos = List.of(new MovieInfo(null, "Batman Begins",
                2005, List.of("Christian Bale"), LocalDate.parse("2005-06-15")),
                new MovieInfo(null, "The Dark Knight",
                        2008, List.of("Christian Bale", "Heath Ledger"), LocalDate.parse("2008-07-18")),
                new MovieInfo("abc", "Dark Knight Rises",
                        2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));

        movieInfoRepository.saveAll(movieInfos).blockLast();
    }

    @AfterEach
    void tearDown() {
        movieInfoRepository.deleteAll().block();
    }

    @Test
    void findAll() {
        var moviesInfoFlux = movieInfoRepository.findAll().log();

        StepVerifier.create(moviesInfoFlux)
                .expectNextCount(3)
                .verifyComplete();
    }
    @Test
    void findById() {
        var moviesInfoMono = movieInfoRepository.findById("abc").log();

        StepVerifier.create(moviesInfoMono)
                .assertNext(movieInfo -> {
                    assertEquals("Dark Knight Rises", movieInfo.getName());
                })
                .verifyComplete();
    }

    @Test
    void saveMovieInfo() {
        var movieInfo = new MovieInfo(null, "The Dark Knight",
                2008, List.of("Christian Bale", "Heath Ledger"), LocalDate.parse("2008-07-18"));

        var moviesInfoMono = movieInfoRepository.save(movieInfo).log();

        StepVerifier.create(moviesInfoMono)
                .assertNext(movieInfoData -> {
                    assertNotNull(movieInfoData.getMovieInfoId());
                    assertEquals("Dark Knight Rises", movieInfoData.getName());
                })
                .verifyComplete();
    }

    @Test
    void updateMovieInfo() {
        var moviesData = movieInfoRepository.findById("abc").block();
        moviesData.setYear(2021);
        var moviesInfoMono = movieInfoRepository.save(moviesData).log();

        StepVerifier.create(moviesInfoMono)
                .assertNext(movieInfoData -> {
                    assertEquals(2021, movieInfoData.getYear());
                })
                .verifyComplete();
    }

    @Test
    void deleteMovieInfo() {
        movieInfoRepository.deleteById("abc").block();
        var moviesInfoFlux = movieInfoRepository.findAll().log();

        StepVerifier.create(moviesInfoFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

}