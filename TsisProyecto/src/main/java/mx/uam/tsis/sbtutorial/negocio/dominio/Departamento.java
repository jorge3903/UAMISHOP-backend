package mx.uam.tsis.sbtutorial.negocio.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Departamento")
public class Departamento extends Producto{
	
	@Column(name="ubicacion")
	private String ubicacion;

	public Departamento() {
		
	}
	
	public Departamento(String nombre,String ubicacion, Double precio, String descripcion, Archivo archivo) {
		super(nombre,precio,descripcion,archivo);
		this.ubicacion = ubicacion;
	}
	
	/**
	 * Recupera la ubicacion del departamento
	 * @return ubicacion
	 */
	public String getUbicacion() {
		return this.ubicacion;
	}

	/**
	 * Actualiza la ubicacion del departamento
	 * @param nombre
	 */
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	
}
