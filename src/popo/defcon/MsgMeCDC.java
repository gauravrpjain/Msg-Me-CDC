/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package popo.defcon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 *
 * @author Popo
 */
public class MsgMeCDC {
    static String oldtime="26-11-2015 01:33";
    String readPage(){
     try{
        URL site = new URL("http://cdc.iitkgp.ernet.in/notice/");
            Proxy p = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("10.3.100.207", 8080));
            HttpURLConnection notice_board = (HttpURLConnection)site.openConnection(p);
           // System.out.println("Proxy in Use: "+notice_board.usingProxy());
            BufferedReader in = new BufferedReader(new InputStreamReader(notice_board.getInputStream()));
            //System.out.println(in.toString());
            String temp;
            StringBuilder http = new StringBuilder();
           // int line = 1;
        while((temp = in.readLine()) != null){
            http.append('\n').append(temp);
            //System.out.println("reading line no" + line++);
        }
        //System.out.println(http);
        return(http.toString());
    }catch(Exception e){
    e.printStackTrace();
    return null;
    }
    }
    
    void Parse(){
    String input = readPage();
        if(input==null){
        System.out.println("Error connecting to Internet");
        return;
    }        
        String time;
        Document cdc = Jsoup.parse(input);
        Elements notices = cdc.getElementsByTag("tbody");
        Elements alerts = notices.get(1).getElementsByTag("tr");
        alerts.remove(0);
        System.out.println("Current Old Time = "+oldtime);
        for (Element node : alerts) {
            Elements content = node.getElementsByTag("td");
            time = content.last().text();
            if(convertTime(time).compareTo(convertTime(oldtime))<=0) {
                MsgMeCDC.oldtime = alerts.get(0).getElementsByTag("td").last().text();
                return;}
            System.out.println("Current notice time :"+convertTime(time));
            Logger.getLogger(MsgMeCDC.class.getName()).log(Level.INFO, "Current notice time :"+convertTime(time));
            //for (Element text : content) {
            //    System.out.println(text.text());
            //}
            String smsTitle = content.get(1).text();
            //String smsCompanyName = content.get(2).text();
            String smsNoticeTime = content.get(4).text();
            String preSMStext = content.get(3).text();
            String randomtext = "Placement/ Internship Form Description Files";
            int start = preSMStext.indexOf(randomtext)+randomtext.length()+1;
            int twilio = "Sent from your Twilio trial account - ".length();
            int end = 150-(smsTitle.length() + smsNoticeTime.length()+ twilio + 2);
            String smsContent = preSMStext.substring(start,start+end);
            String sms = smsTitle+'\n'+smsNoticeTime+'\n'+smsContent;
            System.out.println(sms);
            sendSMS(sms);
            Logger.getLogger(MsgMeCDC.class.getName()).log(Level.INFO, "SMS sent: "+sms );
            Logger.getLogger(MsgMeCDC.class.getName()).log(Level.INFO, "Length of SMS is "+(sms.length()+twilio));
            System.out.println("\nLength of SMS is "+(sms.length()+twilio));
            System.out.println("");
        }
        //System.out.println(notices.toString());
    }
    void sendSMS(String msg){
        Client c = new Client();
        c.sendMessage(msg);
    }
    
    String convertTime(String time){
    String[] s = time.split(" ");
    String[] date = s[0].split("-");
    String result = date[2]+"-"+date[1]+"-"+date[0]+" "+s[1];
    return result;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MsgMeCDC m = new MsgMeCDC();
        while( true){
        m.Parse();
            try {
                System.out.println("\n Starting Sleep");
                Thread.sleep((long)600000);
                System.out.println("\n SLEEP Finished");
            } catch (InterruptedException ex) {
                System.out.println("\n SLEPP FAILED \n");
                Logger.getLogger(MsgMeCDC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       }
    
}
