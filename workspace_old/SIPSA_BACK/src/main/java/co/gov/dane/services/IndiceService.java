package co.gov.dane.services;


import java.io.IOException;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

import gov.dane.indices.enums.TipoCarga;
import gov.dane.indices.model.Status;
import net.lingala.zip4j.exception.ZipException;


/**
 * Esta interfaz declara los metodos para procesar recoleccion
 * @author djromero *
 */
public interface IndiceService {
	/**
	 * Procesa un archivo zip que contiene una recoleccion de insumo
	 * @param file 	archivo zip
	 * @param insumo true= insumo false=insumo01
	 * @return El estado del procesamiento
	
	 */
	Status procesarArchivo(MultipartFile file, Boolean insumo) ;

	
	FileSystemResource obtenerArchivoJson(String usuario, String clave, TipoCarga tipoCarga)
			throws IOException, ZipException;
	
	
	
}
