package linkedIn.controller;

import implementation.graph.AdjacencyMapGraph;
import implementation.graph.Vertex;
import linkedIn.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LinkedInPanel {
    private AdjacencyMapGraph<User, Integer> graph;
    private User currentUser = null;
    private Scanner sc;

    public LinkedInPanel() {
        graph = new AdjacencyMapGraph<>(false);
        sc = new Scanner(System.in).useDelimiter("\n");
    }

    private void start() {
        int command = sc.nextInt();
        while (true) {
            switch (command) {
                case 1 -> {
                    signup();
                    printMainMenu();
                }
                case 2 -> {
                    if (!signIn()) {
                        System.out.println("Invalid ID!");
                        printMainMenu();
                    }
                }
                case 3 -> System.exit(0);
            }
            command = sc.nextInt();
        }
    }

    public static void printMainMenu() {
        System.out.println("""
                >[1] Sign up
                >[2] Sign in
                >[3] Exit
                """);
    }

    //----------------------------------------------------------------------
    private void signup() {
        System.out.println("-> Enter your ID :");
        String id = sc.next();
        if (checkID(id)) {
            System.out.println("Invalid ID!");
            signup();
        } else {
            System.out.println("-> Enter your full name :");
            String name = sc.next();

            System.out.println("-> Enter date of your birthday : (yyyy/MM/dd)");
            String birth = sc.next();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

            System.out.println("-> Enter universityLocation :");
            String universityLocation = sc.next();

            System.out.println("-> Enter field :");
            String field = sc.next();

            System.out.println("-> Enter workplace :");
            String workplace = sc.next();

            System.out.println("-> Enter your specialties :");
            String specialties = sc.next();
            String[] string = specialties.split(" ");
            List<String> list = new ArrayList<>(List.of(string));

            User newUser = new User(id, name, LocalDate.parse(birth, formatter), universityLocation, field, workplace, list, null);
            graph.insertVertex(newUser);

            System.out.println("You Signup Successfully!");
        }
    }

    /*
      This method used for checking the id of new user that must be unique
      or for finding id of a user that want to sign in
      if it doesn't found that id returns null.
     */
    private boolean checkID(String id) {
        for (Vertex<User> vertex : graph.vertices()) {
            if (vertex.getElement().getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
    //----------------------------------------------------------------------

     /*
      This method used for finding user with input id
     */
    private User findUser(String id) {
        for (Vertex<User> vertex : graph.vertices()) {
            if (vertex.getElement().getId().equals(id))
                return vertex.getElement();
        }
        return null;
    }

    private boolean signIn() {
        System.out.println("-> Enter your ID :");
        String id = sc.next();
        if (!checkID(id)) {
            return false;
        }
        new UserPanel(findUser(id));
        return true;
    }

}
