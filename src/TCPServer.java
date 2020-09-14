import java.io.File;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    public static void main (String [] args ) throws IOException {
        
        // guarda nome dos arquivos para montar os paths
        final String fileName = "received_file.txt";
        final String srcDir = System.getProperty("user.dir");

        // monta path do arquivo a ser recebido
        String absoluteFilePath = srcDir + File.separator + "receive" + File.separator + fileName;

        ServerSocket serverSocket = new ServerSocket(9876);

        while(true)
        {
            try
            {
                Socket clientSocket = serverSocket.accept();
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                FileOutputStream fos = new FileOutputStream(absoluteFilePath);
                byte[] receiveData = new byte[10*1024];
                
                int filesize = 10*1024;
                int read = 0;
                int totalRead = 0;
                int remaining = filesize;
                while((read = dis.read(receiveData, 0, Math.min(receiveData.length, remaining))) > 0) {
                    totalRead += read;
                    remaining -= read;
                    System.out.println(totalRead + " bytes lidos.");
                    fos.write(receiveData, 0, read);
                }     
                           
                fos.close();
                dis.close();

                // o lint do java sempre apontava warning para 'serverSocket'
                // pois o mesmo nunca era fechado. portanto este trecho serve 
                // apenas para eliminar este warning.
                if (receiveData.equals(null))
                    break;
            } catch (IOException e) {
                e.printStackTrace();
            }            
        }
        serverSocket.close();        
    }
}