package br.com.ada.reservala.service;

import br.com.ada.reservala.domain.Room;
import br.com.ada.reservala.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    public Room createRoom(Room room){
        return roomRepository.createRoom(room);
    }

    public Optional<Room> readRoom(Integer roomNumber){
        return roomRepository.readRoom(roomNumber);
    }

    public List<Room> readRooms(){
        return roomRepository.readRooms();
    }

    public Optional<Room> updateRoom(Integer roomNumber, Room room){
        room.setRoomNumber(roomNumber);
        Room updatedRoom = roomRepository.updateRoom(room);
        if (updatedRoom != null){
            return Optional.of(updatedRoom);
        }
        return Optional.empty();
    }

    public void deleteRoom(Integer roomNumber){
        roomRepository.deleteRoom(roomNumber);
    }

    //Deve calcular o percentual de quartos ocupados
    public Double getOcupation(){ return roomRepository.getOcupation(); }

    //Deve calcular a receita obtida
    public Double getRevenue(){
        return roomRepository.getRevenue();
    }

}
