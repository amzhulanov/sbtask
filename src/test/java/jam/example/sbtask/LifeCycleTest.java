package jam.example.sbtask;

import jam.example.sbtask.config.ConstantSQLTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Тестирование всего жизненного цикла
 *                    - создать новый справочник;
 *                    - изменить поле справочника;
 *                    - добавить несколько записей в справочник
 *                    - выполнить поиск по значению 2-х полей справочника
 *                    - изменить запись в справочнике
 *                    - удалить записи в справочнике
 *                    - удалить справочник
 */
@SpringBootTest
@PropertySource("classpath:test.properties")
@AutoConfigureMockMvc
class LifeCycleTest extends ConstantSQLTest {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mvc;

    @Autowired
    LifeCycleTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeEach
    void setup() {
        jdbcTemplate.execute(dropTable);
    }

    @Test
    void lifeCycleTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .put("/db/person/create")
                .contentType(MediaType.APPLICATION_JSON).content(lc_json_createGuide))
                .andDo(print())
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders
                .post("/db/person/edit_name_field")
                .contentType(MediaType.APPLICATION_JSON).content(lc_json_editNameField))
                .andDo(print())
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders
                .put("/data/person")
                .contentType(MediaType.APPLICATION_JSON).content(lc_json_addData))
                .andDo(print())
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders
                .get("/data/person")
                .contentType(MediaType.APPLICATION_JSON).content(lc_json_find))
                .andDo(print())
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders
                .post("/data/person")
                .contentType(MediaType.APPLICATION_JSON).content(lc_json_updateData))
                .andDo(print())
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders
                .get("/data/person")
                .contentType(MediaType.APPLICATION_JSON).content(lc_json_find))
                .andDo(print())
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders
                .delete("/data/person")
                .contentType(MediaType.APPLICATION_JSON).content(lc_json_delete))
                .andDo(print())
                .andExpect(status().isOk());
        mvc.perform(delete("/db/person"))
                .andExpect(status().isOk());

    }
}
