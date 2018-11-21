package mx.uam.tsis.sbtutorial.datos;

import org.springframework.data.repository.CrudRepository;
import mx.uam.tsis.sbtutorial.negocio.dominio.Producto;


/**
 * Este es un repository de productos
 * 
 */
public interface ProductoRepository extends CrudRepository<Producto, Long> {
	
	//metodos para el repository
	
}
