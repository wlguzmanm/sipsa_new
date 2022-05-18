package co.gov.dane.sipsa.backend;

import android.content.ContentValues;

import java.util.List;

import co.gov.dane.sipsa.backend.dao.Articulo;
import co.gov.dane.sipsa.backend.dao.ArticuloDistrito;
import co.gov.dane.sipsa.backend.dao.ArticuloI01;
import co.gov.dane.sipsa.backend.dao.CaracteristicaDistrito;
import co.gov.dane.sipsa.backend.dao.CaracteristicaI01;
import co.gov.dane.sipsa.backend.dao.CasaComercial;
import co.gov.dane.sipsa.backend.dao.Configuracion;
import co.gov.dane.sipsa.backend.dao.EnvioArtiCaraValoresI01;
import co.gov.dane.sipsa.backend.dao.Fuente;
import co.gov.dane.sipsa.backend.dao.FuenteArticulo;
import co.gov.dane.sipsa.backend.dao.FuenteArticuloDistrito;
import co.gov.dane.sipsa.backend.dao.FuenteDistrito;
import co.gov.dane.sipsa.backend.dao.Grupo;
import co.gov.dane.sipsa.backend.dao.InformadorI01;
import co.gov.dane.sipsa.backend.dao.Observacion;
import co.gov.dane.sipsa.backend.dao.PrincipalI01;
import co.gov.dane.sipsa.backend.dao.Recoleccion;
import co.gov.dane.sipsa.backend.dao.RecoleccionDistrito;
import co.gov.dane.sipsa.backend.dao.RecoleccionI01;
import co.gov.dane.sipsa.backend.dao.RecoleccionInsumo;
import co.gov.dane.sipsa.backend.dao.TipoRecoleccion;
import co.gov.dane.sipsa.backend.dao.UnidadMedida;
import co.gov.dane.sipsa.backend.dao.Usuario;
import co.gov.dane.sipsa.backend.dao.ValcarapermitidosI01;
import co.gov.dane.sipsa.backend.model.Elemento;
import co.gov.dane.sipsa.backend.model.Elemento01;
import co.gov.dane.sipsa.backend.model.Factor;
import co.gov.dane.sipsa.backend.model.FactorI01;
import co.gov.dane.sipsa.backend.model.GrupoFuente;
import co.gov.dane.sipsa.backend.model.Municipio;
import co.gov.dane.sipsa.backend.model.ObservacionElem;
import co.gov.dane.sipsa.backend.model.ParametrosDistrito;
import co.gov.dane.sipsa.backend.model.ParametrosInsumos;
import co.gov.dane.sipsa.backend.model.ParametrosInsumos01;
import co.gov.dane.sipsa.backend.model.Presentacion;
import co.gov.dane.sipsa.backend.model.RecoleccionPrincipal;
import co.gov.dane.sipsa.backend.model.Resumen01;
import co.gov.dane.sipsa.backend.model.ResumenFuente;
import co.gov.dane.sipsa.backend.model.ValorCaracteristica;


/**
 * Interface that provides methods for managing the database inside the Application.
 *
 * @author Daniel Romero
 */
public interface IDatabaseManager {

    /**
     * Closing available connections
     */
    public void closeDbConnections();
    public void dropDatabase();
    public void dropDatabaseDistrito();



    void fillParametrosInsumos(ParametrosInsumos parametrosInsumos);

    void fillParametrosInsumos01(ParametrosInsumos01 parametrosInsumos01);

    void fillParametrosDistrito(ParametrosDistrito parametrosDistrito);

    void deleteAllTables();

    void deleteAllDistritoTables();

    Configuracion getConfiguracion();

    List<Fuente> listaFuenteArticulo(String idMunicipio);

    List<TipoRecoleccion> listaTipoRecoleccion();

    List<CasaComercial> listaCasaComercial();


    List<UnidadMedida> listaUnidadMedidaByPresentacionId(Long idPresentacion);

    List<CaracteristicaDistrito> listaUnidadMedidaByPresentacionIdDistrito(Long idPresentacion);

    List<PrincipalI01> listaPrincipalI01ByArticulo(Long artiId, Long grin2Id, Long futiId);

    List<Fuente> listaFuentes();

    List<Fuente> listaFuentesByTransmitidoEstado(boolean transmitido);

    List<FuenteDistrito> listaFuentesByTransmitidoEstadoDistrito(boolean transmitido);

    List<PrincipalI01> getPrincipalI01();

    List<PrincipalI01> noFuentes(Long artiId, Long grin2Id, Long futiId);

    List<ArticuloI01> listaElementoByTransmitidoEstado01(boolean transmitido);

    List<FuenteArticulo> listaFuenteArticuloByTransmitidoEstado();

    List<FuenteArticuloDistrito> listaFuenteArticuloByTransmitidoEstadoDistrito();

    List<ObservacionElem> listaObservaciones(String novedad);

    List<ObservacionElem> listaObservacionesDistrito(String novedad);

    List<ObservacionElem> listaObservacionesTodas01();

    List<ObservacionElem> listaObservacionesExistentes01();

    List<ObservacionElem> listaObservaciones01(String novedad);

