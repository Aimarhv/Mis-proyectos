import java.util.ArrayList;

public class Partido {
    public static void main(String[] args) {
        // Crear un ArrayList de objetos PartidoFutbol
        ArrayList<PartidoFutbol> listaPartidos = new ArrayList<>();

        // Añadir partidos
        listaPartidos.add(new PartidoFutbol("Real Madrid", "Barcelona", 2, 1));
        listaPartidos.add(new PartidoFutbol("Atlético de Madrid", "Sevilla", 0, 0));

        // Mostrar información de los partidos
        for (PartidoFutbol partido : listaPartidos) {
            partido.mostrarInfo();
        }

        // Buscar un partido por equipo local
        String equipoBuscado = "Real Madrid";
        for (PartidoFutbol partido : listaPartidos) {
            if (partido.getEquipoLocal().equals(equipoBuscado)) {
                System.out.println("Encontrado: " + partido.getEquipoLocal());
            }
        }

        // Eliminar un partido (por índice o por objeto)
        listaPartidos.remove(0); // Elimina el primer partido (Real Madrid vs. Barcelona)

        // Mostrar información actualizada
        System.out.println("Partidos restantes:");
        for (PartidoFutbol partido : listaPartidos) {
            partido.mostrarInfo();
        }
    }
}