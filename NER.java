/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlp.platform;

import NLL.Compound_list;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elazab
 */
public class NER {
    public static int counter=0;
    private Compound_list input;
    private Compound_list ner;
    private ArrayList <String> Finalinput=new ArrayList<>();// contain the input after organizing
    private ArrayList <String> Finalner=new ArrayList<>();
    private ArrayList <String> Fner=new ArrayList<>();
    private ArrayList <String> input2=new ArrayList<>();
    private ArrayList <String> input1=new ArrayList<>();
    public NER() { 
        counter++;
        System.out.print(counter);  
        writetofile3(""+counter, "GET.txt");
    }
      
      
      
      
    public void initialize(Compound_list input,String Flag,Compound_list ner)
    {
        this.input=input;// input file from user and pos and ner and so on 
        // multiple dimntion array 
        this.ner=ner;//one dimention array
        //this.Finalinput=this.input;
         Divide(input,ner);
         writetofile(Fner, "in"+counter+".txt");
        if(Flag=="0")
        {//one to one
            onetoone(); 
        }
        if(Flag=="1")
        {//one to many
            onetomany();
        }
        if(Flag=="2")
        {//many to one
          manytoone();   
        }
        if(Flag=="3")
        {//many to many
          manytomany();  
        }
        if(Flag=="4")
        {//n to n
            nton();
        }
    }

    
    
    
    
    private void Divide(Compound_list input,Compound_list ner) {
       
        for(int i=0;i<input.get_size();i++)
        {
             if(input.get_index_type(i)=="File")
             {
                 if(i==0)
                 {
                     for(int j=0;j<input.get_index_filelist(i).length;j++)
                     {
                          input1.add(input.get_index_filelist(i)[j]);
                     }
                   
                 }
                 else
                 {
                      if(input.get_index_type(i-1)=="Relation")
                        {
                          if((input.get_index_info(i-1))=="0")
                          {
                              // one to one
                              // previous is file
                              for(int j=0;j<input.get_index_filelist(i).length;j++)
                              {
                                input1.set(j, input1.get(j)+" "+input.get_index_filelist(i)[j]);
                             // input2.add(input1.get(j)+" "+input.get_index_filelist(i)[j]);
                              }  
                          }
                          if((input.get_index_info(i-1))=="1")
                          {
                              String temp=input1.get(0);
                             //one to many 
                              // previous is string
                              input1.set(0,temp+" "+input.get_index_filelist(i)[0]);
                              for(int j=1;j<input.get_index_filelist(i).length;j++)
                              {
                              input1.add(temp+" "+input.get_index_filelist(i)[j]);
                              }
                          }
                          if((input.get_index_info(i-1))=="2")
                          {
                            //many to one
                              // not valid
                          }
                          if((input.get_index_info(i-1))=="3")
                          {
                              //many to many
                              // previous file but each element match with next file
                               for(int j=0;j<input1.size();j++)
                              {
                                   for(int k=0;k<input.get_index_filelist(i).length;k++)
                              {
                              input2.add(input1.get(j)+" "+input.get_index_filelist(i)[k]);
                              }
                              }   
                               input1.removeAll(input1);
                               input1=input2;
                          }
                        }
                 }
             }
             else if(input.get_index_type(i)=="String")
             {
                 if(i==0)
                 {
                          input1.add(input.get_index_info(i)); 
                 }
                 else
                 {
                    if(input.get_index_type(i-1)=="Relation")
                        {
                            if((input.get_index_info(i-1))=="0")
                          {
                               // one to one
                              input1.set(0,input1.get(0)+" "+input.get_index_info(i));
                          }
                           if((input.get_index_info(i-1))=="1")
                          {
                               //one to many 
                              // not valid 
                          }
                          if((input.get_index_info(i-1))=="2")
                          {
                             //many to one 
                              //previous is file
                              for(int j=0;j<input1.size();j++)
                              {
                              input1.set(j, input1.get(j)+" "+input.get_index_info(i));
                              }
                          }
                          if((input.get_index_info(i-1))=="3")
                          {
                              //many to many
                              // not valid
                          }
                        }
                 }
             }  
        }
       Finalinput=input1;
       if(ner.get_index_type(0)=="File")
       {
           for(int i=0;i<ner.get_index_filelist(0).length;i++)
           {
           Fner.add(ner.get_index_filelist(0)[i]);
           }
       }
       else if(ner.get_index_type(0)=="String")
       {
       Fner.add(ner.get_index_info(0));
       }
       else
       {
           //Error
       }
    }

    private void onetoone() 
    {
        for(int i=0;i<Finalinput.size();i++)
        {
        
         Finalner.add(Finalinput.get(i)+"\t"+Fner.get(i)+" ");
                                        
                                        
        }
        writetofile( Finalner,"config"+counter+".txt");
    }

    private void onetomany() 
    {
        String x="";
        
         for(int i=0;i<Fner.size();i++)
        {
         x=x.concat(Fner.get(i)+",");
        }
        x=x.substring(0, x.length()-2);
        Fner.removeAll(Fner);
        Fner.add(0, x);
        Finalner.add(Finalinput.get(0)+"\t"+Fner.get(0)+" ");
        writetofile( Finalner,"config"+counter+".txt");
    }

    private void manytoone() {
         for(int i=1;i<Finalinput.size();i++)
        {
          Fner.add(Fner.get(0));
        }
         for(int i=0;i<Finalinput.size();i++)
        {
        
         Finalner.add(Finalinput.get(i)+"\t"+Fner.get(i)+" ");
         
        }
         writetofile( Finalner,"config"+counter+".txt");
       }

    private void manytomany() {
        
        String x="";
        
         for(int i=0;i<Fner.size();i++)
        {
         x=x.concat(Fner.get(i)+",");
        }
          x=x.substring(0, x.length()-2);
         for(int i=0;i<Fner.size();i++)
        {
           Fner.set(i, x);
        }
       
        for(int i=0;i<Finalinput.size();i++)
        {
        
         Finalner.add(Finalinput.get(i)+"\t"+Fner.get(i)+" ");
                                        
        }
         writetofile( Finalner,"config"+counter+".txt");
    }

    private void nton() {
        
        // not yet
         writetofile( Finalner,"config"+counter+".txt");
    }

    private void writetofile(ArrayList<String> Finalner,String link) {
        String x;
     String y;
       try {
            PrintWriter out ;
           out = new PrintWriter(link);
           
           for(int i=0;i<Finalner.size();i++)
           {
             
           out.append(Finalner.get(i));
           out.println("");
           }
           out.close();
       } catch (FileNotFoundException ex) {
           Logger.getLogger(NER.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    private void writetofile3(String Finalrelation,String link) {
        String x;
        String y;
       try {
            PrintWriter out ;
           out = new PrintWriter(link);  
           out.append(Finalrelation);
           out.println("");
           out.close();
       } catch (FileNotFoundException ex) {
           Logger.getLogger(NER.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    
}
