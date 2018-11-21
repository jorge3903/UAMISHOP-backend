package mx.uam.tsis.sbtutorial.negocio;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.tsis.sbtutorial.datos.UsuarioRepository;
import mx.uam.tsis.sbtutorial.negocio.dominio.Producto;
import mx.uam.tsis.sbtutorial.negocio.dominio.Usuario;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	public Usuario agregarUsuario(Usuario usuario) {
	for(Usuario usuarioActual : repository.findAll()) {
			
			if(usuarioActual.getCorreo().equals(usuario.getCorreo())) {
				return null;
			}
	}

		return repository.save(usuario);
	}

	public Collection<Usuario> dameUsuarios() {
		// TODO Auto-generated method stub
		
		return repository.findAll();
	}

	public Usuario actualizarUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		if(repository.save(usuario) != null) {
			return usuario;
		}
		
		return null;
	}

	public boolean eliminarUsuario(Long idUsuario) {
		// TODO Auto-generated method stub
		return false;
	}

	public Usuario validaUsuario(String correo, String contraseña) {
		// TODO Auto-generated method stub
        for(Usuario usuarioActual : repository.findAll()) {
			
			if ( (usuarioActual.getCorreo().equals(correo) )&&
				(usuarioActual.getContra().equals(contraseña)) ){
				return usuarioActual;
			}
	    }
        return null;
		
	}

	public Usuario usuarioById(Long idUsuario) {
		// TODO Auto-generated method stub
		
		for(Usuario usuarioActual : repository.findAll()) {
			
			if (usuarioActual.getId() == idUsuario ){
				return usuarioActual;
			}
	    }
		
		return null;
	}
	
	public Usuario usuarioByCorreo(String correo) {
		// TODO Auto-generated method stub
		
		for(Usuario usuarioActual : repository.findAll()) {
			
			if (usuarioActual.getCorreo().equals(correo) ){
				return usuarioActual;
			}
	    }
		
		return null;
	}

	public Usuario usuarioConProducto(Long idProducto) {
       for(Usuario usuarioActual : repository.findAll()) {
			for(Producto productoActual:usuarioActual.getProductos()) {
				if(productoActual.getId().equals(idProducto)) {
					return usuarioActual;
				}
			}
	    }
		return null;
	}
	
	//metodos para el servicio
}
