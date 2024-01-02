package com.example.backia.repositories;

import com.example.backia.models.ContactEntreprise;
import com.example.backia.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactEntrepriseRepository extends JpaRepository<ContactEntreprise, Long> {
    List<ContactEntreprise> findByUser(User user);
    void deleteByUser(User user);


}
