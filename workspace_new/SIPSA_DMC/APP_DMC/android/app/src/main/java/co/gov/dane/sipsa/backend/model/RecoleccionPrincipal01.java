package co.gov.dane.sipsa.backend.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hdblanco on 23/09/17.
 */

public class RecoleccionPrincipal01 implements Serializable {

    private Long id;
    private Long tireId;
    private String tireNombre;
    private Long futiId;
    private String muniId;
    private String muniNombre;
    private Long fuenId;
    private String fuenNombre;
    private Long artiId;
    private String artiNombre;
    private Long grin2Id;
    private String nombreComplemento;
    private Long artiVlrMinTomas;
    private Long artiVlrMaxTomas;
    private Long artiVlrMinRondas;
    private Long artiVlrMaxRondas;
    private Long artiVlrMinDiasm;
    private Long artiVlrMaxDiasm;
    private Date prreFechaProgramada;
    private Long promAnterior;
    private String vapeDescripcion;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTireNombre() {
        return tireNombre;
    }

    public void setTireNombre(String tireNombre) {
        this.tireNombre = tireNombre;
    }

    public String getMuniNombre() {
        return muniNombre;
    }

    public void setMuniNombre(String muniNombre) {
        this.muniNombre = muniNombre;
    }

    public Long getTireId() {
        return tireId;
    }

    public void setTireId(Long tireId) {
        this.tireId = tireId;
    }

    public Long getFutiId() {
        return futiId;
    }

    public void setFutiId(Long futiId) {
        this.futiId = futiId;
    }

    public String getMuniId() {
        return muniId;
    }

    public void setMuniId(String muniId) {
        this.muniId = muniId;
    }

    public Long getFuenId() {
        return fuenId;
    }

    public void setFuenId(Long fuenId) {
        this.fuenId = fuenId;
    }

    public String getFuenNombre() {
        return fuenNombre;
    }

    public void setFuenNombre(String fuenNombre) {
        this.fuenNombre = fuenNombre;
    }

    public Long getArtiId() {
        return artiId;
    }

    public void setArtiId(Long artiId) {
        this.artiId = artiId;
    }

    public String getArtiNombre() {
        return artiNombre;
    }

    public void setArtiNombre(String artiNombre) {
        this.artiNombre = artiNombre;
    }

    public Long getGrin2Id() {
        return grin2Id;
    }

    public void setGrin2Id(Long grin2Id) {
        this.grin2Id = grin2Id;
    }

    public String getNombreComplemento() {
        return nombreComplemento;
    }

    public void setNombreComplemento(String nombreComplemento) {
        this.nombreComplemento = nombreComplemento;
    }

    public Long getArtiVlrMinTomas() {
        return artiVlrMinTomas;
    }

    public void setArtiVlrMinTomas(Long artiVlrMinTomas) {
        this.artiVlrMinTomas = artiVlrMinTomas;
    }

    public Long getArtiVlrMaxTomas() {
        return artiVlrMaxTomas;
    }

    public void setArtiVlrMaxTomas(Long artiVlrMaxTomas) {
        this.artiVlrMaxTomas = artiVlrMaxTomas;
    }

    public Long getArtiVlrMinRondas() {
        return artiVlrMinRondas;
    }

    public void setArtiVlrMinRondas(Long artiVlrMinRondas) {
        this.artiVlrMinRondas = artiVlrMinRondas;
    }

    public Long getArtiVlrMaxRondas() {
        return artiVlrMaxRondas;
    }

    public void setArtiVlrMaxRondas(Long artiVlrMaxRondas) {
        this.artiVlrMaxRondas = artiVlrMaxRondas;
    }

    public Long getArtiVlrMinDiasm() {
        return artiVlrMinDiasm;
    }

    public void setArtiVlrMinDiasm(Long artiVlrMinDiasm) {
        this.artiVlrMinDiasm = artiVlrMinDiasm;
    }

    public Long getArtiVlrMaxDiasm() {
        return artiVlrMaxDiasm;
    }

    public void setArtiVlrMaxDiasm(Long artiVlrMaxDiasm) {
        this.artiVlrMaxDiasm = artiVlrMaxDiasm;
    }

    public Date getPrreFechaProgramada() {
        return prreFechaProgramada;
    }

    public void setPrreFechaProgramada(Date prreFechaProgramada) {
        this.prreFechaProgramada = prreFechaProgramada;
    }

    public Long getPromAnterior() {
        return promAnterior;
    }

    public void setPromAnterior(Long promAnterior) {
        this.promAnterior = promAnterior;
    }

    public String getVapeDescripcion() {
        return vapeDescripcion;
    }

    public void setVapeDescripcion(String vapeDescripcion) {
        this.vapeDescripcion = vapeDescripcion;
    }
}
