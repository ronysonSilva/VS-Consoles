package vs_2021;
import java.util.concurrent.Semaphore;
import java.util.Scanner;

public class Main extends Thread{

    static int quantidadeConsumidores = 12;
    static int quantidadeProdutores = 3;
    static int tempoDeEspera = 1000;
    public static void main(String[] args) {

        Semaphore semaforoProdutor = new Semaphore(5);
        Semaphore semaforoConsumidor = new Semaphore(5);
        Semaphore mutex = new Semaphore(1);
        Scanner scanner = new Scanner(System.in); //recebe os inputs do jogador.
        
        System.out.println("Insira o tempo até que adormeça.");
        tempoDeEspera = scanner.nextInt(); //é utilizado pelo produtor e consumidor em seu sleep e pela main para definir o tempo "total da simulação"
        System.out.println("Insira a quantidade de consumidores");
        quantidadeConsumidores = scanner.nextInt();
        System.out.println("Insira a quantidade de produtores");
        quantidadeProdutores = scanner.nextInt();

        Produtor[] produtor =  new Produtor[quantidadeProdutores];
        Consumidor[] consumidor = new Consumidor[quantidadeConsumidores];
        
        
        Buffer buffer = new Buffer(5, semaforoProdutor, semaforoConsumidor, mutex); //buffer é iniciado
        
        //Produtores e Consumidores são iniciados abaixo.
        for (int i = 0; i < quantidadeProdutores; i++) 
        {
            produtor[i] = new Produtor(buffer, i, tempoDeEspera / 10);
            produtor[i].start();
        }
        
        for (int i = 0; i < quantidadeConsumidores; i++)
        {
            consumidor[i] = new Consumidor(buffer,i, tempoDeEspera / 10);
            consumidor[i].start();
        } 
        
        
        try {
            Thread.sleep(100 + tempoDeEspera); // Caso o valor seja 0 o 100 garante que não seja exatos 0.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        for(int i = 0; i < quantidadeProdutores; i++) {

            System.out.println("Produtor " + produtor[i].id + " produziu um total de : " + produtor[i].produzido); //Quanto foi produzido.
        	produtor[i].stop();
        }
        for(int i = 0; i < quantidadeConsumidores; i++) {
            System.out.println("Consumidor " + consumidor[i].id + " consumiu um total de : " + consumidor[i].consumiu); //Quanto foi consumido.
        	consumidor[i].stop();
        }

    }
}