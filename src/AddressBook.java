import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {
    private HashMap<String, String> contacts;
    private String filePath;

    public AddressBook(String filePath) {
        this.contacts = new HashMap<>();
        this.filePath = filePath;
        load();
    }

    // Cargar contactos desde el archivo
    public void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                contacts.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar contactos: " + e.getMessage());
        }
    }

    // Guardar contactos en el archivo
    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar contactos: " + e.getMessage());
        }
    }

    // Listar contactos
    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    // Crear un nuevo contacto
    public void create(String number, String name) {
        contacts.put(number, name);
        save();
    }

    // Borrar un contacto
    public void delete(String number) {
        contacts.remove(number);
        save();
    }

    // Menú interactivo
    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menú de opciones:");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear un nuevo contacto");
            System.out.println("3. Borrar un contacto");
            System.out.println("4. Salir");
            System.out.print("Elija una opción: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            switch (option) {
                case 1:
                    list();
                    break;
                case 2:
                    System.out.print("Ingrese el número de contacto: ");
                    String number = scanner.nextLine();
                    System.out.print("Ingrese el nombre de contacto: ");
                    String name = scanner.nextLine();
                    create(number, name);
                    break;
                case 3:
                    System.out.print("Ingrese el número de contacto a eliminar: ");
                    number = scanner.nextLine();
                    delete(number);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook("contactos.txt");
        addressBook.menu();
    }
}
