package pl.tourpol.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.tourpol.backend.persistance.AppUser;
import pl.tourpol.backend.persistance.UserRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        AppUser user = userRepository.findAppUserByMail(mail)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
        return User.builder()
                .username(user.getMail())
                .password(user.getPasswordHash())
                .roles(user.getRole().getRoleName())
                .build();
    }
}
