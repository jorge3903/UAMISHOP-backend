package mx.uam.tsis.sbtutorial.datos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import mx.uam.tsis.sbtutorial.negocio.dominio.Usuario;

/**
 * Este es un repository de usuarios
 * 
 */
public interface UsuarioRepository extends CrudRepository<Usuario, Long>{

	//metodos para el repository
	
	Collection<Usuario> findAll();
}
