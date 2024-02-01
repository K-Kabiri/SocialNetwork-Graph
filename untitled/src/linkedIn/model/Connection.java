package linkedIn.model;

import java.util.Comparator;

public class Connection implements Comparable<Connection> {
    private User user;
    private int value;
    private int level;

    public Connection(User user, int level) {
        this.user = user;
        this.level = level;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public int compareTo(Connection c) {
         if(c.value > this.value)
             return -1;
         else if(c.value == this.value)
             return 0;
         else
             return 1;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
