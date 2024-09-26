import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;

public class HttpRequestBySocket {
    private static final String HOST = "api.openweathermap.org";
    private static final int PORT = 443;

    public static void main(String[] args) {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

        try(Socket socket = factory.createSocket(HOST, PORT);

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);

            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            writer.println("GET /data/2.5/weather?q=seoul&appid=802f611842d9fbf242d026e9f227217d HTTP/1.1");
            writer.println("Host: " +HOST);
            writer.println("User-Agent: Chrome");
            writer.println("Connection: close");
            writer.println();

            StringBuilder response = new StringBuilder();
            String line;
            boolean isContent = false;
            while((line = reader.readLine()) != null){
                if(!isContent){
                    if(line.isEmpty()){
                        isContent = true;
                    }
                    continue;
                }
                response.append(line);
            }
            System.out.println(response.toString());
        } catch (IOException e){
            System.out.println("IO Exception: " + e.getMessage());
        }
    }
}
