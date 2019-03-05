package mx.uam.tsis.sbtutorial.negocio;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.tsis.sbtutorial.datos.ProductoRepository;
import mx.uam.tsis.sbtutorial.datos.UsuarioRepository;
import mx.uam.tsis.sbtutorial.negocio.dominio.Producto;
import mx.uam.tsis.sbtutorial.negocio.dominio.Usuario;

@Service
public class UsuarioService {

	@Autowired
	private ProductoRepository repositoryProductos;
	@Autowired
	private UsuarioRepository repository;

	public Usuario agregarUsuario(Usuario usuario) {
		for(Usuario usuarioActual : repository.findAll()) {	
			if(usuarioActual.getCorreo().equals(usuario.getCorreo())) {
				return usuarioActual;
			}
		}
		return repository.save(usuario);
	}
	
	public Usuario agregarProducto(Long idUsuario,Producto producto) {
		Usuario usuario = repository.findOne(idUsuario);
		if(usuario!=null) {
			Collection<Long> productos = usuario.getProductos();
			if(productos.add(producto.getId())) {
				if(repository.save(usuario)!=null) {
					return usuario;
				}else {
					return null;
				}
			}else {
				return null;
			}
		}else {
			return null;
		}
	}

	public Collection<Usuario> dameUsuarios() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	public Usuario editaUsuario(Long idUsuario,String telefono) {
		// TODO Auto-generated method stub
		Usuario usuario=repository.findOne(idUsuario);
		if(usuario!= null) {
			usuario.setTelefono(telefono);
			if(repository.save(usuario)!=null) {
				return usuario;
			}else {
				return null;
			}
		}else {
			return null;
		}
	}

	public boolean eliminarUsuario(Long idUsuario) {
		Usuario usuario = repository.findOne(idUsuario);
		if ( usuario != null) {
			for(Long idProducto:usuario.getProductos()) {
				repositoryProductos.delete(idProducto);
			}
			repository.delete(idUsuario);
			return true;
		} else {
			return false;
		}
	}
	
	public Collection<String> calificaUsuario(Long idUsuario,Long idUsuarioAcalificar, Double cal){
		Usuario usuario = repository.findOne(idUsuarioAcalificar);
		Usuario calificador = repository.findOne(idUsuario);
		if ( usuario == null || calificador == null) {
			return null;
		} else {
			Collection<String> calificaciones = usuario.getCalificacion();
			Collection<String> nuevasCalif = new ArrayList<String>();
			String[] partes;
			String calif;
			Boolean bandera= false;
			for (String calificacion: calificaciones) {
				partes = calificacion.split("-");
				if(Long.parseLong(partes[0]) == idUsuario) {
					calif = idUsuario+"-"+cal;
					nuevasCalif.add(calif);
					bandera= true;
				} else {
					nuevasCalif.add(calificacion);
				}
			}
			if(!bandera) {
				calif=idUsuario+"-"+cal;
				nuevasCalif.add(calif);
			}
			usuario.setCalificacion(nuevasCalif);
			repository.save(usuario);
			return nuevasCalif;
		}
	}

	/**public Usuario validaUsuario(String correo, String contraseña) {
		// TODO Auto-generated method stub
        for(Usuario usuarioActual : repository.findAll()) {
			
			if ( (usuarioActual.getCorreo().equals(correo) )&&
				(usuarioActual.getContra().equals(contraseña)) ){
				return usuarioActual;
			}
	    }
        return null;
		
	}*/

	public Usuario usuarioById(Long idUsuario) {
		// TODO Auto-generated method stub
		return repository.findOne(idUsuario);
		/**for(Usuario usuarioActual : repository.findAll()) {
			
			if (usuarioActual.getId() == idUsuario ){
				return usuarioActual;
			}
	    }
		
		return null;**/
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

	/**public Usuario usuarioConProducto(Long idProducto) {
       for(Usuario usuarioActual : repository.findAll()) {
			for(Producto productoActual:usuarioActual.getProductos()) {
				if(productoActual.getId().equals(idProducto)) {
					return usuarioActual;
				}
			}
	    }
		return null;
	}*/
	
	//metodos para el servicio
}
