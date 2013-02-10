package com.example.mythirdapp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.example.mythirdapp.response.CurrentWeather;
import com.example.mythirdapp.response.WeatherReport;
import com.example.mythirdapp.response.Wind;
import com.example.mythirdapp.util.JSONParser;

public class ThirdMainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "";
	 String result = ""; 
	 private static final String TAG_WEATHER = "weather";
	 private static final String TAG_CURRENT_WEATHER = "curren_weather";
	 private static final String TAG_HUMIDITY = "humidity";
	 private static final String TAG_PRESSURE = "pressure";
	 private static final String TAG_TEMP = "temp";
	 private static final String TAG_TEMP_UNIT = "temp_unit";
	 private static final String TAG_WEATHER_CODE = "weather_code";
	 private static final String TAG_WEATHER_TEXT = "weather_text";
	 private static final String TAG_WIND = "wind";
	 private static final String TAG_DIRECTION = "dir";
	 private static final String TAG_SPEED = "speed";
	 private static final String TAG_WIND_UNIT = "wind_unit";
	 
	
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_third_main);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.activity_third_main, menu);
       return true;
   }
   
   /** Called when the user clicks the Send button */
   @SuppressLint({ "NewApi", "NewApi", "NewApi" })
	@TargetApi(10)
	public void getWeatherReport(View view) {
   	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
   	StrictMode.setThreadPolicy(policy);
   	
       // Do something in response to button
   	Intent intent = new Intent(this, DisplayWeatherReportActivity.class);
  
   	EditText editText = (EditText) findViewById(R.id.edit_message);
   	String location = editText.getText().toString();
   	String message = getWeather(location);
   	intent.putExtra(EXTRA_MESSAGE, message);
   	startActivity(intent);
   }
   
   //get actual weather report
   private String getWeather(String location){
   	String weatherReport = "";
   	
   	System.out.println("Before Calling WebService");
   
   	ConnectivityManager connMgr = (ConnectivityManager) 
       getSystemService(Context.CONNECTIVITY_SERVICE);
   	NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
   	if (networkInfo != null && networkInfo.isConnected()) {
   		//make webservice call to get json response
   		String jsRes = this.callWebService(location);
   		if (null != jsRes && !"".equals(jsRes)){
   			//call json parser to parse the webservice response and get json object
   			JSONObject jobj = this.getJSONObject(jsRes);
   			
   			if(null != jobj){
   				//get weatherreport object
   				WeatherReport wReport = this.buildWeatherReport(jobj);
   				StringBuilder sb = new StringBuilder();
   				
   				ArrayList<CurrentWeather> wCurRep = wReport.getCurr_weather();
   				
   				sb.append("Weather at " + location);
   				sb.append("\n");
   				
   				for(CurrentWeather currWeath: wCurRep){
   					sb.append(TAG_TEMP.toUpperCase());
   					sb.append(": ");
   					sb.append(currWeath.getTemp());
   					sb.append(" ");
   					sb.append(currWeath.getTempUnit().toUpperCase());
   					sb.append("\n");
   					sb.append(TAG_PRESSURE.toUpperCase());
   					sb.append(": ");
   					sb.append(currWeath.getPressure());
   					sb.append("\n");
   					sb.append(TAG_WEATHER_TEXT.toUpperCase());
   					sb.append(": ");
   					sb.append(currWeath.getWeatherText());
   				}
   				weatherReport = sb.toString();
   			} else {
   				weatherReport = jsRes;
   			}
   		}
   		 
   	} else {
   		
   		System.out.println("Network Not Connected");
   		weatherReport = location + " will be at 54F / 34F";
   		
   	}
   	return weatherReport;
   }
   
   //calling webservice
   private String callWebService(String q){  
   	String URL = "http://www.myweather2.com/developer/forecast.ashx?uac=wH3ME-.9qA&query=43012&temp_unit=f&ws_unit=kph&output=json";  
       
       String deviceId = "5554" ;   
       final String tag = "Your Logcat tag: ";  
       System.out.println("1");
       HttpClient httpclient = new DefaultHttpClient();  
       System.out.println("2");
       HttpGet request = new HttpGet(URL);  
       System.out.println("3");
       //request.addHeader("deviceId", deviceId);  
       ResponseHandler<String> handler = new BasicResponseHandler();
       System.out.println("4");
       try {  
           result = httpclient.execute(request, handler);
           System.out.println("5");
       } catch (ClientProtocolException e) {  
           e.printStackTrace();  
       } catch (IOException e) {  
           e.printStackTrace();  
       } catch (Exception ex){
       	ex.printStackTrace();
       }
       httpclient.getConnectionManager().shutdown();   
       System.out.println("6");
       Log.i(tag, result);  
       return result;
   } // end callWebService()  
   
 //parse JSON Object to get WeatherReport Object
	public WeatherReport buildWeatherReport(JSONObject jobj) {
		WeatherReport wReport = new WeatherReport();
		
		ArrayList<CurrentWeather> cwList = new ArrayList<CurrentWeather>();
		
		
		try{
			//get weather object 
			JSONObject weather = jobj.getJSONObject(TAG_WEATHER);
			
			//get current weather array
			JSONArray currWeather = weather.getJSONArray(TAG_CURRENT_WEATHER);
			
			//looping through current weather array
			for(int i=0; i < currWeather.length(); i++){
				JSONObject cw = currWeather.getJSONObject(i);
				CurrentWeather cWeather = new CurrentWeather();
				ArrayList<Wind> windList = new ArrayList<Wind>();
				
				cWeather.setHumidity(cw.getString(TAG_HUMIDITY));
				cWeather.setPressure(cw.getString(TAG_PRESSURE));
				cWeather.setTemp(cw.getString(TAG_TEMP));
				cWeather.setTempUnit(cw.getString(TAG_TEMP_UNIT));
				cWeather.setWeatherCode(cw.getString(TAG_WEATHER_CODE));
				cWeather.setWeatherText(cw.getString(TAG_WEATHER_TEXT));
				
				JSONArray wnd = cw.getJSONArray(TAG_WIND);
				for(int k=0; k < wnd.length(); k++){
					JSONObject jwind = wnd.getJSONObject(i);
					Wind wind = new Wind();
					wind.setDirection(jwind.getString(TAG_DIRECTION));
					wind.setSpeed(jwind.getString(TAG_SPEED));
					wind.setWindUnit(jwind.getString(TAG_WIND_UNIT));
					
					windList.add(wind);
				}
				
				cWeather.setWind(windList);
				cwList.add(cWeather);
			}
			wReport.setCurr_weather(cwList);
			
		} catch (JSONException ex){
			ex.printStackTrace();
		}
		return wReport;
	}
	
	
	//parse webservice json response to get json object
	public JSONObject getJSONObject(String jsRes) {
		JSONObject jobj = null;
		
		try{
			InputStream is = new ByteArrayInputStream(jsRes.getBytes("UTF-8"));
			JSONParser jsonParser = new JSONParser();
			jobj = jsonParser.getJSONFromUrl(is);
			
		} catch(IOException ex){
			ex.printStackTrace();
		}
		return jobj;
	}
}
