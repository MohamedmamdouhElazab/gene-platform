/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package get;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
/**
 *
 * @author elazab
 */
public class GET {
    private ArrayList <String> word=new ArrayList<>();
    private ArrayList <String> ner=new ArrayList<>();
    private ArrayList <String> word1=new ArrayList<>();
    private ArrayList <String> ner1=new ArrayList<>();
    private ArrayList <String> word2=new ArrayList<>();
    private ArrayList <String> ner2=new ArrayList<>();
    private ArrayList <String> normalizedNER=new ArrayList<>();
    private ArrayList <String> input=new ArrayList<>();
    private ArrayList <String> Finalrelation=new ArrayList<>();
    private static ArrayList <String> command=new ArrayList<>();
    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) {
         ArrayList <String> inputloc=new ArrayList<>();
          ArrayList <String> getloc=new ArrayList<>();
           ArrayList <String> gettype=new ArrayList<>();
           String outputloc="output1.txt";
           getloc.add("1");//no of configure file
           inputloc.add("input1.txt");//location of input file
           gettype.add("3");// type of get client need
           GET G=new GET();
           G.work(inputloc,inputloc, getloc, outputloc, gettype);
         
     }
     
    public void work (ArrayList <String> inputloc,ArrayList <String> inputname,ArrayList <String> getloc,String outputloc,ArrayList <String> gettype) {
        GET G=new GET();
        G.writetofile(inputloc,"list.txt");
        String cmd="./corenlp.sh -annotators 'tokenize,ssplit,pos,lemma,ner,regexner' -filelist list.txt -outputFormat json -regexner.mapping ";
       for(int i=0;i<getloc.size();i++)
       {
           if(i!=0)
        {
             cmd.concat(",");
        }
          
            cmd = cmd.concat("config"+getloc.get(i)+".txt");
       }        
// determine the input file location from the user ???
        // determine the config file location from previous program ??
        // determine the output file location from the user ???
        
        command.add(cmd);
        
        G.writetofile(command, "cmd2");
        G.run(3);// run nlp
        G.read2(inputname.get(0));// read json file
        
        for(int i=0;i<getloc.size();i++)
        {
        if(gettype.get(i)=="1")
        {
            System.err.println("hello");
            //G.readf("in"+getloc.get(i)+".txt");
            G.GET1();//relationship output form1
             for(int j=0;j<G.word2.size();j++)
            {
            Finalrelation.add(G.word2.get(j)+"    "+G.ner2.get(j)+" ");
            }
            
        }
        if(gettype.get(i)=="2")
        {
            //G.readf("in"+getloc.get(i)+".txt");
            G.GET2();//relationship output form2
             for(int j=0;j<G.word2.size();j++)
            {
            Finalrelation.add(G.word2.get(j)+"    "+G.ner2.get(j)+" ");
            }
           
        }
        if(gettype.get(i)=="3")
        {
            // G.readf("in"+getloc.get(i)+".txt");
             G.GETboth(); //relationship output form3
              for(int j=0;j<G.word2.size();j++)
            {
            Finalrelation.add(G.word2.get(j)+"    "+G.ner2.get(j)+" ");
            }
             
        }
        if(gettype.get(i)=="4")
        {
             G.readf("in"+getloc.get(i)+".txt");
             G.get();//ner ooutput form
             for(int j=0;j<G.word2.size();j++)
            {
            Finalrelation.add(G.word2.get(j)+"    "+G.ner2.get(j)+" ");
             
            }
             
        }
        }
         
        writetofile(Finalrelation,outputloc);
    }
    
    
        public void run(int x)
    {
    String command = null;
    if(x==1)
    {
        
         command = "./cmd"; //without adding our nlp
    }
     if(x==3)
    {
         command = "./cmd2"; // with adding our nlp
    }
      if(x==2)
    {
         command = "./cmd2"; // with adding our nlp
    }
      
       
       Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(command);
        } catch (IOException ex) {
            Logger.getLogger(GET.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Read the output
        BufferedReader reader =  
              new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String line = "";
        try {
            while((line = reader.readLine()) != null) {
                System.out.print(line + "\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(GET.class.getName()).log(Level.SEVERE, null, ex);
        }

        try { 
            proc.waitFor();
        } catch (InterruptedException ex) {
            Logger.getLogger(GET.class.getName()).log(Level.SEVERE, null, ex);
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
           Logger.getLogger(GET.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
     public void readf(String x){
        
                 BufferedReader br = null;
		 FileReader fr = null;
           
		try {

			fr = new FileReader(x);
			br = new BufferedReader(fr);

			String sCurrentLine;

			//br = new BufferedReader(new FileReader(FILENAME));

			while ((sCurrentLine = br.readLine()) != null) {
                           // sCurrentLine="\""+sCurrentLine+"\"";
		           // d.add(sCurrentLine);
                             input.add(sCurrentLine);    
			}

		} catch (IOException e) {

			e.printStackTrace();

		}
}
 
    public void read2(String x)  
    {
         try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(x+".json"));
            
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray data = (JSONArray)jsonObject.get("sentences");
            for(Object o: data){
                JSONObject jsonObject1 = (JSONObject) o;
                JSONArray data1 = (JSONArray)jsonObject1.get("tokens");
             for(Object o1: data1){
                JSONObject jsonObject2 = (JSONObject) o1;
                String word1 = (String)jsonObject2.get("word");
                String ner1 = (String)jsonObject2.get("ner");
                String normalize1 = (String)jsonObject2.get("normalizedNER");
                word.add(word1);
                ner.add(ner1);
                if(normalize1==null)
                {
                    normalizedNER.add("0");
                }
                else
                {
                    normalizedNER.add(normalize1);
                }
                }
            }
             
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void get()
    {
        ner2.removeAll(ner2);
        word2.removeAll(word2);
         
        //input is ner
        for(int i=0;i<ner.size();i++)
        {
             for(int j=0;j<input.size();j++)
        {
        if(input.get(j).equals(ner.get(i)))
        {
          ner2.add(ner.get(i));
          word2.add(word.get(i));
        }
        }
        }
         
        
    }
    
    public void GET1()
    {
        ner2.removeAll(ner2);
        word2.removeAll(word2);
         ner1.removeAll(ner1);
        word1.removeAll(word1);
        int count=0;
        for(int i=0;i<ner.size();i++)
        {
        if("ftarget".equals(ner.get(i)))
        {
            if("ftarget".equals(ner.get(i+1)))
            {
                count++;
                 ner1.add(ner.get(i));
                 word1.add(word.get(i));
            }
            else if("ltarget".equals(ner.get(i+1)))
            {
                count=0;
                ner1.add(ner.get(i));
                word1.add(word.get(i));
            }
            else
            {
               for(int k=0;k<count;k++)
               {
                   ner1.remove(ner1.size()-k-1);
                   word1.remove(word1.size()-k-1);
               } 
               count=0;
            }
          
        }
        }
        for(int i=0;i<ner1.size();i++)
        {
                    word2.add(word1.get(i));
                    ner2.add(ner1.get(i));
        }
        
       
        
        }
    public void GET2()
    {
        ner2.removeAll(ner2);
        word2.removeAll(word2);
         ner1.removeAll(ner1);
        word1.removeAll(word1);
        int flag=0;
        for(int i=0;i<ner.size();i++)
        {
        if("ltarget".equals(ner.get(i)))
        {
            if("ftarget".equals(ner.get(i-1)))
            {
                 ner1.add(ner.get(i));
                 word1.add(word.get(i));
                 flag=1;
            }
            else if("ltarget".equals(ner.get(i-1)))
            {
                if(flag==1)
                {
                 ner1.add(ner.get(i));
                 word1.add(word.get(i));
            
                }
             }
            else
            {
              flag=0;
            }
        }
        else
        {
            flag=0;
        }
        }
//        for(int i=0;i<ner1.size();i++)
//        {
//            for(int j=0;j<input.size();j++)
//            {
//                if(word1.get(i)==input.get(j))
//                {
//                    word1.set(i, "0");
//                    ner1.set(i, "0");
//                    
//                }
//            }
//        }
        for(int j=0;j<ner1.size();j++)
            {
                    word2.add(word1.get(j));
                    ner2.add(ner1.get(j));
            }
         
       
    }
    public void GETboth()
    {
        ner2.removeAll(ner2);
        word2.removeAll(word2);
        ner1.removeAll(ner1);
        word1.removeAll(word1);
        int count=0,flag=0;
        for(int i=0;i<ner.size();i++)
        {
        if("ftarget".equals(ner.get(i)))
        {
            if("ftarget".equals(ner.get(i+1)))
            {
                count++;
                 ner1.add(ner.get(i));
                 word1.add(word.get(i));
            }
            else if("ltarget".equals(ner.get(i+1)))
            {
                count=0;
                ner1.add(ner.get(i));
                word1.add(word.get(i));
            }
            else
            {
               for(int k=0;k<count;k++)
               {
                   ner1.remove(ner1.size()-k-1);
                   word1.remove(word1.size()-k-1);
               } 
               count=0;
            }
          
        }
        else if("ltarget".equals(ner.get(i)))
        {
            if("ftarget".equals(ner.get(i-1)))
            {
                 ner1.add(ner.get(i));
                 word1.add(word.get(i));
                 flag=1;
            }
            else if("ltarget".equals(ner.get(i-1)))
            {
                if(flag==1)
                {
                 ner1.add(ner.get(i));
                 word1.add(word.get(i));
            
                }
             }
            else
            {
              flag=0;
            }
        }
        else
        {
            flag=0;
        }
        
        }
        for(int i=0;i<ner1.size();i++)
        {
          ner2.add(ner1.get(i));
          word2.add(word1.get(i));
        
        }
         
         
    }   
}

