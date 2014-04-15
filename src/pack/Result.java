package pack;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shyam
 */
import java.util.Calendar;
import java.sql.Date;
public class Result {
    public Result()
    {

    }
    public static long interest(String date1,String date2,long amount)
    {
          int a[]=null;
          int tint=0,tfine=0,tamount=0;
          String d1[]=date1.split("-");
          String d2[]=date2.split("-");
          Calendar calendar1 = Calendar.getInstance();
          Calendar calendar2 = Calendar.getInstance();
          calendar1.set(Integer.valueOf(d1[0]),Integer.valueOf(d1[1])-1,Integer.valueOf(d1[2]));
          calendar2.set(Integer.valueOf(d2[0]),Integer.valueOf(d2[1])-1,Integer.valueOf(d2[2]));
          int ydiff=calendar2.get(calendar2.YEAR)-calendar1.get(calendar1.YEAR);
          int mdiff=0;
          int ddiff=0;
          mdiff=calendar2.get(calendar2.MONTH)+12*ydiff-calendar1.get(calendar1.MONTH);
          ddiff=calendar2.get(calendar2.DAY_OF_MONTH)-calendar1.get(calendar1.DAY_OF_MONTH);
          if(ddiff<0)
              mdiff--;
          long l=Math.round(mdiff*2.0/100*amount);
          System.out.println("l="+l);

          return l;
    }
    public static int[] initialdesposite(String date1,String date2,int recurring,int nonrecurring,int person)
    {
          int a[]=null;
          int tint=0,tfine=0,tamount=0;
          String d1[]=date1.split("-");
          String d2[]=date2.split("-");
          Calendar calendar1 = Calendar.getInstance();
          Calendar calendar2 = Calendar.getInstance();
          calendar1.set(Integer.valueOf(d1[0]),Integer.valueOf(d1[1])-1,Integer.valueOf(d1[2]));
          calendar2.set(Integer.valueOf(d2[0]),Integer.valueOf(d2[1])-1,Integer.valueOf(d2[2]));
          int ydiff=calendar2.get(calendar2.YEAR)-calendar1.get(calendar1.YEAR);
          int mdiff=0;
          int ddiff=0;
          mdiff=calendar2.get(calendar2.MONTH)+12*ydiff-calendar1.get(calendar1.MONTH);
          ddiff=calendar2.get(calendar2.DAY_OF_MONTH)-calendar1.get(calendar1.DAY_OF_MONTH);
          if(ddiff<0)
              mdiff--;
          if(mdiff<0)
          {
              System.out.println("<0");
              return null;
          }
          else if(mdiff == 0)
          {
              System.out.println("=0");
              tamount=nonrecurring*person;
              System.out.println("tam="+tamount);
          }
          else if(mdiff==1)
          {
            System.out.println("=1");
            tint=(int)Math.round(1.0*nonrecurring*person*2/100);
            tamount=nonrecurring*person+recurring*person;
          }
          else if(mdiff>1)
          {
              System.out.println(">1   "+mdiff);
              for(int i=0;i<mdiff;i++)
              {
                if(i==0)
                {
                    tint=(int)Math.round(1.0*nonrecurring*person*2/100);
                    tamount=nonrecurring*person+recurring*person;
                }
                else
                {                    
                    tint+=(int)Math.round(1.0*tamount*2/100);
                    tamount+=recurring*person;
                    System.out.println("i="+i+" am="+tamount+" tint="+tint);
                }
             }
          }
          System.out.println("tam="+tamount+" fine"+tint);
          a=new int[4];
          a[0]=(tamount+tint);
          a[1]=tamount;
          a[2]=tint;
          a[3]=mdiff;
          return a;

    }
    public static int[] monthlydesposite(String date1,String date2,int month,int recurring,int balance,int person)
    {
          int a[]=null;
          int tint=0,tfine=0,tamount=0;
          String d1[]=date1.split("-");
          String d2[]=date2.split("-");
          Calendar calendar1 = Calendar.getInstance();
          Calendar calendar2 = Calendar.getInstance();
          Calendar calendar3 = Calendar.getInstance();
          calendar1.set(Integer.valueOf(d1[0]),Integer.valueOf(d1[1])-1+month,Integer.valueOf(d1[2]));
          calendar2.set(Integer.valueOf(d2[0]),Integer.valueOf(d2[1])-1,Integer.valueOf(d2[2]));
          calendar3.set(Integer.valueOf(d2[0]),Integer.valueOf(d2[1])-2,Integer.valueOf(d2[2]));
          int ydiff=calendar2.get(calendar2.YEAR)-calendar1.get(calendar1.YEAR);
          int mdiff=0;
          int ddiff=0;
          mdiff=calendar2.get(calendar2.MONTH)+12*ydiff-calendar1.get(calendar1.MONTH);
          ddiff=calendar2.get(calendar2.DAY_OF_MONTH)-calendar1.get(calendar1.DAY_OF_MONTH);
                        
          int fine=0;
          int interest=0;
          int ram=0;
          if(ddiff<0 && mdiff>1)
          {
              System.out.println("<0>1");
              System.out.println(ddiff+" "+mdiff+" "+ydiff);
              mdiff--;

              ddiff=calendar3.getMaximum(calendar3.DATE)-calendar1.get(calendar3.DAY_OF_MONTH)+calendar2.get(calendar1.DAY_OF_MONTH);
              System.out.println(calendar3.getMaximum(calendar3.DATE)+"   "+calendar1.get(calendar3.DAY_OF_MONTH)+"   "+calendar2.get(calendar1.DAY_OF_MONTH));
              System.out.println(ddiff+" "+mdiff+" "+ydiff);
          }
          if(mdiff==0)
          {
              System.out.println("m<0");
             return null;
          }
          else if(mdiff == 1 && ddiff < 0)
          {
              System.out.println("m=1 d<0");
             return null;
          }
          else if(mdiff ==1 && ddiff>=0)
          {
              System.out.println("m=1 d>0");
              interest+=Math.round(balance*2.0/100);
              ram+=recurring*person;
              for(int j=0;j<ddiff;j++)
              {
                   if(j==0)
                   {
                     fine+=11*person;
                   }
                   else
                   {
                     fine+=5*person;
                   }
              }
          }
          else if(mdiff >1)
          {
              System.out.println("m>1   m="+mdiff);
              //for(int i=0;i<mdiff;i++)
              //{
                   fine=(11+29*5)*person*(mdiff-1);
                   interest+=Math.round(balance*2.0/100)*(mdiff);
                   for(int j=0;j<ddiff;j++)
                   {
                       if(j==0)
                       {
                         fine+=11*person;
                       }
                       else
                       {
                         fine+=5*person;
                       }
                   }
                   ram=recurring*person*mdiff;
              System.out.println("    "+ram+"    "+fine+"    "+interest);
          }
          System.out.println(ddiff+" "+mdiff+" "+ydiff);
          a=new int[5];
          a[0]=(ram+fine+balance+interest);
          a[1]=ram;
          a[2]=fine;
          a[3]=interest;
          a[4]=mdiff;
          for(int i:a)
          {
              System.out.println("    "+i);
          }
          
          return a;

          
    }
    public static int[] memberdelete(String date1,String date2,int month,int recurring,int balance,int person)
    {
          int a[]=null;
          String d1[]=date1.split("-");
          String d2[]=date2.split("-");
          Calendar calendar1 = Calendar.getInstance();
          Calendar calendar2 = Calendar.getInstance();
          Calendar calendar3 = Calendar.getInstance();
          calendar1.set(Integer.valueOf(d1[0]),Integer.valueOf(d1[1])-1+month,Integer.valueOf(d1[2]));
          calendar2.set(Integer.valueOf(d2[0]),Integer.valueOf(d2[1])-1,Integer.valueOf(d2[2]));
          calendar3.set(Integer.valueOf(d2[0]),Integer.valueOf(d2[1])-2,Integer.valueOf(d2[2]));
          int ydiff=calendar2.get(calendar2.YEAR)-calendar1.get(calendar1.YEAR);
          int mdiff=0;
          int ddiff=0;
          mdiff=calendar2.get(calendar2.MONTH)+12*ydiff-calendar1.get(calendar1.MONTH);
          ddiff=calendar2.get(calendar2.DAY_OF_MONTH)-calendar1.get(calendar1.DAY_OF_MONTH);

          int fine=0;
          int interest=0;
          int ram=0;
          if(ddiff<0 && mdiff>1)
          {
              System.out.println("<0>1");
              System.out.println(ddiff+" "+mdiff+" "+ydiff);
              mdiff--;

              ddiff=calendar3.getMaximum(calendar3.DATE)-calendar1.get(calendar3.DAY_OF_MONTH)+calendar2.get(calendar1.DAY_OF_MONTH);
              System.out.println(calendar3.getMaximum(calendar3.DATE)+"   "+calendar1.get(calendar3.DAY_OF_MONTH)+"   "+calendar2.get(calendar1.DAY_OF_MONTH));
              System.out.println(ddiff+" "+mdiff+" "+ydiff);
          }
          if(mdiff==0)
          {
              System.out.println("m<0");
             return null;
          }
          else if(mdiff == 1 && ddiff < 0)
          {
              System.out.println("m=1 d<0");
             return null;
          }
          else if(mdiff ==1 && ddiff>=0)
          {
              System.out.println("m=1 d>0");
              interest+=Math.round(balance*2.0/100);
              ram+=recurring*person;
              for(int j=0;j<ddiff;j++)
              {
                   if(j==0)
                   {
                     fine+=11*person;
                   }
                   else
                   {
                     fine+=5*person;
                   }
              }
          }
          else if(mdiff >1)
          {
              System.out.println("m>1");
              //for(int i=0;i<mdiff;i++)
              //{
                   fine=(11+29*5)*person*(mdiff-1);
                   interest+=Math.round(balance*2.0/100)*(mdiff);
                   for(int j=0;j<ddiff;j++)
                   {
                       if(j==0)
                       {
                         fine+=11*person;
                       }
                       else
                       {
                         fine+=5*person;
                       }
                   }
                   ram=recurring*person*mdiff;
              System.out.println("    "+ram+"    "+fine+"    "+interest);
          }
          System.out.println(ddiff+" "+mdiff+" "+ydiff);
          a=new int[2];
          a[0]=(balance+interest);
          a[1]=interest;
          for(int i:a)
          {
              System.out.println("    "+i);
          }
          return a;

    }

}
