/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlp.platform;

import NLL.Compound_list;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static nlp.platform.NER.counter;

/**
 *
 * @author elazab
 */
public class Relations {
    private Compound_list input;
    private Compound_list pos; 
    private String x,y;
    private ArrayList <String> Finalinput=new ArrayList<>();// contain the input after organizing
    private ArrayList <String> Finalrelation=new ArrayList<>();
    private ArrayList <String> input1=new ArrayList<>();
    private ArrayList <String> input2=new ArrayList<>();
    private ArrayList <String> pos1=new ArrayList<>();
    private ArrayList <String> pos2=new ArrayList<>();
    
    public Relations() {
        counter++;
        
        System.out.print(counter);
        
         writetofile3(""+counter, "GET.txt");
    }
    
    
    
    
    public void initialize(Compound_list input,Compound_list pos)
    {
       
          this.input=input;
          writetofile2(input);
         
          this.pos=pos;
          Divide(input, pos);
          writetofile(Finalrelation,"config"+counter+".txt");
    }

    private void Divide(Compound_list input, Compound_list pos) {
        for(int i=0;i<input.get_size();i++)
        {
          if(input.get_index_type(i)=="Generic")
        {
           if(i==0)
            {
            x="( ( [ { tag:"+input.get_index_info(i)+" } ]) ";
            input1.add(x);
            }
           else
           {
             if(input.get_index_type(i-1)=="Relation")
             {
                if(input.get_index_info(i-1)=="0")
             {
                 // one to one 
                 input1.set(0, input1.get(0)+" ( [ { tag:"+input.get_index_info(i)+" } ]) ");
             } 
                 if(input.get_index_info(i-1)=="1")
             {
                  //one to many
                 // not valid
             }    
                  if(input.get_index_info(i-1)=="2")
             {
                // many to one
                  for(int j=0;j<input1.size();j++)
                   {
                    input1.set(j, input1.get(j)+" ( [ { tag:"+input.get_index_info(i)+" } ]) ");
                   }
                 
             }
                   if(input.get_index_info(i-1)=="3")
             {
              // many to many
                 // not valid
             }
           }
        }
        }
         if(input.get_index_type(i)=="File")
        {
             if(i==0)
            {
             for(int j=0;j<input.get_index_filelist(i).length;j++)
            {
              x="( /"+input.get_index_filelist(i)[j]+"/ ";
              input1.add(x);
            }
            }
             else
             {
                 if(input.get_index_type(i-1)=="Relation")
             {
                if(input.get_index_info(i-1)=="0")
             {
                 // one to one 
                 for(int j=0;j<input.get_index_filelist(i).length;j++)
                     {
                      input1.set(j, input1.get(j)+" /"+input.get_index_filelist(i)[j]+"/ ");
                     }
                
             } 
                 if(input.get_index_info(i-1)=="1")
                 {  
                     String temp=input1.get(0);
                     input1.set(0, temp+" /"+input.get_index_filelist(i)[0]+"/ ");
                  //one to many
                 for(int j=1;j<input.get_index_filelist(i).length;j++)
                     {
                      input1.add(temp+" /"+input.get_index_filelist(i)[j]+"/ ");  
                     }
                 
             }    
                  if(input.get_index_info(i-1)=="2")
             {
                // many to one
                 // not vallid
             }
                   if(input.get_index_info(i-1)=="3")
             {
              // many to many
                 for(int j=0;j<input1.size();j++)
                  {
                     for(int k=0;k<input.get_index_filelist(i).length;k++)
                       {
                       input2.add(input1.get(j)+" /"+input.get_index_filelist(i)[k]+"/ ");
                       }
                  }   
                   input1.removeAll(input1);
                   input1=input2;
             }
             }  
             }
        }
        if(input.get_index_type(i)=="String")
        {
             if(i==0)
            {
            x="( /"+input.get_index_info(i)+"/ ";
            input1.add(x);
            }
             else
             {
               if(input.get_index_type(i-1)=="Relation")
             {
                if(input.get_index_info(i-1)=="0")
             {
                 // one to one 
                 input1.set(0, input1.get(0)+" /"+input.get_index_info(i)+"/ ");
             } 
                 if(input.get_index_info(i-1)=="1")
             {
                  //one to many
                  // not valid
             }    
                  if(input.get_index_info(i-1)=="2")
             {
                // many to one
                 for(int j=0;j<input1.size();j++)
                   {
                    input1.set(j, input1.get(j)+" /"+input.get_index_info(i)+"/ ");
                   }
             }
                   if(input.get_index_info(i-1)=="3")
             {
              // many to many
                  // not valid
             }
             }  
             }
        }
        
        
        }
        
        
        
        
        
         for(int i=0;i<pos.get_size();i++)
        {
           if(pos.get_index_type(i)=="Generic")
        {
             if(i==0)
            {
                 y=" ( [ { tag:"+pos.get_index_info(i)+" } ]) ";
                 pos1.add(y);
            //Finalinput.add(x);
            }
              else
           {
             if(pos.get_index_type(i-1)=="Relation")
             {
                if(pos.get_index_info(i-1)=="0")
             {
                 // one to one 
                 pos1.set(0, pos1.get(0)+" ( [ { tag:"+pos.get_index_info(i)+" } ]) ");
             } 
                 if(pos.get_index_info(i-1)=="1")
             {
                  //one to many
                 // not valid
             }    
                  if(pos.get_index_info(i-1)=="2")
             {
                // many to one
                  for(int j=0;j<pos1.size();j++)
                   {
                    pos1.set(j, pos1.get(j)+" ( [ { tag:"+pos.get_index_info(i)+" } ]) ");
                   }
                 
             }
                   if(pos.get_index_info(i-1)=="3")
             {
              // many to many
                 // not valid
             }
           }
        }
            
            
        }
         if(pos.get_index_type(i)=="File")
        {
            
        
             if(i==0)
            {
               for(int j=0;j<pos.get_index_filelist(i).length;j++)
            {
                y=" /"+pos.get_index_filelist(i)[j]+"/ ";
               pos1.add(y);
              // Finalinput.add(x);
            } 
            }
             else
             {
                 if(pos.get_index_type(i-1)=="Relation")
             {
                if(pos.get_index_info(i-1)=="0")
             {
                 // one to one 
                 for(int j=0;j<pos.get_index_filelist(i).length;j++)
                     {
                      pos1.set(j, pos1.get(j)+" /"+pos.get_index_filelist(i)[j]+"/ ");
                     }
                
             } 
                 if(pos.get_index_info(i-1)=="1")
                 {  
                     String temp=pos1.get(0);
                      pos1.set(0, temp+" /"+pos.get_index_filelist(i)[0]+"/ ");
                  //one to many
                 for(int j=1;j<pos.get_index_filelist(i).length;j++)
                     {
                      pos1.add(temp+" /"+pos.get_index_filelist(i)[j]+"/ ");  
                     }
                 
             }    
                  if(pos.get_index_info(i-1)=="2")
             {
                // many to one
                 // not vallid
             }
                   if(pos.get_index_info(i-1)=="3")
             {
              // many to many
                 for(int j=0;j<pos1.size();j++)
                  {
                     for(int k=0;k<pos.get_index_filelist(i).length;k++)
                       {
                       pos2.add(pos1.get(j)+" /"+pos.get_index_filelist(i)[k]+"/ ");
                       }
                  }   
                   pos1.removeAll(pos1);
                   pos1=pos2;
             }
             }  
             }
            
             
        }
         if(pos.get_index_type(i)=="String")
        {
             if(i==0)
            {
                
                y=" /"+pos.get_index_info(i)+"/ ";
                pos1.add(y);
             //Finalinput.add(x); 
            }
           else
             {
               if(pos.get_index_type(i-1)=="Relation")
             {
                if(pos.get_index_info(i-1)=="0")
             {
                 // one to one 
                 pos1.set(0, pos1.get(0)+" /"+pos.get_index_info(i)+"/ ");
             } 
                 if(pos.get_index_info(i-1)=="1")
             {
                  //one to many
                  // not valid
             }    
                  if(pos.get_index_info(i-1)=="2")
             {
                // many to one
                 for(int j=0;j<pos1.size();j++)
                   {
                    pos1.set(j, pos1.get(j)+" /"+pos.get_index_info(i)+"/ ");
                   }
             }
                   if(pos.get_index_info(i-1)=="3")
             {
              // many to many
                  // not valid
             }
             }  
             }
        }
         
        }
         
         
        // first solve
//             for(int i=0;i<pos1.size();i++)
//             {
//           for(int j=0;j<input1.size();j++)
//             {
//                 Finalinput.add(input1.get(j)+pos1.get(i)+")");
//             }
//             }
//             for(int i=0;i<Finalinput.size();i++)
//            {
//                Finalrelation.add(Finalinput.get(i)+"\t"+"target"+" ");
//            }
         
             // another solve 
              for(int i=0;i<input1.size();i++)
             {
                 input1.set(i, input1.get(i)+")"+"\t"+"ftarget"+" ");
             }
              for(int i=0;i<pos1.size();i++)
             {
                 pos1.set(i, "("+pos1.get(i)+")"+"\t"+"ltarget"+" ");
             }
              
              for(int i=0;i<input1.size();i++)
            {
                Finalrelation.add(input1.get(i));
            }
              for(int i=0;i<pos1.size();i++)
            {
                Finalrelation.add(pos1.get(i));
            }
              
             
         
         
          
        
    }
    
