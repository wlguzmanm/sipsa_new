package gov.dane.indices.sqlite.dao;


public class Elemento {


    private Long idElemento;
    private String primerNivel;
    private String segundoNivel;
    private String nombreElemento;
    private String unidadMedida;
    private Integer tipo;
    private String codigoTematico;
    private Double cantidadBase;
    private String mensualObservado;
    public String codigoInvima;
	public String codigoDane;

  

    public Elemento() {
    }

    public Elemento(Long idElemento) {
        this.idElemento = idElemento;
    }

    public Elemento(Long idElemento, String primerNivel, String segundoNivel, String nombreElemento, String unidadMedida, Integer tipo, String codigoTematico, Double cantidadBase, String mensualObservado) {
        this.idElemento = idElemento;
        this.primerNivel = primerNivel;
        this.segundoNivel = segundoNivel;
        this.nombreElemento = nombreElemento;
        this.unidadMedida = unidadMedida;
        this.tipo = tipo;
        this.codigoTematico = codigoTematico;
        this.cantidadBase = cantidadBase;
        this.mensualObservado = mensualObservado;
    }

 
    public Long getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(Long idElemento) {
        this.idElemento = idElemento;
    }

    public String getPrimerNivel() {
        return primerNivel;
    }

    public void setPrimerNivel(String primerNivel) {
        this.primerNivel = primerNivel;
    }

    public String getSegundoNivel() {
        return segundoNivel;
    }

    public void setSegundoNivel(String segundoNivel) {
        this.segundoNivel = segundoNivel;
    }

    public String getNombreElemento() {
        return nombreElemento;
    }

    public void setNombreElemento(String nombreElemento) {
        this.nombreElemento = nombreElemento;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getCodigoTematico() {
        return codigoTematico;
    }

    public void setCodigoTematico(String codigoTematico) {
        this.codigoTematico = codigoTematico;
    }

    public Double getCantidadBase() {
        return cantidadBase;
    }

    public void setCantidadBase(Double cantidadBase) {
        this.cantidadBase = cantidadBase;
    }

    public String getMensualObservado() {
        return mensualObservado;
    }

    public void setMensualObservado(String mensualObservado) {
        this.mensualObservado = mensualObservado;
    }

	/**
	 * @return the codigoInvima
	 */
	public String getCodigoInvima() {
		return codigoInvima;
	}

	/**
	 * @param codigoInvima the codigoInvima to set
	 */
	public void setCodigoInvima(String codigoInvima) {
		this.codigoInvima = codigoInvima;
	}

	/**
	 * @return the codigoDane
	 */
	public String getCodigoDane() {
		return codigoDane;
	}

	/**
	 * @param codigoDane the codigoDane to set
	 */
	public void setCodigoDane(String codigoDane) {
		this.codigoDane = codigoDane;
	}

    
    
}
