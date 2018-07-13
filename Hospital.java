//Jason A. Young
import java.io.*;
import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.text.DateFormat;

public class Hospital{
    Patients head=new Patients(0), end=head;
    private int heal, waited,time=180,waitin=0;
    private int one,two,three;
    String []FNAMES={"Jake","Udeme","Ashley","Jen","Sarah","Jason","Jay","Ippo","Luffy","Robin","Olowo","Jordan","Cutty","Forever","Micheal","Chante","Sendo","Javier","Bob","Henry"};
    String []LNAMES={"Young","Monkey","Nico","Flam","Russel","Brown","Ketchum","Kiba","Battle","Rosario","Maginan","Charles","Jackson","Landers","Barns","Green","Harry"};
    ArrayList <String> data=new ArrayList <String>();
    Patients []Doc=new Patients[1];     
    private boolean prior=true,DAILY=false,stop_recording=false;
    
    /**
     * Constructor
     */
    public Hospital(){
        //System.out.println("Welcome to Young's Family Hospital");
    }    
    
    /**
     * Resets/Presets all of the variables
     */
    public void reset(){
        one=two=three=heal=waited=waitin=0;
        data.clear();
        DAILY=false;
        head.setNext(null);
        stop_recording=false;
        for(int x=0;x<Doc.length;x++)Doc[x]=new Patients("DOCTOR");     
        //System.out.println("-----------------------RESET-------------------------");
    }
    
    /**
     * Returns the amount of doctors in the array
     * @return Doc.length The amount of doctors
     */
    public int getDoc(){
        return Doc.length;
    }
    
    /**
     * Returns an arraylist of the printed out data
     * @return data A arraylist of printed out strings 
     */
    public ArrayList<String> getData(){
        return data;
    }
        
    /**
     *Sets shift of the work
     *@param t The length of the work shift in hours
     */
    public void setTime(int t){
        t*=60;
        time=t;
    }
    
    /**
     * Returns the work shift
     * @returns time/60 the length of the work shift
     */
    public int getTime(){
        return time/60;
    }
    
    /**
     * Creates a first and a last name from a group of names and then returns the name
     * @return name The name of the patient
     */
    public String nameMixer(){
        int fname=(int)(Math.random()*FNAMES.length);
        int lname=(int)(Math.random()*LNAMES.length);
        String name=FNAMES[fname]+" "+LNAMES[lname];
        return name;
    }    
    
    /**
     * Treats the patients and remove them from the queue
     */
    public void healPatients(){
        waited=1;
        if(head.Next()!=null)head.setNext(head.Next().Next());
        else {
            waited=0;
            head.setNext(null);
            end=head;
        }        
        heal++;
    }
    
    /**
     * Searches through an array of doctors searching if there are any doctors available
     * @return boolean If a doctor is available or not
     */
    public boolean Available(){
        for(int x=0;x<Doc.length;x++)
            if(Doc[x].Next()==null)
                return true;
        return false;   
    }
    
    /**
     * Adds an patient to one an availabledoctors to be treated
     */
    public void Emergency_Room(Patients previous){
        int x;
        for(x=0;Doc[x].Next()!=null;x++){}
        Doc[x].setNext(previous.Next());
        previous.setNext(previous.Next().Next());
    }
    
    /**
     * Returns the address of the last node in the patient queue
     * @return curr The last patient node address
     */
    public Patients endList(){
        Patients curr=head;
        while(curr.Next()!=null)curr=curr.Next();
        return curr;
    }
    
    /**
     * Simulation of an hospital
     */
    public void daily(){
        reset();                
        for(int x=0;x<=time||head.Next()!=null;x++){
            int a=(int)(Math.random()*60);
            boolean print=false;
            
            for(int y=0;y<Doc.length;y++)
               if(Doc[y].Next()!=null&&Doc[y].Next().getDept()==x){
                  data.add(Doc[y].Next().Departure(x));
                  heal++;
                  Doc[y].setNext(null);
                  print=true;
                    }
                                
            if(a<=5&&!stop_recording){
                Patients abc=new Patients(nameMixer(),3,x);
                if(a==0){
                    one++;
                    abc=new Patients(nameMixer(),1,x);
                }
                else if(a<=2){
                    two++;
                    abc=new Patients(nameMixer(),2,x);
                }
                else three++;
                print=true;
                data.add(abc.Arrival());
                //System.out.println(abc.Arrival(x));
                if(prior)getNextPrior(abc);
                else endList().setNext(abc);
            }
            
            if(Available()&&head.Next()!=null){
                data.add(head.Next().Started(x));
                //System.out.println(head.Next().Started(x));
                waitin+=head.Next().getWaitTime();
                Emergency_Room(head);
                print=true;
            }        
            
            if(print){//spacer
                //System.out.println();
                //data.add("");
            }
            if(x==time)stop_recording=true;
        }
        DAILY=true;
    }
    
