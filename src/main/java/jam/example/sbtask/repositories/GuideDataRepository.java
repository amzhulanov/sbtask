package jam.example.sbtask.repositories;

import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Репозиторий с методами работы с данными для выполнения запросов в базу данных
 *
 * @author JAM
 */
@Repository
@Slf4j
public class GuideDataRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GuideDataRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Метод для добавления данных
     *
     * @param sql Текст запроса
     */
    public void addRecord(String sql) {
        jdbcTemplate.execute(sql);
    }

    /**
     * Метод для обновления данных
     *
     * @param sql Строка запроса
     * @return Кол-во изменённых записей
     */
    public Integer updateByFields(String sql) {
        return jdbcTemplate.update(sql);
    }

    /**
     * Метод для поиска данных
     *
     * @param sql текст запроса
     * @return Список строк
     */
    public List<Map<String, Object>> find(String sql) {
        return jdbcTemplate.query(sql, new ColumnMapRowMapper());
    }

    /**
     * Метод для удаления записей из справочника
     *
     * @param sql текст запроса
     * @return кол-во удалённых записей
     */
    public Integer deleteRecords(String sql) {
        return jdbcTemplate.update(sql);
    }


}
