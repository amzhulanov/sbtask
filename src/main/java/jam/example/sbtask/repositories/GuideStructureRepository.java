package jam.example.sbtask.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий с методами работы со структурой справочника для выполнения запросов в базу данных
 *
 * @author JAM
 */


//todo Добавить обработку ошибок выполнений SQL-запросов
@Repository
@Slf4j
public class GuideStructureRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GuideStructureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Метод выполняет запрос на создание справочника
     *
     * @param sql Текст запроса
     */
    public void createGuide(String sql) {
        executeJdbc(sql);
    }

    /**
     * Метод выполняет запрос для обновления типа полей справочника
     *
     * @param sql Текст запроса
     */
    public void updateTypeFields(String sql) {
        executeJdbc(sql);
    }

    /**
     * Метод выполняет список запросов для обновления имени поля справочника. Одно поле=один запрос
     *
     * @param sqlList Список запросов.
     */
    public void updateNameFields(List<String> sqlList) {
        sqlList.forEach(this::executeJdbc);
    }

    /**
     * Метод для удаления полей из справочника
     *
     * @param sql текст запроса
     */
    public void deleteFields(String sql) {
        executeJdbc(sql);
    }

    /**
     * Метод для добавления полей в справочник
     *
     * @param sql Текст запроса
     */
    public void addFields(String sql) {
        executeJdbc(sql);
    }

    /**
     * Метод выполняет удаление справочника
     *
     * @param sql Текст запроса
     */
    public void dropGuide(String sql) {
        executeJdbc(sql);
    }

    /**
     * Метод для выполнения запросов jdbcTemplate
     *
     * @param sql Текст запроса
     */
    private void executeJdbc(String sql) {
        try {
            jdbcTemplate.execute(sql);
            log.info("jdbcTemplate success. " + sql);
        } catch (Exception e) {
            log.warn(e.toString());
            throw e;
        }
    }

    /**
     * Метод проверяет существование справочника     *
     *
     * @return возвращает имя справочника, если он существует в базе
     * @sql Текст запроса
     */
    public String checkGuide(String sql) {
        try {
            return jdbcTemplate.queryForObject(sql, String.class);
        } catch (EmptyResultDataAccessException e) {
            return "";
        }
    }

}
