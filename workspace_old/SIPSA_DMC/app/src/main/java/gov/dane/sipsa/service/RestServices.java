package gov.dane.sipsa.service;



import android.util.Log;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import gov.dane.sipsa.dao.FuenteArticulo;
import gov.dane.sipsa.manager.DatabaseManager;
import gov.dane.sipsa.model.EnvioRecoleccion;
import gov.dane.sipsa.model.EnvioRecoleccion01;
import gov.dane.sipsa.model.EnvioRecoleccion02;
import gov.dane.sipsa.model.ParametrosInsumos;
import gov.dane.sipsa.model.ParametrosInsumos01;
import gov.dane.sipsa.model.ParametrosDistrito;
import gov.dane.sipsa.model.Status;
import gov.dane.sipsa.model.Usuario;

public class RestServices
{
	private RestTemplate restTemplate;
	private static final String TAG = DatabaseManager.class.getCanonicalName();
	private static RestServices _instance = null;
	private static int MAX_TIMEOUT = 180 * 1000;
	private boolean logged = false;
//	private String PATH_URL = "http://apps.dane.gov.co/sipsa/dane/api/v1/";  //PRODUCTIVO http://192.168.1.157:9007/
//    private String PATH_URL = "http://192.168.1.183:9007/dane/api/v1/";  //PRUEBAS
//	private String PATH_URL = "http://10.58.1.127:9007/dane/api/v1/";   ///EN EL DANE
//	private String PATH_URL = "http://10.4.0.7:9007/dane/api/v1/";             // EN LA HOUSE
	private String PATH_URL = "http://192.168.56.1:9007/dane/api/v1/";             // EN LA HOUSE
//	private String PATH_URL = "http://172.16.10.90:9007/dane/api/v1/";

	public void setPATH_URL(String PATH_URL) {
		this.PATH_URL = PATH_URL;
	}

	private RestServices() {

	}