    private void writetofile(ArrayList<String> Finalrelation,String link) {
        String x;
        String y;
       try {
            PrintWriter out ;
           out = new PrintWriter(link);
           
           for(int i=0;i<Finalrelation.size();i++)
           {
             
           out.append(Finalrelation.get(i));
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
    
     private void writetofile2(Compound_list Finalrelation) {
        String x;
        String y;
         String[] splitStr = null;
       try {
            PrintWriter out ;
           out = new PrintWriter( "in"+counter+".txt" );
           
           for(int i=0;i<Finalrelation.get_size();i++)
           {
               if(Finalrelation.get_index_type(i)=="String")
               {
               splitStr =Finalrelation.get_index_info(i).split("\\s+");
               }
               if(Finalrelation.get_index_type(i)=="Generic")
               {
               splitStr =Finalrelation.get_index_info(i).split("\\s+");
               }
//               if(Finalrelation.get_index_type(i)=="Generic")
//               {
//               splitStr[0] ="";
//               }
               if(Finalrelation.get_index_type(i)=="File")
               {
               splitStr =Finalrelation.get_index_filelist(i);
           // what if file have more than on word per line
               }
               if(Finalrelation.get_index_type(i)!="Relation")
               {
                for(int j=0;j<splitStr.length;j++)
           {
               out.append(splitStr[j]);
               out.println("");
           }
           }
           }
           out.close();
       } catch (FileNotFoundException ex) {
           Logger.getLogger(NER.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    
    
  
    
    
    
    
    
}