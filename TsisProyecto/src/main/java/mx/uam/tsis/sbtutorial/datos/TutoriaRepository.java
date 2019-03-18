package mx.uam.tsis.sbtutorial.datos;

import org.springframework.data.repository.PagingAndSortingRepository;

import mx.uam.tsis.sbtutorial.negocio.dominio.Tutoria;

public interface TutoriaRepository extends PagingAndSortingRepository<Tutoria, Long>{

}
