package mx.uam.tsis.sbtutorial.negocio.dominio;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Electronica")
public class Electronica extends Producto{
	

	public Electronica() {
		
	}
	public Electronica(String nombre, Double precio, String descripcion, Archivo archivo) {
		super(nombre,precio,descripcion,archivo);
	}
}