/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2;

/**
 *
 * @author deleo
 */
public class Huffman 
{
    public class NodoHuffman 
    {   
        boolean eshoja;
        char caracter;          
        int frecuencia;         
        NodoHuffman izquierdo;  
        NodoHuffman derecho;    
    }

    static final int MAX_CARACTERES = 256;  

    static char[] caracteres = new char[MAX_CARACTERES];  
    static String[] codigos = new String[MAX_CARACTERES]; 
    static int contadorCodigos = 0;  

    
    public static int[] conteoFrecuencias(String texto) 
    {
        int[] frecuencias = new int[MAX_CARACTERES];  

        for (int i = 0; i < texto.length(); i++) 
        {
            frecuencias[texto.charAt(i)]++;  
        }

        return frecuencias;  
    }

    
    public static Lista<NodoHuffman> crearLista(int[] frecuencias) 
    {
        Lista<NodoHuffman> listaNodos = new Lista<>();

        for (int i = 0; i < MAX_CARACTERES; i++) 
        {
            if (frecuencias[i] > 0)
            {
                NodoHuffman nodo = new Huffman().new NodoHuffman();
                nodo.caracter = (char) i;
                nodo.frecuencia = frecuencias[i];
                nodo.izquierdo = null;
                nodo.derecho = null;
                listaNodos.add(nodo);  
            }
        }

        return listaNodos;
    }

    
    public static void ordenarLista(Lista<NodoHuffman> lista) 
    {
        int n = lista.size();
        for (int i = 0; i < n - 1; i++) 
        {
            for (int j = 0; j < n - i - 1; j++)
            {
                NodoHuffman n1 = lista.get(j);
                NodoHuffman n2 = lista.get(j + 1);
                if (n1.frecuencia > n2.frecuencia)
                {
                    lista.reemplazar(j, n2);
                    lista.reemplazar(j + 1, n1);
                }
            }
        }
    }

   
    public static NodoHuffman construirArbol(String texto) 
    {
        int[] frecuencias = conteoFrecuencias(texto);            
        Lista<NodoHuffman> nodos = crearLista(frecuencias);  

        
        while (nodos.size() > 1) 
        {
            ordenarLista(nodos);  

            
            NodoHuffman izquierdo = nodos.remove(0);
            NodoHuffman derecho = nodos.remove(0);

            
            NodoHuffman padre = new Huffman().new NodoHuffman();
            padre.caracter = '-';  
            padre.frecuencia = izquierdo.frecuencia + derecho.frecuencia;
            padre.izquierdo = izquierdo;
            padre.derecho = derecho;

            
            nodos.add(padre);
        }

        return nodos.get(0);  
    }

    
    public static void generarCodigos(NodoHuffman raiz, String codigo)
    {
        if (raiz == null) return;

        
        if (raiz.izquierdo == null && raiz.derecho == null) 
        {
            caracteres[contadorCodigos] = raiz.caracter;
            codigos[contadorCodigos] = codigo;
            contadorCodigos++;
            return;
        }

        
        generarCodigos(raiz.izquierdo, codigo + "0");
        generarCodigos(raiz.derecho, codigo + "1");
    }

   
    public static String obtenerCodigo(char caracter) 
    {
        for (int i = 0; i < contadorCodigos; i++) 
        {
            if (caracteres[i] == caracter) return codigos[i];
        }
        return "";  
    }

    
    public static String comprimir(String texto)
    {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < texto.length(); i++) {
            resultado.append(obtenerCodigo(texto.charAt(i)));  
        }

        return resultado.toString();  
    }
    
    public static String descomprimir(NodoHuffman raiz, String textoComprimido)
    {
        StringBuilder resultado = new StringBuilder();
        NodoHuffman actual = raiz;

        if (raiz == null || textoComprimido == null) return "";

        for (int i = 0; i < textoComprimido.length(); i++) 
        {
            char bit = textoComprimido.charAt(i);
            
            if (bit == '0') {
                actual = actual.izquierdo;
            } else {
                actual = actual.derecho;
            }

            
            if (actual != null && actual.izquierdo == null && actual.derecho == null) 
            {
                resultado.append(actual.caracter);
                actual = raiz; 
            }
        }

        return resultado.toString();
    }

    
}
