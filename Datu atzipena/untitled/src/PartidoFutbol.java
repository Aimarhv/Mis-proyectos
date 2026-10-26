public class PartidoFutbol  {
    private String equipoLocal;
    private String equipoVisitante;
    private int golesLocal;
    private int golesVisitante;

    // Eraikitzailea (Constructor)
    public PartidoFutbol(String local, String visitante, int golesLocal, int golesVisitante) {
        this.equipoLocal = local;
        this.equipoVisitante = visitante;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
    }

    // Getters
    public String getEquipoLocal() {
        return equipoLocal;
    }

    public String getEquipoVisitante() {
        return equipoVisitante;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    // Setters
    public void setEquipoLocal(String equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public void setEquipoVisitante(String equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public void setGolesLocal(int golesLocal) {
        this.golesLocal = golesLocal;
    }

    public void setGolesVisitante(int golesVisitante) {
        this.golesVisitante = golesVisitante;
    }

    // Informazioa bistaratzeko metodoa
    public void mostrarInfo() {
        System.out.printf("%s vs. %s | Resultado: %d-%d%n",
                equipoLocal, equipoVisitante, golesLocal, golesVisitante);
    }
}