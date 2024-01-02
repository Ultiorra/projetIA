package com.example.backia.services;

import com.example.backia.models.ContactEntreprise;
import com.example.backia.models.User;
import com.example.backia.repositories.ContactEntrepriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactEntrepriseService {

    private final ContactEntrepriseRepository contactEntrepriseRepository;

    @Autowired
    public ContactEntrepriseService(ContactEntrepriseRepository contactEntrepriseRepository) {
        this.contactEntrepriseRepository = contactEntrepriseRepository;
    }

    public List<ContactEntreprise> getContactsByUserId(User utilisateurID) {
        return contactEntrepriseRepository.findByUser(utilisateurID);
    }

    public ContactEntreprise saveContactEntreprise(ContactEntreprise contactEntreprise) {
        return contactEntrepriseRepository.save(contactEntreprise);
    }
}
