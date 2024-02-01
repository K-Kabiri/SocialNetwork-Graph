package linkedIn.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class User {
    //---------------- Field -------------------------
    private String id;
    private String name;
    private LocalDate dateOfBirth;
    //-------------------
    private String universityLocation;
    private String field;
    private String workplace;
    //-------------------
    private List<String> specialties;
    //-------------------
    private Set<String> connectionId;

    //---------------- Constructor -------------------------

    public User(String id, String name, LocalDate dateOfBirth, String universityLocation, String field, String workplace, List<String> specialties, Set<String> connectionId) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.universityLocation = universityLocation;
        this.field = field;
        this.workplace = workplace;
        this.specialties = specialties;
        this.connectionId = connectionId;
    }


    //---------------- Setter & Getter --------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUniversityLocation() {
        return universityLocation;
    }

    public void setUniversityLocation(String universityLocation) {
        this.universityLocation = universityLocation;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public List<String> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<String> specialties) {
        this.specialties = specialties;
    }

    public Set<String> getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(Set<String> connectionId) {
        this.connectionId = connectionId;
    }


}
