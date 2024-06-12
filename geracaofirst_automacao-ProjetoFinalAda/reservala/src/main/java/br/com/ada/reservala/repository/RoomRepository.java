package br.com.ada.reservala.repository;

import br.com.ada.reservala.domain.Room;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomRepository {

    private final JdbcTemplate jdbcTemplate;

    private String createSQL = "insert into room(roomNumber, type, price, available) values (?, ?, ?, ?)";
    private String readSQL = "select * from room";
    private String readRoomSQL = "select * from room where roomNumber = ?";
    private String updateSQL = "update room SET type=?, price=?, available=? where roomNumber=?";
    private String deleteSQL = "delete from room where roomNumber=?";

    public RoomRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Room createRoom(Room room){
        jdbcTemplate.update(
                createSQL,
                room.getRoomNumber(),
                room.getType(),
                room.getPrice(),
                room.getAvailable()
        );
        return room;
    }

    public List<Room> readRooms(){
        RowMapper<Room> rowMapper = ((rs, rowNum) -> new Room(
                rs.getInt("roomNumber"),
                rs.getString("type"),
                rs.getInt("price"),
                rs.getBoolean("available")
        ));
        return jdbcTemplate.query(readSQL, rowMapper);
    }

    public Optional<Room> readRoom(Integer roomNumber) {
        RowMapper<Room> rowMapper = (rs, rowNum) -> new Room(
                rs.getInt("roomNumber"),
                rs.getString("type"),
                rs.getInt("price"),
                rs.getBoolean("available")
        );
        List<Room> rooms = jdbcTemplate.query(readRoomSQL, rowMapper, roomNumber);
        if (rooms.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(rooms.get(0));
        }
    }

    public Room updateRoom(Room room){

         int result = jdbcTemplate.update(
                updateSQL,
                room.getType(),
                room.getPrice(),
                room.getAvailable(),
                room.getRoomNumber()
        );
         if (result == 1){
            return room;
         }
         return null;
    }

    public void deleteRoom(Integer roomNumber){
        jdbcTemplate.update(deleteSQL, roomNumber);
    }

    public double getOcupation() {
        List<Room> rooms = readRooms();
        double totalRooms = rooms.size();
        double ocupiedRooms = rooms.stream().filter(room -> !room.getAvailable()).count();
        return (double) (ocupiedRooms *100 / totalRooms);
    }

    public Double getRevenue() {
        List<Room> rooms = readRooms();
        return rooms.stream()
                .filter(room -> !room.getAvailable())
                .mapToDouble(Room::getPrice)
                .sum();
    }
}
