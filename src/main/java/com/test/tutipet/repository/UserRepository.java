package com.test.tutipet.repository;

import com.test.tutipet.entity.User;
import com.test.tutipet.enums.ObjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Page<User> findByFullNameContainingAndObjectStatus(String fullName, Pageable pageable, ObjectStatus objectStatus);

    Optional<User> findByEmailAndToken(String email, String token);
}
