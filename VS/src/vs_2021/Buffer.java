package vs_2021;

import java.util.concurrent.Semaphore;

public class Buffer {

    Semaphore semaforoProdutor;
    Semaphore semaforoConsumidor;
    Semaphore mutex;
    int value[];
    boolean livre;
    int parteinicial;
    int partefinal;
    int max;

    public Buffer(int max, Semaphore semaforoProdutor, Semaphore semaforoConsumidor, Semaphore mutex) {
        this.max = max;
        this.semaforoProdutor = semaforoProdutor;
        this.semaforoConsumidor = semaforoConsumidor;
        this.mutex = mutex;
        parteinicial = 0;
        partefinal = 0;
        value = new int[this.max];
        livre = false;
    }
//Inserir e remover valores
    public void InsertItem(int v) {
        try {
        	semaforoProdutor.acquire();
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (VerificarProx(parteinicial) != partefinal) {
            value[parteinicial] = v;
            parteinicial = VerificarProx(parteinicial);
        }
        semaforoProdutor.release();
    }
    public int Remove_Item() {
        int valor= 0;
        try {
        	semaforoConsumidor.acquire();
            partefinal = VerificarProx(partefinal);
            if (partefinal == 0) {
            	valor = value[max - 1];
            } else {
            	valor = value[partefinal - 1];
            }
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        semaforoConsumidor.release();
        return valor;
    }

    public boolean EstaDisponivel() { //verificação se está disponível para uso.
        boolean disponivel = false;
        try {
        	semaforoProdutor.acquire();
        	disponivel = partefinal != parteinicial;
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        semaforoProdutor.release();
        return disponivel;
    }

    int VerificarProx(int n) { //verifica se o próximo espaço está livre.
        int tmpN = 0;
        try {
            mutex.acquire();
            if (n + 1 >= max) {
                tmpN = 0;
            } else {
                tmpN = n + 1;
            }
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        mutex.release();
        return tmpN;

    }
}
