package com.knowlarity.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

public class HttpConnection {

	private static InputStream openHttpConnection(String url_link) {
        InputStream in = null;
        int resCode = -1;
            try {
                URL url = new URL(url_link);
                URLConnection urlConn = url.openConnection();
                urlConn.setConnectTimeout(3000);
                HttpURLConnection httpConn = (HttpURLConnection)urlConn;
                httpConn.setAllowUserInteraction(false);
                httpConn.setInstanceFollowRedirects(true);
                httpConn.setRequestMethod("GET");
                httpConn.connect();
                resCode = httpConn.getResponseCode();

                if (resCode == HttpURLConnection.HTTP_OK)
                {
                    in = httpConn.getInputStream(); 
                } 
            } catch (MalformedURLException e) {
         
            } catch (SocketTimeoutException e) {
          
            }
            catch (IOException e) {
              
                }
            catch (Exception e) {
               
                }
            return in;
        
        }
	
	
	public static String reasStream(String url_link) throws IOException{
		InputStream is = openHttpConnection(url_link);
		
		BufferedReader br = null;
		String json = null;
		br = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
		
		while ((json = br.readLine()) != null) {
			sb.append(json);
		} 
	

		br.close();
		
		return sb.toString();
	}
	

}