    List<Observacion> listaObservacionExistentes(Long fuenteId);

    List<Municipio> listaMunicipioFuente();

    List<Municipio> listaMunicipioI01FuenteTireI01();

    List<FuenteArticulo> obtenerFuenteArticulo(Long fuenteId, Long tireId);

    List<FuenteArticuloDistrito> obtenerFuenteArticuloDistrito(Long fuenteId, Long tireId);

    List<UnidadMedida> listaUnidadMedidaArticulo(Long idPresentacion, Long idArticulo, Long idFuente, Long idCaco, String icaId);

    List<InformadorI01> listaInformadorI01ByElementos(String idmunicipio, Long grin2Id, Long artiId);

    List<InformadorI01> listaInformadorI01(String idmunicipio);

    List<ArticuloI01> listaArticuloI01(Long tireId);

    List<ArticuloDistrito> listaArticuloDistrito(Long tireId);

    List<CaracteristicaI01> listaCarateristicaI01(Long tireId);

    List<ValcarapermitidosI01> listaValCaraI01(Long tireId, Long caraId);

    List<Presentacion> listaPresentacionArticulo();

    List<Factor> listaFactorByIdMunicipio(Long muniID);

    List<FactorI01> listaFactorByIdMunicipioI01(String muniID);

    List<FuenteArticulo> listaTipoRecoleccion(Long fuenteId);

    List<FuenteArticulo> listaTipoRecoleccionByFuenteId(Long fuenteId);

    List<FuenteArticuloDistrito> listaTipoRecoleccionDistritoByFuenteId(Long fuenteId);

    List<Elemento01> listaRecoleccionPrincipal01(Long tireId, String muniId, Long futiId);

    List<Resumen01> listaResumenRecoleccion(Long tireId, Long artiId, Long grin2Id, String muniId, Long futiId);

    List<ValorCaracteristica> listaValorCaracteristica(Long tireId, Long grin2Id, Long artiId, String muniId);

    List<ValorCaracteristica> listaValorCaracteristicaAdd(Long tireId, Long grin2Id, Long artiId, String muniId);

    List<ArticuloI01> listaArticulosAdicionado(Long tireId, String muniId);

    List<RecoleccionPrincipal> listaRecoleccionPricipal (Long tireId, Long fuenId);

    List<RecoleccionPrincipal> listaRecoleccionDistritoPricipal (Long tireId, Long fuenId);

    List<Elemento> listaElementoByTireId(Long tireId, Long fuenId, String muniId);

    List<Elemento> listaElementoDistritoByTireId(Long tireId, Long fuenId, String muniId);

    List<Grupo> listaGrupoByTireId(Long tireId);







    List<GrupoFuente> listGrupoFuente();
    Long save(String nombreTabla, ContentValues values, boolean isInsert, String where);
    void delete(Object object);
    void deleteAll(Class clazz);
    RecoleccionInsumo getRecoleccionById(Long recoleccionId);

    RecoleccionI01 getRecoleccionI01ById(Long recoleccionId);

    List<RecoleccionI01> getRecoleccionI01();

    List<EnvioArtiCaraValoresI01> getArtiCaraValoresI01();

    void updateRecoleccion(RecoleccionInsumo recoleccion);
    void updateSecuencias();


    List<ResumenFuente> obtenerResumenFuente();

    Integer cantidadFuentesSinRecoleccion();
    void obtenerSecuencia();
    Usuario getUsuario(String nombreUsuario, String clave);
    Usuario getUsuario(String nombreUsuario);
    Usuario getUsuario();
    Integer getFuentesByEstado(String estado);

    Integer getElementosByEstados01(String estado);

    Integer getElementosByEstadosDistrito(String estado);

    String getEstadoFuente(Long idFuente, Long idPeriodo);


    Fuente getFuenteById(Long fuenteId);

    List<FuenteArticulo> getFuenteArticuloByFuenteId(Long fuenteId);

    List<FuenteArticuloDistrito> getFuenteArticuloDistritoByFuenteId(Long fuenteId);

    List<ArticuloI01> getArticuloByArtiId01(Long artiId, Long grupoInsumo);

    List<PrincipalI01> getPrincipalById01(Long artiId, Long grupoInsumo);

    List<Articulo> getArticuloByArtiId(Long artiId);

    List<Recoleccion> getRecoleccionByFuenteId(Long fuenteId);

    List<RecoleccionI01> getRecoleccionByArtiId01(Long artiId, Long grupoInsumo);

    List<RecoleccionI01> getRecoleccionByRecoId01(Long recoId);

    List<Recoleccion> getRecoleccionByRecoId(Long recoId);

    List<RecoleccionDistrito> getRecoleccionDistritoByRecoId(Long recoId);

    List<Recoleccion> listaRecoleccionTransmitir();

    List<RecoleccionDistrito> listaRecoleccionTransmitirDistrito();

    List<RecoleccionDistrito> listaRecoleccionAllDistrito(Long idFuente);

    FuenteDistrito getFuenteDistritoById(Long fuenteId);

    ArticuloDistrito getArticuloDistrito(Elemento elemento);

}
