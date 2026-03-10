/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabajoenclase1;

import java.util.Scanner;

/**
 *
 * @author nasry
 */
public class Empresa {
    
    public static void main(String[] args) {
        Scanner lea = new Scanner(System.in);
        int opcion = 0;
        do {
            System.out.println("***** MENÚ PRINCIPAL *****");
            System.out.println("1. Agregar Empleado");
            System.out.println("2. Listar Empleados NO Despedidos");
            System.out.println("3. Agregar Venta a Empleado");
            System.out.println("4. Pagar Empleado");
            System.out.println("5. Despedir Empleado");
            System.out.println("6. Salir");
            System.out.print("Escoja una opción:");
        } while (opcion != 6);
    }

}
