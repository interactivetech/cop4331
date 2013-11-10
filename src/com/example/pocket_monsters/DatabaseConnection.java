package com.example.pocket_monsters;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.pocket_monsters.LoginActivity.Debug;

public class DatabaseConnection {
	
	public HttpClient httpClient;
	public HttpPost httpPost;
	
	public int connect(String path){
		String s = "";
		try {
			httpClient = new DefaultHttpClient();
			httpPost = new HttpPost("http://192.232.218.255:2222/php/"+path);
			return 1;
		} catch(Exception e){
			Debug.out("Could not connect");
			return 0;
		}
	}
	
	public Boolean login(String username, String password){
		Boolean result = false;
		if( connect("login.php") == 1){
			InputStream stream = null;
			try{
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("username",username));
				nameValuePairs.add(new BasicNameValuePair("password",password));
				
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				
				HttpResponse response = httpClient.execute(httpPost);
				
				HttpEntity entity = response.getEntity();
				stream = entity.getContent();
			}catch(Exception e){
				Debug.out("Could not post");
				e.printStackTrace(System.out);
			}
			
			try{
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream,"iso-8859-1"),8);
				String printed = reader.readLine();
				
				stream.close();
				Debug.out(printed);
				
				if( printed.compareTo("1") == 0 ){
					result = true;
				} else {
					result = false;
				}
			}catch(Exception e){
				Debug.out("Could not get content");
			}
		}
		return result;
	}
	
	/*
			try{
				JSONArray jArray = new JSONArray(result);
				for ( int i=0; i<jArray.length(); i++ ){
					JSONObject json = jArray.getJSONObject(i);
					s = s + "ID: "+json.getString("userid") + "Name: "+json.getString("name");
				}
			}catch(Exception e){
				Debug.out("Could not parse content");
			}
		}		
		return s;
	}
	*/
}
