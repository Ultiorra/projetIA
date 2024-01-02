package com.example.backia.models;

import jakarta.persistence.*;

@Entity
@Table(name = "utilisateur") // Specify the table name in the database
public class User {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long utilisateurID;
    private String nom;
    private String prenom;
    private String email;

    @Column(name = "MotDePasse")
    private String motDePasse;

    @Column(name = "TypeUtilisateur")
    private String typeUtilisateur;

    public User() {

    }


    public String getEmail() {
        return email;
    }

    public Long getUtilisateurID() {
        return utilisateurID;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTypeUtilisateur() {
        return typeUtilisateur;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTypeUtilisateur(String typeUtilisateur) {
        this.typeUtilisateur = typeUtilisateur;
    }

    public void setUtilisateurID(Long utilisateurID) {
        this.utilisateurID = utilisateurID;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    // Parameterized constructor
    public User(String nom, String prenom, String email, String motDePasse, String typeUtilisateur) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.typeUtilisateur = typeUtilisateur;
    }

    @Override
    public String toString() {
        return "User{" +
                "utilisateurID=" + utilisateurID +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", typeUtilisateur='" + typeUtilisateur + '\'' +
                '}';
    }
}
