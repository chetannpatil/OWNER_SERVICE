package io.chetan.owner.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class Util 
{

/*	public static Properties calculateDueMonths(int duesMonthCount)
	{
		System.out.println("UTIl calculateDueMonths(int duesMonthCount) ");
		Date today =  new Date();
		int thisYear = today.getYear();
		int thisMonth = today.getMonth();
		System.out.println("\n thiseyar = "+thisYear);
		System.out.println("\n thismonth = "+thisMonth);
		
		//Map<Integer, Integer> duesYearNMonthsMap = new LinkedHashMap<Integer,Integer>();
		Properties duesYearNMonthsProperty = new Properties();
		for(int i=1;i<duesMonthCount;i++)
		{
			System.out.println("\n for i = "+i);
			int month = thisMonth - i;
			System.out.println("month diff = "+month);
			if(month >= 0)
			{
				System.out.println("\n yes month doff > 0");
				duesYearNMonthsProperty.put(thisYear, month);
			}
			else
			{
				System.out.println(" no moth diff is negativ ");
				duesYearNMonthsProperty.put(thisYear-1,11);
				thisYear --;
				thisMonth = 10;
				System.out.println("year aftr substn = "+thisYear);
				System.out.println("\n thismonth aftr setting = "+thisMonth);
			}
		}
		
		return duesYearNMonthsProperty ;
		
		
	}*/
	
	public static List<String> calculateDuesMonthNYear(int duesMonthCount)
	{
		System.out.println("UTIl calculateDueMonths(int duesMonthCount) ");
		Date today =  new Date();
		int thisYear = today.getYear()+1900;
		
		int thisMonth = today.getMonth()+1;
		System.out.println("\n thiseyar = "+thisYear);
		System.out.println("\n thismonth = "+thisMonth);
		
		//Map<Integer, Integer> duesYearNMonthsMap = new LinkedHashMap<Integer,Integer>();
		//Properties duesYearNMonthsProperty = new Properties();
		//StringBuilder sb = new StringBuilder();
		List<String> duesYearNMonthsList =  new ArrayList<String>();
		for(int i=0,k=1;i<duesMonthCount;i++,k++)
		{
			System.out.println("\n for i = "+i+"and k ="+k+" fro thsmoth = "+thisMonth+" thiyear = "+thisYear);
			
			//int month = thisMonth - i;
			int month = thisMonth - k;
			System.out.println("month diff = "+month);
			if(month >= 0)
			{
				System.out.println("\n yes month doff > 0");
				//duesYearNMonthsProperty.put(thisYear, month);
				//sb.append(thisYear+" - "+(month));
				System.out.println("\n added monthYear = ");
				System.out.println(thisYear+" - "+(getUserReadableDuesMonthNYear(month)));
				duesYearNMonthsList.add(thisYear+" - "+(getUserReadableDuesMonthNYear(month)));
			}
			else
			{
				System.out.println(" no moth diff is negativ ");
				//duesYearNMonthsProperty.put(thisYear-1,11);
				System.out.println("\n added monthYear = ");
				System.out.println((thisYear-1)+" - "+"DEC");
				duesYearNMonthsList.add((thisYear-1)+" - "+"DEC");
				thisYear --;
				thisMonth = 11;
				k =0;
				System.out.println("year aftr substn = "+thisYear);
				System.out.println("\n thismonth aftr setting = "+thisMonth);
			}
		}
		
		//return duesYearNMonthsProperty ;
		return duesYearNMonthsList ;
	}
	
	public static String getUserReadableDuesMonthNYear(int month)
	{
		//return 
		
			switch (month) 
			{
			case 0: return "JANUARY";
				//break;
			case 1: return "FEBRUARY";
				//break;	
			case 2: return "MARCH";
			
			case 3: return "APRIL";
			
			case 4: return "MAY";
			
			case 5: return "JUNE";
			
			case 6: return "JULY";
			
			case 7: return "AUGUST";
			
			case 8: return "SEPTEMBER";
			
			case 9: return "OCTOBER";
			
			default:  return "NOVEMBER";
				//break;
			}
		
	}
	
	public static int getTotalMonths(Date inMatesJoinedDate)
	{
		Date today = new Date();
		
		System.out.println("\ntoday.getYear() = "+today.getYear());
		System.out.println("\ninMatesJoinedDate.getYear() -1 = "+(inMatesJoinedDate.getYear() -1));
		int yearCount = (today.getYear() - (inMatesJoinedDate.getYear()))-1;
		System.out.println("\n\n yearcount = "+yearCount);
		//int monthCount = Math.abs(today.getMonth() - inMatesJoinedDate.getMonth()) ;
		int monthCount = 0;
		if(today.getYear() == inMatesJoinedDate.getYear())
		{
		   	
			monthCount = (today.getMonth()+1) - inMatesJoinedDate.getMonth();
		}
		else
		{
			 monthCount = (today.getMonth()+1)+(12-inMatesJoinedDate.getMonth());
				System.out.println("\nmonthcount = "+monthCount);
		}
	
		if(yearCount <= 0)
		{
			yearCount = 0 ;
		}
		int totalMonths = (yearCount *12) +monthCount ;
		
		return totalMonths ;
	}
	
	public static List<Date> generatePaymentMonths(int numberOfMonthsInMateWantsToPay) throws ParseException
	{
		List<Date> payMentMonthsList = new ArrayList<Date>();
		Date today = new Date();
		int thisYear = today.getYear()+1900;
		int thisMonth = today.getMonth()+1;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for(int i=0;i<numberOfMonthsInMateWantsToPay;i++)
		{
			System.out.println("\n Util genrtpymnt() -> fro i = "+i);
			System.out.println("today.getDay() = "+today.getDay());
			System.out.println("(today.getMonth()-i) = "+(thisMonth-i));
			System.out.println("\n thisYear = "+thisYear);
			String dateStr = today.getDay()+"/"+(thisMonth-i)+"/"+thisYear;
			System.out.println("prscble dateStr "+dateStr);
			Date payMentDate = sdf.parse(dateStr);
			payMentMonthsList.add(payMentDate);
			System.out.println("\npaymentDate = ");
			System.out.println(payMentDate);
		}
		System.out.println("size of pamntslist b4 return = "+payMentMonthsList.size());
		return payMentMonthsList;
	}
}
