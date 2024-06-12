package br.com.ada.reservala.controller;

import br.com.ada.reservala.domain.Room;
import br.com.ada.reservala.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    public RoomController(RoomService roomService){
        this.roomService = roomService;
    }

    RoomService roomService;

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room){
        roomService.createRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    @GetMapping
    public ResponseEntity<List<Room>> readRooms(){
        return ResponseEntity.ok(roomService.readRooms());
    }

    @GetMapping("/{roomNumber}")
    public ResponseEntity<Room> readRoom(@PathVariable Integer roomNumber){
        return roomService.readRoom(roomNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{roomNumber}")
    public ResponseEntity<Room> updateRoom(@PathVariable Integer roomNumber, @RequestBody Room room){
        return roomService.updateRoom(roomNumber, room)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    @RequestMapping("/{roomNumber}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("roomNumber") Integer roomNumber){
        roomService.deleteRoom(roomNumber);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ocupation")
    public ResponseEntity<Double> getOcupation() {
        return ResponseEntity.ok(roomService.getOcupation());
    }

    @GetMapping("/revenue")
    public ResponseEntity<Double> getRevenue(){
        return ResponseEntity.ok(roomService.getRevenue());
    }

}
