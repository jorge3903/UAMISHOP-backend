package mx.uam.tsis.sbtutorial.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.tsis.sbtutorial.negocio.dominio.Archivo;


public interface ArchivoRepository extends CrudRepository<Archivo, Long>{

}
