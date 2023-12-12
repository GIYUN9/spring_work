package com.kh.data.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class test {
	public static final String SERVICE_KEY = "IDeAmTcA3vN8H1G68cBcJZ4rRU%2BBI0IMjGXD3cYPVLAzogioccmFQ0DrVl27K4Y6OkMvgj8s%2FnPEFtwuziT9FA%3D%3D";
	
	public static void main(String[] args) throws IOException {
		String url = "http://apis.data.go.kr/1741000/TsunamiShelter3/getTsunamiShelter1List";
		url += "?serviceKey=" + SERVICE_KEY;
		url += "&numOfRows=2" ; //요청값에 한글이있다면 인코딩 해줘야한다.
		url += "&returnType=xml";

		URL requestUrl = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		urlConnection.setRequestMethod("GET");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
//		String responseText = "";
		String line;
		
		while((line = br.readLine()) != null) {
			System.out.println(line);
//			responseText += line;
		}
		
		br.close();
		urlConnection.disconnect();
	}
}
