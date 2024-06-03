package linkedIn.controller;

import implementation.graph.Vertex;
import linkedIn.model.Connection;
import linkedIn.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class UserPanel {
    //------------------- Fields -------------------
    private Vertex<User> currentUser;
    private ArrayList<Connection> connections;
    private Scanner sc;

    //------------------- Constructor -------------------

    public UserPanel(Vertex<User> currentUser) {
        this.currentUser = currentUser;
        this.connections = new ArrayList<>();
    }

    // ================================ prioritize =================================
    // ===================== for vertices with connection ===========================
   public  ArrayList<Connection> prioritize(ArrayList<Connection> connections)
   {
       for(Connection ptr : connections)
       {
           if(ptr.getUser().getField().compareTo(this.currentUser.getElement().getField())==0)
               ptr.setValue(ptr.getValue()+3);
           for(String strPtr : ptr.getUser().getSpecialties())
           {
               if(this.currentUser.getElement().getSpecialties().contains(strPtr))
                   ptr.setValue(ptr.getValue()+3);
           }
           if(ptr.getUser().getUniversityLocation().compareTo(this.currentUser.getElement().getUniversityLocation())==0)
               ptr.setValue(ptr.getValue()+2);
           if(ptr.getUser().getWorkplace().compareTo(this.currentUser.getElement().getWorkplace())==0)
               ptr.setValue(ptr.getValue()+2);
           for(String strPtr : ptr.getUser().getConnectionId())
           {
               int counter = 0 ;
               if(this.currentUser.getElement().getConnectionId().contains(strPtr))
                   counter++;
               ptr.setValue(((ptr.getValue()+1)*(counter+1)));
           }
           ptr.setValue(ptr.getValue()/ (ptr.getLevel()+1));
       }
       Collections.sort(connections,Connection::compareTo);
       return connections;
   }

}
