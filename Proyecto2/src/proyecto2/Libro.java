/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2;

/**
 *
 * @author deleo
 */
public class Libro 
{
    String titulo, ibsn;
    public Libro(String titulo,String ibsn)
    {
        this.titulo=titulo;
        this.ibsn=ibsn;
    }
    //settrs
    public void setTitulo(String titulo)
    {
        this.titulo=titulo;
    }
    public void setIsbn(String ibsn)
    {
        this.ibsn=ibsn;
    }
    //settrs
    public String getTitulo()
    {
        return titulo;
    }
    public String getIsbn()
    {
        return ibsn;
    }
    
    
    
}
