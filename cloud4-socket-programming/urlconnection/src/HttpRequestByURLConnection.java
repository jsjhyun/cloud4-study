import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpRequestByURLConnection {
    public static void main(String[] args) {
        try{
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=seoul&appid=802f611842d9fbf242d026e9f227217d");
            HttpURLConnection conneection = (HttpURLConnection) url.openConnection();

            conneection.setRequestMethod("GET");
            conneection.setRequestProperty("User-Agent", "Chrome");
            conneection.setReadTimeout(30000);

            int responseCode = conneection.getResponseCode();
            System.out.println("HTTP 상태 코드: "+responseCode);

            if(responseCode != HttpURLConnection.HTTP_OK){
                System.out.println("커넥션 에러 발생");
                System.out.println(conneection.getResponseMessage());
                return;
            }

            InputStream inputStream = conneection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = reader.readLine()) != null){
                System.out.println(line);
            }
            reader.close();
        } catch (MalformedURLException e){
            System.out.println("URL Malformed: " + e.getMessage());
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}