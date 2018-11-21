package mx.uam.tsis.sbtutorial.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.tsis.sbtutorial.negocio.dominio.Electronica;

public interface ElectronicaRepository extends CrudRepository<Electronica, Long>{

}
