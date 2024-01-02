package com.example.backia.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomDataGenerator {

    public static List<StudentData> generateRandomStudentData(int dataSize) {
        List<StudentData> studentDataList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < dataSize; i++) {
            double averageNote = random.nextDouble() * 20; // Moyenne des notes entre 10 et 20
            int acceptedContacts = random.nextInt(20); // Nombre de rendez-vous acceptés entre 0 et 5
            int rejectedContacts = random.nextInt(20); // Nombre de rendez-vous refusés entre 0 et 5
            int pendingContacts = random.nextInt(20); // Nombre de rendez-vous en attente entre 0 et 5
            boolean isIntern = random.nextBoolean(); // Alternant ou non

            studentDataList.add(new StudentData(averageNote, acceptedContacts, rejectedContacts, pendingContacts, isIntern));
        }

        return studentDataList;
    }

    public static void main(String[] args) {
        List<StudentData> randomStudentData = generateRandomStudentData(10);

        // Afficher les données générées
        for (StudentData data : randomStudentData) {
            System.out.println(data);
        }
    }
}
