package sg.edu.np.mad.madpractical5;

public class User {
    private int id;
    private String name;
    private String description;
    private boolean followed;
    public void setID(int id){ this.id = id; }
    public void setName(String username) { this.name = username; }
    public void setDescription(String description) {this.description = description;}
    public void setFollowed(boolean followed) { this.followed = followed; }
    public int getID() { return id; }
    public String getName() { return name; }
    public String getDescription() {return description;}
    public boolean getFollowed() { return followed; }
    public User(int id, String name, String description, boolean followed){
        this.id = id;
        this.name = name;
        this.description = description;
        this.followed = followed;
    }
}