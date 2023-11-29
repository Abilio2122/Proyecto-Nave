package com.mygdx.game;

import java.util.Scanner;

public class MovimientoEstrategiaManager {
    private static MovimientoEstrategiaManager instance;
    private MovimientoEstrategia estrategia;

    private MovimientoEstrategiaManager() {
        // Private constructor to prevent instantiation
    }

    public static MovimientoEstrategiaManager getInstance() {
        if (instance == null) {
            instance = new MovimientoEstrategiaManager();
        }
        return instance;
    }

    public MovimientoEstrategia getEstrategia() {
        if (estrategia == null) {
            @SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);

            System.out.println("Selecciona una opción de comportamiento:");
            System.out.println("1. Presionar teclas");
            System.out.println("2. Mantener teclas presionadas");
            System.out.println("3. Mantener teclas presionadas acelerado");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    estrategia = new MovimientoPresionado();
                    break;
                case 2:
                    estrategia = new MovimientoMantenido();
                    break;
                case 3:
                    estrategia = new MovimientoMantenidoAcelerado();
                    break;
                default:
                    System.out.println("Opción no válida. Seleccionando comportamiento predeterminado.");
                    // Puedes asignar un comportamiento predeterminado aquí si lo deseas.
                    estrategia = new MovimientoMantenido();
            }
        }
        return estrategia;
    }
}
