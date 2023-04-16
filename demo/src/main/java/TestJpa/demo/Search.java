package TestJpa.demo;

public class Search {
    private String firstName;
    public Search(){
        super();
    }
    public Search(String firstName) {
        this.firstName = firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public String toString() {
        return "Search{" +
                "firstName='" + firstName + '\'' +
                '}';
    }


}
