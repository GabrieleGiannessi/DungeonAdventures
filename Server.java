package DungeonAdventures;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {
    private static final int port = 12345;
    private static ExecutorService p = new ThreadPoolExecutor(4, 4, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    public static void main(String[] args) {

        System.out.println("Benvenuti in Dungeon Adventures!\n");

        try (ServerSocket s = new ServerSocket(port)) {
            while (true) { //rimane sempre attivo
                p.execute(new Combattimento(new Giocatore(), new Mostro(), s.accept())); //thread in cui stabilisco la connessione con il client
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            p.shutdown();
        }
        
    }
}
    