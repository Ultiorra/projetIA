package com.example.backia.models;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "contactentreprise")
public class ContactEntreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contactid")
    private Long contactID;
    @ManyToOne
    @JoinColumn(name = "utilisateurID")
    private User user;
    private Date dateContact;
    private int nombreEntretiens;
    private String statut;
    private String commentairesEntreprise;
    private String nomEntreprise;

    public ContactEntreprise() {

    }


    public Date getDateContact() {
        return dateContact;
    }

    public int getNombreEntretiens() {
        return nombreEntretiens;
    }

    public Long getContactID() {
        return contactID;
    }

    public String getCommentairesEntreprise() {
        return commentairesEntreprise;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public String getStatut() {
        return statut;
    }


    public void setCommentairesEntreprise(String commentairesEntreprise) {
        this.commentairesEntreprise = commentairesEntreprise;
    }

    public void setContactID(Long contactID) {
        this.contactID = contactID;
    }

    public void setDateContact(Date dateContact) {
        this.dateContact = dateContact;
    }

    public void setNombreEntretiens(int nombreEntretiens) {
        this.nombreEntretiens = nombreEntretiens;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ContactEntreprise(User user, Date dateContact, int nombreEntretiens, String statut, String commentairesEntreprise, String nomEntreprise) {
        this.user = user;
        this.dateContact = dateContact;
        this.nombreEntretiens = nombreEntretiens;
        this.statut = statut;
        this.commentairesEntreprise = commentairesEntreprise;
        this.nomEntreprise = nomEntreprise;
    }


}
