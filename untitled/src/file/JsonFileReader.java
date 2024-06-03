package file;
import implementation.graph.Vertex;
import linkedIn.controller.LinkedInPanel;
import linkedIn.model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;


public class JsonFileReader {
    public void readFile()throws Exception{
        Map<String , Vertex<User>> mapId = new HashMap<>();
        JSONParser parser = new JSONParser();
        try {
            JSONArray a = (JSONArray) parser.parse(new FileReader("social-network-eliix13/users.json"));
            for (Object o : a) {

                JSONObject person = (JSONObject) o;

                String id = (String) person.get("id");

                String name = (String) person.get("name");

                String dateOfBirth = (String) person.get("dateOfBirth");

                String universityLocation = (String) person.get("universityLocation");

                String field = (String) person.get("field");

                String workplace = (String) person.get("workplace");
                List<String> list=new ArrayList<>();
                JSONArray specialties = (JSONArray) person.get("specialties");
                for (Object c : specialties) {
                    list.add((String)c);
                }
                Set<String> set=new HashSet<>();
                JSONArray connectionId = (JSONArray) person.get("connectionId");
                for (Object c : connectionId) {
                    set.add((String) c);
                }
                User newUser = new User(id,name, dateOfBirth,universityLocation,field,workplace,list,set);
                mapId.put(id,LinkedInPanel.graph.insertVertex(newUser));
            }
            LinkedInPanel.makeEdges(mapId);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
