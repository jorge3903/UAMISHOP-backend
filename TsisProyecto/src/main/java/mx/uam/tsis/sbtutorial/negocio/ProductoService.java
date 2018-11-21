package mx.uam.tsis.sbtutorial.negocio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import mx.uam.tsis.sbtutorial.datos.ProductoRepository;
import mx.uam.tsis.sbtutorial.negocio.dominio.Producto;
import mx.uam.tsis.sbtutorial.negocio.dominio.Usuario;

@Service
public class ProductoService {

	@Autowired
	private ProductoRepository repository;

	/**
     * Metodo del negocio que regresa todos los productos de la BD
     * @param 
     * @return  iterable de los productos
     */
	public Iterable<Producto> dameProductos() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	/**
     * Metodo del negocio que añade un productoa la BD
     * @param producto
     * @return  productos
     */
	public Producto agregarProducto(Producto producto) {
		// TODO Auto-generated method stub
		return repository.save(producto);
	}

	/**
     * Metodo del negocio que actualiza un producto de la BD
     * @param  producto
     * @return  booleano con el estado del producto
     */
	public boolean actualizarProducto(Long idProducto) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
     * Metodo del negocio que elimina un producto de la BD
     * @param  producto
     * @return  booleano con estado del producto
     */
	public boolean eliminarProducto(Long idProducto) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
     * Metodo del negocio que regresa el producto especificado de la BD
     * @param   idProducto
     * @return  Producto
     */
	public Producto ProductoById(Long idProducto) {
		// TODO Auto-generated method stub
		
		for(Producto productoActual : repository.findAll()) {
			
			if (productoActual.getId() == idProducto ){
				return productoActual;
			}
	    }
		
		return null;
	}

	/*public Iterable<Producto> dameProductosByCategoria(String categoria) {
		// TODO Auto-generated method stub
		ArrayList<Producto> productosCategoria = new ArrayList<>();
		
			for(Producto productoActual : repository.findAll()) {
				
				if (productoActual.getCategoria().equals(categoria)) {
					productosCategoria.add(productoActual);
				}
					

		    }
			
		return productosCategoria;
	}*/
}
