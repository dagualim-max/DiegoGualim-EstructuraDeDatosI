/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2;

/**
 *
 * @author deleo
 */
import java.util.Objects;

public class TablaHash 
{
    public class Nodo
    {
        private Usuario usuario;
        private String clave;
        private String contraseña; 
        Nodo siguiente;
       
        Nodo(Usuario usuario)
        {
            this.usuario = usuario;
            this.clave = usuario.getName();            
            this.contraseña = usuario.getContraseña(); 
            this.siguiente = null;            
        }
    }   
    
    private Nodo[] buckets;
    private int capacidad;
    private int cantidadElementos;
    private double factorCarga; 
    
    TablaHash()
    {
        this.capacidad = 10;
        this.cantidadElementos = 0;
        this.buckets = new Nodo[capacidad];
        this.factorCarga = 0.70;
    }
    
    
    private int hash(String clave)
    {
        int h = 0;
        for(char c: clave.toCharArray())
        {
            h = h + (int)c;
        }
        
        return Math.abs(h) % capacidad;
    }
    
    public void crecer() 
    {       
        int capacidadbase = capacidad;
        Nodo[] tablaBase = buckets;
        capacidad = capacidadbase * 2;
        buckets = new Nodo[capacidad];
        cantidadElementos = 0;

        for (int i = 0; i < capacidadbase; i++) 
        {
            Nodo actual = tablaBase[i];
            while (actual != null) 
            {
                insertar(actual.usuario); 
                actual = actual.siguiente;
            }
        }
    }
    
    public void insertar(Usuario usuario)
    {
        double verificacion = (double) cantidadElementos / capacidad;
        if (verificacion > factorCarga)
        {
            crecer();
        }

        String clave = usuario.getName();
        int indice = hash(clave);
        Nodo actual = buckets[indice];
        
        while(actual != null)
        {
            if(Objects.equals(actual.clave, clave))
            {
                actual.usuario = usuario;
                actual.contraseña = usuario.getContraseña(); 
                return;
            }
            actual = actual.siguiente;
        }
        
        Nodo nuevo = new Nodo(usuario);        
        nuevo.siguiente = buckets[indice];
        buckets[indice] = nuevo;
        cantidadElementos++;
    }
    
    public Nodo buscar(String claveBuscada) 
    {
        int indice = hash(claveBuscada);
        Nodo actual = buckets[indice];

        while (actual != null) {
            if (Objects.equals(actual.clave, claveBuscada)) 
            {
                return actual;
            }
            actual = actual.siguiente;
        }

        return null;
    }
    
    public boolean veriUsuario(String claveBuscada)
    {
        int indice = hash(claveBuscada);
        Nodo actual = buckets[indice];
        
        while(actual != null)
        {
            if(actual.clave.equalsIgnoreCase(claveBuscada))
            {
                return true;
            }
            actual = actual.siguiente;
        }
        
        return false;
    }
    public boolean validarContraseña(String calve, String contraseñaIngresada) 
    {
        
        Nodo nodoBuscado = buscar(calve);

       
        if (nodoBuscado == null) 
        {
            return false;
        }
       
        String hashIngresado = Integer.toString(hash(contraseñaIngresada)); 

        if (Objects.equals(hashIngresado, nodoBuscado.contraseña))
        {
            return true;
        }
        return false;
    }
    
    public boolean eliminar(String claveEliminar)
    {
        int indice = hash(claveEliminar);
        Nodo actual = buckets[indice];
        Nodo anterior = null;

        while (actual != null) 
        {
            if (Objects.equals(actual.clave, claveEliminar))
            {
                if (anterior == null)
                {
                    buckets[indice] = actual.siguiente;
                } else 
                {
                    anterior.siguiente = actual.siguiente;
                }
                cantidadElementos--;
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
        }

        return false;
    }
    
    public Usuario devolverUsuario(String usuario)
    {
        if (usuario == null) 
        {
            return null;
        }        
        int indice = hash(usuario);       
        Nodo actual = buckets[indice];
       
        while (actual != null) 
        {            
            if (actual.clave != null && actual.clave.equals(usuario)) 
            {
                return actual.usuario; 
            }
            actual = actual.siguiente;
        }

        return null;
    }
    public void imprimirEstructura() 
    {
        for (int i = 0; i < capacidad; i++) 
        {
            System.out.print("Bucket " + i + ": ");
            Nodo actual = buckets[i]; 

            if (actual == null) 
            {
                System.out.println("(vacío)"); 
            } else 
            {
                while (actual != null) 
                {
                    System.out.print("[" + actual.clave + "] ");
                    actual = actual.siguiente;
                }
                System.out.println();
            }
        }
    }
}