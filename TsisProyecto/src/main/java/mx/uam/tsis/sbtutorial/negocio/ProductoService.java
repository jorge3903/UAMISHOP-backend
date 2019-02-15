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
import mx.uam.tsis.sbtutorial.datos.UsuarioRepository;
import mx.uam.tsis.sbtutorial.negocio.dominio.Archivo;
import mx.uam.tsis.sbtutorial.negocio.dominio.Producto;
import mx.uam.tsis.sbtutorial.negocio.dominio.Usuario;

@Service
public class ProductoService {

	@Autowired
	private ProductoRepository repository;
	@Autowired
	private UsuarioRepository repositoryUsuario;

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
	
	public Collection<Producto> dameFavoritos(Long idUsuario){
		Usuario usuario = repositoryUsuario.findOne(idUsuario);
		ArrayList<Producto> favoritos = new ArrayList<Producto>();
		ArrayList<Long> numFav = new ArrayList<Long>();
		if(usuario!=null) {
			for(Long idProd:usuario.getFavoritos()) {
				Producto prod=repository.findOne(idProd);
				if(prod!=null) {
					favoritos.add(prod);
					numFav.add(idProd);
				}
			}
			usuario.setFavoritos(numFav);
			repositoryUsuario.save(usuario);
			return favoritos;
		}else {
			return favoritos;
		}
	}
	
	
	public boolean agregarEnFavoritos(Long idUsuario,Long idProducto) {
		Producto prod=repository.findOne(idProducto);
		Usuario usuario = repositoryUsuario.findOne(idUsuario);
		if(prod!=null) {
			if(usuario!=null) {
				Collection<Long> fav = usuario.getFavoritos();
				for(Long favorito:fav) {
					if(favorito==idProducto) {
						return false;
					}
				}
				fav.add(idProducto);
				usuario.setFavoritos(fav);
				repositoryUsuario.save(usuario);
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	
	public Collection<Producto> quitarFavorito(Long idUsuario,Long idProducto){
		Usuario usuario = repositoryUsuario.findOne(idUsuario);
		Producto producto = repository.findOne(idProducto);
		ArrayList<Long> idFav = new ArrayList<Long>();
		ArrayList<Producto> favoritos= new ArrayList<Producto>();
		if(usuario!=null && producto!=null) {
			for(Long idProd:usuario.getFavoritos()) {
				if(idProd==idProducto) {
					continue;
				}else {
					favoritos.add(repository.findOne(idProd));
					idFav.add(idProd);
				}
			}
			usuario.setFavoritos(idFav);
			repositoryUsuario.save(usuario);
			return favoritos;
		}else {
			return favoritos;
		}
	}
	
	
	public Collection<Producto> misProductos(Long idUsuario){
		Usuario usuario = repositoryUsuario.findOne(idUsuario);
		ArrayList<Producto> productos= new ArrayList<Producto>();
		if(usuario !=null) {
			for(Long idProducto:usuario.getProductos()) {
				Producto prod = repository.findOne(idProducto);
				if(prod!=null) {
					productos.add(prod);
				}
			}
			return productos;
		}else {
			return productos;
		}
	}

	/**
     * Metodo del negocio que elimina un producto de la BD
     * @param  producto
     * @return  booleano con estado del producto
     */
	public Collection<Producto> eliminarProducto(Long idUsuario,Long idProducto) {
		Usuario usuario = repositoryUsuario.findOne(idUsuario);
		ArrayList<Long> productos= new ArrayList<Long>();
		ArrayList<Producto> productosCompletos= new ArrayList<Producto>();
		if(usuario!= null) {
			for(Long idProd:usuario.getProductos()) {
				if(idProd ==idProducto) {
					repository.delete(idProd);
				}else {
					productos.add(idProd);
					productosCompletos.add(repository.findOne(idProd));
				}
			}
			usuario.setProductos(productos);
			repositoryUsuario.save(usuario);
			return productosCompletos;
		}else {
			return productosCompletos;
		}
	}
	
	/**
     * Metodo del negocio que regresa el producto especificado de la BD
     * @param   idProducto
     * @return  Producto
     */
	public Producto ProductoById(Long idProducto) {
		return repository.findOne(idProducto);
	}
	
	public Archivo modificaImagen(Long idUsuario,Long idProducto,Archivo archivo) {
		Usuario usuario = repositoryUsuario.findOne(idUsuario);
		if(usuario!= null) {
			for(Long idProd:usuario.getProductos()) {
				if(idProd == idProducto) {
					Producto producto= repository.findOne(idProducto);
					if(producto!=null) {
						Archivo archAnterior =producto.getArchivo();
						producto.setArchivo(archivo);
						repository.save(producto);
						return archAnterior;
					}else {
						return null;
					}
				}
			}
			return null;
		}else {
			return null;
		}
	}
	
	public boolean modificaProducto(Long idUsuario,Long idProducto,String nombre,Double precio,String descripcion) {
		Usuario usuario = repositoryUsuario.findOne(idUsuario);
		if(usuario!= null) {
			for(Long idProd:usuario.getProductos()) {
				if(idProd == idProducto) {
					Producto producto= repository.findOne(idProducto);
					if(producto!=null) {
						producto.setNombre(nombre);
						producto.setPrecio(precio);
						producto.setDescripcion(descripcion);
						repository.save(producto);
						return true;
					}else {
						return false;
					}
				}
			}
			return false;
		}else {
			return false;
		}
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
