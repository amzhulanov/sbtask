package jam.example.sbtask.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Класс содержит методы подготовки SQL-запросов для работы со структурой справочника и данными
 *
 * @author JAM
 */
@Component
@Slf4j
public class PrepareSQL extends ConstantSQL {
    private final StringBuilder sql = new StringBuilder();

    /**
     * Подготовка запроса для обновления данных в справочнике с условиями поиска
     *
     * @param fieldsSearch Список полей и значений для условий WHERE в sql-запросе
     * @param fieldsGuide  Список полей с новыми значениями
     * @param nameTable    Имя таблицы
     * @return Возвращает готовый sql-запрос
     */
    public String updateData(Map<String, String> fieldsSearch, Map<String, String> fieldsGuide, String nameTable) {
        prepareSQL(nameTable, UPDATE);
        sql.append(SET);
        fieldsGuide.forEach((field, data) -> sql.append(field).append(EQUALLY).append(wrapSQL(data)).append(COMMA));
        if (!fieldsSearch.isEmpty()) {
            sql.replace(sql.length() - 1, sql.length(), WHERE);
            fieldsSearch.forEach((field, data) -> sql.append(field).append(EQUALLY).append(wrapSQL(data)).append(AND));
        }
        replaceSQL(4,SEMICOLON);
        log.info("updateData. "+sql);
        return String.valueOf(sql);
    }

    /**
     * Подготовка запроса для добавления записей в справочник
     * @param fieldList Список полей
     * @param dataList Список значений
     * @param nameTable имя спрачовника
     * @return готовый sql-запрос
     */
    public String addRecords(List<String> fieldList, List<String> dataList, String nameTable) {
        prepareSQL(nameTable, INSERT);
        sql.append(OPENING_BRACKET);
        fieldList.forEach(n -> sql.append(n).append(COMMA));
        replaceSQL(1,CLOSING_BRACKET + VALUES + OPENING_BRACKET);
        dataList.forEach(n -> sql.append(wrapSQL(n)).append(COMMA));
        replaceSQL(1,CLOSING_BRACKET+SEMICOLON);
        log.info("addRecords. "+sql);
        return String.valueOf(sql);
    }

    /**
     * Подготовка запроса для удаления записей из справочника
     * @param fieldsGuide Список условий для описка удаляемых записей
     * @param nameTable Имя справочника
     * @return готовый sql-запрос
     */
    public String deleteRecords(Map<String, String> fieldsGuide, String nameTable) {
        prepareSQL(nameTable, DELETE);
        if (!fieldsGuide.isEmpty()){
            sql.append(SPACE).append(WHERE);
            fieldsGuide.forEach((name,data)->sql.append(name).append(EQUALLY).append(wrapSQL(data)).append(AND));
            replaceSQL(4,SEMICOLON);
        }
        log.info("deleteRecords. "+sql);
        return String.valueOf(sql);
    }

    /**
     * Подготовка sql-запроса для создания справочника
     *
     * @param fieldsGuide Список полей и их типы
     * @param nameTable   Имя таблицы
     * @return Возвращает готовый sql-запрос
     */
    public String sqlCreateGuide(Map<String, String> fieldsGuide, String nameTable) {
        prepareSQL(nameTable, CREATE_GUIDE);
        sql.append(" (");
        fieldsGuide.forEach((name, type) -> sql.append(name).append(" ").append(type).append(","));
        replaceSQL(1,CLOSING_BRACKET + SEMICOLON);
        log.info("sqlCreateGuide. " + sql);
        return String.valueOf(sql);
    }

    /**
     * Подготовка запроса для обновления типа полей справочника
     *
     * @param fields    Список полей с новыми значениями типов
     * @param nameTable Имя таблицы
     * @return Возвращает готовый sql-запрос
     */
    public String updateTypeFields(Map<String, String> fields, String nameTable) {
        prepareSQL(nameTable, ALTER_TABLE);
        fields.forEach((name, type) -> sql.append(UPDATE_TYPE_FIELDS).append(name).append(SPACE).append(TYPE).append(type).append(SPACE).append(USING).append(CLOSING_BRACKET).append(name).append(COLON).append(type).append(CLOSING_BRACKET).append(COMMA));
        replaceSQL(1,SEMICOLON);
        log.info("updateTypeFields. "+sql);
        return String.valueOf(sql);
    }

