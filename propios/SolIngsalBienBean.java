package siasi.seguimiento;

import java.util.Date;

public class SolIngsalBienBean {

	private int idSolIngsal;
	private int idUsuSolicitante;
	private int ingresoSalida;
	private String motivo;
	private String descripcion;
	private Date fechaDesde;
	private Date fechaHasta;
	private int idUsuAutorizador;
	private int txId;
	private Date txFecha;
	private String usuario;
	private String estado;
	
	//No proipos de la tabla
	private String alias;
	private String descIngSal;
	private String nomsol;
	private String papesol;
	private String sapesol;
	private String nomaut;
	private String papeaut;
	private String sapeaut;
	
	//getters y setters
	public int getIdSolIngsal() {
		return idSolIngsal;
	}
	public void setIdSolIngsal(int idSolIngsal) {
		this.idSolIngsal = idSolIngsal;
	}
	public int getIdUsuSolicitante() {
		return idUsuSolicitante;
	}
	public void setIdUsuSolicitante(int idUsuSolicitante) {
		this.idUsuSolicitante = idUsuSolicitante;
	}
	public int getIngresoSalida() {
		return ingresoSalida;
	}
	public void setIngresoSalida(int ingresoSalida) {
		this.ingresoSalida = ingresoSalida;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public Date getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	public int getIdUsuAutorizador() {
		return idUsuAutorizador;
	}
	public void setIdUsuAutorizador(int idUsuAutorizador) {
		this.idUsuAutorizador = idUsuAutorizador;
	}
	public int getTxId() {
		return txId;
	}
	public void setTxId(int txId) {
		this.txId = txId;
	}
	public Date getTxFecha() {
		return txFecha;
	}
	public void setTxFecha(Date txFecha) {
		this.txFecha = txFecha;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	// no propios de la tabla
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getDescIngSal() {
		return descIngSal;
	}
	public void setDescIngSal(String descIngSal) {
		this.descIngSal = descIngSal;
	}
	public String getNomsol() {
		return nomsol;
	}
	public void setNomsol(String nomsol) {
		this.nomsol = nomsol;
	}
	public String getPapesol() {
		return papesol;
	}
	public void setPapesol(String papesol) {
		this.papesol = papesol;
	}
	public String getSapesol() {
		return sapesol;
	}
	public void setSapesol(String sapesol) {
		this.sapesol = sapesol;
	}
	public String getNomaut() {
		return nomaut;
	}
	public void setNomaut(String nomaut) {
		this.nomaut = nomaut;
	}
	public String getPapeaut() {
		return papeaut;
	}
	public void setPapeaut(String papeaut) {
		this.papeaut = papeaut;
	}
	public String getSapeaut() {
		return sapeaut;
	}
	public void setSapeaut(String sapeaut) {
		this.sapeaut = sapeaut;
	}
	
}
