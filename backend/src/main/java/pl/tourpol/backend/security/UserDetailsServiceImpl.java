package pl.tourpol.backend.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.tourpol.backend.persistance.entity.AppUser;
import pl.tourpol.backend.persistance.repository.UserRepository;
class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        AppUser user = userRepository.findAppUserByMail(mail)
                .filter(AppUser::isEnabled)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));

        return User.builder()
                .username(user.getMail())
                .password(user.getPasswordHash())
                .roles(user.getRole().getRoleName())
                .build();
    }
}