    /**
     * Calculate the waiting time for the next patient
     * returns least The amount of time the patient will have to wait before the Doctor will be available
     */
    public int getNearest_End(int y){
        int least=0;
        if(Doc[0].Next()!=null){
            least=Doc[0].Next().getDept();
            for(int x=0;x<Doc.length;x++)if(least>Doc[x].getDept())least=Doc[x].getWait();
            least=y-least;
        }
        Patients curr=head.Next();
        for(least=least;curr!=null;curr=curr.Next())least+=curr.getWait();
        return least;
    }
    
    /**
     * Sorts the patients queue into a sort list with level 1 at the front. Then level 2 and level 3 at the end
     * @param a The patient waiting to be sorted
     */
    public void getNextPrior(Patients a){
        Patients curr=head;
        while(curr.Next()!=null&&a.getLvl()>=curr.Next().getLvl())
            curr=curr.Next();
        a.setNext(curr.Next());
        curr.setNext(a);
    }
    
    /**
     * Changes the rules from priority to non priority
     */
    public void change_Priority(){
        prior=!prior;
    }
    
    /**
     * Caculate how many patients are being serviced
     * @returns ct The amount of patients being serviced
     */
    public int getServicing(){
        int ct=0;
        for(int x=0;x<Doc.length;x++)
            if(Doc[x].Next()!=null)ct++;  
        return ct;
    }
    
    /**
     * Returns a list of the patients still waiting to be serviced
     * @return ar The list of patients still waiting
     */
    public ArrayList<String> printPatientList(){
        ArrayList<String> ar=new ArrayList<String>();
        Patients curr=head.Next();
        int wait=0;
        while(curr!=null){
            ar.add("Name: "+curr.getName());
            ar.add("~Emergency Level: "+curr.getLvl());
            ar.add("~Operation Time: "+curr.getWait());
            ar.add("~Estimated Wait Time: "+wait);
            ar.add("");
            wait+=curr.getWait();
            curr=curr.Next();
        }
        return ar;
    }
    
    /**
     * Returns the amount of people still waiting to be serviced
     * @return count The amount of people still in line
     */
    public int count(){
        Patients curr=head.Next();
        int count=0;
        for(count=0;curr!=null;count++) curr=curr.Next();
        return count;
    }
    
    /**
     * Returns an a daily report of the simulation progress
     * @return ar The daily report
     */
    public ArrayList<String> print_New_Daily_Sheet(){
        if(DAILY){
            ArrayList<String> ar=new ArrayList<String>();
            ar.add("    St. Young's Hospital Data Chart");
            ar.add("                From 12:00PM  ~  "+(time/60)+":00PM");
            ar.add("");
            ar.add("                ~Doctors~");
            ar.add("    Amount of Doctors on Duty:"+Doc.length);
            ar.add("    The average amount of patients treated by the Doctors:"+(TotalP()/Doc.length));
            ar.add("");
            ar.add("                ~Patients~");
            ar.add("    Total Amount of Patients:"+(TotalP()));
            ar.add("    The Total waiting time for patients:"+waitin+" minutes");     
            ar.add("    The average waiting time for patients was about "+(waitin/TotalP())+ " minutes each");      
            ar.add("    The amount of level 1 Emergency: "+one);      
            ar.add("    The amount of level 2 Emergency: "+two);   
            ar.add("    The amount of level 3 Emergency: "+three);   
            
            
            
            if((waitin/(heal+count()+getServicing()))>10){
                ar.add("");
                ar.add("         Recommendation: Hiring more doctors");
                ar.add("                "+(int)(waitin/TotalP()/10)+" Doctors Should Be Hired");
            }     
            return ar;
        }
        return null;
    }
    
    /**
     * The total amount of patients for that day
     * @return total The amount of patients that checked into the hospital
     */
    public int TotalP(){
        return ((heal+count()+getServicing()));
    }
    
    /**
     * Calcuate the totals amount of time each patient has waited 
     * @return total The total amount of time patients have waited
     */
    public int sumWait(int x){
        int total;
        Patients curr=head.Next();
        for(total=x;curr!=null;curr=curr.Next())total+=curr.getWait();
        return total;
    }
        
    /**
     * Sets the amount of doctors
     * @param num The amount of doctors
     */
    public void setDoctors(int num){
        Doc=new Patients[num];
    }
}