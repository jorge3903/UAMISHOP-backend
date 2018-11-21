package mx.uam.tsis.sbtutorial.negocio.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entidad del modelo del dominio
 *
 */
@Entity
@Table(name="Producto")
public class Producto implements Serializable{
	
	@Id
	@Column(name="IdProducto")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(name="nombre")
	private String nombre;
	@Column(name="precio")
	private Double precio;
	@Column(name="descripcion")
	private String descripcion;
	@Column(name="calificacion")
	private Double calificacion;
	@ManyToOne
	@JoinColumn(name="idUsuario")
	private Usuario usuario;
	@ManyToOne
	@JoinColumn(name="idArchivo")
	private Archivo archivo;
	
	/**
	 * Constructor por Defecto
	 */
	public Producto() {
		
	}

	/**
	 * Constructor para el producto
	 * @param nombre
	 * @param precio
	 * @param descripcion
	 * @param imagen
	 */
	public Producto(String nombre, Double precio, String descripcion, Archivo archivo) {
		this.nombre = nombre;
		this.precio = precio;
		this.descripcion = descripcion;
		this.archivo = archivo;
		
	}



	/**
	 * Recupera el ID del producto
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Actualiza el ID del Producto
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Recupera el Nombre del Producto
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Actualiza el Nombre del Producto
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Recupera el Precio del producto
	 * @return precio
	 */
	public Double getPrecio() {
		return precio;
	}

	/**
	 * Actualiza el Precio del Producto
	 * @param precio
	 */
	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	/**
	 * Recupera la calificacion del producto
	 * @return calificacion
	 */
	public Double getCalificacion() {
		return calificacion;
	}

	/**
	 * Actualiza la calificacion del Producto
	 * @param calificacion
	 */
	public void setCalificacion(Double calificacion) {
		this.calificacion = calificacion;
	}
	
	/**
	 * Recupera la Descripcion del producto
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Actualiza la Descripcion del Producto
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * Recupera la imagen del Producto
	 * @param archivo
	 */
	public Archivo getArchivo() {
		return archivo;
	}
	/**
	 * Actualiza la imagen del Producto
	 * @param archivo
	 */
	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}
	
	/**
	 * Recupera al usuario del Producto
	 * @param archivo
	 */
	public Usuario getUsuario() {
		return usuario;
	}
	/**
	 * Actualiza el usuario del Producto
	 * @param archivo
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
