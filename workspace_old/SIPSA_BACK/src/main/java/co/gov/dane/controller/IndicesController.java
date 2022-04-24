package co.gov.dane.controller;


import java.io.IOException;

import org.apache.commons.io.output.NullOutputStream;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.wordnik.swagger.annotations.Api;

import co.gov.dane.inmodel.SrvRecoleccion;
import co.gov.dane.inmodel.SrvRecoleccion01;
import co.gov.dane.inmodel.SrvRecoleccionDistrito;
import co.gov.dane.inmodel.SrvrSupervision;
import co.gov.dane.persistence.DataInput;
import co.gov.dane.persistence.DataManager;
import co.gov.dane.services.IndiceService;
import co.gov.dane.todo.TodoNotFoundException;
import co.gov.dane.todo.TodoNotFoundException;
import co.gov.dane.util.Util;
import gov.dane.indices.enums.TipoCarga;
import gov.dane.indices.model.DmcIndice;
import gov.dane.indices.model.DmcRecoleccion;
import gov.dane.indices.model.DmcSupervisor;
import gov.dane.indices.model.DmcUsuario;
import gov.dane.indices.model.DmcUsuario;
import gov.dane.indices.model.Status;
import gov.dane.indices.model.Status;
import gov.dane.indices.model.transmision.Elemento;
import gov.dane.indices.model.transmision.ElementoEspecificacion;
import gov.dane.indices.model.transmision.ParametrosMensual;
import gov.dane.indices.sqlite.dao.Usuario;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;



@Api(basePath="/Indice", value="Indices (DANE)", description="Recurso para gestionar datos relacionados a indices")
@RestController
@RequestMapping(value={"/dane/api/v1/Indice"})
@PropertySource("classpath:general.properties")
public class IndicesController {
	private static final Logger log = LoggerFactory.getLogger(IndicesController.class);

	private enum TipoCarga{
		INDICE,RECOLECCION,MENSUAL,SUPERVISION
	}
	
	@Value("${general.passwordZip}")
	private String passwordZip;
	
	
	@Value("${database.host}")
	private String databaseHost;

	@Value("${database.port}")
	private String databasePort;

	@Value("${database.name}")
	private String databaseName;

	@Value("${database.user}")
	private String databaseUser;

	@Value("${database.password}")
	private String databasePassword;
	
	@Autowired
	private IndiceService indiceService;		


	@RequestMapping(value = "obtenerId", method = RequestMethod.GET,produces={"application/json"})
	public  @ResponseBody DmcUsuario obtenerUsuario(@RequestParam String usuario, @RequestParam String clave) {
		return DataManager.getInstance().obtenerUsuario(usuario,clave);		
	}

	@RequestMapping(value = "obtenerInsumos", method = RequestMethod.GET, produces={"application/json"})
	@ResponseBody public String obtenerDatosInsumos(@RequestParam int id_persona) {
		 return DataManager.getInstance().obtenerDatosInsumos(id_persona).toString();
	}
	
	@RequestMapping(value = "obtenerInsumos01", method = RequestMethod.GET, produces={"application/json"})
	@ResponseBody public String obtenerDatosInsumos01(@RequestParam int id_persona) {
		 return DataManager.getInstance().obtenerDatosInsumos01(id_persona).toString();
	}
	
	@RequestMapping(value = "obtenerDistrito", method = RequestMethod.GET, produces={"application/json"})
	@ResponseBody public String obtenerDatosDistrito(@RequestParam int id_persona) {
		 return DataManager.getInstance().obtenerDatosDistrito(id_persona).toString();
	}
	
	@RequestMapping(value="cargarRecoleccion", method=RequestMethod.POST,produces={"application/json"})
	public Status setRecoleccion(@RequestBody SrvRecoleccion recoleccion,@RequestParam int id_persona){	
		DataInput di = new DataInput(databaseUser,  databasePassword,  databaseHost,  databasePort,
				 databaseName);
		//return  di.setRecoleccion(id_persona, recoleccion);		
		return  di.setRecoleccionInsumo(id_persona, recoleccion);
	}
	
	@RequestMapping(value="cargarDistrito", method=RequestMethod.POST,produces={"application/json"})
	public Status setRecoleccionDistrito(@RequestBody SrvRecoleccionDistrito recoleccion,@RequestParam int id_persona){	
		DataInput di = new DataInput(databaseUser,  databasePassword,  databaseHost,  databasePort,
				 databaseName);
		return  di.setRecoleccionDistrito(id_persona, recoleccion);			
	}
	
	@RequestMapping(value="cargarRecoleccion01", method=RequestMethod.POST,produces={"application/json"})
	public Status setRecoleccion01(@RequestBody SrvRecoleccion01 recoleccion,@RequestParam int id_persona){	
		DataInput di = new DataInput(databaseUser,  databasePassword,  databaseHost,  databasePort,
				 databaseName);
		return  di.setRecoleccionInsumo01(id_persona, recoleccion);			
	}
	
	@RequestMapping(value = "obtenerArchivo", method = RequestMethod.GET, produces={"application/json"})
	@ResponseBody public FileSystemResource obtenerArchivo(@RequestParam String usuario, @RequestParam String clave, @RequestParam  gov.dane.indices.enums.TipoCarga tipoArchivo) throws IOException, ZipException {
		 return indiceService.obtenerArchivoJson( usuario,clave,tipoArchivo);
	}
	
	
	@RequestMapping(value = "subirArchivoRecoleccion", method = RequestMethod.POST)
	public @ResponseBody Status subirArchivoRecoleccion(
			@RequestParam("file") MultipartFile file) {
		return indiceService.procesarArchivo(file,true);
	}
	
	@RequestMapping(value = "subirArchivoRecoleccion01", method = RequestMethod.POST)
	public @ResponseBody Status subirArchivoRecoleccion01(
			@RequestParam("file") MultipartFile file) {
		return indiceService.procesarArchivo(file,false);
	}



	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handleTodoNotFound(TodoNotFoundException ex) {
		log.error("Handling error with message: {}", ex.getMessage());
	}

}

