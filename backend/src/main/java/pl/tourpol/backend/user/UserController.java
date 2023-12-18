package pl.tourpol.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.tourpol.backend.persistance.entity.AppUser;
import pl.tourpol.backend.security.permissions.AccessSensitiveOperation;

import static pl.tourpol.backend.security.permissions.AccessSensitiveOperationType.APP_USER_ACCESS;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AccessSensitiveOperation accessSensitiveOperation;

    @GetMapping("/delete")
    ResponseEntity<?> deleteUser(@RequestParam Long id) {
        boolean deleted = accessSensitiveOperation.callWithAccessCheck(id, userService::deleteUserById, APP_USER_ACCESS);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/findByName")
    ResponseEntity<AppUser> findUserByName(@RequestParam String firstName) {
        return userService.findUserByName(firstName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
