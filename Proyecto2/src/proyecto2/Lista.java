/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2;

/**
 *
 * @author deleo
 */
public class Lista<T> 
{
    private static class Nodo<T> 
    {
        T dato;
        Nodo<T> siguiente;

        public Nodo(T dato) 
        {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    private Nodo<T> cabeza; 
    private int tamaño;    

    
    public Lista() 
    {
        this.cabeza = null;
        this.tamaño = 0;
    }

    
    public void add(T elemento) 
    {
        Nodo<T> nuevo = new Nodo<>(elemento);
        if (cabeza == null) 
        {
            cabeza = nuevo;
        } else 
        {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != null)
            {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        tamaño++;
    }

   
    public T get(int indice) 
    {
        if(indice<tamaño&&indice>=0)
        {            
            Nodo<T> actual = cabeza;
            for (int i = 0; i < indice; i++) 
            {
                actual = actual.siguiente;
            }
            return actual.dato;
        }
        return null;
        
    }

    
    public void reemplazar(int indice, T elemento)
    {
        if(indice<tamaño&&indice>=0)
        {
            Nodo<T> actual = cabeza;
            for (int i = 0; i < indice; i++) 
            {
                actual = actual.siguiente;
            }
            actual.dato = elemento;
        }else
        {
            System.out.println("Indice invalido");
        }        
    }

    
    public T remove(int indice) {
        if (indice < tamaño && indice >= 0) 
        {
            T eliminado;
            if (indice == 0) 
            {
                eliminado = cabeza.dato;
                cabeza = cabeza.siguiente;
            } else 
            {
                Nodo<T> anterior = cabeza;
                for (int i = 0; i < indice - 1; i++) 
                {
                    anterior = anterior.siguiente;
                }
                eliminado = anterior.siguiente.dato;
                anterior.siguiente = anterior.siguiente.siguiente;
            }
            tamaño--;
            return eliminado;
        } else 
        {
            
            return null;
        }
    }

   
    public int size() 
    {
        return tamaño;
    }

    
    public boolean estaVacia() 
    {
        return tamaño == 0;
    }

   
    public void limpiar() 
    {
        cabeza = null;
        tamaño = 0;
    }
    
    
}
