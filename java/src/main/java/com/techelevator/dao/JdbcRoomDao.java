package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Room;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcRoomDao implements RoomDao{

    JdbcTemplate jdbcTemplate;

    UserDao userDao;

    public JdbcRoomDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Room> getRooms(int userId) {
        final List<Room> rooms = new ArrayList<>();

        final String sql = "SELECT room_id, room_name, is_default, created_by_user_id\n" +
                "\tFROM rooms\n" +
                "\tWHERE is_default = true\n" +
                "\t\tOR created_by_user_id = (SELECT user_id FROM users WHERE user_id = ?)";

        try {

            final SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                rooms.add(mapRowToRoom(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("unable to connect to server or database", e);
        }
        return rooms;
        }
        @Override
        public Room getRoomName(int itemId) {
        Room room = null;
        final String sql = "SELECT r.room_id, r.room_name, r.is_default, r.created_by_user_id \n" +
                "\tFROM rooms AS r\n" +
                "\tJOIN item_rooms AS ir ON r.room_id = ir.room_id\n" +
                "\tWHERE ir.item_id = ?";
        try {

            final SqlRowSet result = jdbcTemplate.queryForRowSet(sql, itemId);
            if (result.next()) {
                room = mapRowToRoom(result);
            }
        }   catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return room;
        }

        Room mapRowToRoom(SqlRowSet rowSet) {
            Room room = new Room();
            room.setRoomId(rowSet.getInt("room_id"));
            room.setRoomName(rowSet.getString("room_name"));
            room.setDefault(rowSet.getBoolean("is_default"));
            room.setCreatedByUserId(rowSet.getInt("created_by_user_id"));
            return room;
        }




}
