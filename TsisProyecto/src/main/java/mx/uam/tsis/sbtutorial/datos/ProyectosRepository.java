package mx.uam.tsis.sbtutorial.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.tsis.sbtutorial.negocio.dominio.Proyectos;

public interface ProyectosRepository extends CrudRepository<Proyectos, Long>{

}
