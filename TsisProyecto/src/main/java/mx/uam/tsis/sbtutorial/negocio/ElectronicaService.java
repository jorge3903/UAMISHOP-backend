package mx.uam.tsis.sbtutorial.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.tsis.sbtutorial.datos.ElectronicaRepository;
import mx.uam.tsis.sbtutorial.negocio.dominio.Electronica;

@Service
public class ElectronicaService {
	
	@Autowired
	private ElectronicaRepository repository;

	public Iterable<Electronica> dameElectronica() {
		// TODO Auto-generated method stub
		return repository.findAll();
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
