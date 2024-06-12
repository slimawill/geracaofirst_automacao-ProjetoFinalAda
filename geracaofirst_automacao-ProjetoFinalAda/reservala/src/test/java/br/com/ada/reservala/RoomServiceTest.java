package br.com.ada.reservala;

import br.com.ada.reservala.domain.Room;
import br.com.ada.reservala.repository.RoomRepository;
import br.com.ada.reservala.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setUp() {
        // Configuração inicial para criar o serviço de quarto com o repositório mockado
        roomService = new RoomService(roomRepository);
    }

    @Test
    void testCreateRoom() {
        // Testa a criação de um quarto

        // Cria um quarto arbitrário
        Room room = new Room(101, "Single", 100, true);

        // Mockamos a resposta da camada de repositório
        when(roomRepository.createRoom(room)).thenReturn(room);

        // Execução da função de criação de quarto
        Room createdRoom = roomService.createRoom(room);

        // Verifica se o quarto criado é o mesmo que foi retornado pelo mock
        assertEquals(room, createdRoom);

        // Verifica se a função createRoom foi chamada exatamente uma vez
        verify(roomRepository, times(1)).createRoom(room);
    }

    @Test
    void testCreateRoomWithNull() {
        // Testa a criação de um quarto com valor nulo

        // Criação de um quarto nulo
        Room room = null;

        // Execução da função de criação de quarto
        Room createdRoom = roomService.createRoom(room);

        // Verifica se o resultado é nulo
        assertNull(createdRoom);

        // Verifica se a função createRoom nunca foi chamada
        verify(roomRepository, never()).createRoom(any(Room.class));
    }

    @Test
    void testReadExistentRoom() {
        // Testa a leitura dos quartos

        // Cria quarto arbitrários
        Room room1 = new Room(101, "Single", 100, true);

        // Mockamos a resposta da camada de repositório
        when(roomRepository.readRoom(101)).thenReturn(Optional.of(room1));

        // Execução da função de leitura de quarto
        Room fetchedRooms = roomService.readRoom(101).get();

        // Verifica se o quarto retornado é o esperado
        assertEquals(room1, fetchedRooms);

        // Verifica se a função readRoom foi chamada exatamente uma vez
        verify(roomRepository, times(1)).readRoom(101);
    }


    @Test
    void testReadNonExistentRoom() {
        // Testa a leitura dos quartos

        // Mockamos a resposta da camada de repositório
        when(roomRepository.readRoom(101)).thenReturn(Optional.empty());

        // Verificamos se a execução da função de leitura de quartos gerou uma NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> roomService.readRoom(101).get());

        // Verifica se a função readRoom foi chamada exatamente uma vez
        verify(roomRepository, times(1)).readRoom(101);
    }

    @Test
    void testReadRooms() {
        // Testa a leitura dos quartos

        // Cria dois quartos arbitrários
        Room room1 = new Room(101, "Single", 100, true);
        Room room2 = new Room(102, "Double", 150, true);

        // Cria uma lista que armazena os dois quartos criados acima
        List<Room> rooms = Arrays.asList(room1, room2);

        // Mockamos a resposta da camada de repositório
        when(roomRepository.readRooms()).thenReturn(rooms);

        // Execução da função de leitura de quartos
        List<Room> fetchedRooms = roomService.readRooms();

        // Verifica se a lista de quartos retornada é a esperada
        assertEquals(rooms, fetchedRooms);

        // Verifica se a função readRoom foi chamada exatamente uma vez
        verify(roomRepository, times(1)).readRooms();
    }

    @Test
    void testReadRoomsWhenNoRooms() {
        // Testa a leitura quando não há quartos

        // Mockamos a resposta da camada de repositório
        when(roomRepository.readRooms()).thenReturn(Collections.emptyList());

        // Execução da função de leitura de quartos
        List<Room> fetchedRooms = roomService.readRooms();

        // Verifica se a lista retornada está vazia
        assertTrue(fetchedRooms.isEmpty());

        // Verifica se o método readRoom foi chamado exatamente uma vez
        verify(roomRepository, times(1)).readRooms();
    }

    @Test
    void testUpdateRoom() {
        // Criamos um quarto arbitrário
        Room room = new Room(101, "Single", 120, true);

        // Mockamos a resposta da camada de repositório
        when(roomRepository.updateRoom(any(Room.class))).thenReturn(room);

        // Executamos a função de Update
        Optional<Room> updatedRoom = roomService.updateRoom(101, room);

        //Verificamos se o resultado é o esperado
        assertTrue(updatedRoom.isPresent());
        assertEquals(room, updatedRoom.get());
        verify(roomRepository, times(1)).updateRoom(room);
    }

    @Test
    void testUpdateNonExistentRoom() {
        // Criamos um quarto arbitrário
        Room room = new Room(101, "Single", 120, true);

        // Mockamos a resposta da camada de repositório
        when(roomRepository.updateRoom(any(Room.class))).thenReturn(null);

        // Executamos a função de Update
        Optional<Room> updatedRoom = roomService.updateRoom(102, room);

        //Verificamos se o resultado é o esperado
        assertFalse(updatedRoom.isPresent());
        verify(roomRepository, times(1)).updateRoom(room);
    }


    @Test
    void testDeleteRoom() {
        // Testa a exclusão de um quarto

        // Cria um quarto arbitrário
        Integer roomNumber = 101;

        // Mockamos a resposta da camada de repositório
        doNothing().when(roomRepository).deleteRoom(roomNumber);

        // Execução da função de exclusão de quarto
        roomService.deleteRoom(roomNumber);

        // Verifica se o método deleteRoom foi chamado exatamente uma vez
        verify(roomRepository, times(1)).deleteRoom(roomNumber);
    }

    @Test
    void testDeleteNonExistentRoom() {
        // Testa a exclusão de um quarto com número inexistente

        // Definimos o número do quarto inexistente
        Integer roomNumber = 999;

        // Mockamos a resposta da camada de repositório
        doNothing().when(roomRepository).deleteRoom(roomNumber);

        // Execução da função de exclusão de quarto
        roomService.deleteRoom(roomNumber);

        // Verifica se o método deleteRoom foi chamado exatamente uma vez
        verify(roomRepository, times(1)).deleteRoom(roomNumber);
    }

    @Test
    void testGetOcupation() {
        // Testa o cálculo de ocupação

        // Definimos a ocupação esperada
        Double expectedOcupation = 75.0;

        // Mockamos a resposta da camada de repositório
        when(roomRepository.getOcupation()).thenReturn(expectedOcupation);

        // Execução da função de cálculo de ocupação
        Double ocupation = roomService.getOcupation();

        // Verifica se a ocupação retornada é a esperada
        assertEquals(expectedOcupation, ocupation);

        // Verifica se o método getOcupation foi chamado exatamente uma vez
        verify(roomRepository, times(1)).getOcupation();
    }

    @Test
    void testGetOcupationWhenNoRooms() {
        // Testa o cálculo de ocupação quando não há quartos

        // Mockamos a resposta da camada de repositório
        when(roomRepository.getOcupation()).thenReturn(0.0);

        // Execução da função de cálculo de ocupação
        Double ocupation = roomService.getOcupation();

        // Verifica se a ocupação retornada é zero
        assertEquals(0.0, ocupation);

        // Verifica se o método getOcupation foi chamado exatamente uma vez
        verify(roomRepository, times(1)).getOcupation();
    }

    @Test
    void testGetRevenue() {
        // Testa o cálculo de receita

        // Definimos o faturamento esperado
        Double expectedRevenue = 5000.0;

        // Mockamos a resposta da camada de repositório
        when(roomRepository.getRevenue()).thenReturn(expectedRevenue);

        // Execução da função de cálculo de faturamento
        Double revenue = roomService.getRevenue();

        // Verifica se a receita retornada é a esperada
        assertEquals(expectedRevenue, revenue);

        // Verifica se o método getRevenue foi chamado exatamente uma vez
        verify(roomRepository, times(1)).getRevenue();
    }

    @Test
    void testGetRevenueWhenNoRooms() {
        // Testa o cálculo de receita quando não há quartos

        // Mockamos a resposta da camada de repositório
        when(roomRepository.getRevenue()).thenReturn(0.0);

        // Execução da função de cálculo de receita
        Double revenue = roomService.getRevenue();

        // Verifica se a receita retornada é zero
        assertEquals(0.0, revenue);

        // Verifica se o método getRevenue foi chamado exatamente uma vez
        verify(roomRepository, times(1)).getRevenue();
    }
}
