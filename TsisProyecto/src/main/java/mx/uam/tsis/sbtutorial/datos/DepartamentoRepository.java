package mx.uam.tsis.sbtutorial.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.tsis.sbtutorial.negocio.dominio.Departamento;

public interface DepartamentoRepository extends CrudRepository<Departamento, Long>{

}
