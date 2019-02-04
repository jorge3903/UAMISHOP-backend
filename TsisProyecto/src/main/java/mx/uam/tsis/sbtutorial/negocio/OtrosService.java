package mx.uam.tsis.sbtutorial.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.tsis.sbtutorial.datos.OtrosRepository;
import mx.uam.tsis.sbtutorial.negocio.dominio.Otros;
import mx.uam.tsis.sbtutorial.negocio.dominio.Usuario;

@Service
public class OtrosService {

	@Autowired
	private OtrosRepository repository;
	@Autowired
	private UsuarioService servicioUsuario;

	public Iterable<Otros> dameOtros() {
		return repository.findAll();
	}
	
	public boolean agregarDueño(long idUsuario,Otros otro) {
		Usuario usuario = servicioUsuario.agregarProducto(idUsuario, otro);
		if(usuario!=null) {
			otro.setUsuario(usuario);
			if(repository.save(otro)!=null) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

	public Otros agregarOtro(Otros otro) {
		return repository.save(otro);
	}

	public boolean eliminarOtro(Long idOtro) {
		repository.delete(idOtro);
		return true;
	}
	
	public Otros LibroById(Long idOtro) {
		for(Otros otroActual : repository.findAll()) {
			
			if (otroActual.getId() == idOtro ){
				return otroActual;
			}
	    }
		
		return null;
	}
}
