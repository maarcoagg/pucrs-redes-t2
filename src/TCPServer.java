import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

  public static void main (String [] args ) throws IOException {
    // guarda nome dos arquivos para montar os paths
    //final String tinyFileName = "file_less_then_1500bytes.txt";
    final String bigFileName = "file_more_then_10000bytes.txt";
    final String srcDir = System.getProperty("user.dir");

    // monta path dos arquivos a serem enviados
    //String absoluteTinyFilePath = srcDir + File.separator + "send" + File.separator + tinyFileName;
    String absoluteBigFilePath = srcDir + File.separator + "send" + File.separator + bigFileName;
    //String filePath = null;

    final int SOCKET_PORT = 9876;
    final String FILE_TO_SEND = absoluteBigFilePath;

    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    ServerSocket servsock = null;
    Socket sock = null;
    
    try {
      servsock = new ServerSocket(SOCKET_PORT);
      while (true) {
        System.out.println("Waiting...");
        try {
          sock = servsock.accept();
          System.out.println("Accepted connection : " + sock);
          // send file
          File myFile = new File (FILE_TO_SEND);
          byte [] mybytearray  = new byte [(int)myFile.length()];
          fis = new FileInputStream(myFile);
          bis = new BufferedInputStream(fis);
          bis.read(mybytearray,0,mybytearray.length);
          os = sock.getOutputStream();
          System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
          os.write(mybytearray,0,mybytearray.length);
          os.flush();
          System.out.println("Done.");
        }
        finally {
          if (bis != null) bis.close();
          if (os != null) os.close();
          if (sock!=null) sock.close();
        }
      }
    }
    finally {
      if (servsock != null) servsock.close();
    }
  }
}