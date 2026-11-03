/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2;

/**
 *
 * @author deleo
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.nio.file.Files;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;


public class Funcionalidad 
{   
    private TablaHash tablaUsuario=new TablaHash();   
    private String rutaServidor;
    private String rutaClientes;
    private String rutaCatalogo;
    private String rutaLibros;
    private String rutaLibrosOriginales;
    private Usuario usuarioActual;
    private String rutaDescargaUsuarios;
    private String rutaUsuariosOriginales;
    
    
    public Funcionalidad()
    {
        cargarConfiguracion("./Configuracion.txt");
    }
    //se utiliza para almacenar las rutas a usar
    private void cargarConfiguracion(String archivoConfig) 
    {
        String linea;
        try (BufferedReader br = new BufferedReader(new FileReader(archivoConfig))) 
        {            
            linea = br.readLine();
            if (linea != null) 
            {
                this.rutaServidor = extraerRuta(linea);
            }       
            linea = br.readLine();
            if (linea != null) 
            {
                this.rutaClientes = extraerRuta(linea);
            }            
            linea = br.readLine();
            if (linea != null) 
            {
                this.rutaCatalogo = extraerRuta(linea);
            }            
            linea = br.readLine();
            if (linea != null) 
            {
                this.rutaLibros = extraerRuta(linea);
            }            
            linea = br.readLine();
            if (linea != null) 
            {
                this.rutaLibrosOriginales = extraerRuta(linea);
            }            
            linea = br.readLine();
            if (linea != null) 
            {
                this.rutaDescargaUsuarios = extraerRuta(linea); 
            }           
            linea = br.readLine();
            if (linea != null) 
            {
                this.rutaUsuariosOriginales = extraerRuta(linea); 
            }        
            
        } 
        catch (IOException e) 
        {
            System.err.println("ERROR AL LEER LOS ARCHIVOS DE CONFIGURACIÓN: " + e.getMessage());
        }
    }

    
    private String extraerRuta(String linea) 
    {
        if (linea.contains(":")) 
        {
            String[] partes = linea.split(":", 2);
            return partes[1].trim();
        }
        return linea.trim();
    }
    
    private Lista<String> extraerContenidoUsuarios(String linea) 
    {
        Lista<String> li = new Lista<>();
        if (linea.contains("|")) 
        {
            String[] partes = linea.split("[|]", 3);            
            li.add(partes[0].trim());
            li.add(partes[1].trim());
            li.add(partes[2].trim());            
        }
        return li;
    }  
    
    public String getRutaServidor() { return rutaServidor; }
    public String getRutaClientes() { return rutaClientes; }
    public String getRutaCatalogo() { return rutaCatalogo; }
    public String getRutaLibros() { return rutaLibros; }
    public String getRutaLibrosOriginales() { return rutaLibrosOriginales; }    
    public Usuario getUsuario(){return usuarioActual;}
    public String getRutaDescargaUsuarios() { return rutaDescargaUsuarios; }
    public String getRutaUsuariosOriginales() { return rutaUsuariosOriginales; }
    
    public void cargarArchivoUsuarios()
    {
        File archivoClientes = new File(getRutaClientes());         
        File archivoOriginal = new File(getRutaUsuariosOriginales()); 
       
        try (BufferedReader br = new BufferedReader(new FileReader(archivoOriginal));
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivoClientes))) 
        {
        bw.write("Usuario|hash|boolean_premium");
        bw.newLine();
        String linea;
        int l1=0;
        while ((linea = br.readLine()) != null) 
        {
            linea = linea.trim();
            if (linea.isEmpty()) continue;

            if (l1==0)
            {
                l1 = 2;
                continue; 
            }

            String[] partes = linea.split("\\|");
            if (partes.length >= 3) 
            {
                String usuario = partes[0].trim();
                String chash = partes[1].trim();
                String premium = partes[2].trim();
                int hash = 0;
                for (char c : chash.toCharArray()) 
                {
                    hash += (int) c;
                }
                int passwordHash = hash % 10;
                bw.write(usuario + "|" + passwordHash + "|" + premium);
                bw.newLine();
            }
        }        

        } catch (IOException e) 
        {
            System.err.println("ERROR: " + e.getMessage());
        
        }
    }    
    
    public void cargarUsuarios()
    {
        
        cargarArchivoUsuarios();
        String linea;
        try (BufferedReader br = new BufferedReader(new FileReader(rutaClientes))) 
        {
            linea = br.readLine();
            while ((linea = br.readLine()) != null) 
            {
                linea = linea.trim();
                if (!linea.isEmpty()) 
                {
                    Lista<String> datos = extraerContenidoUsuarios(linea);                    
                    if (datos.size() >= 3) 
                    {
                        String nombre = datos.get(0);
                        String hash = datos.get(1); 
                        boolean esPremium; 
                        if(datos.get(2).equals("0"))
                        {
                            esPremium=false;
                        }else
                        {
                            esPremium=true;
                        }
                        Usuario us = new Usuario(nombre, hash, esPremium);
                        tablaUsuario.insertar(us);                                                          
                    }
                }
            }                      
        } 
        catch (IOException e) 
        {
            System.err.println("ERROR AL LEER EL ARCHIVO USUARIOS.TXT: " + e.getMessage());
        }
    }   
    
    
    public boolean iniciarSesion()
    {
        Scanner teclado = new Scanner(System.in);
        System.out.println("--------------------------------");
        System.out.println("INGRESE USUARIO: "); String usuario=teclado.nextLine();
        if (tablaUsuario.veriUsuario(usuario))
        {
            System.out.println("INGRESE CONTRASEÑA: ");String contraseña=teclado.nextLine();
            if(tablaUsuario.validarContraseña(usuario, contraseña))
            {
                System.out.println("INICIO DE SESIÓN EXITOSO");
                usuarioActual=tablaUsuario.devolverUsuario(usuario);
                return true;
            }else
            {
                System.out.println("CONTRASEÑA INCORRECTA");
            }
            
        }else
        {
            System.out.println("EL USARIO NO EXSITE"); 
            return false;
        }
        
        return false;
    }
    
    
    public void leerCatalogo()
    {
        
        try (BufferedReader br = new BufferedReader(new FileReader(getRutaCatalogo())))
        {
            String linea;
            while ((linea = br.readLine()) != null) 
            {
                System.out.println(linea);
                System.out.println("-------------------------------");
            }
            
        }catch (Exception e) 
        {
            System.err.println("ERROR EN EL CATALOGO: " + e.getMessage());
        }
    }
    
    
    public void cargaLibros()
    {
        File carpetaOriginales = new File(getRutaLibrosOriginales()); 
        File carpetaLibros = new File(getRutaLibros()); 
        
        BigInteger p = new BigInteger("23");
        BigInteger privadaServidor = new BigInteger("15"); 

        try 
        {            
            BigInteger privada = new BigInteger("2");
            BigInteger g = new BigInteger("5");
            BigInteger publica = g.modPow(privada, p);

            
            BigInteger secreto = publica.modPow(privadaServidor, p);

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(secreto.toByteArray());
            byte[] clave24Bytes = Arrays.copyOf(hash, 24);
            SecretKeySpec clave = new SecretKeySpec(clave24Bytes, "DESede");

            byte[] ivBytes = "12345678".getBytes();
            IvParameterSpec iv = new IvParameterSpec(ivBytes);

            File[] archivos = carpetaOriginales.listFiles();
            if (archivos != null) 
            {
                for (File archivoOriginal : archivos) 
                {
                    if (archivoOriginal.isFile()) 
                    {
                        String contenido = new String(Files.readAllBytes(archivoOriginal.toPath()), "UTF-8");
                        Cipher cifrador = Cipher.getInstance("DESede/CBC/PKCS5Padding");
                        cifrador.init(Cipher.ENCRYPT_MODE, clave, iv);
                        byte[] cifrado = cifrador.doFinal(contenido.getBytes("UTF-8"));
                        String cifradoBase64 = Base64.getEncoder().encodeToString(cifrado);
                        File archivoDestino = new File(carpetaLibros, archivoOriginal.getName());
                        Files.write(archivoDestino.toPath(), cifradoBase64.getBytes("UTF-8"));
                    }
                }
            }
        } catch (Exception e) 
        {
            System.err.println("ERROR AL CIFRAR LIBROS: " + e.getMessage());
        }
    }
    
    
    
    
    public void cargarServidor()
    {
        cargarArchivoUsuarios();
        cargarUsuarios();        
        cargaLibros();            
    }
    
    public void lecturaEnLinea(String usuarioBuscado, String libroNombre)
    {
        BigInteger p = new BigInteger("23");
        BigInteger publicaServidor = new BigInteger("19"); 

        try 
        {            
            File archivoLibro = new File(getRutaLibros(), libroNombre);
            if (!archivoLibro.exists()) 
            {
                archivoLibro = new File(getRutaLibros(), libroNombre + ".txt");
            }
            if (!archivoLibro.exists()) 
            {
                System.err.println("ERROR NO SE ENCONTRO EL LIBRO");
                return;
            }
            String libroCifradoBase64 = new String(Files.readAllBytes(archivoLibro.toPath()), "UTF-8").trim();            
            BigInteger privadaPredeterminada = new BigInteger("2");
            BigInteger secretoUsuario = publicaServidor.modPow(privadaPredeterminada, p);

            
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(secretoUsuario.toByteArray());
            byte[] clave24Bytes = Arrays.copyOf(hash, 24);
            SecretKeySpec clave = new SecretKeySpec(clave24Bytes, "DESede");

            byte[] ivBytes = "12345678".getBytes();
            IvParameterSpec iv = new IvParameterSpec(ivBytes);

            Cipher descifrador = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            descifrador.init(Cipher.DECRYPT_MODE, clave, iv);
            byte[] descifrado = descifrador.doFinal(Base64.getDecoder().decode(libroCifradoBase64));

            System.out.println("LIBRO DESCIFRADO:");
            System.out.println(new String(descifrado, "UTF-8"));

        } catch (Exception e) 
        {
            System.err.println("ERROR: " + e.getMessage());
        }
    }
    
    public void descargarLibro(String isbn, String usuarioBuscado)
    {
        BigInteger p = new BigInteger("23");
        BigInteger publicaServidor = new BigInteger("19"); 

        try 
        {
            
            File libro = new File(getRutaLibros(), isbn);
            if (!libro.exists()) 
            {
                libro = new File(getRutaLibros(), isbn + ".txt");
            }
            
            if (!libro.exists()) 
            {
                System.err.println("ERROR NO SE ENCONTRO EL LIBRRO");
                return;
            }

            String libroCifrado = new String(Files.readAllBytes(libro.toPath()), "UTF-8").trim();

            
            BigInteger privadaPredeterminada = new BigInteger("2");
            BigInteger secretoUsuario = publicaServidor.modPow(privadaPredeterminada, p);

           
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(secretoUsuario.toByteArray());
            byte[] clave24Bytes = Arrays.copyOf(hash, 24);
            SecretKeySpec clave = new SecretKeySpec(clave24Bytes, "DESede");

            byte[] ivBytes = "12345678".getBytes();
            IvParameterSpec iv = new IvParameterSpec(ivBytes);

            Cipher descifrador = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            descifrador.init(Cipher.DECRYPT_MODE, clave, iv);
            byte[] descifrado = descifrador.doFinal(Base64.getDecoder().decode(libroCifrado));
            String textoDescifrado = new String(descifrado, "UTF-8");

            
            Huffman.contadorCodigos = 0; 
            Huffman.NodoHuffman raiz = Huffman.construirArbol(textoDescifrado);
            Huffman.generarCodigos(raiz, "");
            String textoComprimido = Huffman.comprimir(textoDescifrado);

            
            File carpetaDescargas = new File(getRutaDescargaUsuarios()); 
            
            
            File carpetaUsuario = new File(carpetaDescargas, usuarioBuscado);
            if (!carpetaUsuario.exists()) 
            {
                carpetaUsuario.mkdirs();
            }

            
            File archivoSalida = new File(carpetaUsuario, isbn + "_comprimido.txt");
            Files.write(archivoSalida.toPath(), textoComprimido.getBytes("UTF-8"));

            System.out.println("LIBRO DESCARGADO Y COMPRI GUARDADO EN SU CARPETA");            

        } catch (Exception e) 
        {
            System.err.println("ERROR EN LA DESCARGA: " + e.getMessage());
        }
    }
    
    
    public void lecturaLocal(String isbn, String usuarioBuscado)
    {
        try 
        {         
            File carpetaDescargas = new File(getRutaDescargaUsuarios());
            File carpetaUsuario = new File(carpetaDescargas, usuarioBuscado);
            
            File libro = new File(carpetaUsuario, isbn + "_comprimido.txt");
            if (!libro.exists()) 
            {
                libro = new File(carpetaUsuario, isbn + ".txt");
            }

            if (!libro.exists()) 
            {
                System.err.println("AUN NO TIENE UNA COPIA DEL LIBRO " + isbn);
                return;
            }
            
            String textoComprimido = new String(Files.readAllBytes(libro.toPath()), "UTF-8").trim();
            
            File libroOriginal = new File(getRutaLibros(), isbn);
            if (!libroOriginal.exists()) 
            {
                libroOriginal = new File(getRutaLibros(), isbn + ".txt");
            }           

            String libroCifrado = new String(Files.readAllBytes(libroOriginal.toPath()), "UTF-8").trim();            
            
           
            BigInteger p = new BigInteger("23");
            BigInteger publicaServidor = new BigInteger("19");
            BigInteger privadaPredeterminada = new BigInteger("2"); 
            BigInteger secretoUsuario = publicaServidor.modPow(privadaPredeterminada, p);

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(secretoUsuario.toByteArray());
            byte[] clave24Bytes = Arrays.copyOf(hash, 24);
            SecretKeySpec clave = new SecretKeySpec(clave24Bytes, "DESede");
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());

            Cipher descifrador = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            descifrador.init(Cipher.DECRYPT_MODE, clave, iv);
            byte[] descifrado = descifrador.doFinal(Base64.getDecoder().decode(libroCifrado));
            String textoOriginalReferencia = new String(descifrado, "UTF-8");

            Huffman.NodoHuffman raiz = Huffman.construirArbol(textoOriginalReferencia);
            
            String textoDescomprimido = Huffman.descomprimir(raiz, textoComprimido);

            System.out.println("LECTURA DE " + isbn);
            System.out.println(textoDescomprimido);

        } catch (Exception e) 
        {
            System.err.println("ERROR EN LA LECTURA LOCAL: " + e.getMessage());
        }
    }    
    
}
