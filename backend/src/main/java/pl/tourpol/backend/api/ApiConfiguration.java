package pl.tourpol.backend.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import pl.tourpol.backend.api.contract.ContractService;
import pl.tourpol.backend.api.contract.listener.ContractCreationListener;
import pl.tourpol.backend.api.contract.listener.ContractCreationListenerImpl;
import pl.tourpol.backend.api.location.LocationService;
import pl.tourpol.backend.api.notification.NotificationService;
import pl.tourpol.backend.api.opinion.OpinionService;
import pl.tourpol.backend.api.resort.ResortService;
import pl.tourpol.backend.api.room.RoomService;
import pl.tourpol.backend.api.tour.TourService;
import pl.tourpol.backend.persistance.repository.*;
import pl.tourpol.backend.security.permissions.AccessSensitiveOperation;
import pl.tourpol.backend.user.UserService;

@Configuration
class ApiConfiguration {

    @Value("${appMail:tourpol.aplikacja@gmail.com}")
    private String appMail;

    @Value("${bankAccount}")
    private String bankAccount;

    @Bean
    ResortService resortService(ResortRepository resortRepository, TourRepository tourRepository,
                                LocationService locationService, RoomService roomService, PopularityReportRepository popularityReportRepository) {
        return new ResortService(resortRepository, tourRepository, locationService, roomService, popularityReportRepository);
    }

    @Bean
    OpinionService opinionService(OpinionRepository opinionRepository, ContractRepository contractRepository, AccessSensitiveOperation accessSensitiveOperation) {
        return new OpinionService(opinionRepository, contractRepository, accessSensitiveOperation);
    }

    @Bean
    NotificationService notificationService(NotificationRepository notificationRepository, UserService userService, ContractRepository contractRepository,
                                            AccessSensitiveOperation accessSensitiveOperation) {
        return new NotificationService(notificationRepository, userService, contractRepository, accessSensitiveOperation);
    }

    @Bean
    ContractService contractService(ContractRepository contractRepository, RoomContractRepository roomContractRepository,
                                    UserService userService, TourService tourService, NotificationService notificationService,
                                    ApplicationEventPublisher applicationEventPublisher, AccessSensitiveOperation accessSensitiveOperation) {
        return new ContractService(contractRepository, roomContractRepository, userService, tourService, notificationService, applicationEventPublisher, accessSensitiveOperation);
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
    TourService tourService(TourRepository tourRepository, RoomTourRepository roomTourRepository, RoomContractRepository roomContractRepository,
                            ResortRepository resortRepository, FacilityRepository facilityRepository, RoomService roomService, ContractRepository contractRepository) {
        return new TourService(tourRepository, roomTourRepository, roomContractRepository, resortRepository, facilityRepository, roomService, contractRepository);
    }
    @Bean
    ContractCreationListener contractCreationListener(JavaMailSender javaMailSender) {
        return new ContractCreationListenerImpl(javaMailSender, appMail, bankAccount);
    }

}