	public ObjectMapper objectMapper(String pattern) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JodaModule());
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		if (pattern.equals("")) {
			objectMapper.setDateFormat(new ISO8601DateFormat());
		} else {
			SimpleDateFormat dateFormat= new SimpleDateFormat(pattern);
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-05:00"));
			objectMapper.setDateFormat(dateFormat);

		}

		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//	objectMapper.registerModule(new Jackson2HalModule());
		return objectMapper;
	}

	public void configureMessageConverters( List<HttpMessageConverter<?>> messageConverters, String pattern) {
		MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
		jsonMessageConverter.setObjectMapper(objectMapper(pattern));
		jsonMessageConverter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json,application/json"));
		messageConverters.add(jsonMessageConverter);
	}

	public RestTemplate restTemplate(String pattern) {
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		configureMessageConverters(messageConverters, pattern);
		restTemplate.setMessageConverters(messageConverters);
		return restTemplate;
	}

	public static RestServices getInstance()
	{
		if ( _instance == null )
			_instance = new RestServices();
		return _instance;
	}


	public File obtenerArchivo(String idPersona, String tipoArchivo, String pathFile) {
		String url = PATH_URL + "Indice/obtenerSqlite?idPersona=" + idPersona + "&tipoArchivo=" + tipoArchivo;
		File file = null;
		try {
			RestTemplate restTemplate2 = new RestTemplate();
			restTemplate2.getMessageConverters().add(new ByteArrayHttpMessageConverter());
			org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.ALL));
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			ResponseEntity<byte[]> response = restTemplate2.exchange(url,HttpMethod.GET, entity, byte[].class);

			if(response.getStatusCode().equals(HttpStatus.OK)) {
				file=new File(pathFile + "/" + tipoArchivo + idPersona +".zip");
				FileOutputStream output = new FileOutputStream(file);
				IOUtils.write(response.getBody(), output);

			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			return file;
		}
	}

	public ParametrosInsumos obtenerInsumo(String idPersona) {
		String url = PATH_URL + "Indice/obtenerInsumos";
		Log.d(TAG, "URL CONEXION--->" + url+"?id_persona="+idPersona);
		Status status = null;
		ParametrosInsumos parametrosInsumos =null;
		try {
			parametrosInsumos = restTemplate("yyyy-MM-dd HH:mm:ss.S").
										getForObject(url + "?id_persona={idPersona}", ParametrosInsumos.class, idPersona);
			status = new Status();
			status.setType(Status.StatusType.OK);
			status.setDescription("Información Cargada Exitosamente");
			parametrosInsumos.setStatus(status);
		}catch(Exception e){
			e.printStackTrace();
			parametrosInsumos = new ParametrosInsumos();
			status = new Status();
			status.setType(Status.StatusType.ERROR);
			status.setDescription("Error recibiendo datos");
			//status.setDescription("Error recibiendo datos :"+e.getMessage());
			parametrosInsumos.setStatus(status);
		}finally {
			return parametrosInsumos;
		}
	}

	public ParametrosInsumos01 obtenerInsumo01(String idPersona) {
		String url = PATH_URL + "Indice/obtenerInsumos01";
		Log.d(TAG, "URL CONEXION--->" + url+"?id_persona="+idPersona);
		Status status = null;
		ParametrosInsumos01 parametrosInsumos01 =null;
		try {
			parametrosInsumos01 = restTemplate("yyyy-MM-dd HH:mm:ss.S").
					getForObject(url + "?id_persona={idPersona}", ParametrosInsumos01.class, idPersona);
			//System.out.println(url+"...."+idPersona);
			//System.out.println(toJson(parametrosInsumos01));
			status = new Status();
			status.setType(Status.StatusType.OK);
			status.setDescription("Información Cargada Exitosamente");
			parametrosInsumos01.setStatus(status);
			return parametrosInsumos01;
		}catch(Exception e){
			e.printStackTrace();
			parametrosInsumos01 = new ParametrosInsumos01();
			status = new Status();
			status.setType(Status.StatusType.ERROR);
			status.setDescription("Error recibiendo datos");
			//status.setDescription("Error recibiendo datos :"+e.getMessage());
			parametrosInsumos01.setStatus(status);
		}finally {
			return parametrosInsumos01;
		}
	}

	public ParametrosDistrito obtenerDistrito(String idPersona) {
		String url = PATH_URL + "Indice/obtenerDistrito";
		Log.d(TAG, "URL CONEXION--->" + url+"?id_persona="+idPersona);
		Status status = null;
		ParametrosDistrito parametrosDistrito =null;
		try {
			parametrosDistrito = restTemplate("yyyy-MM-dd HH:mm:ss.S").
					getForObject(url + "?id_persona={idPersona}", ParametrosDistrito.class, idPersona);
			status = new Status();
			status.setType(Status.StatusType.OK);
			//status.setDescription("Información Cargada Exitosamente idPersona-->"+idPersona+"---URL -->"+url+"---->parametrosDistrito: "+parametrosDistrito.Fuente.get(0).getFuenNombre()+"--->"+parametrosDistrito.Principal.toArray().toString());
			status.setDescription("Información Cargada Exitosamente...");
			parametrosDistrito.setStatus(status);
		}catch(Exception e){
			e.printStackTrace();
			parametrosDistrito = new ParametrosDistrito();
			status = new Status();
			status.setType(Status.StatusType.ERROR);
			status.setDescription("Error recibiendo datos");
			//status.setDescription("Error recibiendo datos :"+e.getMessage());
			parametrosDistrito.setStatus(status);
		}finally {
			return parametrosDistrito;
		}
	}


	public Usuario obtenerUsuario(String nombreUsuario, String clave) {
		Usuario usuario =null;
		String url = PATH_URL + "Indice/obtenerId";
		try{
			usuario  = restTemplate("yyyy-MM-dd HH:mm:ss.S").getForObject(url + "?usuario={nombreUsuario}&clave={clave}", Usuario.class, nombreUsuario,clave);
		}catch(Exception e){
			e.printStackTrace();
			usuario =new Usuario();
			usuario.setCodError(1);
			usuario.setMensaje("No hay comunicacion con el servidor, verifique su conexión a internet");
		}

		return usuario;
	}


	public Status enviarRecoleccion(EnvioRecoleccion envioRecoleccion, Integer idPersona) {

		Status status =null;
		String url = PATH_URL + "Indice/cargarRecoleccion";
		Log.d(TAG, "URL CONEXION--->" + url+"?id_persona="+idPersona);
		try{
			Log.d(TAG, toJson(envioRecoleccion));
			System.out.println(toJson(envioRecoleccion));
			status  = restTemplate("").postForObject(url + "?id_persona=" + idPersona, envioRecoleccion,Status.class);
		}catch (Exception e) {
			Log.d(TAG, "Error--->" + e.getMessage());
			Log.d(TAG, "Error--->" + e.getCause());
			Log.d(TAG, "Error--->" + e.getLocalizedMessage());
			System.out.println(e.getStackTrace());

			e.printStackTrace();
			status =new Status();
			status.setType(Status.StatusType.ERROR);
			status.setDescription("No se cargo correctamente los insumos, intente de nuevo.");
			//status.setDescription("No se cargo correctamente los insumos, intente de nuevo1--->"+e.getMessage()+"----"+e.getCause());
		}
		return status;
	}


	public Status enviarRecoleccionDistrito(EnvioRecoleccion02 envioRecoleccion, Integer idPersona) {

		Status status =null;
		String url = PATH_URL + "Indice/cargarDistrito";
		Log.d(TAG, "URL CONEXION--->" + url+"?id_persona="+idPersona);
		try{
			System.out.println(toJson(envioRecoleccion));
			status  = restTemplate("").postForObject(url + "?id_persona=" + idPersona, envioRecoleccion,Status.class);
		}catch (Exception e) {
			e.printStackTrace();
			status =new Status();
			status.setType(Status.StatusType.ERROR);
			if(e.getMessage()!= null && e.getMessage().contains("400")){
				status.setDescription("Error Servicio: "+e.getMessage());
			}else{
				status.setDescription("No se cargo correctamente los insumos, intente de nuevo.");
				//status.setDescription("No se cargo correctamente los insumos, intente de nuevo2--->"+e.getMessage()+"----"+e.getCause());
			}
		}
		return status;
	}


	public Status enviarRecoleccion01(EnvioRecoleccion01 envioRecoleccion, Integer idPersona) {
		Status status =null;
		String url = PATH_URL + "Indice/cargarRecoleccion01";
		Log.d(TAG, "URL CONEXION--->" + url+"?id_persona="+idPersona);
		try{
			Log.d(TAG, toJson(envioRecoleccion));
			System.out.println(toJson(envioRecoleccion));
			status  = restTemplate("").postForObject(url + "?id_persona=" + idPersona, envioRecoleccion,Status.class);
		}catch (Exception e) {
			e.printStackTrace();
			status =new Status();
			status.setType(Status.StatusType.ERROR);
			status.setDescription("No se cargo correctamente los insumos, intente de nuevo");
			//status.setDescription("No se cargo correctamente los insumos, intente de nuevo3--->"+e.getMessage()+"----"+e.getCause());
		}
		return status;
	}


	public static String toJson(Object jsonObject) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S", Locale.US);
		Gson gson = new GsonBuilder().setDateFormat(sdf.toPattern()).create();
		return gson.toJson(jsonObject);
	}
}



