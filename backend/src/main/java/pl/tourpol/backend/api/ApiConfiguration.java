package pl.tourpol.backend.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tourpol.backend.api.contract.ContractService;
import pl.tourpol.backend.api.location.LocationService;
import pl.tourpol.backend.api.notification.NotificationService;
import pl.tourpol.backend.api.opinion.OpinionService;
import pl.tourpol.backend.api.resort.ResortService;
import pl.tourpol.backend.api.room.RoomService;
import pl.tourpol.backend.api.tour.TourService;
import pl.tourpol.backend.persistance.repository.*;
import pl.tourpol.backend.user.UserService;

@Configuration
class ApiConfiguration {

    @Bean
    ResortService resortService(ResortRepository resortRepository, TourRepository tourRepository,
                                LocationService locationService, RoomService roomService) {
        return new ResortService(resortRepository, tourRepository, locationService, roomService);
    }

    @Bean
    OpinionService opinionService(OpinionRepository opinionRepository) {
        return new OpinionService(opinionRepository);
    }

    @Bean
    NotificationService notificationService(NotificationRepository notificationRepository, UserService userService) {
        return new NotificationService(notificationRepository, userService);
    }

    @Bean
    ContractService contractService(ContractRepository contractRepository, RoomContractRepository roomContractRepository,
                                    UserService userService) {
        return new ContractService(contractRepository, roomContractRepository, userService);
    }

    @Bean
    RoomService roomService(RoomRepository roomRepository, ResortRepository resortRepository) {
        return new RoomService(roomRepository, resortRepository);
    }

    @Bean
    LocationService locationService(CityRepository cityRepository, AddressRepository addressRepository) {
        return new LocationService(cityRepository, addressRepository);
    }

    @Bean
    TourService tourService(TourRepository tourRepository, RoomTourRepository roomTourRepository, RoomContractRepository roomContractRepository) {
        return new TourService(tourRepository, roomTourRepository, roomContractRepository);
    }

}
