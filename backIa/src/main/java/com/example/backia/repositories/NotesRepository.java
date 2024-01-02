package com.example.backia.repositories;

import com.example.backia.models.Notes;
import com.example.backia.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Long> {
    List<Notes> findByUser(User user);
    void deleteByUser(User user);
}

