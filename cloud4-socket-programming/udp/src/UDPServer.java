import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer {
    public static void main(String[] args) {
        DatagramSocket socket = null;

        try{
            socket = new DatagramSocket(9876);
            byte[] recievedData = new byte[1024];

            while(true){
                DatagramPacket recievedPacket = new DatagramPacket(recievedData, recievedData.length);
                socket.receive(recievedPacket);

                String message = new String(recievedPacket.getData(), 0, recievedPacket.getLength());
                System.out.println("수신된 클라이언트 메시지: " +message);
                // 추가된 코드 : send
                String sendMessage = "공지사항 봇: "+message;
                byte[] sendBytes = sendMessage.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(sendBytes, sendBytes.length, recievedPacket.getAddress(), recievedPacket.getPort());
                socket.send(datagramPacket);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if(socket != null){
                socket.close();
            }
        }
    }
}