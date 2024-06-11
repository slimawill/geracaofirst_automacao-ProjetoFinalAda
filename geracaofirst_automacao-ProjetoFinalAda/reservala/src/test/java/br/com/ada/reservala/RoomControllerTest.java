package br.com.ada.reservala;

import br.com.ada.reservala.controller.RoomController;
import br.com.ada.reservala.domain.Room;
import br.com.ada.reservala.service.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Anotação que indica que estamos testando o RoomController com o MockMvc
@WebMvcTest(RoomController.class)
public class RoomControllerTest {

    // Mock do RoomService para simular o comportamento dos métodos
    @MockBean
    private RoomService roomService;

    // Injeta o RoomController com o mock do RoomService
    @InjectMocks
    private RoomController roomController;

    // MockMvc para simular as requisições HTTP
    private MockMvc mockMvc;

    // ObjectMapper para converter objetos Java em JSON e vice-versa
    private ObjectMapper objectMapper;

    // Método que é executado antes de cada teste
    @BeforeEach
    void setUp() {
        // Inicializa o MockMvc para testar o RoomController
        this.mockMvc = MockMvcBuilders.standaloneSetup(roomController).build();
        // Inicializa o ObjectMapper
        this.objectMapper = new ObjectMapper();
    }

    // Teste para o endpoint de criação de quarto
    @Test
    void testCreateRoom() throws Exception {
        // Carrega o JSON de exemplo para criar um quarto
        File jsonFile = ResourceUtils.getFile("classpath:json/createRoom.json");
        Room room = objectMapper.readValue(jsonFile, Room.class);

        // Configura o mock do RoomService para retornar o quarto criado
        when(roomService.createRoom(any(Room.class))).thenReturn(room);

        // Executa a requisição POST para criar o quarto e verifica a resposta
        mockMvc.perform(post("/room")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value(11))
                .andExpect(jsonPath("$.type").value("Family Suite"))
                .andExpect(jsonPath("$.price").value(300))
                .andExpect(jsonPath("$.available").value(true));
    }

    // Teste para o endpoint de leitura dos quartos
    @Test
    void testReadRoom() throws Exception {
        // Cria uma lista de quartos de exemplo
        List<Room> rooms = Arrays.asList(
                new Room(1, "Casal", 100, true),
                new Room(2, "Solteiro", 100, true),
                new Room(3, "Casal", 200, true),
                new Room(4, "Kitnet", 100, false),
                new Room(5, "Casal", 100, false),
                new Room(6, "Suíte Master", 500, true),
                new Room(7, "Casal", 200, true),
                new Room(8, "Tubarão Shark Temático", 1500, false),
                new Room(9, "Casal", 200, false),
                new Room(10, "Solteiro", 100, true)
        );

        // Configura o mock do RoomService para retornar a lista de quartos
        when(roomService.readRoom()).thenReturn(rooms);

        // Executa a requisição GET para ler os quartos e verifica a resposta
        mockMvc.perform(get("/room"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].number").value(1))
                .andExpect(jsonPath("$[0].type").value("Casal"))
                .andExpect(jsonPath("$[0].price").value(100))
                .andExpect(jsonPath("$[0].available").value(true))
                .andExpect(jsonPath("$[1].number").value(2))
                .andExpect(jsonPath("$[1].type").value("Solteiro"))
                .andExpect(jsonPath("$[1].price").value(100))
                .andExpect(jsonPath("$[1].available").value(true))
                .andExpect(jsonPath("$[2].number").value(3))
                .andExpect(jsonPath("$[2].type").value("Casal"))
                .andExpect(jsonPath("$[2].price").value(200))
                .andExpect(jsonPath("$[2].available").value(true))
                .andExpect(jsonPath("$[3].number").value(4))
                .andExpect(jsonPath("$[3].type").value("Kitnet"))
                .andExpect(jsonPath("$[3].price").value(100))
                .andExpect(jsonPath("$[3].available").value(false))
                .andExpect(jsonPath("$[4].number").value(5))
                .andExpect(jsonPath("$[4].type").value("Casal"))
                .andExpect(jsonPath("$[4].price").value(100))
                .andExpect(jsonPath("$[4].available").value(false))
                .andExpect(jsonPath("$[5].number").value(6))
                .andExpect(jsonPath("$[5].type").value("Suíte Master"))
                .andExpect(jsonPath("$[5].price").value(500))
                .andExpect(jsonPath("$[5].available").value(true))
                .andExpect(jsonPath("$[6].number").value(7))
                .andExpect(jsonPath("$[6].type").value("Casal"))
                .andExpect(jsonPath("$[6].price").value(200))
                .andExpect(jsonPath("$[6].available").value(true))
                .andExpect(jsonPath("$[7].number").value(8))
                .andExpect(jsonPath("$[7].type").value("Tubarão Shark Temático"))
                .andExpect(jsonPath("$[7].price").value(1500))
                .andExpect(jsonPath("$[7].available").value(false))
                .andExpect(jsonPath("$[8].number").value(9))
                .andExpect(jsonPath("$[8].type").value("Casal"))
                .andExpect(jsonPath("$[8].price").value(200))
                .andExpect(jsonPath("$[8].available").value(false))
                .andExpect(jsonPath("$[9].number").value(10))
                .andExpect(jsonPath("$[9].type").value("Solteiro"))
                .andExpect(jsonPath("$[9].price").value(100))
                .andExpect(jsonPath("$[9].available").value(true));
    }

    // Teste para o endpoint de atualização de quarto
    @Test
    void testUpdateRoom() throws Exception {
        // Carrega o JSON de exemplo para atualizar um quarto
        File jsonFile = ResourceUtils.getFile("classpath:json/updateRoom.json");
        Room room = objectMapper.readValue(jsonFile, Room.class);

        // Configura o mock do RoomService para retornar o quarto atualizado
        when(roomService.updateRoom(any(Room.class))).thenReturn(room);

        // Executa a requisição PUT para atualizar o quarto e verifica a resposta
        mockMvc.perform(put("/room")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value(1))
                .andExpect(jsonPath("$.type").value("Updated Casal Room"))
                .andExpect(jsonPath("$.price").value(150))
                .andExpect(jsonPath("$.available").value(false));
    }

    // Teste para o endpoint de exclusão de quarto
    @Test
    void testDeleteRoom() throws Exception {
        // Executa a requisição DELETE para excluir um quarto e verifica a resposta
        mockMvc.perform(delete("/room/1"))
                .andExpect(status().isNoContent());
    }

    // Teste para o endpoint de obtenção da ocupação
    @Test
    void testGetOcupation() throws Exception {
        // Configura o mock do RoomService para retornar a ocupação
        when(roomService.getOcupation()).thenReturn(75.0);

        // Executa a requisição GET para obter a ocupação e verifica a resposta
        mockMvc.perform(get("/room/ocupation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(75.0));
    }

    // Teste para o endpoint de obtenção da receita
    @Test
    void testGetRevenue() throws Exception {
        // Configura o mock do RoomService para retornar a receita
        when(roomService.getRevenue()).thenReturn(1000.0);

        // Executa a requisição GET para obter a receita e verifica a resposta
        mockMvc.perform(get("/room/revenue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1000.0));
    }
}
