package linkedIn.controller;

import linkedIn.model.Connection;
import linkedIn.model.User;

import java.util.ArrayList;
import java.util.Scanner;

public class UserPanel {
    //------------------- Fields -------------------
    private User currentUser;
    private ArrayList<Connection> connections;
    private Scanner sc;

    //------------------- Constructor -------------------

    public UserPanel(User currentUser) {
        this.currentUser = currentUser;
        this.connections = new ArrayList<>();
        sc=new Scanner(System.in);
        userManager();
    }

    private void userManager(){
        printUserMenu();
        int command=sc.nextInt();
        while (true){
            switch (command){
                case 1->{
                    //update
                }
                case 2->{
                    //suggestion
                }
                case 3->{
                    LinkedInPanel.printMainMenu();
                }
            }
            printUserMenu();
            command=sc.nextInt();
        }
    }


    private void printUserMenu(){
        System.out.println("""
                    [1] Update information
                    [2] Suggestions
                    [3] Back
                    """);
    }
}
