package mx.uam.tsis.sbtutorial.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.tsis.sbtutorial.datos.LibroRepository;
import mx.uam.tsis.sbtutorial.negocio.dominio.Libro;

@Service
public class LibroService {

	@Autowired
	private LibroRepository repository;

	public Iterable<Libro> dameLibros() {
		// TODO Auto-generated method stub
		return repository.findAll();
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
		// TODO Auto-generated method stub
		return false;
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
