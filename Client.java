package DungeonAdventures; //specifica che ci troviamo nel package DungeonAdventures, importante eseguire il programma dalla directory principale (..) perchè altrimenti java risalendo il classpath non trova il package

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static final int port = 12345;
    public static final String ipServer = "127.0.0.1";

    public static void main(String[] args) {

        try (Socket s = new Socket(ipServer, port);
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                BufferedReader sc = new BufferedReader(new InputStreamReader(System.in))) {
            String risposta = "";
            boolean rematch = false;

            do {
                System.out.println("Cosa vuoi fare?");
                System.out.println("1) Combatti ");
                System.out.println("2) Bevi pozione");
                System.out.println("3) Esci");
                System.out.print("Scelta: ");
                int scelta = Integer.parseInt(sc.readLine());
                System.out.println();

                if (scelta == 3)
                    break;

                else {
                    // si manda la scelta al server e stiamo in attesa di dati provenienti dal
                    // server

                    out.writeInt(scelta);
                    out.writeBoolean(rematch);
                    out.flush();

                    System.out.println(in.readLine()); // stampo l'esito del combattimento
                    System.out.println();
                    
                    // gestione esito del combattimento
                     int res = in.read(); //leggo il valore bool che manda il server (0 false 1 true)
                     
                     if (res == 1){
                     System.out.println("Combattimento finito! Vuoi rigiocare? (Y/n)");
                     if ((risposta = sc.readLine()).equals("Y")){
                     //mando un messaggio al server con il quale creo una nuova partita
                     rematch = true;
                     out.writeBoolean(rematch);
                     
                     rematch = false; //altrimenti vado a creare sempre un nuovo giocatore nel server
                     }else break; 
                    }
                }

            } while (true);

            }catch (IOException e) {
            e.printStackTrace();
            }
        }
    }

