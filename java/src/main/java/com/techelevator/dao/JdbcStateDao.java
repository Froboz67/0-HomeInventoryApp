package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.State;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcStateDao implements StateDao {

    private JdbcTemplate jdbcTemplate;

    // use constructor injection to get the instance of JdbcTemplate
    public JdbcStateDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<State> getAll() {

        final String sql = "SELECT state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region\n" +
                "FROM state;";

        final List<State> states = new ArrayList<>();
        try {
            SqlRowSet results = this.jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                final State state = new State(
                        results.getString("state_abbreviation"),
                        results.getString("state_name"),
                        results.getInt("population"),
                        results.getInt("area"),
                        results.getDouble("sales_tax"),
                        results.getString("state_nickname"),
                        results.getString("census_region")
                );
                states.add(state);
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return states;
    }
}
