/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9_programacion2;

/**
 *
 * @author denam
 */

public class ListaEnlazada<T> {
    
   

    private Nodo<T> cabeza;
    private int tamanio;
    private final int capacidad;

    public ListaEnlazada() {
        cabeza = null;
        tamanio = 0;
        capacidad = Integer.MAX_VALUE;
    }

    public ListaEnlazada(int capacidad) {
        cabeza = null;
        tamanio = 0;
        this.capacidad = capacidad;
    }

    public synchronized void agregar(T dato) {
        while (tamanio >= capacidad) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        agregarInternamente(dato);
        notifyAll();
    }

    public synchronized void agregarEsperando(T dato)
            throws InterruptedException {

        while (tamanio >= capacidad) {
            wait();
        }

        agregarInternamente(dato);
        notifyAll();
    }

    private void agregarInternamente(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);

        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;

            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }

            actual.setSiguiente(nuevo);
        }

        tamanio++;
    }

    public synchronized T eliminarPrimeroEsperando() throws InterruptedException {
        
        while (cabeza == null) {
            wait();
        }

        Nodo<T> actual = cabeza;
        Nodo<T> anterior = null;

        Nodo<T> mejorNodo = cabeza;
        Nodo<T> anteriorMejor = null;

        while (actual != null) {
            if (actual.getDato()
                    instanceof Paquete) {

                Paquete paqueteActual =
                        (Paquete) actual.getDato();

                Paquete mejorPaquete =
                        (Paquete) mejorNodo.getDato();

                if (paqueteActual.getPrioridad().getNivel() < mejorPaquete  .getPrioridad().getNivel()) {

                    mejorNodo = actual;
                    anteriorMejor = anterior;
                }
            }

            anterior = actual;
            actual = actual.getSiguiente();
        }

        if (anteriorMejor == null) {
            cabeza = mejorNodo.getSiguiente();
        } else {
            anteriorMejor.setSiguiente(
                    mejorNodo.getSiguiente()
            );
        }

        tamanio--;

        notifyAll();

        return mejorNodo.getDato();

        
    }

    public synchronized void eliminar(T dato) {
        if (cabeza == null) {
            return;
        }

        if (cabeza.getDato().equals(dato)) {
            cabeza = cabeza.getSiguiente();
            tamanio--;
            notifyAll();
            return;
        }

        Nodo<T> actual = cabeza;

        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getDato().equals(dato)) {

                actual.setSiguiente(actual.getSiguiente() .getSiguiente()  );

                tamanio--;
                notifyAll();
                return;
            }

            actual = actual.getSiguiente();
        }
    }

    public synchronized boolean buscar(T dato) {
        Nodo<T> actual = cabeza;

        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return true;
            }

            actual = actual.getSiguiente();
        }

        return false;
    }

    public synchronized T obtener(int posicion) {
        if (posicion < 0 || posicion >= tamanio) {
            throw new IndexOutOfBoundsException( "Posición inválida");
        }

        Nodo<T> actual = cabeza;

        for (int i = 0; i < posicion; i++) {
            actual = actual.getSiguiente();
        }

        return actual.getDato();
    }

    public synchronized void recorrer() {
        Nodo<T> actual = cabeza;

        while (actual != null) {
            System.out.println(actual.getDato());
            actual = actual.getSiguiente();
        }
    }

    public synchronized int tamanio() {
        return tamanio;
    }

    public synchronized boolean estaVacia() {
        return cabeza == null;
    }

    public int getCapacidad() {
        return capacidad;
    }
}
