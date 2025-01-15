import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {

    private static final Scanner sc = new Scanner(System.in);
    private static Session session;
    private static Accesobd instancia;

    public static void main(String[] args) throws Exception {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);
        instancia=new Accesobd();
        int opc = 0;
        do {
            mostrarMenu();
            try {
                opc=sc.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("ERROR: Respuesta invalida (0x00)");
            } finally {
                sc.nextLine();
            }
            switch(opc) {
                case 1 -> {
                    datosGuardar();
                }
                case 2 -> {
                    obtener();
                }
                case 3 -> {
                    modificar();
                }
                case 4 -> {
                    borrar();
                }
                case 5 -> {
                    System.out.println("Saliendo del programa...");
                }
                default -> {
                    System.err.println("ERROR: Opcion invalida (0x01)");
                }
            }
        } while (opc != 5);

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("Tarea 5.1 - Hibernate\n");
        System.out.println("1. Guardar persona");
        System.out.println("2. Obtener persona");
        System.out.println("3. Modificar persona");
        System.out.println("4. Eliminar persona");
        System.out.println("5. Salir");
        System.out.println("\nIngresa una opcion: ");
    }

    private static void guardar(Object cosa) throws Exception {
        instancia.abrir();
        int id = (int) instancia.guardar(cosa);
        System.out.println(id);
        instancia.cerrar();
    }

    private static void leer(int id) throws Exception {
        instancia.abrir();
        Transaction transaction=session.beginTransaction();
        Persona per = session.load(Persona.class, id);//   PersonasEntity persona = session.get(PersonasEntity.class, id); // Esta línea también funcionaría como la anterior
        System.out.println(per.getNombre());
        transaction.commit();
        instancia.cerrar();
    }

    private static void actualizar(int id,String nombre, Double saldo) throws Exception {
        instancia.abrir();
        Transaction transaction = session.beginTransaction();
        Persona persona = session.get(Persona.class,id);
        persona.setNombre(nombre);
        persona.setSaldo(saldo);
        // session.saveOrUpdate(persona);       // session.merge(persona);
        session.update(persona);
        transaction.commit();
        instancia.cerrar();
    }

    private static void datosGuardar() throws Exception {
        String nombre;
        double salario;
        System.out.println("Ingresa un nombre: ");
        nombre = sc.nextLine();
        System.out.println("Ingresa el salario de la persona: ");
        salario = sc.nextDouble();
        Persona per = new Persona(nombre, salario);
        guardar(per);
    }

    private static void obtener() throws Exception {
        int id;
        System.out.println("Ingresa el id del persona: ");
        id = sc.nextInt();
        leer(id);
    }

    private static void modificar() throws Exception {
        int id;
        String nombre;
        double salario;
        System.out.println("Ingresa el id del persona a editar: ");
        id = sc.nextInt();
        System.out.println("Ingresa un nombre: ");
        nombre = sc.nextLine();
        System.out.println("Ingresa el salario de la persona: ");
        salario = sc.nextDouble();
        actualizar(id, nombre, salario);
    }

    private static void borrar() throws Exception {
        int id;
        boolean conf;
        System.out.println("Ingresa el id del persona: ");
        id = sc.nextInt();
        System.out.println("¿Estas seguro de que desea eliminar la persona? (S/N)");
        conf = sc.next().equals("S");
        if (conf) {
            Persona per = session.load(Persona.class, id);
            session.delete(per);
        }
    }

}