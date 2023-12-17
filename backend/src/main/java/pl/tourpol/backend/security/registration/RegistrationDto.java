package pl.tourpol.backend.security.registration;

public record RegistrationDto(String firstName,
                              String lastName,
                              String mail,
                              String hashedPassword,
                              String phone) {

}
