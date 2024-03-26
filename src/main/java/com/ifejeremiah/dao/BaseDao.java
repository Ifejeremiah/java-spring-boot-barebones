package com.ifejeremiah.dao;


import com.ifejeremiah.model.Page;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BaseDao<T> {

    protected static final String SINGLE_RESULT = "single";
    protected static final String MULTIPLE_RESULT = "list";
    protected static final String RESULT_COUNT = "count";
    protected static final String ID = "id";
    protected static final String PAGE = "page";
    protected static final String PAGE_SIZE = "page_size";
    protected static final String SEARCH_QUERY = "search_query";

    protected JdbcTemplate jdbcTemplate;
    protected SimpleJdbcCall create;
    protected SimpleJdbcCall update;
    protected SimpleJdbcCall delete;
    protected SimpleJdbcCall findById;
    protected SimpleJdbcCall findAllPaginated;

    public long create(T model) throws DataAccessException {
        SqlParameterSource in = new BeanPropertySqlParameterSource(model);
        Map<String, Object> m = this.create.execute(in);
        long id = (Long) m.get(ID);
        return id;
    }

    public void update(T model) throws DataAccessException {
        SqlParameterSource in = new BeanPropertySqlParameterSource(model);
        this.update.execute(in);
    }

    public int updateWithRowCount(T model) throws DataAccessException {
        SqlParameterSource in = new BeanPropertySqlParameterSource(model);
        Map<String, Object> map = this.update.execute(in);
        return (Integer) map.get("rowCount");
    }

    public void delete(Integer id) throws DataAccessException {
        SqlParameterSource in = (new MapSqlParameterSource()).addValue(ID, id);
        this.delete.execute(in);
    }

    public T find(Integer id) throws DataAccessException {
        SqlParameterSource in = (new MapSqlParameterSource()).addValue(ID, id);
        Map<String, Object> m = this.findById.execute(in);
        List<T> result = (List<T>) m.get(SINGLE_RESULT);
        return !result.isEmpty() ? result.get(0) : null;
    }

    public List<T> findAll() throws DataAccessException {
        return this.findAll(0, 0).getContent();
    }

    public Page<T> findAll(Integer pageNum, Integer pageSize) throws DataAccessException {
        SqlParameterSource in = (new MapSqlParameterSource()).addValue(PAGE, pageNum <= 0 ? 1 : pageNum).addValue(PAGE_SIZE, pageSize <= 0 ? 10 : pageSize);
        Map<String, Object> m = this.findAllPaginated.execute(in);
        List<T> content = (List<T>) m.get(MULTIPLE_RESULT);
        List<Long> counts = (List<Long>) m.get(RESULT_COUNT);
        Long count = counts.isEmpty()?0: (Long) counts.get(0);
        return new Page<>(count, content);
    }


    public class RowCountMapper implements RowMapper<Long> {
        public RowCountMapper() {
        }

        public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getLong(1);
        }
    }
}
