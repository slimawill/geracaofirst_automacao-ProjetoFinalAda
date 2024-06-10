package br.com.ada.reservala;

import br.com.ada.reservala.domain.Room;
import br.com.ada.reservala.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = (Logger) LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(RoomRepository roomRepository) {
        return args -> {
            log.info("Preloading " + roomRepository.createRoom(new Room(1, "Casal", 100, true)));
            log.info("Preloading " + roomRepository.createRoom(new Room(2, "Solteiro", 100, true)));
            log.info("Preloading " + roomRepository.createRoom(new Room(3, "Casal", 200, true)));
            log.info("Preloading " + roomRepository.createRoom(new Room(4, "Kitnet", 100, false)));
            log.info("Preloading " + roomRepository.createRoom(new Room(5, "Casal", 100, false)));
            log.info("Preloading " + roomRepository.createRoom(new Room(6, "Suíte Master", 500, true)));
            log.info("Preloading " + roomRepository.createRoom(new Room(7, "Casal", 200, true)));
            log.info("Preloading " + roomRepository.createRoom(new Room(8, "Tubarão Shark Temático", 1500, false)));
            log.info("Preloading " + roomRepository.createRoom(new Room(9, "Casal", 200, false)));
            log.info("Preloading " + roomRepository.createRoom(new Room(10, "Solteiro", 100, true)));

        };
    }
}
