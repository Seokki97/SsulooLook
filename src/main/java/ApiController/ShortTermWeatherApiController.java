package ApiController;


import java.io.*;
import java.net.*;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ssuloolook")
public class ShortTermWeatherApiController {
    String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
    String serviceKey = "94IUu6EJxu2UrPcxztU%2FP0MMCn5E5uC7JVsJz14ZJGdxftID5IkhPGpAHAEXpMYon%2BT1N%2Fy%2F6AWODxRq325LIg%3D%3D";
    String nx = "60";    //위도
    String ny = "125";    //경도
    String baseDate = "20190607";    //조회하고싶은 날짜
    String baseTime = "1100";    //조회하고싶은 시간
    String type = "json";    //타입 xml, json 등등 ..

    @GetMapping("/stweather")
    public String restApiGetWeather() throws Exception {
        HashMap<String, Object> resultMap = getDataFromJson(url, "UTF-8", "get", "");

        System.out.println("# Result" + resultMap);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("result", resultMap);

        return jsonObject.toString();
    }

    public HashMap<String, Object> getDataFromJson(String url, String encoding, String type, String jsonStr) throws Exception {
        boolean isPost = false;

        if ("post".equals(type)) {

            if ("post".equals(type)) {
                isPost = true;

            } else {
                url = "".equals(jsonStr) ? url : url + "?request=" + jsonStr;
            }
        }
            return getStringFromUrl(url, encoding, isPost, jsonStr, "applicaion/json");

    }

    public HashMap<String, Object> getStringFromUrl(String url, String encoding
            , boolean isPost, String parameter, String contentType) throws Exception {
        URL apiURL = new URL(url);

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        try {
            httpURLConnection = (HttpURLConnection) apiURL.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setDoInput(true);

            if (isPost) {
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", contentType);
                httpURLConnection.setRequestProperty("Accept", "*/*");
            } else {
                httpURLConnection.setRequestMethod("GET");
            }
            httpURLConnection.connect();

            if (isPost) {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
                bufferedWriter.write(parameter);
                bufferedWriter.flush();
                bufferedWriter = null;
            }

            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), encoding));

            String line = null;

            StringBuffer result = new StringBuffer();

            while ((line = bufferedReader.readLine()) != null) result.append(line);

            ObjectMapper objectMapper = new ObjectMapper();

            resultMap = objectMapper.readValue(result.toString(), HashMap.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(url + " interface Fail" + e.toString());
        } finally {
            if (httpURLConnection != null) httpURLConnection.disconnect();
            if (bufferedReader != null) bufferedReader.close();
            if (bufferedWriter != null) bufferedWriter.close();
        }
        return resultMap;
    }
}
