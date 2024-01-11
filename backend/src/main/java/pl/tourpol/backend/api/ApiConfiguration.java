package pl.tourpol.backend.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tourpol.backend.api.contract.ContractService;
import pl.tourpol.backend.api.notification.NotificationService;
import pl.tourpol.backend.api.opinion.OpinionService;
import pl.tourpol.backend.api.resort.ResortService;
import pl.tourpol.backend.persistance.repository.*;
import pl.tourpol.backend.user.UserService;

@Configuration
class ApiConfiguration {

    @Bean
    ResortService resortService(ResortRepository resortRepository, TourRepository tourRepository,
                                RoomTourRepository roomTourRepository, RoomContractRepository roomContractRepository) {
        return new ResortService(resortRepository, tourRepository,roomTourRepository, roomContractRepository);
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

}
