package vs_2021;

import java.util.Random;

public class Produtor extends Thread {

    Buffer buffer;
    int id;
    int espera;
    Random random = new Random();
    int produzido;

    public Produtor(Buffer buffer, int id, int espera) {
        this.buffer = buffer;
        this.id = id;
        this.espera = espera;
    }

    @Override
    public void run() { //irá inserir valores no buffer para o consumidor.
        while (true) {

            int valor = (int) (random.nextInt(15) + 1); //Garantir que nunca seja produzido 0

            if (!buffer.EstaDisponivel()) {
                buffer.InsertItem(valor);

                System.out.println("Produtor " + id + " produziu o valor " + valor);
                produzido += valor; //adiciona na variável do produtor o quanto ele produziu
            }

            try {
                sleep(100 + (random.nextInt(espera)));
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
