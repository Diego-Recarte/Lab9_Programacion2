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

    public ListaEnlazada() {
        cabeza = null;
        tamanio = 0;
    }

    public void agregar(T dato) {
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

    public void eliminar(T dato) {
        if (cabeza == null) {
            return;
        }

        if (cabeza.getDato().equals(dato)) {
            cabeza = cabeza.getSiguiente();
            tamanio--;
            return;
        }

        Nodo<T> actual = cabeza;

        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getDato().equals(dato)) {
                actual.setSiguiente(
                        actual.getSiguiente().getSiguiente()
                );

                tamanio--;
                return;
            }

            actual = actual.getSiguiente();
        }
    }

    public boolean buscar(T dato) {
        Nodo<T> actual = cabeza;

        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return true;
            }

            actual = actual.getSiguiente();
        }

        return false;
    }

    public T obtener(int posicion) {
        if (posicion < 0 || posicion >= tamanio) {
            throw new IndexOutOfBoundsException(
                    "Posición inválida: " + posicion
            );
        }

        Nodo<T> actual = cabeza;

        for (int i = 0; i < posicion; i++) {
            actual = actual.getSiguiente();
        }

        return actual.getDato();
    }

    public void recorrer() {
        Nodo<T> actual = cabeza;

        while (actual != null) {
            
            actual = actual.getSiguiente();
        }
    }

    public int tamanio() {
        return tamanio;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    
}
