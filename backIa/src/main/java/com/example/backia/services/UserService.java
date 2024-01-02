package com.example.backia.services;

import com.example.backia.models.User;
import com.example.backia.repositories.ContactEntrepriseRepository;
import com.example.backia.repositories.NotesRepository;
import com.example.backia.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final NotesRepository notesRepository;
    private final ContactEntrepriseRepository contactEntrepriseRepository;
    @Autowired
    public UserService(UserRepository userRepository,NotesRepository notesRepository,ContactEntrepriseRepository contactEntrepriseRepository) {
        this.userRepository = userRepository;
        this.notesRepository=notesRepository;
        this.contactEntrepriseRepository= contactEntrepriseRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }
    public User getUserByEmail(String email ){
        return userRepository.findUserByEmail(email);
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }
    @Transactional
    public void deleteUserById(Long id)
    {
        Optional<User> usr = getUserById(id);
        if (usr.isPresent()) {
            notesRepository.deleteByUser(usr.get());
            contactEntrepriseRepository.deleteByUser(usr.get());
            userRepository.deleteById(id);
        }
    }
}
