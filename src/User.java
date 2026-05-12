
public class User extends Record
{
    private String name;
    private String contactInfo;

    public User(String userID, String name, String contactInfo) 
    {
        super(userID);
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public String getUserID() 
    {
        return ID;
    }

    public String getName() 
    {
        return name;
    }

    public String getContactInfo() 
    {
        return contactInfo;
    }

    public void setUserID(String userID) 
    {
        this.ID = userID;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public void setContactInfo(String contactInfo) 
    {
        this.contactInfo = contactInfo;
    }
    
    public void displayInfo(){
        System.out.println("User ID: " + ID + ", Name: " + name + ", Contact: " + contactInfo);
    }
    
    public String toString() 
    {
        return "User ID: " + ID + ", Name: " + name + ", Contact: " + contactInfo;
    }
}
