import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

import javax.swing.text.DateFormatter;


public class Kontzertua {

	  public static final DateTimeFormatter D_FORMATO =
	            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
	    public static final DateTimeFormatter H_FORMATO = DateTimeFormatter.ofPattern("HH:mm");
	
	private int id;
	private String taldea;
	private LocalDate dia;
	private LocalTime hora;
	private double taquilla;
	private double precio;
	private boolean agotatuta;
	
	  // ===================== KONSTRUKTOREAK =====================
	
	public Kontzertua(int id, String  taldea, LocalDate dia, LocalTime hora, double taquilla, double precio, boolean agotatuta) {
		
		this.id = id;
		this.taldea = taldea;
		this.dia = dia;
		this.hora = hora;
		this.taquilla = taquilla;
		this.precio = precio;
		this.agotatuta = agotatuta;

	}
	
	 // ===================== GETTERRAK =====================
	
	public int getid() {
		return id;
	}
	
	public String gettaldea() {
		return taldea;
	}
	
	public LocalDate getdia() {
		return dia;
	}
	
	public LocalTime gethora() {
		return hora;
	}
	
	public double gettaquilla() {
		return taquilla;
	}
	
	public double getprecio() {
		return precio;
	}
	
	public boolean isagotatuta() {
		return agotatuta;
	}
	
	 // ===================== SETTERRAK =====================
	
	public void setid(int id) {
		this.id = id;
	}
	
	public void settaldea(String taldea) {
		this.taldea = taldea;
	}
	
	public void setdia(LocalDate dia) {
		this.dia = dia;
	}  
	
	public void sethora(LocalTime hora) {
		this.hora = hora;
	}
	
	public void settaquilla(double taquilla) {
		this.taquilla = taquilla;
	}
	
	public void setprecio(double precio) {
		this.precio = precio;
	}
	
	public void setagotatuta(boolean agotatuta) {
		this.agotatuta = agotatuta;
	}
	
	// ===================== toString =====================
	
	@Override
	public String toString() {
	    return "Id: " + id +
	           " | Taldea: " + taldea +
	           " | Data: " + dia.format(D_FORMATO) +       
	           " | Taquilla: " + String.format(Locale.US, "%.2f€", taquilla) +
	           " | Precio: " + String.format(Locale.US, "%.2f€", precio) +
	           " | Agortuta: " + (agotatuta ? "Bai" : "Ez");
	
	  
	}

}
