import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

import java.util.concurrent.Executors;
public class Server implements Runnable 
{
    private ArrayList<ConnectionHandler> connection; // list of clients
    private ServerSocket server; // server socket
    private boolean done; // flag to check if the server is done or not
    private ExecutorService pool; // thread pool

    public Server() {
        done = false; // set the done flag to false
        connection = new ArrayList<>(); // create a new array list of connection handler
    }

    @Override // override the run method of Runnable interface
    public void run() {
        try {
            server = new ServerSocket(9999); // create a server socket on port 9999
            pool = Executors.newCachedThreadPool();
            while (!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connection.add(handler);
                pool.execute(handler); // execute the handler in the thread pool
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    public void broadcast(String message) {
        for (ConnectionHandler ch : connection) {
            if (ch != null) {
                ch.sendMessage(message);
            }
        }
    }

    public void shutdown() {
        try {
            done = true;
            if (!server.isClosed()) {
                server.close();

            }
            for (ConnectionHandler ch : connection) {
                if (ch != null) {
                    ch.sendMessage("Server is shutting down");
                    ch.shutdown();
                }
            }
        } catch (IOException e) {
            // ignore
        }
    }

    class ConnectionHandler implements Runnable { // handle the client connection.
        private Socket client;
        private BufferedReader in; // input from the client
        private PrintWriter out; // output to the client
        private String nickname; // nickname of the client

        public ConnectionHandler(Socket client) {
            this.client = client; // this pointer to refer to the current object
        }

        @Override // override the run method of Runnable interface
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(), true); // auto flush true so we do not flush it manually
                                                                       // when client write to the server
                in = new BufferedReader(new InputStreamReader(client.getInputStream())); // read from the client
                out.println("Enter the Nickname:");
                // out.println("Enter your gender: ");
                out.flush();
                nickname = in.readLine();
                /*
                 * add the necessary if condition as per requirement if string is empty or null
                 * then ask the user to enter the nickname again
                 */
                System.out.println(nickname + " Connected"); // for server only
                System.out.println(nickname + " Connected And joined the chat "); // for server only
                broadcast(nickname + " Connected And joined the chat "); // for all

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("/nick ")) {
                        String[] messageSplit = message.split(" ", 2);
                        if (messageSplit.length == 2) {
                            broadcast("Nickname renamed themselves to " + messageSplit[1]); // for all clients
                            System.out.println(nickname + " renamed themselves to " + messageSplit[1]); // for server//

                            nickname = messageSplit[1];
                            out.println("Successfully nickname changed to: " + nickname); // for the client only
                        } else {
                            out.println("Invalid nickname"); // for the client only
                        }
                    } else if (message.startsWith("/quit")) {
                        broadcast(nickname + " has left the chat"); // for all clients
                        shutdown();
                    } else {
                        broadcast(nickname + " : " + message); // for all clients
                    }
                }
            } catch (IOException e) {
                shutdown();
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }

        public void shutdown() {
            try {
                in.close();
                out.close();
                if (!client.isClosed()) {
                    client.close();
                }
                connection.remove(this);
            } catch (IOException e) {
                // ignore
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}
