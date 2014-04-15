/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pack;

/**
 *
 * @author my
 */
public class DateConverter {

    public static String toDDMMYYYY(String inDate)
    {
        String outDate="";
        String temp[]=inDate.split("-");
        if(temp[0].length()==4)
        {
            outDate=temp[2]+"-"+temp[1]+"-"+temp[0];
            return outDate;
        }
        return inDate;
    }
    public static String toYYYYMMDD(String inDate)
    {
        String outDate="";
        String temp[]=inDate.split("-");
        if(temp[0].length()<=2)
        {
            outDate=temp[2]+"-"+temp[1]+"-"+temp[0];
            return outDate;
        }
        return inDate;
    }
}
