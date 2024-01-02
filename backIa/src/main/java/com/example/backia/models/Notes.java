package com.example.backia.models;

import jakarta.persistence.*;

@Entity
@Table(name = "notes") // Specify the table name in the database
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteID;

    @ManyToOne
    @JoinColumn(name = "UtilisateurID")
    private User user;
    private String matiere;
    private float note;


    public void setNote(float note) {
        this.note = note;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public void setNoteID(Long noteID) {
        this.noteID = noteID;
    }
    public float getNote() {
        return note;
    }

    public Long getNoteID() {
        return noteID;
    }

    public String getMatiere() {
        return matiere;
    }
    public Notes() {
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Parameterized constructor
    public Notes(User user, String matiere, float note) {
        this.user = user;
        this.matiere = matiere;
        this.note = note;
    }
}
