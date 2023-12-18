package pl.tourpol.backend.security.registration;

public record RegistrationDto(String firstName,
                              String lastName,
                              String mail,
                              String password,
                              String phone) {

}
