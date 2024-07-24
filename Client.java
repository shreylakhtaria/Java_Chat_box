import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client implements Runnable {

    private volatile boolean done;
    private BufferedReader in;
    private PrintWriter out;
    private Socket client;
    
    @Override
    public void run() {
        try {
            client = new Socket("127.0.0.1", 9999);

            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            InputHandler inputHandler = new InputHandler();
            Thread t = new Thread(inputHandler);
            t.start();

            String inMessage;
            while ((inMessage = in.readLine()) != null) {
                System.out.println(inMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            shutdown();
        }
    }

    public void shutdown() {
        done = true;

        try {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
            if (client != null && !client.isClosed()) {
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class InputHandler implements Runnable {
        @Override
        public void run() {
            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                while (!done) {
                    String message = input.readLine();
                    if (message.equals("/quit")) {
                        out.print(message);
                        input.close();
                    } else {
                        out.println(message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                shutdown();
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        Thread clientThread = new Thread(client);
        clientThread.start();
    }
}