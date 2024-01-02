package com.example.backia.controllers;

import com.example.backia.models.*;
import com.example.backia.services.ContactEntrepriseService;
import com.example.backia.services.NotesService;
import com.example.backia.services.UserService;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final NotesService notesService;
    private double[] regressionCoefficients;
    private final UserService userService;
    private final ContactEntrepriseService contactEntrepriseService;
    @Autowired
    public ApiController(UserService userService,ContactEntrepriseService contactEntrepriseService,NotesService notesService) {
        this.contactEntrepriseService = contactEntrepriseService;
        this.userService = userService;
        this.notesService = notesService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/contacts/{utilisateurID}")
    public List<ContactEntreprise> getContactsByUserId(@PathVariable Long utilisateurID) {
        Optional<User> user = userService.getUserById(utilisateurID);
        if (user.isPresent())
            return contactEntrepriseService.getContactsByUserId(user.get());
        else
            return new ArrayList<>();
    }

    @GetMapping("/notes/{utilisateurID}")
    public List<Notes> getNotesByUserId(@PathVariable Long utilisateurID) {
        Optional<User> userOptional = userService.getUserById(utilisateurID);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return notesService.getNotesByUserId(user);
        }
        else {
            return new ArrayList<>();
        }
    }
    @GetMapping("/predictAlternance/{utilisateurID}")
    public ResponseEntity<?> predictAlternanceEligibility(@PathVariable Long utilisateurID) {
        Optional<User> userOptional = userService.getUserById(utilisateurID);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Notes> notesList = notesService.getNotesByUserId(user);
            List<ContactEntreprise> contactsList = contactEntrepriseService.getContactsByUserId(user);

            boolean isEligible = PredictionModel.predictAlternanceEligibility(user, notesList, contactsList);
            return ResponseEntity.ok(isEligible);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User n t found for ID: " + utilisateurID);
            // Alternatively, you can throw a specific exception or return a custom error response.
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        User user = userService.getUserByEmail(email);

        if (user != null && user.getMotDePasse().equals(password)) {
            // User authenticated successfully
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("user", user);
            return ResponseEntity.ok().body(response);
        } else {
            // Invalid credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }
    public void trainLinearRegression() {
        List<StudentData> studentDataList = StudentData.getSampleStudentData();

        double[][] x = new double[studentDataList.size()][4];
        double[] y = new double[studentDataList.size()];

        for (int i = 0; i < studentDataList.size(); i++) {
            StudentData studentData = studentDataList.get(i);
            x[i][0] = studentData.getAverageNote();
            x[i][1] = studentData.getAcceptedAppointments();
            x[i][2] = studentData.getRejectedAppointments();
            x[i][3] = studentData.getPendingAppointments();

            y[i] = studentData.isIntern() ? 1.0 : 0.0;
        }


        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        regression.newSampleData(y, x);

        regressionCoefficients = regression.estimateRegressionParameters();

    }

    @GetMapping("/performLinearRegression/{utilisateurID}")
    public ResponseEntity<?> performLinearRegression(@PathVariable Long utilisateurID) {
        if (regressionCoefficients == null) {
            trainLinearRegression();
        }

        if (regressionCoefficients == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Model not trained");
        }
        Optional<User> userOptional = userService.getUserById(utilisateurID);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Notes> notesList = notesService.getNotesByUserId(user);

            List<ContactEntreprise> contactsList = contactEntrepriseService.getContactsByUserId(user);
            List<ContactEntreprise> acceptedContacts = contactsList.stream()
                    .filter(contact -> "accepted".equals(contact.getStatut())).toList();

            List<ContactEntreprise> rejectedContacts = contactsList.stream()
                    .filter(contact -> "rejected".equals(contact.getStatut())).toList();

            List<ContactEntreprise> pendingContacts = contactsList.stream()
                    .filter(contact -> "pending".equals(contact.getStatut())).toList();

            double prediction = predict(regressionCoefficients, notesList, acceptedContacts.size(), rejectedContacts.size(), pendingContacts.size());


            return ResponseEntity.ok(prediction);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for ID: " + utilisateurID);
        }
    }



    private double predict(double[] coefficients, List<Notes> notesList, int acceptedContacts, int rejectedContacts, int pendingContacts) {
        // Calculez la moyenne des notes
        double averageNote = calculateAverageNote(notesList);

        // Utiliser les coefficients pour faire une prédiction
        double prediction = coefficients[0] + coefficients[1] * averageNote
                + coefficients[2] * acceptedContacts + coefficients[3] * rejectedContacts + coefficients[4] * pendingContacts;


        return prediction;
    }

    private double calculateAverageNote(List<Notes> notesList) {
        if (notesList.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Notes note : notesList) {
            sum += note.getNote();
        }

        return sum / notesList.size();
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/users/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return user != null ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/users/{email}")
    public ResponseEntity<String> deleteUserByEmail(@PathVariable String email) {
        userService.deleteUserByEmail(email);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/usersAdd")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    @PostMapping("/companyAdd")
    public ResponseEntity<?> addCompany(@RequestBody Map<String, String> companyRequest) {
        String companyName = companyRequest.get("nomEntreprise");
        String interviewCountStr = companyRequest.get("nombreEntretiens");
        String interviewDateStr = companyRequest.get("dateContact");
        String status = companyRequest.get("statut");
        String comments = companyRequest.get("commentairesEntreprise");
        String idStudentStr = companyRequest.get("idStudent");
        System.out.println(companyRequest);
        try {
            Long idStudent = Long.parseLong(idStudentStr);
            int interviewCount = Integer.parseInt(interviewCountStr);
            java.sql.Date sqlInterviewDate = java.sql.Date.valueOf(interviewDateStr);


            Optional<User> userOptional = userService.getUserById(idStudent);
            if (userOptional.isPresent()) {
                User user = userOptional.get();


                ContactEntreprise contact = new ContactEntreprise();
                contact.setNomEntreprise(companyName);
                contact.setNombreEntretiens(interviewCount);
                contact.setDateContact(sqlInterviewDate);
                contact.setStatut(status);
                contact.setCommentairesEntreprise(comments);
                contact.setUser(user);

                ContactEntreprise newContact = contactEntrepriseService.saveContactEntreprise(contact);
                return new ResponseEntity<>(newContact, HttpStatus.CREATED);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for ID: " + idStudent);
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid numeric or date format in request");
        }
    }
    @PostMapping("/notesAdd")
    public ResponseEntity<?> addNotes(@RequestBody Map<String, String> companyRequest) {
        String matiere= companyRequest.get("matiere");
        Float note= Float.valueOf(companyRequest.get("note"));
        String idStudentStr = companyRequest.get("idStudent");
        System.out.println(companyRequest);
        try {
            Long idStudent = Long.parseLong(idStudentStr);


            Optional<User> userOptional = userService.getUserById(idStudent);
            if (userOptional.isPresent()) {
                User user = userOptional.get();


               Notes notes = new Notes();
               notes.setNote(note);
               notes.setMatiere(matiere);
               notes.setUser(user);
               notesService.save(notes);
               return new ResponseEntity<>(notes, HttpStatus.CREATED);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for ID: " + idStudent);
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid numeric or date format in request");
        }
    }
    @DeleteMapping("/usersDelete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }


}
