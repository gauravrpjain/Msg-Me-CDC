# Msg-Me-CDC

An application to send SMS for each new update/notice on the IIT Kharagpur CDC Noticeboard. It uses Java and Twilio API to send SMS, to run the application you just need a server which has JVM installed and a working internet connection (IIT Kharagpur LAN or Wifi, as the CDC noticeboard is hosted on Intranet). 

### Installation

  - Make a twilio account and add the Twilio credentials in the Client.java. 
  - Also add all the phone no.s to which you want to send the sms. 
  - Once that is done build the package to generate a executable .jar file.
  - In Windows based server, double clicking on the .jar file would execute the program, or you can run the following command in terminal: 
  ```sh
  $ java -jar Msg-Me-CDC.jar
  ```
  Replace 'Msg-Me-CDC.jar' with your jar file name. 
  
  - You can check if the program is running or not by opening the task manager and checking if the concerned java process is running or not. 

### How it works

The MsgMeCDC.java scans the CDC noticeboard every 10 mins to check if there is any new notice/update since the past scan by checking the index of the notice posted there. This ensures that it need not read the whole page. The time duration between consecutive scans can be edited in the MsgMeCDC.java. 
Any new notifications are sent to Client.java which then uses the twilio API to sending a SMS to all the phone numbers mentioned there. Officially the Twilio API charge $0.01/SMS but for a trial account it is free. So check the terms and conditions of the twilio account to make sure you will be charged or not.
