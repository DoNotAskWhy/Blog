package cn.xhxc.blog.util;

import java.net.InetAddress;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class IdFactory {
	 private static IdFactory _instance  = new IdFactory();
	    private static long max = 999999;
	    private static long saveSecond = 0l;
	    private static String IP_LAST;
	    int i = 1;
	    static {
	      saveSecond = System.currentTimeMillis() / 1000;

	      // saveSecond = saveSecond / 60;
	    }

	    private IdFactory() {
	      try {
	         InetAddress objAddr = InetAddress.getLocalHost();
	         // 获取IP地址
	         String ip = objAddr.getHostAddress();

	         String[] ips = ip.split("\\.");

	         String ip1 = ips[3];

	         int ip1_last = Integer.valueOf(ip1);

	         String ip_3 = format(ip1, 2);

	         IP_LAST = ip_3;

	      } catch (Exception e) {
	         // log.error("get IP_LAST error!", e);
	         e.printStackTrace();
	         String ip_last1 = format(String.valueOf(new Random().nextInt(255)),
	               2);
	         IP_LAST = ip_last1;
	      }
	    }

	    
	    public static IdFactory getInstance() {
	      return _instance ;
	    }

	    
	    private synchronized String getUniqTimer() {

	      StringBuffer sb = new StringBuffer();

	      if (max == i) {
	         long currentTime = System.currentTimeMillis() / 1000;
	         // currentTime = currentTime / 60;

	         if (currentTime <= saveSecond) {
	            saveSecond++;
	         } else {
	            saveSecond = currentTime;
	         }
	         i = 1;

	      } else {
	         i++;
	      }
	      sb.append(saveSecond);

	      String saveTime = format(String.valueOf(saveSecond), 5);

	      String add_i = format(String.valueOf(i), 1);
//	      System.out.println("saveSecond="+saveSecond );
//	      System.out.println("UniqTimer+sequence="+saveTime + add_i);

	      return saveTime + add_i;
	    }

	    
	    public static String getNextKeyValue() {
	      StringBuffer sb = new StringBuffer();
	      sb.append(IP_LAST);
	      sb.append(IdFactory.getInstance().getUniqTimer());
	      // System.out.println("UniqTimer()="+CreateId.getInstance().getUniqTimer());
	      
//	      System.out.println("SequenceId="+sb);
	      return sb.toString();
	    }
	    
	    
	    public static long getID() {
	      StringBuffer sb = new StringBuffer();
	      sb.append(IP_LAST);
	      sb.append(IdFactory.getInstance().getUniqTimer());
	      // System.out.println("UniqTimer()="+CreateId.getInstance().getUniqTimer());
	      
//	      System.out.println("SequenceId="+sb);
	      return Long.valueOf(sb.toString());
	    }

	    
	    private static String format(String str, int len) {
	      if (str.length() < len) {
	         for (int i = 0; i < len - str.length(); i++) {
	            str = "1" + str;
	         }
	         return str;
	      } else {
	         return str;
	      }
	    }

	    public static void main(String args[]) {
	      Set set = new HashSet();
	      for (int i = 0; i < 10; i++) {
//	         set.add(SequenceUtilImpl.getInstance().getNextKeyValue());
	         
	         set.add(IdFactory.getNextKeyValue());
//	         System.out.println("SequenceId()="
//	               + SequenceUtilImpl.getNextKeyValue());
	      }
	      
	      System.out.println("set size=" + set.size());
	      set.forEach(e -> System.out.println(e));
	    }
}
