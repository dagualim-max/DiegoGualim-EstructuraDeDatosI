/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2;

/**
 *
 * @author deleo
 */
public class Usuario 
{
    private String name;
    private String contraseña;
    private boolean premium;
    public Usuario(String name, String contraseña, boolean premium)
    {
        this.name=name;
        this.contraseña=contraseña;
        this.premium=premium;              
        
    }
    //setters
    public void setName(String name)
    {
        this.name=name;
    }
    public void setHash(String contraseña)
    {
        this.contraseña=contraseña;
    }
    public void setPremium(boolean premium)
    {
        this.premium=premium;
    }
    //getters
    public String getName()
    {
        return name;
    }
    public String getContraseña()
    {
        return contraseña;
    }
    public boolean getPremium()
    {
        return premium;
    }
    
    
    
}
