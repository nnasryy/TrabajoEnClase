/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabajoenclase1;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author nasry
 */
public class Empresa {
    
    public static void main(String[] args) {
        Scanner lea = new Scanner(System.in);
        EmpleadoManager manager = new EmpleadoManager();
        int opcion = 0;
        
        do {
            System.out.println("\n***** MENÚ PRINCIPAL *****");
            System.out.println("1. Agregar Empleado");
            System.out.println("2. Listar Empleados NO Despedidos");
            System.out.println("3. Agregar Venta a Empleado");
            System.out.println("4. Pagar Empleado");
            System.out.println("5. Despedir Empleado");
            System.out.println("6. Imprimir Detalles de Empleado");
            System.out.println("7. Salir");
            System.out.print("Escoja una opción: ");
            
            opcion = lea.nextInt();
            lea.nextLine(); 
            
            try {
                switch(opcion) {
                    case 1:
                        System.out.print("Nombre: ");
                        String nombre = lea.nextLine();
                        System.out.print("Salario: ");
                        double salario = lea.nextDouble();
                        manager.addEmployee(nombre, salario);
                        System.out.println("Empleado agregado exitosamente!");
                        break;
                    case 2:
                        manager.employeeList();
                        break;
                    case 3:
                        System.out.print("Codigo empleado: ");
                        int codV = lea.nextInt();
                        System.out.print("Monto venta: ");
                        double monto = lea.nextDouble();
                        manager.AddSales(codV, monto);
                        break;
                    case 4:
                        System.out.print("Codigo empleado a pagar: ");
                        int codP = lea.nextInt();
                        manager.payEmployee(codP);
                        break;
                    case 5:
                        System.out.print("Codigo empleado a despedir: ");
                        int codD = lea.nextInt();
                        manager.fireEmployee(codD);
                        break;
                    case 6:
                        System.out.print("Codigo empleado a imprimir: ");
                        int codI = lea.nextInt();
                        manager.printEmployee(codI);
                        break;
                    case 7:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch(IOException e) {
                System.out.println("Error de archivo: " + e.getMessage());
            } catch(Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            
        } while (opcion != 7);
    }
}