    /**
     * Подготовка списка запросов для обновления имени поля справочника
     *
     * @param fields    Список полей с новыми наименованиям.
     * @param nameTable Имя таблицы
     * @return Возвращает список sql-запросов
     */
    public List<String> updateNameFields(Map<String, String> fields, String nameTable) {
        List<String> sqlList = new ArrayList<>();
        fields.forEach((name, type) -> {
            prepareSQL(nameTable, ALTER_TABLE);
            sql.append(UPDATE_NAME_FIELDS).append(name).append(SPACE).append(TO).append(type);
            sqlList.add(String.valueOf(sql));
            log.info("updateNameFields. "+sql);
        });
        return sqlList;
    }

    /**
     * Подготовка запроса для удаления столбцов из справочника
     *
     * @param columnsGuide Список столбцов
     * @param nameTable    Имя таблицы
     * @return готовый sql-запрос
     */
    public String deleteFields(List<String> columnsGuide, String nameTable) {
        prepareSQL(nameTable, ALTER_TABLE);
        sql.append(SPACE).append(DROP_COLUMN);
        columnsGuide.forEach(column -> sql.append(DROP_COLUMN).append(column).append(COMMA));
        replaceSQL(1,SEMICOLON);
        log.info("deleteFields. "+sql);
        return String.valueOf(sql);
    }

    /**
     * Подготовка запроса для добавления столбцов в справочник
     *
     * @param fieldsNew Список полей
     * @param nameTable Имя таблицы
     * @return готовый sql-запрос
     */
    public String addFields(Map<String, String> fieldsNew, String nameTable) {
        prepareSQL(nameTable, ALTER_TABLE);
        fieldsNew.forEach((name, type) -> sql.append(ADD_COLUMN).append(name).append(SPACE).append(type).append(COMMA));
        replaceSQL(1,SEMICOLON);
        log.info("addFields."+sql);
        return String.valueOf(sql);
    }

    /**
     * Подготовка запроса для удаления справочника
     *
     * @param nameTable имя таблицы
     * @return готовый sql-запрос
     */
    public String deleteGuide(String nameTable) {
        prepareSQL(nameTable, DROP_TABLE);
        log.info("deleteGuide."+sql);
        return String.valueOf(sql);
    }

    /**
     * Подготовка запроса для поиска записей в справочнике
     * @param datas Map с условиями для поиска обновляемых записей. Опционлаьно
     * @param nameTable Имя таблицы
     * @return готовый sql-запрос
     */
    public String find(Map<String, String> datas, String nameTable) {
        prepareSQL(nameTable, SELECT_ALL);
        if (!datas.isEmpty()) {
            sql.append(WHERE);
            datas.forEach((field, data) -> sql.append(field).append(EQUALLY).append(wrapSQL(data)).append(AND));
            replaceSQL(4,SEMICOLON);
        }
        log.info("find. "+sql);
        return String.valueOf(sql);
    }

    /**
     * Подготовка запроса для проверки существования таблицы в базе
     *
     * @param nameTable Имя таблицы
     * @return готовый sql-запрос
     */
    public String checkGuide(String nameTable) {
        prepareSQL(wrapSQL(nameTable), CHECK_GUIDE);
        log.info("checkGuide. "+sql);
        return String.valueOf(sql);

    }

    /**
     * Метод для предварительной подготовки переменной sql.
     *
     * @param nameTable Имя таблицы
     * @param operation Тип операции в запросе
     */
    private void prepareSQL(String nameTable, String operation) {
        sql.delete(0, sql.length());
        sql.append(operation).append(nameTable).append(" ");
    }

    /**
     * Метод для обертывания значения в одинарные кавычки, для использования в тексте sql-запроса
     *
     * @param item значение, необходимое обернуть в кавычки
     * @return Возвращает значение в кавычках
     */
    private String wrapSQL(String item) {
        return " '" + item + "' ";
    }

    /**
     * Метод для замены последних символов с строке запроса
     * @param n кол-во символов для замены
     * @param result чем заменяем
     */
    private void replaceSQL(Integer n,String result){
        sql.replace(sql.length() - n, sql.length(), result);
    }
}
