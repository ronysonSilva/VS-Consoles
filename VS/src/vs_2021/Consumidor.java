package vs_2021;

import java.util.Random;

public class Consumidor extends Thread {

    Buffer buffer;
    int id;
    int espera;
    int consumiu;
    Random random = new Random();


    public Consumidor(Buffer buffer, int id, int espera) {
        this.buffer = buffer;
        this.id = id;
        this.espera = espera;
    }

    @Override
    public void run() { //enquanto estiver rodando irá consumir pegando do buffer oq foi produzido já.
        while (true) {

            if (buffer.EstaDisponivel()) {
                int valor = buffer.Remove_Item();

                System.out.println("Consumidor " + id + " consumiu o valor " + valor);
                consumiu += valor; //adiciona na variável do consumidor o quanto o mesmo consumiu.
            }

            try {
                sleep(100+ (random.nextInt(espera)));
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
