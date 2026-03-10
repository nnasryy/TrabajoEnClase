/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trabajoenclase1;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author nasry
 */
public class EmpleadoManager {

    /**
     * @param args the command line arguments
     */
    //El mismo programa daria el codigo
    /*
    Formato:
    1- File Codigos.emp:
    int code -> 4 bytes Mantener
    
    2- File Empleados.emp:
    int code 
    String name
    double salario
    long fechaContratacion -> Cuando se crea el empleado
    long fechaDespido
     */
    private RandomAccessFile rcods, remps;

    public EmpleadoManager() {
        try {
            File mf = new File("company");
            mf.mkdir();
            rcods = new RandomAccessFile("company/codigos.emp", "rw");
            remps = new RandomAccessFile("company/empleados.emp", "rw");

            initCode();
        } catch (IOException e) {
            System.out.println("Error!");
        }
    }
   //rw, r, nullpointer, .seek(), 
    //inicializar el archivo con los codigos

    private void initCode() throws IOException {
        if (rcods.length() == 0) {
            rcods.writeInt(1);
        }
    }

    //Seek, read, write -> puntero
    //getFilePointer -> Solo para saber donde esta el puntero
    //Lo vuelve a pasar en 0 cuando se llama la funcion nuevamente, de 0 a 4
    //Metodos del Puntero -> manipula el espacio de memoria en bytes
    private int getCode() throws IOException {
        rcods.seek(0);
        //Lee el primer dato, que seria el codigo 1
        int code = rcods.readInt();
        //Mover el puntero a 0 de nuevo porque readInt lo mueve
        rcods.seek(0);
        rcods.writeInt(code + 1);
        return code;
    }
    //el codigo ya se autogenera y por eso no se usa como parametro

    public void addEmployee(String name, double salary) throws IOException {
        remps.seek(remps.length()); //La cantidad del valor del puntero
        int code = getCode();
        remps.writeInt(code);
        remps.writeUTF(name);
        remps.writeDouble(salary);
        remps.writeLong(Calendar.getInstance().getTimeInMillis());
        remps.writeLong(0); //dias de despido
        createEmployeeFolders(code);
    }

    private String employeeFolder(int code) {
        return "company/empleado" + code; //dirección
    }
//Crea el archivo de ventas

    private RandomAccessFile salesFileFor(int code) throws IOException {
        String dirPadre = employeeFolder(code);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String dir = dirPadre + "/ventas" + year + ".emp";
        return new RandomAccessFile(dir, "rw");
    }

    /*
    Formato de VentasYear.emp
    double saldo
    boolean estadoPago * mes
     */
    private void createYearSalesFilesFor(int code) throws IOException {
        RandomAccessFile rventas = salesFileFor(code);
        if (rventas.length() == 0) {
            for (int mes = 0; mes < 12; mes++) {
                rventas.writeDouble(0);
                rventas.writeBoolean(false);
            }
        }
    }

    private void createEmployeeFolders(int code) throws IOException {
        File dir = new File(employeeFolder(code));
        dir.mkdir();
        createYearSalesFilesFor(code);
    }

    public void employeeList() throws IOException {
        remps.seek(0);
        while (remps.getFilePointer() < remps.length()) {
            int code = remps.readInt();
            String name = remps.readUTF();
            double salario = remps.readDouble();
            Date dateH = new Date(remps.readLong());
            //Si no es despedido, 
            if (remps.readLong() == 0) {
                System.out.println("Code: " + code + " Nombre: " + name + " Salary: $" + salario + "Fecha: " + dateH);
            }

        }

    }

    private boolean isEmployeeActive(int code) throws IOException {
        remps.seek(0);
        while (remps.getFilePointer() < remps.length()) {
            int cod = remps.readInt(); //extraer informacion
            long pos = remps.getFilePointer(); // al ser encontrado se guarda la posición
            remps.readUTF();
            remps.skipBytes(16);

            if (remps.readLong() == 0 && cod == code) {
                remps.seek(pos);
            }
            return true;
        }

        return false;
    }

    public boolean fireEmployee(int code) throws IOException {
        if (isEmployeeActive(code)) {
            String name = remps.readUTF();
            remps.skipBytes(16);
            remps.writeLong(new Date().getTime());
            System.out.println("Despidiendo a:" + name);
            return true;
        }
        return false;
    }

    public void AddSales(int code, double amount) throws IOException {
        if (!isEmployeeActive(code)) {
            System.out.println("Empleado no encontrado");
        } else {
            RandomAccessFile sales = salesFileFor(code);
            int pos = Calendar.getInstance().get(Calendar.MONTH) * 9; //por la cantidad de bytes por registro dentroe del archivo double + boolean
            sales.seek(pos);
            double monto = sales.readDouble();
            sales.seek(pos);
            sales.writeDouble(monto + amount);
        }

    }

}
