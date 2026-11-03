/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto2;

import java.util.Scanner;

/**
 *
 * @author deleo
 */
public class Proyecto2 
{

    
    public static void main(String[] args) 
    {
        Funcionalidad sistema=new Funcionalidad();
        Scanner teclado = new Scanner(System.in);
        sistema.cargarServidor();        
        
        int op=5;
        
        while(op!=2)
        {
            System.out.println("----------------------------");
            System.out.println("INGRESANSO AL SERVIDOR");
            System.out.println("1. INICIAR SESIÓN");
            System.out.println("2. SALIR");
            System.out.print("INGRESE OPCION: ");
            if (!teclado.hasNextInt()) 
            {
                System.err.println("!!!DEBE INGRESAR UN NUMERO");
                teclado.next(); 
                continue;      
            }
            op=teclado.nextInt();
            switch(op)
            {
                case 1->
                {
                    if(sistema.iniciarSesion())
                    {         
                        int opcionMenu = 1;
                        while(opcionMenu!=3)
                        {        
                            System.out.println("----------------------------");
                            System.out.println("SISTEMA DE LIBROS");
                            System.out.println("1. EXPLORAR CATALOGO");
                            System.out.println("2. LECTURA");
                            System.out.println("3. CERRAR SESION");
                            System.out.print("INGRESE OPCION: ");
                            if (!teclado.hasNextInt()) 
                            {
                                System.err.println("!!!DEBE INGRESAR UN NUMERO");
                                teclado.next(); 
                                continue;       
                            }
                            opcionMenu=teclado.nextInt();
                            Usuario usario=sistema.getUsuario();
                            switch(opcionMenu)
                            {
                                case 1->
                                {
                                    System.out.println("----------------------------");
                                    System.out.println("CATALGO");
                                    sistema.leerCatalogo();
                                }
                                case 2->
                                {
                                    if(usario.getPremium())
                                    {
                                        System.out.println("----------------------------");
                                        System.out.println("OPCIONES DE LECTURA");
                                        System.out.println("1 LECTURA EN LINEA");
                                        System.out.println("2 DESCAGAR LIBRO");
                                        System.out.println("3 LECTURA LOCAL");
                                        System.out.print("INGRESE OPCION DE LECTURA: ");
                                        if (!teclado.hasNextInt()) 
                                        {
                                            System.err.println("!!!DEBE INGRESAR UN NUMERO");
                                            teclado.next();
                                            break;
                                        }
                                        int op2=teclado.nextInt();
                                        if(op2==1)
                                        {
                                            System.out.println("----------------------------");
                                            System.out.println("LECTURA EN LINEA");
                                            System.out.print("INGRESE IBNS DEL LIBRO A LEER: ");
                                            teclado.nextLine();
                                            String libro=teclado.nextLine(); 
                                            sistema.lecturaEnLinea(usario.getName(), libro);
                                            
                                            
                                        }else if(op2==2)
                                        {
                                            System.out.println("----------------------------");
                                            System.out.println("DESCARGAR LIBRO");
                                            System.out.print("INGRESE IBNS DEL LIBRO A DESCARGAR: ");
                                            teclado.nextLine(); 
                                            String libro = teclado.nextLine();                      
                                            sistema.descargarLibro(libro, usario.getName());
                                            
                                        }else if(op2==3)
                                        {
                                            System.out.println("----------------------------");
                                            System.out.println("LECTURA LOCAl");                                            
                                            System.out.print("INGRESE IBNS DEL LIBRO A LEER LOCALMENTE: ");
                                            teclado.nextLine();
                                            String libro = teclado.nextLine();
                                            sistema.lecturaLocal(libro, usario.getName());
                                        }else
                                        {   
                                            System.out.println("----------------------------");
                                            System.out.println("INGRESE OPCION VALIDA");
                                             
                                        }
                                    }else
                                    {
                                        System.out.println("----------------------------");
                                        System.out.println("LECTURA EN LINEA");
                                        System.out.print("INGRESE IBNS DEL LIBRO A LEER: ");
                                        teclado.nextLine();
                                        String libro=teclado.nextLine(); 
                                        sistema.lecturaEnLinea(usario.getName(), libro);                                      
                                        
                                    }
                                }
                                case 3->
                                {
                                    System.err.println("REGRESANDO AL MENU PRICIPAL");
                                }
                                default->
                                {
                                    System.out.println("----------------------------");
                                    System.err.println("INGRESE OPCION CORRECTA");
                                }
                            }
                        }
                        
                    }
                }
                case 2->
                {
                    System.err.println("ADIOS :(");
                }
                default->
                {
                    System.err.println("INGRESE OPCION CORRECTA");
                }
            }
        }
        
    }
    
}
