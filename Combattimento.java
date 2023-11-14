package DungeonAdventures;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Combattimento implements Runnable {

    private Giocatore g;
    private Mostro m;
    Socket s;

    public Combattimento(Giocatore g, Mostro m, Socket s) {
        this.g = g;
        this.m = m;
        this.s = s;

    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream())) {

            boolean fineCombattimento = false;
            int scelta;
            boolean rigioca = false;

            while (!fineCombattimento && !rigioca) {
                scelta = in.readInt();
                

                switch (scelta) {
                    case 1: {
                        // il giocatore combatte con il mostro
                        int dannoGiocatore = (int) Math.round(Math.random() * g.getHp());
                        g.setHp(g.getHp() - dannoGiocatore);
                        int dannoMostro = (int) Math.round(Math.random() * m.getHp());
                        m.setHp(m.getHp() - dannoMostro);

                        String risultatoRound = "";

                        // considerazioni post combattimento
                        if (g.getHp() == 0 && m.getHp() == 0) {
                            risultatoRound = "Combattimento concluso, mostro e giocatore con HP = 0\n";
                            fineCombattimento = true;
                        }

                        else if (g.getHp() != 0 && m.getHp() == 0) {
                            risultatoRound = "Il giocatore ha vinto\n";
                            fineCombattimento = true;
                        }

                        else if (g.getHp() == 0 && m.getHp() != 0) {
                            risultatoRound = "Il mostro ha vinto\n";
                            fineCombattimento = true;
                        } else {
                            risultatoRound = "Round concluso: HP giocatore -> " + g.getHp() + ", HP mostro -> "

                                    + m.getHp() + "\n";
                        }
                        // mando il risultato del round al client : se fineCombattimento = true devo
                        // mandare al client un messaggio di fine combattimento
                        out.writeChars(risultatoRound);
                        out.writeBoolean(fineCombattimento);
                        out.flush();

                        rigioca = in.readBoolean(); //ricevo la risposta dal client
                        if (rigioca){
                            fineCombattimento = false; 
                            this.g = new Giocatore(); 
                            this.m = new Mostro(); 
                        }
                    }
                    case 2: {
                        // il giocatore beve la pozione

                        if (g.getPozione() == 0) {
                            out.writeChars("Pozione esaurita! \n");
                            out.writeBoolean(fineCombattimento);
                            out.flush();
                            rigioca = in.readBoolean(); //da protocollo
                            break;
                        }

                        int p = (int) Math.round(Math.random() * g.getPozione());
                        g.setHp(g.getHp() + p);
                        g.setPozione(g.getPozione() - p);

                        out.writeChars("Pozione bevuta! HP giocatore -> " + g.getHp() + ", Pozione rimanente -> "
                                + g.getPozione() + ".\n");
                        out.writeBoolean(fineCombattimento);
                        out.flush();
                        rigioca = in.readBoolean(); //da protocollo
                        break;
                    }
                    default: {
                        System.err.println("Azione non specificata");
                        fineCombattimento = true; // termino
                        break;
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                s.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
