package jam.example.sbtask.controllers;

import jam.example.sbtask.config.ConstantSQLTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Slf4j
@PropertySource("classpath:test.properties")
class GuideDataControllerTest extends ConstantSQLTest {

    @Autowired
    private MockMvc mvc;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    GuideDataControllerTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute(dropTable);
        jdbcTemplate.execute(createTable);
        jdbcTemplate.execute(insertFirstRecord);
        jdbcTemplate.execute(insertSecondRecord);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute(dropTable);
    }

    @Test
    void addDataGuide() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .put("/data/person")
                .contentType(MediaType.APPLICATION_JSON).content(json_addData))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateDataGuide() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/data/person")
                .contentType(MediaType.APPLICATION_JSON).content(json_updateData))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void find() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/data/person")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andDo(print())
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders
                .get("/data/person")
                .contentType(MediaType.APPLICATION_JSON).content(json_find))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteGuide() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete("/data/person")
                .contentType(MediaType.APPLICATION_JSON).content(json_delete))
                .andDo(print())
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders
                .delete("/data/person")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}