package com.example.kadai10.integrationtest;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MovieRestApiIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql(
            scripts = {"classpath:/sqlannotation/delete-movies.sql", "classpath:/sqlannotation/insert-movies.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Transactional
    void 映画情報を全件取得して200レスポンスが返ること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/movies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("[" +
                "   {" +
                "       \"id\": 1," +
                "       \"name\": \"ショーシャンクの空に\"," +
                "       \"director\": \"フランク・ダラボン\"," +
                "       \"publishedYear\": 1994" +
                "   }," +
                "   {" +
                "       \"id\": 2," +
                "       \"name\": \"この世界の片隅に\"," +
                "       \"director\": \"片渕須直\"," +
                "       \"publishedYear\": 2020" +
                "   }," +
                "   {" +
                "       \"id\": 3," +
                "       \"name\": \"アルマゲドン\"," +
                "       \"director\": \"ジェリー・ブラッカイマー\"," +
                "       \"publishedYear\": 1998" +
                "   }" +
                "]", response, JSONCompareMode.STRICT);
    }

    @Test
    @Sql(
            scripts = {"classpath:/sqlannotation/delete-movies.sql", "classpath:/sqlannotation/insert-movies.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Transactional
    void 指定したIDの映画情報を取得して200レスポンスが返ること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/movies/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("{" +
                "       \"id\": 1," +
                "       \"name\": \"ショーシャンクの空に\"," +
                "       \"director\": \"フランク・ダラボン\"," +
                "       \"publishedYear\": 1994" +
                "   }", response, JSONCompareMode.STRICT);
    }

    @Test
    @Sql(
            scripts = {"classpath:/sqlannotation/delete-movies.sql", "classpath:/sqlannotation/insert-movies.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Transactional
    void 存在しない映画情報のIDを指定して取得したときに404レスポンスが返ること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/movies/4"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Sql(
            scripts = {"classpath:/sqlannotation/delete-movies.sql", "classpath:/sqlannotation/insert-movies.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Transactional
    void 映画情報の登録成功し201レスポンスとLocationヘッダに登録したidが返ること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "       \"name\": \"千と千尋の神隠し\"," +
                                "       \"director\": \"宮崎駿\"," +
                                "       \"publishedYear\": 2003" +
                                "   }")
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/movies/4"));
    }

    @Test
    @Sql(
            scripts = {"classpath:/sqlannotation/delete-movies.sql", "classpath:/sqlannotation/insert-movies.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Transactional
    void 指定したIDの映画情報を更新して200レスポンスが返ること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "       \"id\":1," +
                                "       \"name\": \"ショーシャンクの空に\"," +
                                "       \"director\": \"フランク・ダラボン\"," +
                                "       \"publishedYear\": 2000" +
                                "   }"
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Sql(
            scripts = {"classpath:/sqlannotation/delete-movies.sql", "classpath:/sqlannotation/insert-movies.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Transactional
    void 存在しないIDの映画情報を指定して更新したとき404レスポンスが返ること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/movies/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "       \"id\":4," +
                                "       \"name\": \"千と千尋の神隠し\"," +
                                "       \"director\": \"宮崎駿\"," +
                                "       \"publishedYear\": 2003" +
                                "   }"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Sql(
            scripts = {"classpath:/sqlannotation/delete-movies.sql", "classpath:/sqlannotation/insert-movies.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Transactional
    void 指定したIDの映画情報を削除して200レスポンスが返ること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/movies/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Sql(
            scripts = {"classpath:/sqlannotation/delete-movies.sql", "classpath:/sqlannotation/insert-movies.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Transactional
    void 存在しないIDの映画情報を指定して削除したときに404レスポンスが返ること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/movies/4"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
