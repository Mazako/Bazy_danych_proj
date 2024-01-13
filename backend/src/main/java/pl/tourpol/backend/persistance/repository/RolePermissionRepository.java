package pl.tourpol.backend.persistance.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.tourpol.backend.persistance.entity.RolePermission;

import java.util.List;

public interface RolePermissionRepository extends CrudRepository<RolePermission, Long> {

    @Query("SELECT rp FROM RolePermission rp WHERE rp.role.id = ?1")
    List<RolePermission> getRolePermissionByRoleId(Long roleId);
}