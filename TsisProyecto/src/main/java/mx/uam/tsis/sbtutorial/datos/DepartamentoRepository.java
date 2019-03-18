package mx.uam.tsis.sbtutorial.datos;

// import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import mx.uam.tsis.sbtutorial.negocio.dominio.Departamento;

public interface DepartamentoRepository extends PagingAndSortingRepository<Departamento, Long>{

}
