package co.gov.dane.persistence;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.internal.sessions.ArrayRecord;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.CaseFormat;
import com.google.gson.Gson;

import gov.dane.indices.model.DmcUsuario;

public class DataManager {

	
	private static DataManager INSTANCE = new DataManager();

	public static DataManager getInstance() {
		return INSTANCE;
	}

	private static final Logger log = LoggerFactory.getLogger(DataManager.class);

	public DataManager() {

	}

	public JSONObject obtenerDatosInsumos(int id_persona) {
		System.out.println(new Date().toString() + " Entro a obtener datos insumos  " + id_persona);

		List<String> mensual_tbl = Arrays.asList( "TipoRecoleccion", "Principal", "Fuente",
				"FuenteArticulo", "Usuario", "Articulo", "UnidadMedida", "CasaComercial", 
				"Grupo", "Observacion", "codigo_error", "mensaje");
		
		EntityManagerFactory emfactory2 = Persistence.createEntityManagerFactory("dataSourceDane");
		EntityManager entitymanager2 = emfactory2.createEntityManager();		
		javax.persistence.Query getmensual = entitymanager2.createNamedQuery("PQ_SIPSA_RECOLECCION.Pr_CargarDMCRecoleccionI")
				.setParameter("p_uspe_id", id_persona);
		Object[] cursormensual = (Object[]) getmensual.getResultList().get(0);
		JSONObject jo = new JSONObject();
		int k = 0;

		System.out.println(new Date().toString() + " Termino de consultar Mensual de la DB " + id_persona);
		for (Object row : cursormensual) {
			JSONArray items = new JSONArray();
			if (row instanceof java.util.Vector) {
				int cursorSize = ((Vector<ArrayRecord>) row).size();
				for (int i = 0; i < cursorSize; i++) {
					JSONObject json = new JSONObject();
					ArrayRecord cursor = ((Vector<ArrayRecord>) row).elementAt(i);
					for (Object key : cursor.getFields()) {
						json.put(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key.toString() ), cursor.get(key));		
					}
					items.put(json);
				}
				jo.put(mensual_tbl.get(k), items);
				k += 1;
			}
			else {
				// AQUI SE DEBE CAPTURAR SI HUBO ERROR
			}
		}	
		return jo;	
	}
	
	
	public JSONObject obtenerDatosInsumos01(int id_persona) {
		System.out.println(new Date().toString() + " Entro a obtener datos insumos  " + id_persona);

		/*List<String> mensual_tbl = Arrays.asList( "Principal","Informadores", "FuentesTire",
				"Persona", "Articulo", "Caracteristica", "VALCARAPERMITIDOS", 
				"ARTICARAVALORES", "Observacion", "codigo_error", "mensaje");*/
		
		List<String> mensual_tbl = Arrays.asList( "PrincipalI01","InformadorI01", "FuenteTireI01",
				"Usuario", "ArticuloI01", "CaracteristicaI01", "ValcarapermitidosI01", 
				"ArtiCaraValoresI01", "ObservacionI01", "codigo_error", "mensaje");
		
		EntityManagerFactory emfactory2 = Persistence.createEntityManagerFactory("dataSourceDane");
		EntityManager entitymanager2 = emfactory2.createEntityManager();		
		javax.persistence.Query getmensual = entitymanager2.createNamedQuery("PQ_SIPSA_RECOLECCION.Pr_CargarDMCRecoleccionI2")
				.setParameter("p_uspe_id", id_persona);
		Object[] cursormensual = (Object[]) getmensual.getResultList().get(0);
		JSONObject jo = new JSONObject();
		int k = 0;

		System.out.println(new Date().toString() + " Termino de consultar Mensual de la DB " + id_persona);
		for (Object row : cursormensual) {
			JSONArray items = new JSONArray();
			if (row instanceof java.util.Vector) {
				int cursorSize = ((Vector<ArrayRecord>) row).size();
				for (int i = 0; i < cursorSize; i++) {
					JSONObject json = new JSONObject();
					ArrayRecord cursor = ((Vector<ArrayRecord>) row).elementAt(i);
					for (Object key : cursor.getFields()) {
						json.put(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key.toString() ), cursor.get(key));		
					}
					items.put(json);
				}
				jo.put(mensual_tbl.get(k), items);
				k += 1;
			}
			else {
				// AQUI SE DEBE CAPTURAR SI HUBO ERROR
			}
		}	
		return jo;	
	}
	
	public JSONObject obtenerDatosDistrito(int id_persona) {
		System.out.println(new Date().toString() + " Entro a obtener datos Distrito  " + id_persona);

		List<String> mensual_tbl = Arrays.asList( "Principal","Informadores", "Fuente",
				"FuenteArticulo", "Persona", "Articulo", "UnidadMedida", "CasaComercial", 
				"Grupo", "Caracteristica", "CaracteristicaD", 
				"ARTICARAVALORES", "Observacion", "codigo_error", "mensaje");
		
		EntityManagerFactory emfactory2 = Persistence.createEntityManagerFactory("dataSourceDane");
		EntityManager entitymanager2 = emfactory2.createEntityManager();		
		javax.persistence.Query getmensual = entitymanager2.createNamedQuery("PQ_SIPSA_RECOLECCION.Pr_CargarDMCRecoleccionDIS")
				.setParameter("p_uspe_id", id_persona);
		Object[] cursormensual = (Object[]) getmensual.getResultList().get(0);
		JSONObject jo = new JSONObject();
		int k = 0;
		System.out.println("Ajustes");
		System.out.println(new Date().toString() + " Termino de consultar Mensual de la DB " + id_persona);
		for (Object row : cursormensual) {
			JSONArray items = new JSONArray();
			if (row instanceof java.util.Vector) {
				int cursorSize = ((Vector<ArrayRecord>) row).size();
				for (int i = 0; i < cursorSize; i++) {
					JSONObject json = new JSONObject();
					ArrayRecord cursor = ((Vector<ArrayRecord>) row).elementAt(i);
					for (Object key : cursor.getFields()) {
						json.put(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key.toString() ), cursor.get(key));		
					}
					items.put(json);
				}
				jo.put(mensual_tbl.get(k), items);
				k += 1;
			}
			else {
				// AQUI SE DEBE CAPTURAR SI HUBO ERROR
			}
		}	
		System.out.println(jo);
		return jo;	
	}
	

	public DmcUsuario obtenerUsuario(String usuario, String clave) {
		List<String> tbl = Arrays.asList("idPersona", "nombrePerfil", "codError", "mensaje");
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("dataSourceDane");
		EntityManager entitymanager = emfactory.createEntityManager();
		javax.persistence.Query query = entitymanager
				.createNamedQuery("pq_sipsa_usuarios_perfiles.pr_consultarusuarioDmc").
				setParameter("p_usua_usuario", usuario).
				setParameter("p_clave", clave);
		Object[] cursores = (Object[]) query.getResultList().get(0);
		int k = 0;
		JSONObject json = new JSONObject();
		for (Object cursor : cursores) {
			json.put(tbl.get(k), cursor);
			k++;
		}
		Gson gson = new Gson();
		return gson.fromJson(json.toString(), DmcUsuario.class);
	}

	
	

}
