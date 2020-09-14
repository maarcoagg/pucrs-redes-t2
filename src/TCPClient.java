import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.net.Socket;

public class TCPClient {
    public static void main (String [] args ) throws IOException {
        
        final String fileName = "received_file.txt";
        final String srcDir = System.getProperty("user.dir");

        // monta path dos arquivos a serem enviados
        String absoluteFilePath = srcDir + File.separator + "receive" + File.separator + fileName;

        final String SERVER = "127.0.0.1";  // localhost
        final String FILE_TO_RECEIVED = absoluteFilePath;  

        final int FILE_SIZE = 10*1024;  
        
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        try
        {
            sock = new Socket(SERVER, 9876);
            System.out.println("Connecting...");

            // receive file
            byte [] mybytearray  = new byte [FILE_SIZE];
            InputStream is = sock.getInputStream();
            fos = new FileOutputStream(FILE_TO_RECEIVED);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead;

            do {
                bytesRead =
                    is.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);

            bos.write(mybytearray, 0 , current);
            bos.flush();
            System.out.println("File " + FILE_TO_RECEIVED
                + " downloaded (" + current + " bytes read)");
        }
        finally {
            if (fos != null) fos.close();
            if (bos != null) bos.close();
            if (sock != null) sock.close();
        }
    }
}