package gov.dane.sipsa.dao;

import gov.dane.sipsa.dao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "RECOLECCION_INSUMO".
 */
public class RecoleccionInsumo {

    private Long id;
    private Long usuarioPerfil;
    private java.util.Date fechaProgramadaRecoleccion;
    private Long idArticulo;
    private Long unidadRecoleccion;
    private Double precioAnterior;
    private String novedadAnterior;
    private Long precioRecolectado;
    private String novedadActual;
    private String observacion;
    private Long idEstado;
    private java.util.Date fechaRecoleccion;
    private java.util.Date fechaCreacion;
    private String usuarioCreacion;
    private java.util.Date fechaModificacion;
    private String usuarioModificacion;
    private long recoIdObservacion;

    /**
     * Datos para Distrito
     */
    private String tipo;
    private String frecuencia;
    private String unidadMedida;



    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient RecoleccionInsumoDao myDao;

    private Observacion RecoObservacion;
    private Long RecoObservacion__resolvedKey;


    public RecoleccionInsumo() {
    }

    public RecoleccionInsumo(Long id) {
        this.id = id;
    }

    public RecoleccionInsumo(Long id, Long usuarioPerfil, java.util.Date fechaProgramadaRecoleccion, Long idArticulo, Long unidadRecoleccion, Double precioAnterior, String novedadAnterior, Long precioRecolectado, String novedadActual, String observacion, Long idEstado, java.util.Date fechaRecoleccion, java.util.Date fechaCreacion, String usuarioCreacion, java.util.Date fechaModificacion, String usuarioModificacion, long recoIdObservacion) {
        this.id = id;
        this.usuarioPerfil = usuarioPerfil;
        this.fechaProgramadaRecoleccion = fechaProgramadaRecoleccion;
        this.idArticulo = idArticulo;
        this.unidadRecoleccion = unidadRecoleccion;
        this.precioAnterior = precioAnterior;
        this.novedadAnterior = novedadAnterior;
        this.precioRecolectado = precioRecolectado;
        this.novedadActual = novedadActual;
        this.observacion = observacion;
        this.idEstado = idEstado;
        this.fechaRecoleccion = fechaRecoleccion;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaModificacion = fechaModificacion;
        this.usuarioModificacion = usuarioModificacion;
        this.recoIdObservacion = recoIdObservacion;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRecoleccionInsumoDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioPerfil() {
        return usuarioPerfil;
    }

    public void setUsuarioPerfil(Long usuarioPerfil) {
        this.usuarioPerfil = usuarioPerfil;
    }

    public java.util.Date getFechaProgramadaRecoleccion() {
        return fechaProgramadaRecoleccion;
    }

    public void setFechaProgramadaRecoleccion(java.util.Date fechaProgramadaRecoleccion) {
        this.fechaProgramadaRecoleccion = fechaProgramadaRecoleccion;
    }

    public Long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Long getUnidadRecoleccion() {
        return unidadRecoleccion;
    }

    public void setUnidadRecoleccion(Long unidadRecoleccion) {
        this.unidadRecoleccion = unidadRecoleccion;
    }

    public Double getPrecioAnterior() {
        return precioAnterior;
    }

    public void setPrecioAnterior(Double precioAnterior) {
        this.precioAnterior = precioAnterior;
    }

    public String getNovedadAnterior() {
        return novedadAnterior;
    }

    public void setNovedadAnterior(String novedadAnterior) {
        this.novedadAnterior = novedadAnterior;
    }

    public Long getPrecioRecolectado() {
        return precioRecolectado;
    }

    public void setPrecioRecolectado(Long precioRecolectado) {
        this.precioRecolectado = precioRecolectado;
    }

    public String getNovedadActual() {
        return novedadActual;
    }

    public void setNovedadActual(String novedadActual) {
        this.novedadActual = novedadActual;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public java.util.Date getFechaRecoleccion() {
        return fechaRecoleccion;
    }

    public void setFechaRecoleccion(java.util.Date fechaRecoleccion) {
        this.fechaRecoleccion = fechaRecoleccion;
    }

    public java.util.Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(java.util.Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public java.util.Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(java.util.Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public long getRecoIdObservacion() {
        return recoIdObservacion;
    }

    public void setRecoIdObservacion(long recoIdObservacion) {
        this.recoIdObservacion = recoIdObservacion;
    }

    /** To-one relationship, resolved on first access. */
    public Observacion getRecoObservacion() {
        long __key = this.recoIdObservacion;
        if (RecoObservacion__resolvedKey == null || !RecoObservacion__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ObservacionDao targetDao = daoSession.getObservacionDao();
            Observacion RecoObservacionNew = targetDao.load(__key);
            synchronized (this) {
                RecoObservacion = RecoObservacionNew;
            	RecoObservacion__resolvedKey = __key;
            }
        }
        return RecoObservacion;
    }

    public void setRecoObservacion(Observacion RecoObservacion) {
        if (RecoObservacion == null) {
            throw new DaoException("To-one property 'recoIdObservacion' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.RecoObservacion = RecoObservacion;
            recoIdObservacion = RecoObservacion.getObseId();
            RecoObservacion__resolvedKey = recoIdObservacion;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
