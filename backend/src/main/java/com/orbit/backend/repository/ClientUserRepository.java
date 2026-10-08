package com.orbit.backend.repository;

import com.orbit.backend.entities.ClientUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientUserRepository extends JpaRepository<ClientUser,Long> {
        Optional<ClientUser> findByEmail(String Email);
        List<ClientUser> findAllByApproved(boolean approved);
}
