//Jason A. Young
import java.io.*;
public class Patients{
    private String name;
    private Patients next;
    private int elvl, opTime,arrival,start,waitTime=-1;
    
    /**
     * Constructor
     */
    public Patients(){      
        name="";//Presets the name name to nothing
        next=null;//Presets the next to null
        waitTime=-1;
    }
    
    /**
     * Constructor
     */
    public Patients(String f){
        name=f;//Sets the Patients name name to f
        next=null;//Presets the next to null
        waitTime=-1;
    }
    
    /**
     * Constructor
     */
    public Patients(int a){
        elvl=a;
        next=null;//Presets the next to null
        waitTime=-1;
    }
    
    /**
     * Constructor
     */
    public Patients(String f,int lvl){
        name=f;
        elvl=lvl;
        OPTime();        
        waitTime=-1;
    }
    
    /**
     * Constructor
     */
    public Patients(String f,int lvl,int arr){
        name=f;
        elvl=lvl;
        OPTime();        
        arrival=arr;
        waitTime=-1;
    }
    
    /**
     * Returns the Emergency level of the patient
     * @return elvl The level of Emergency of the Patients
     */
    public int getLvl(){
        return elvl;
    }
    
    /**
     * Returns the time when the patient should be departing from the hospital after there operations
     * @return opTime+start Departing time of the patient
     */
    public int getDept(){
        return opTime+start;
    }
    
    /**
     * Returns how long the patient's operation should take
     * @return opTime The length of time the operation takes
     */
    public int getWait(){
        return opTime;
    }    
    
    /**
     *Returns how long the patients waited for their operation to begins
     *@return start-arrival The amount of time the patient waited
     */
    public int getWaitTime(){
        return start-arrival;
    }
    
    
    /**
     * Returns the address of the Patient after it
     * @return next The address of the next Patient
     */
    public Patients Next(){
        return next;
    }
    
    /**
     * Returns the name of the patient
     * @return name The Patient's Name
     */
    public String getName(){
        return name;
    }
    
    /**
     * Returns the time that the patient arrived at the Hospital
     * @return arrival The arrival time of the patient
     */
    public int getArrival(){
        return arrival;
    }
    
    /**
     * Returns an string stating the Name of the Patient, the arrival time and emergency level
     * @return info The patients information upon arrival to the hospital
     */
    public String Arrival(){
        String a;
        if((arrival%60)<10)a="0"+(arrival%60);
        else a=""+(arrival%60);
        return name+" "+" Arrived at: "+(((11+(arrival/60))%12)+1)+":"+a+" with an "+elvl+" Emergency";
    }
    
    /**
     * Returns a string stating that the patient is healed from there level of emergency and the time
     * @param x The time of departure
     * @return info The patients information upon departure from the hospital
     */
    public String Departure(int x){
        String a;
        if((x%60)<10)a="0"+(x%60);
        else a=""+(x%60);
        return name+" "+" Departed at: "+(((11+(x/60))%12)+1)+":"+a+", fully healed from an "+elvl+" Emergency";
    }   
    
    /**
     * Sets the time the operation procedure begins and returns a string of the info
     * @param x The time the procedure starts 
     * @return info States the operation for a specific patients is about to begin
     */
    public String Started(int x){
        start=x;        
        String a;
        if((x%60)<10)a="0"+(x%60);
        else a=""+(x%60);
        return "Started Operation on "+name+" "+" at "+(((11+(x/60))%12)+1)+":"+a+ "    LEVEL:"+elvl;
    }  
    
    /**
     * Sets the pointer towards another target
     * @param n A New Patient node address
     */
    public void setNext(Patients n){
        next=n;
    }
    
    /**
     * Sets the name of the patients
     * @param f The new name for the patient node
     */
    public void setName(String f){
        name=f;
    }    
    
    /**
     * Sets the level of the patient's emergency
     * @param lvl The level of the patient's emergency
     */
    public void setLvl(int lvl){
        elvl=lvl;
    }
    
    /**
     * Uses the emergency level to sets the duration of the operation
     */
    public void OPTime(){
        if(elvl==3)opTime=12;
        else if(elvl==2)opTime=20;
        else if(elvl==1)opTime=30;
    }
}