package com.example.pocket_monsters;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.pocket_monsters.LoginActivity.Debug;

public class DatabaseConnection {
	
	public static String result = null;
	public static InputStream stream = null;
	
	public void connect(){
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://dainbramaged.me/php/db_connection.php");
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			stream = entity.getContent();
		} catch(Exception e){
			Debug.out("Could not connect");
		}
		
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream,"iso-8859-1"),8);
			StringBuilder builder = new StringBuilder();
			String line = null;
			while( (line = reader.readLine()) != null ){
				builder.append(line+"\n");
			}
			stream.close();
			result = builder.toString();
		}catch(Exception e){
			Debug.out("Could not get content");
		}
		
		try{
			String s = "";
			JSONArray jArray = new JSONArray(result);
			for ( int i=0; i<jArray.length(); i++ ){
				JSONObject json = jArray.getJSONObject(i);
				s = s + "ID: "+json.getString("id") + "Name: "+json.getString("name");
			}
			Debug.out(s);
			
		}catch(Exception e){
			Debug.out("Could not parse content");
		}
	}
}
