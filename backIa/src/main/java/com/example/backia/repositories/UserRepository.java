package com.example.backia.repositories;

import com.example.backia.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();

    User findUserByEmail(String email);

    @Transactional
    void deleteByEmail(String email);

}
