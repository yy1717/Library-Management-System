public class Record{
    protected String ID;
    
    public Record(String ID){
        this.ID = ID;
    }
    
    public String getID(){
        return ID;
    }
    
    public void displayInfo(){
        System.out.println("Record ID: " + ID);
    }
}
