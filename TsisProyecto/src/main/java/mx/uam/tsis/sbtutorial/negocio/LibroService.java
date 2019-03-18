package mx.uam.tsis.sbtutorial.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import mx.uam.tsis.sbtutorial.datos.LibroRepository;
import mx.uam.tsis.sbtutorial.negocio.dominio.Libro;
import mx.uam.tsis.sbtutorial.negocio.dominio.Usuario;

@Service
public class LibroService {

	@Autowired
	private LibroRepository repository;
	@Autowired
	private UsuarioService servicioUsuario;

	public Iterable<Libro> dameLibros() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	public Iterable<Libro> libroPorPagina(int pageNumber, int pageSize){
	      PageRequest pageRequest = new PageRequest(pageNumber,pageSize);
	      Iterable<Libro> res = repository.findAll(pageRequest);
	      return  res;
	   }
	
	public boolean agregarDueño(long idUsuario,Libro libro) {
		Usuario usuario = servicioUsuario.agregarProducto(idUsuario, libro);
		if(usuario!=null) {
			libro.setUsuario(usuario);
			if(repository.save(libro)!=null) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

	public Libro agregarLibro(Libro libro) {
		// TODO Auto-generated method stub
		return repository.save(libro);
	}

	public boolean actualizarLibro(Long idLibro) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean eliminarLibro(Long idLibro) {
		repository.delete(idLibro);
		return true;
	}
	
	public Libro LibroById(Long idLibro) {
		// TODO Auto-generated method stub
		
		for(Libro libroActual : repository.findAll()) {
			
			if (libroActual.getId() == idLibro ){
				return libroActual;
			}
	    }
		
		return null;
	}
}
