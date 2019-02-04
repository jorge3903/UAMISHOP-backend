package mx.uam.tsis.sbtutorial.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.tsis.sbtutorial.negocio.dominio.Tutoria;

public interface TutoriaRepository extends CrudRepository<Tutoria, Long>{

}
