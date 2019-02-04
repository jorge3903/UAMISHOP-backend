package mx.uam.tsis.sbtutorial.negocio.dominio;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Otros")
public class Otros extends Producto{
	
	public Otros() {
		
	}
	
	public Otros(String nombre, Double precio, String descripcion, Archivo archivo) {
		super(nombre,precio,descripcion,archivo);
	}
	
}
