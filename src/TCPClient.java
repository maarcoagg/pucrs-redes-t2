import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    public static void main (String [] args ) throws IOException {

        String filePath = selectFile();

        Socket s = new Socket("localhost",9876);

        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        FileInputStream fis = new FileInputStream(filePath);
        byte[] sendData = new byte[10*1024];

        while (fis.read(sendData) > 0)
        {
            dos.write(sendData);
        }

        fis.close();
        dos.close();
        s.close();
    }

    public static String selectFile()
   {
      // guarda nome dos arquivos para montar os paths
      final String tinyFileName = "file_less_then_1500bytes.txt";
      final String bigFileName = "file_more_then_10000bytes.txt";
      final String srcDir = System.getProperty("user.dir");

      // monta path dos arquivos a serem enviados
      String absoluteTinyFilePath = srcDir + File.separator + "send" + File.separator + tinyFileName;
      String absoluteBigFilePath = srcDir + File.separator + "send" + File.separator + bigFileName;
      String filePath = null;

      // pergunta ao cliente qual arquivo deseja enviar
      System.out.print("\t1 - " + tinyFileName + "\n" +
                        "\t2 - " + bigFileName + "\n"+
                        "Selecione qual arquivo enviar (1-2): ");
      Scanner input = new Scanner(System.in);
      Integer option = input.nextInt();

      if (option.equals(1))
         filePath = absoluteTinyFilePath;
      else if (option.equals(2))
         filePath = absoluteBigFilePath;
      else
      {
         System.err.println("Opcao invalida! Abortando execucao...");
         System.exit(1);
      }
      input.close();
      return filePath;
   }
}