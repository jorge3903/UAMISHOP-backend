package mx.uam.tsis.sbtutorial.negocio.dominio;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Libro")
public class Libro extends Producto{
	

	public Libro() {
		
	}
	public Libro(String nombre, Double precio, String descripcion, Archivo archivo) {
		super(nombre,precio,descripcion,archivo);
	}
}
