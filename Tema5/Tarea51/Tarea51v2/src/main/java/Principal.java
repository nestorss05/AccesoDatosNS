import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {

    private static final Scanner sc = new Scanner(System.in);
    private static Accesobd instancia;

    public static void main(String[] args) throws Exception {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);
        instancia=new Accesobd();
        int opc = 0;

        // Asegurate de que el programa siga ejecutandose hasta que se use la opcion salir
        do {

            // Muestra el menu y registra la opcion del usuario
            mostrarMenu();
            try {
                opc=sc.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("ERROR: Respuesta invalida (0x00)");
            } finally {
                sc.nextLine();
            }

            // Segun cada opcion, hara una funcion determinada
            switch(opc) {
                case 1 -> {
                    // 1: Guardar persona
                    datosGuardar();
                }
                case 2 -> {
                    // 2: Obtener persona
                    obtener();
                }
                case 3 -> {
                    // 3: Modificar persona
                    modificar();
                }
                case 4 -> {
                    // 4: Eliminar persona
                    borrar();
                }
                case 5 -> {
                    // 5: salir
                    System.out.println("Saliendo del programa...");
                }
                default -> {
                    // Default: opcion invalida
                    System.err.println("ERROR: Opcion invalida (0x01)");
                }
            }
        } while (opc != 5);

        // Cierra el Scanner
        sc.close();
    }

    /**
     * Metodo que contiene el menu
     */
    private static void mostrarMenu() {
        System.out.println("Tarea 5.1 - Hibernate\n");
        System.out.println("1. Guardar persona");
        System.out.println("2. Obtener persona");
        System.out.println("3. Modificar persona");
        System.out.println("4. Eliminar persona");
        System.out.println("5. Salir");
        System.out.println("\nIngresa una opcion: ");
    }

    /**
     * Metodo que guarda los datos de una persona en una BD
     * @param cosa datos de la persona a crear
     * @throws Exception Error de SQL / Hibernate
     */
    private static void guardar(Object cosa) throws Exception {
        instancia.abrir();
        try {
            // Guarda la persona en la BD
            int id = (int) instancia.guardar(cosa);
            System.out.println(id);
        } catch (Exception e) {
            System.err.println("ERROR: Error a la hora de guardar una persona (0x02)");
        } finally {
            instancia.cerrar();
        }
    }

    /**
     * Metodo que lee los datos de una persona de una BD
     * @param id ID del usuario
     * @throws Exception Error de SQL / Hibernate
     */
    private static void leer(int id) throws Exception {
        instancia.abrir();
        try {
            // Obten a la persona mediante su ID
            Persona per = instancia.getSession().get(Persona.class, id);
            if (per != null) {
                // Si la persona fue encontrada, muestra su nombre
                System.out.println(per.getNombre());
            } else {
                // De lo contrario, muestra este mensaje de error
                System.err.println("ERROR: No existe un persona con el id: " + id + " (0x03)");
            }
        } catch (Exception e) {
            System.err.println("ERROR: Error a la hora de obtener una persona (0x04)");
        } finally {
            instancia.cerrar();
        }

    }

    /**
     * Actualiza los datos de un usuario
     * @param id ID de la persona
     * @param nombre Nombre de la persona
     * @param saldo Saldo de la persona
     * @throws Exception Error de SQL / Hibernate
     */
    private static void actualizar(int id,String nombre, Double saldo) throws Exception {
        instancia.abrir();
        try {
            // Obten a la persona mediante su ID
            Persona persona = instancia.getSession().get(Persona.class, id);
            if (persona != null) {
                // Si la persona fue encontrada, modifica los datos a modificar y guardalos en la BD
                persona.setNombre(nombre);
                persona.setSaldo(saldo);
                // session.saveOrUpdate(persona);       // session.merge(persona);
                instancia.getSession().update(persona);
            } else {
                // De lo contrario, muestra este mensaje de error
                System.err.println("ERROR: No se encontró una persona con ID " + id + " (0x03)");
            }
        } catch (Exception e) {
            System.err.println("ERROR: Error a la hora de actualizar una persona (0x05)");
        } finally {
            instancia.cerrar();
        }
    }

    /**
     * Pide al usuario los datos de una nueva persona
     * @throws Exception Error de tipo de datos introducidos
     */
    private static void datosGuardar() throws Exception {
        String nombre;
        double salario;
        try {
            // Nombre
            System.out.println("Ingresa un nombre: ");
            nombre = sc.nextLine();
            // Salario
            System.out.println("Ingresa el salario de la persona: ");
            salario = sc.nextDouble();
            // Guarda los datos en un objeto Persona y procede a guardarlos
            Persona per = new Persona(nombre, salario);
            guardar(per);
        } catch (InputMismatchException e) {
            System.err.println("ERROR: Respuesta invalida (0x06)");
        }
    }

    /**
     * Pide al usuario el ID de una persona a obtener
     * @throws Exception Error de tipo de datos introducidos
     */
    private static void obtener() throws Exception {
        int id;
        try {
            // ID de la persona
            System.out.println("Ingresa el id del persona: ");
            id = sc.nextInt();
            // Procede a obtener a la persona a base de su ID
            leer(id);
        } catch (InputMismatchException e) {
            System.err.println("ERROR: Respuesta invalida (0x06)");
        }
    }

    /**
     * Pide al usuario la ID de la persona a modificar y sus datos a modificar
     * @throws Exception Error de tipos de datos introducidos
     */
    private static void modificar() throws Exception {
        int id;
        String nombre;
        double salario;
        try {
            // ID de la persona
            System.out.println("Ingresa el id del persona a editar: ");
            id = sc.nextInt();
            sc.nextLine();
            // Nombre de la persona
            System.out.println("Ingresa un nombre: ");
            nombre = sc.nextLine();
            // Salario de la persona
            System.out.println("Ingresa el salario de la persona: ");
            salario = sc.nextDouble();
            // Actualiza la persona pasando las tres variables introducidas
            actualizar(id, nombre, salario);
        } catch (InputMismatchException e) {
            System.err.println("ERROR: Respuesta invalida (0x06)");
        }
    }

    /**
     * Pide al usuario el ID de la persona a borrar y borrala
     * @throws Exception Error de SQL / Hibernate | Error de tipos de daots introducidos
     */
    private static void borrar() throws Exception {
        int id;
        boolean conf;
        instancia.abrir();
        try {
            // ID de la persona
            System.out.println("Ingresa el id del persona: ");
            id = sc.nextInt();
            // Obten a la persona mediante su ID
            Persona per = instancia.getSession().load(Persona.class, id);
            if (per != null) {
                // Si la persona fue encontrada, pregunta al usuario si realmente quiere eliminar a la persona
                System.out.println("¿Estas seguro de que desea eliminar la persona? (S/N)");
                conf = sc.next().equalsIgnoreCase("S");
                if (conf) {
                    instancia.getSession().delete(per);
                } else {
                    System.out.println("Operacion cancelada por el usuario");
                }
            } else {
                // De lo contrario, muestra este mensaje de error
                System.err.println("ERROR: No se encontró una persona con ID " + id + " (0x03)");
            }
        } catch (InputMismatchException e) {
            System.err.println("ERROR: Respuesta invalida (0x06)");
        } catch (Exception e) {
            System.err.println("ERROR: Error a la hora de eliminar una persona (0x07)");
        } finally {
            instancia.cerrar();
        }
    }

}