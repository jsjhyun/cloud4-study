import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        int port = 6789;
        ServerSocket welcomeSocket = new ServerSocket(port);

        while(true){
            System.out.println("서버가"+port+"포트에서 클라이언트의 연결을 기다립니다.");
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("클라이언트와 연결되었습니다.");

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            String clientSentence = inFromClient.readLine();
            System.out.println("클라이언트로부터 받은 메시지: "+clientSentence);

            String capitalizedSentence = clientSentence.toUpperCase() + '\n';
            outToClient.writeBytes(capitalizedSentence);
        }
    }
}
