import java.util.ArrayList;

class ArregloCompartido {
    long numeros[];
    long tam, sumaNum;
    int cont;

    public ArregloCompartido(int tam) {
        this.tam = tam;
        sumaNum = 0;
        cont = 0;
        numeros = new long[tam];
        for (int i = 0; i < tam; i++) {
            numeros[i] = i + 1;
            sumaNum = sumaNum + numeros[i];
        }
        System.out.println("La suma debe ser = " + sumaNum);
    }

    public long getNum() {
        return numeros[cont];
    }

    public void addCont() {
        cont++;
    }

    public int getCont() {
        return cont;
    }

    public long getTam() {
        return tam;
    }
}

class Matematico extends Thread {
    ArregloCompartido arregloCompartido;
    long sumPar;

    public Matematico(String nombre, ArregloCompartido arregloCompartido) {
        super(nombre);
        this.arregloCompartido = arregloCompartido;
        sumPar = 0;
    }

    public long getSumaParcial() {
        return sumPar;
    }

    public void run() {
        while (true) {
            synchronized (arregloCompartido) {
                if (arregloCompartido.getCont() < arregloCompartido.getTam()) {
                    sumPar = sumPar + (int) arregloCompartido.getNum();
                    arregloCompartido.addCont();
                    System.out.println(getName() + " : Suma Parcial = " + sumPar);
                } else break;
            }
            yield(); // para tener mayor alternancia
        }
    }
}

public class Matematicos {
    public static void main(String arg[]) {

        long inicio = System.currentTimeMillis();
        ArrayList<Matematico> matematicoArrayList = new ArrayList<>();
        int cantidad_matematicos = 5;
        long suma_total = 0;
        ArregloCompartido arregloCompartido = new ArregloCompartido(100000);

        for (int i = 0; i < cantidad_matematicos; i++) {
            matematicoArrayList.add(new Matematico("Matematico " + i, arregloCompartido));
        }

        for (Matematico m : matematicoArrayList) {
            m.start();
        }

        for (Matematico m : matematicoArrayList) {
            try {
                m.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Matematico m : matematicoArrayList) {
            suma_total = suma_total + m.getSumaParcial();
        }

        System.out.println("SUMA TOTAL = " + suma_total);

        long fin = System.currentTimeMillis();
        double tiempo = (double) ((fin - inicio) / 1000);
        System.out.println(tiempo + " segundos");

    }

}
