/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package popo.defcon;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import java.security.Principal;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpHost;
import org.apache.http.auth.Credentials;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Popo
 */
public class Client {
    
    private TwilioRestClient client;

    public Client() {
        
        String accountSid = "xxx";
        String authToken = "xxx";
        this.client = new TwilioRestClient(accountSid, authToken);
        HttpClient http =  new DefaultHttpClient();
        HttpHost proxy = new HttpHost("10.3.100.207", 8080);
        http.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        this.client.setHttpClient(http);
    }

    
    public void sendMessage( String message) {
        List<NameValuePair> params = getParams(message);

        try {
            this.client.getAccount().getMessageFactory().create(params);
        } catch (TwilioRestException exception) {
            exception.printStackTrace();
        }
    }

    private List<NameValuePair> getParams(String message) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("Body", message));
        params.add(new BasicNameValuePair("To", "your phone no"));
        params.add(new BasicNameValuePair("From", "your twilio no"));

        return params;
    }
}

