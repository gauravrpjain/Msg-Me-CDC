# Msg-Me-CDC

An application to send SMS for each new update/notice on the IIT Kharagpur Noticeboard. It uses Twilio API to send SMS and thus they cost $0.01/sms to your  twilio account. The MsgMeCDC.java scans the noticeboard every 10 mins for new notice/update and then sends it. Client.java takes care of the twilio API and sending the message. 

If you want to use this for your phone. Make an account on Twilio, change the details in Client.java and the build the application. Double click on the .jar (generated after building) or run it from terminal with command >> java -jar Msg-Me-CDC.jar 
