//Jason A. Young
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class  Project4 extends Frame implements ActionListener{
    // File Parameters
    String []Jabout={"A program that uses nodes as example of patients in a hospital simulation",
                     "      ~St. Young's Hospital Operations:",
                     "",
                     "              ⋆ Daily:                    A simulation of an active hospital with a set time and amount of doctors",
                     "              ⋆ Doctors Amount:           The user inputs an number that adds/subtracts the amount of doctors on staff",
                     "              ⋆ Hours Amount:             The user inputs the amout of hours in the simulation working shift",
                     "              ⋆ Evaulation:               A print out of the data recorded from the hospital simulation",
                    };
    String command = "";
    String ans="",jay="Jason A. Young",docs="";
    Hospital hop=new Hospital();
    
    /**
     * creates a Frame
     */    
    public static void main(String[] args){
        Frame frame = new  Project4();
        frame.setResizable(false);
        frame.setSize(900,700);
        frame.setVisible(true);
        Color closeBlack=new Color(0,0,50);
        frame.setBackground(closeBlack);
    }
    
    /**
     * Project4 constructor
     */    
    public  Project4(){
        setTitle("ST. YOUNG'S HOSPITAL SIMULATION");
        
        MenuBar mb = new MenuBar();
        setMenuBar(mb);
        
        Menu fileMenu=new Menu("File");
        mb.add(fileMenu);
        
        MenuItem miAbout=new MenuItem("About");
        miAbout.addActionListener(this);
        fileMenu.add(miAbout);
        
        MenuItem miExit=new MenuItem("Exit");
        miExit.addActionListener(this);
        fileMenu.add(miExit);
        
        Menu IDK=new Menu("Hospital");
        mb.add(IDK);
        
        MenuItem miDal=new MenuItem("Daily");
        miDal.addActionListener(this);
        IDK.add(miDal);
        
        MenuItem miDA=new MenuItem("Doctors Amount");
        miDA.addActionListener(this);
        IDK.add(miDA);
        
        MenuItem miHA=new MenuItem("Hours Amount");
        miHA.addActionListener(this);
        IDK.add(miHA);
        
        MenuItem miE=new MenuItem("Evaulation");
        miE.addActionListener(this);
        IDK.add(miE);
        
        WindowListener l = new WindowAdapter(){
                        
            public void windowClosing(WindowEvent ev){
                System.exit(0);
            }
            
            public void windowActivated(WindowEvent ev){
                repaint();
            }
            
            public void windowStateChanged(WindowEvent ev){
                repaint();
            }
        
        };
        
        ComponentListener k = new ComponentAdapter(){
            public void componentResized(ComponentEvent e){
                repaint();           
            }
        };
        
        this.addWindowListener(l);
        this.addComponentListener(k);
    }
    
    /**
     * Depending on the action that the user perform, it performs a certain action
     * @param ev What Action has just been performed by the user
     */
    public void actionPerformed (ActionEvent ev){
            command = ev.getActionCommand();                        
            
            if("About".equals(command)||"Evaulation".equals(command))repaint();
            else if("Exit".equals(command))System.exit(0);
            else if("Daily".equals(command)){
                hop.daily();
                repaint();
            }
            else if("Doctors Amount".equals(command)){
                int az=hop.getDoc(), za=0;
                
                try{  
                    String equate=JOptionPane.showInputDialog(null, "How many doctors are there?","Doctor Amount", JOptionPane.QUESTION_MESSAGE);
                    if(equate!=null)za=Integer.parseInt(equate);
                    if(za<=0)za=1;
                    hop.setDoctors(za);
                }
                catch(NumberFormatException ioe){
                        JOptionPane.showMessageDialog(null,"Not an valid number");
                        za=az;
                    }         
                    
                if(az>za)docs="The amount of doctors working was decreased by "+(az-za);
                else if(za>az)docs="The amount of doctors working was increased by "+(za-az);
                else docs="The amount of doctors have not changed";
                repaint();
            }
            else if("Hours Amount".equals(command)){
                int az=hop.getTime(), za=0;
                
                try{  
                    String equate=JOptionPane.showInputDialog(null, "How many hours of operation are there?","Hours Amount", JOptionPane.QUESTION_MESSAGE);
                    if(equate!=null)za=Integer.parseInt(equate);
                    if(za<=0)za=1;
                    hop.setTime(za);
                }
                catch(NumberFormatException ioe){
                        JOptionPane.showMessageDialog(null,"Not an valid number");
                        za=az;
                    }         
                
                if(az>za)docs="The hours of operation was decreased by "+(az-za);
                else if(za>az)docs="The hours of operation was increased by "+(za-az);
                else docs="The hours of operation have not changed";
                repaint();
            }
        }
        
    /**
     * Displays the graphic upon the frame
     * @param g Graphics currently being displayed
     */
    public void paint(Graphics g){
            int ww = (int)this.getWidth();
            int wh = (int)this.getHeight();            
           
            if("About".equals(command)){
              int x = ww/8;
              int y = (int)(wh*.4);
              
              g.setColor(Color.WHITE);
              g.drawString("About:",25,75);
              g.drawLine(25,78,60,78);
              
              for(int z=0;z<Jabout.length;z++){
                  if(z<=1)g.setColor(Color.WHITE);
                  else if(z==3)g.setColor(Color.MAGENTA);
                  else if(z==4)g.setColor(Color.BLUE);
                  else if(z==5)g.setColor(Color.GREEN);
                  else if(z==6)g.setColor(Color.YELLOW);
                  g.drawString(Jabout[z], x, y+z*20);
                }
            }
            
            else if("Doctors Amount".equals(command)){
                g.setColor(Color.MAGENTA);
                g.drawString(docs,(int) (ww*.4), wh/2);
                g.drawString("      Doctors:"+hop.getDoc(),(int) (ww*.4), (wh/2)+25);
            }
            
            else if("Hours Amount".equals(command)){
                g.setColor(Color.DARK_GRAY);
                g.drawString(docs,(int) (ww*.4), wh/2);
                g.drawString("      HOURS:"+hop.getTime(),(int) (ww*.4), (wh/2)+25);
            }
            
            else if("Evaulation".equals(command)){
                ArrayList<String>ar=hop.print_New_Daily_Sheet();
                for(int z=0;z<ar.size();z++){
                  if(z<=2)g.setColor(Color.BLUE);
                  else if(z<=6)g.setColor(Color.GREEN);
                  else if(z<=13)g.setColor(Color.YELLOW);
                  else g.setColor(Color.ORANGE);
                  g.drawString(ar.get(z),(int) (ww*.4)-75, (wh/2)-100+z*20);          
                  }
                g.setColor(Color.WHITE);                
            }
            
            else if("Daily".equals(command)){
                ArrayList<String>ar=hop.getData();
                int amt=30;
                Color darker=Color.ORANGE;
                darker.darker().darker().darker();
                for(int x=0;x<ar.size()-1&&x<=58;x++){
                  if(-1<ar.get(x).indexOf("Depart"))g.setColor(Color.GREEN);
                  else if(-1<ar.get(x).indexOf("1 Emergency"))g.setColor(Color.RED);
                  else if(-1<ar.get(x).indexOf("2 Emergency"))g.setColor(darker);
                  else if(-1<ar.get(x).indexOf("3 Emergency"))g.setColor(Color.YELLOW);
                  else if(-1<ar.get(x).indexOf("Depart"))g.setColor(Color.GREEN);
                  else g.setColor(Color.WHITE);
                  g.drawString(ar.get(x), 30+x/amt*400, 75+x%amt*20);
                  if(x==58){
                      g.setColor(Color.PINK);
                      g.drawString("Stopped Displaying!!! Not Enough Room!!!", 30+x/amt*400, 100+x%amt*20);    
                      g.drawLine(30+x/amt*400, 105+x%amt*20,260+x/amt*400, 105+x%amt*20);   
                    }
                }
                g.setColor(Color.WHITE);
            }
            else {
                g.setColor(Color.CYAN);
                g.drawString("St. Young's Family Hospital",ww/2-100,wh/2);
                g.setColor(Color.WHITE);
            }            
            g.setColor(Color.BLUE);
            g.drawString(jay,ww-100,wh-25);
            g.setColor(Color.WHITE);
        }
}