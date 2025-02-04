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

    // Formatear número de teléfono
    private String formatPhoneNumber(String number) {
        if (number.length() == 10) {
            return "(" + number.substring(0, 3) + ") " + number.substring(3, 6) + "-" + number.substring(6);
        }
        return number;
    }

    // Cargar contactos desde el archivo txt
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

    // Guardar contactos en el archivo txt
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

    // Lista de Contactos
    public void list() {
        if (contacts.isEmpty()) {
            System.out.println("No hay contactos para mostrar.");
        } else {
            System.out.println("Contactos:");
            int index = 1;
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                System.out.println(index + ". " + formatPhoneNumber(entry.getKey()) + " : " + entry.getValue());
                index++;
            }
        }
    }

    // Crear un nuevo contacto
    public void create(String number, String name) {
        number = formatPhoneNumber(number.replaceAll("[^0-9]", ""));
        if (number.isEmpty() || name.isEmpty()) {
            System.out.println("El número y el nombre no pueden estar vacíos.");
        } else {
            contacts.put(number, name);
            save();
            System.out.println("Contacto creado exitosamente.");
        }
    }

    // Borrar un contacto
    public void delete(int index) {
        if (index <= 0 || index > contacts.size()) {
            System.out.println("Índice inválido. Intente nuevamente.");
            return;
        }

        String keyToRemove = (String) contacts.keySet().toArray()[index - 1];
        contacts.remove(keyToRemove);
        save();
        System.out.println("Contacto eliminado exitosamente.");
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
            scanner.nextLine();

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
                    list();
                    System.out.print("Ingrese el número del contacto a eliminar: ");
                    int index = scanner.nextInt();
                    delete(index);
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
