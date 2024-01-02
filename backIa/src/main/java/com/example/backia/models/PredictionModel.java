package com.example.backia.models;

import java.util.List;

public class PredictionModel {

    public static boolean predictAlternanceEligibility(User user, List<Notes> notesList, List<ContactEntreprise> contactsList) {
        // Logique de prédiction ici
        // Utilisez les informations de l'étudiant (user), les notes, les contacts, etc., pour effectuer la prédiction.
        // Retourne true si l'étudiant est susceptible de trouver une alternance, sinon false.

        // Exemple simple de prédiction : Si la moyenne des notes est supérieure à 12 et a au moins 2 contacts avec des entreprises, alors true.
        double moyenneNotes = calculateAverageNote(notesList);
        int nombreContacts = contactsList.size();

        return moyenneNotes > 12 && nombreContacts >= 2;
    }

    private static double calculateAverageNote(List<Notes> notesList) {
        if (notesList.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Notes note : notesList) {
            sum += note.getNote();
        }

        return sum / notesList.size();
    }
}
