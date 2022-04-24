package co.gov.dane.services.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.output.NullOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import co.gov.dane.inmodel.SrvRecoleccion;
import co.gov.dane.inmodel.SrvRecoleccion01;
import co.gov.dane.persistence.DataInput;
import co.gov.dane.persistence.DataManager;
import co.gov.dane.services.IndiceService;
import co.gov.dane.util.Util;
import gov.dane.indices.enums.TipoCarga;
import gov.dane.indices.model.DmcUsuario;
import gov.dane.indices.model.Status;
import gov.dane.indices.model.Usuario;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class IndiceServiceImpl implements IndiceService {
	

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
	
	
	@Override
	public Status procesarArchivo(MultipartFile file,Boolean insumo) {

		Status status = null;
		if (!file.isEmpty()) {
			ZipInputStream is = null;
			OutputStream os = new NullOutputStream();   // org.apache.commons.io.output.NullOutputStream

			/* Buffered IO */
			BufferedInputStream bis = null;
			BufferedOutputStream bos = new BufferedOutputStream(os);

			try{
				ZipFile zipFile = new ZipFile(Util.convert(file));
				if (zipFile.isEncrypted()) {
					zipFile.setPassword(passwordZip); //colocarla en el properties
				}
				List<FileHeader> fileHeaderList = zipFile.getFileHeaders();
				for (FileHeader fileHeader : fileHeaderList) {
					if (fileHeader != null) {
						is = zipFile.getInputStream(fileHeader);
						bis = new BufferedInputStream(is);


						BufferedReader r = new BufferedReader(
								new InputStreamReader(bis, StandardCharsets.UTF_8));
						StringBuilder responseStrBuilder = new StringBuilder();
						String inputStr;
						while ((inputStr = r.readLine()) != null){
							responseStrBuilder.append(inputStr);
						}	

						System.out.println(responseStrBuilder);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S",Locale.US);
						Gson gson = new GsonBuilder().setDateFormat(sdf.toPattern()).create();
						
						if(insumo){
							SrvRecoleccion srvRecoleccion = gson.fromJson(responseStrBuilder.toString(), SrvRecoleccion.class);
							
							DataInput di = new DataInput(databaseUser,  databasePassword,  databaseHost,  databasePort,
									 databaseName);
							status =  di.setRecoleccionInsumo(srvRecoleccion.idPersona, srvRecoleccion);
						}else{
							SrvRecoleccion01 srvRecoleccion = gson.fromJson(responseStrBuilder.toString(), SrvRecoleccion01.class);
							
							DataInput di = new DataInput(databaseUser,  databasePassword,  databaseHost,  databasePort,
									 databaseName);
							status =  di.setRecoleccionInsumo01(srvRecoleccion.idPersona, srvRecoleccion);
						}
						
						
						
						Util.closeFileHandlers(is, os);
		                    
					}
				}
			} catch (ZipException e) {
				System.out.println(e);
				return (new Status("Error descomprimiendo el Zip", Status.StatusType.ERROR));

			} catch (FileNotFoundException e) {
				System.out.println(e);
				return (new Status("Error archivo no encontrado", Status.StatusType.ERROR));

			} catch (IOException e) {
				System.out.println(e.toString());
				return (new Status("Error en el interior del archivo", Status.StatusType.ERROR));

			} catch (Exception e) {
				System.out.println(e);
				return (new Status("Error descomprimiendo el Zip", Status.StatusType.ERROR));
			}
		}
		return status;
	}
	
	@Override
	public FileSystemResource obtenerArchivoJson(  String usuario, String clave , TipoCarga tipoCarga)throws IOException, ZipException {
		
		DmcUsuario dmcUsuario =DataManager.getInstance().obtenerUsuario(usuario,clave);
		int idPersona = 0;
		if(dmcUsuario!=null){
			if(dmcUsuario.codError == 1){
				 throw new RuntimeException(dmcUsuario.mensaje);
			}else{
				idPersona = dmcUsuario.idPersona;
			}
			
		}else{
			return null;
		}
		
		Calendar calendar = Calendar.getInstance();
		Date time = calendar.getTime();
		long milliseconds = time.getTime();
		Gson gson = new Gson();
		ZipFile zipFile = new ZipFile(tipoCarga.toString() + idPersona + "_" + milliseconds+".zip");
		ZipParameters parameters = new ZipParameters();
		// set compression method to store compression
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		// Set the compression level
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		String json = null;

		if(tipoCarga.equals(TipoCarga.INSUMOS)){
			json=	DataManager.getInstance().obtenerDatosInsumos(idPersona).toString();
		}else if(tipoCarga.equals(TipoCarga.INSUMOS01)){
			json = DataManager.getInstance().obtenerDatosInsumos01(idPersona).toString();	
		}else if(tipoCarga.equals(TipoCarga.ACCESO)){
			Usuario tmpUsuario = new Usuario();
			tmpUsuario.clave = clave;
			tmpUsuario.usuario = usuario;
			tmpUsuario.nombrePerfil = dmcUsuario.nombrePerfil;
			tmpUsuario.uspeID = new Long(dmcUsuario.idPersona);
			json = gson.toJson(tmpUsuario);
		}
		
		File file = new File(tipoCarga.toString() +idPersona + "_"+ milliseconds+".json");
		// if file doesnt exists, then create it

		if (!file.exists()) {
			file.createNewFile();
		}
		System.out.println(new Date().toString() + " Termino de escribir en el archivo");
		FileWriter fileWriter = new FileWriter(file.getAbsolutePath());
		fileWriter.write(json);
		fileWriter.flush();
		fileWriter.close();
		zipFile.addFile(file, parameters);
		return new FileSystemResource(zipFile.getFile()); 
	}

}
