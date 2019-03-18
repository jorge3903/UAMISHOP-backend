package mx.uam.tsis.sbtutorial.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import mx.uam.tsis.sbtutorial.datos.TutoriaRepository;
import mx.uam.tsis.sbtutorial.negocio.dominio.Tutoria;
import mx.uam.tsis.sbtutorial.negocio.dominio.Usuario;

@Service
public class TutoriaService {

	@Autowired
	private TutoriaRepository repository;
	@Autowired
	private UsuarioService servicioUsuario;
	
	public Iterable<Tutoria> dameTutorias() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	public Iterable<Tutoria> tutoriaPorPagina(int pageNumber, int pageSize){
	      PageRequest pageRequest = new PageRequest(pageNumber,pageSize);
	      Iterable<Tutoria> res = repository.findAll(pageRequest);
	      return  res;
	   }
	
	public Tutoria agregarTutoria(Tutoria tutoria) {
		// TODO Auto-generated method stub
		return repository.save(tutoria);
	}
	
	public boolean agregarDueño(long idUsuario,Tutoria tutoria) {
		Usuario usuario = servicioUsuario.agregarProducto(idUsuario, tutoria);
		if(usuario!=null) {
			tutoria.setUsuario(usuario);
			if(repository.save(tutoria)!=null) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	public boolean modificaTutoria(Long idUsuario,Long idTutoria,String nombre,Double precio,String descripcion,String area) {
		Usuario usuario = servicioUsuario.usuarioById(idUsuario);
		if(usuario!= null) {
			for(Long idProd:usuario.getProductos()) {
				if(idProd == idTutoria) {
					Tutoria tutoria= repository.findOne(idTutoria);
					if(tutoria!=null) {
						tutoria.setNombre(nombre);
						tutoria.setPrecio(precio);
						tutoria.setDescripcion(descripcion);
						tutoria.setArea(area);
						repository.save(tutoria);
						return true;
					}else {
						return false;
					}
				}
			}
			return false;
		}else {
			return false;
		}
	}
	
	public boolean eliminarTutoria(Long idTutoria) {
		repository.delete(idTutoria);
		return true;
	}
}
