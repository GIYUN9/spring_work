package com.kh.data.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.data.model.vo.AirVO;

public class Run {
	public static final String SERVICE_KEY = "IDeAmTcA3vN8H1G68cBcJZ4rRU%2BBI0IMjGXD3cYPVLAzogioccmFQ0DrVl27K4Y6OkMvgj8s%2FnPEFtwuziT9FA%3D%3D";
	//나의 고유키 깃에 지우고 올리거나 xml파일에 저장하고 깃이그노어하기!
	public static void main(String[] args) throws IOException {
		//OpenAPI서버로 요청하는 url만들기
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + SERVICE_KEY;
		url += "&sidoName=" + URLEncoder.encode("서울" ,"UTF-8"); //요청값에 한글이있다면 인코딩 해줘야한다.
		url += "&returnType=json";
		
//		System.out.println(url);
		
		//자바코드로 요청을 보내는 방법
		
		//*HttpURLConnection 객체를 활용해서 OpenApi요청 절차
		//1. 요청하고자하는 url을 전달하면서 java.net.URL객체를 생성
		URL requestUrl = new URL(url);
		
		//2. 1번 과정으로 생성된 URL객체를 가지고 HttpURLConnection 객체 생성
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		
		//3. 요청에 필요한 Header값 설정하기
		urlConnection.setRequestMethod("GET");
		
		//4. 해당 OpenAPI서버로 요청을 보낸 후 입력데이터로 읽어오기
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String responseText = "";
		String line;
		
		while((line = br.readLine()) != null) {
			responseText += line;
		}
		System.out.println(responseText);
		
		// JSONObject, JSONArray -> JSON라이브러리에서 제공하는 객체
		// JsonObject, JsonArray -> GSON에서 제공하는 객체, 파싱은 gson으로 하는경우가 많다.
		// 각각의 item정보 => AirVO에 담아서 ArrayList에 차곡차곡담기
		
		JsonObject totalObj = JsonParser.parseString(responseText).getAsJsonObject();
		System.out.println(totalObj);
		
		JsonObject responseObject = totalObj.getAsJsonObject("response"); // response속성에 접근 : {} JsonObject
		System.out.println(responseObject);
		
		JsonObject bodyObject = responseObject.getAsJsonObject("body"); // body속성에 접근 : {} JsonObject
		System.out.println(bodyObject);
		
		int totalCount = bodyObject.get("totalCount").getAsInt();
		System.out.println(totalCount);
		
		JsonArray itemArr = bodyObject.getAsJsonArray("items"); // items속성 접근 [] JsonArray
		System.out.println(itemArr);
		
		ArrayList<AirVO> list = new ArrayList();
		
		for(int i = 0; i < itemArr.size(); i++) {
			JsonObject item = itemArr.get(i).getAsJsonObject();
			
			AirVO air = new AirVO();
			
			air.setStationName(item.get("stationName").getAsString());
			air.setCoValue(item.get("coValue").getAsString());
			air.setDataTime(item.get("dataTime").getAsString());
			air.setKhaiValue(item.get("khaiValue").getAsString());
			air.setNo2Value(item.get("no2Value").getAsString());
			air.setO3Value(item.get("o3Value").getAsString());
			air.setPm10Value(item.get("pm10Value").getAsString());
			air.setSo2Value(item.get("so2Value").getAsString());
			
			list.add(air);
		}
		
		//5. 사용한 스트림 반납
		br.close();
		urlConnection.disconnect();
		
		for(AirVO a : list) {
			System.out.println(a);
		}
	}
}
