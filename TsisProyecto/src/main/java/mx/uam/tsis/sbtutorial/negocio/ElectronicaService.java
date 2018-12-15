package mx.uam.tsis.sbtutorial.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.tsis.sbtutorial.datos.ElectronicaRepository;
import mx.uam.tsis.sbtutorial.negocio.dominio.Electronica;
import mx.uam.tsis.sbtutorial.negocio.dominio.Libro;
import mx.uam.tsis.sbtutorial.negocio.dominio.Usuario;

@Service
public class ElectronicaService {
	
	@Autowired
	private ElectronicaRepository repository;
	@Autowired
	private UsuarioService servicioUsuario;

	public Iterable<Electronica> dameElectronica() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	public boolean agregarDueño(long idUsuario,Electronica electronica) {
		Usuario usuario = servicioUsuario.agregarProducto(idUsuario, electronica);
		electronica.setUsuario(usuario);
		if(repository.save(electronica)!=null) {
			return true;
		}else {
			return false;
		}
	}

	public Electronica agregarElectronica(Electronica electronica) {
		// TODO Auto-generated method stub
		return repository.save(electronica);
	}

	public boolean actualizarElectronica(Long idElectronica) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean eliminarElectronica(Long idElectronica) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Electronica ElectronicaById(Long idElectronica) {
		// TODO Auto-generated method stub
		
		for(Electronica electronicaActual : repository.findAll()) {
			
			if (electronicaActual.getId() == idElectronica ){
				return electronicaActual;
			}
	    }
		
		return null;
	}

}
