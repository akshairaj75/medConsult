package com.backend.medconsult.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.medconsult.entity.auth.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
