package mx.uam.tsis.sbtutorial.negocio.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Tutoria")
public class Tutoria extends Producto{

	@Column(name="area")
	private String area;
	
	public Tutoria() {
		
	}
	public Tutoria(String nombre, Double precio, String descripcion, Archivo archivo,String area) {
		super(nombre,precio,descripcion,archivo);
		this.area = area;
	}
	
	/**
	 * Recupera el area de la tutoria
	 * @return area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * Actualiza el area de la tutoria
	 * @param area
	 */
	public void setArea(String area) {
		this.area = area;
	}
}
