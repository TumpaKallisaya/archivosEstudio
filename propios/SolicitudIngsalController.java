package siasi.seguimiento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

import siasi.comun.transaccion.UtilLog;
import siasi.configuraciones.CatalogoEjb;
import siasi.configuraciones.ValorCatalogoBean;
import siasi.principal.CabeceraEjb;
import siasi.principal.DatosUsuarioBean;
import siasi.principal.UsuariosController;
import siasi.utilities.ControllerBean;

@ManagedBean(name = "solicitudIngsalController")
@SessionScoped
public class SolicitudIngsalController extends ControllerBean implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	CatalogoEjb catalogo;
	
	@EJB
	CabeceraEjb cabeceraEjb;
		
	@EJB
	SolIngsalBien solIngsalBien;
	
	private List<SelectItem> comboRoles;
	private List<SolIngsalBienBean> listaIngsal;
	private SolIngsalBienBean solIngsalBienBean;
	private List<SelectItem> ingresoSalida;
	private String idUsuario;
	
	@PostConstruct
	public void inicio() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		//GLOBAL$IDUSUARIO
		DatosUsuarioBean datosUsuarioBean =(DatosUsuarioBean ) req.getSession().getAttribute("GLOBAL$USUARIO");
		idUsuario=datosUsuarioBean.getIdUsuario();		
		this.comboRoles=cabeceraEjb.getRoles(false);
		
		List<ValorCatalogoBean> ingSalcatalogo = catalogo.getValorCatalogo("1035"); // Catalogo para ingreso/salida
		ingresoSalida = getCatalogoComoSelectItem(ingSalcatalogo);
		
		solIngsalBienBean = new SolIngsalBienBean();
	}
	
	public void initSolicitud(){
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String idSolIngsal = (String)req.getSession().getAttribute("idSolIngsal");
		
		if(idSolIngsal != null){
			solIngsalBienBean = solIngsalBien.getDatosSolIngsalBien(idSolIngsal);
		}else{
			solIngsalBienBean = new SolIngsalBienBean();
		}
		
	}
	
	public void listaSolicitudesIngsal(){
		listaIngsal = solIngsalBien.getListaIngsalBien(this.idUsuario);
	}

	public List<SolIngsalBienBean> getListaIngsal() {
		return listaIngsal;
	}

	public void setListaIngsal(List<SolIngsalBienBean> listaIngsal) {
		this.listaIngsal = listaIngsal;
	}
	
	public SolIngsalBienBean getSolIngsalBienBean() {
		return solIngsalBienBean;
	}

	public void setSolIngsalBienBean(SolIngsalBienBean solInsalBienBean) {
		this.solIngsalBienBean = solInsalBienBean;
	}

	public String crearSolicitud(){
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		req.getSession().removeAttribute("idSolIngsal");
		solIngsalBienBean = null;
		req.getSession().removeAttribute("edicion");
		
		return "IngSalBienCrearSol?faces-redirect=true";
	}
	
	public String verDetalleSolicitud(int idSolIngsal, int ingresoSalida){
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		req.getSession().setAttribute("idSolIngsal", String.valueOf(idSolIngsal));
		req.getSession().setAttribute("ingresoSalida", String.valueOf(ingresoSalida));
		req.getSession().removeAttribute("edicion");
		
		return "IngSalBienLisDet?faces-redirect=true";
	}
	
	public String irDetalleSolicitud(){
		//HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		//req.getSession().setAttribute("idSolIngsal", String.valueOf(idSolIngsal));
		//req.getSession().setAttribute("ingresoSalida", String.valueOf(ingresoSalida));
		
		return "IngSalBienLisDet?faces-redirect=true";
	}
	
	private static List<SelectItem> getCatalogoComoSelectItem(List<ValorCatalogoBean> catalogos){
		if(catalogos != null){
			List<SelectItem> result = new ArrayList<SelectItem>();
			for(ValorCatalogoBean valorCatalogoBean: catalogos){
				String descripcion = valorCatalogoBean.getDescripcion();
				if(valorCatalogoBean.getDescripcion().length() > 59){
					descripcion = descripcion.substring(0, 59)+"...";
				}
				result.add(new SelectItem(valorCatalogoBean.getIdValor(), descripcion));
			}
			return result;
		}else{
			return null;
		}
	}
	
	public String guardarSolicitud(){
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Se recepcionpó la solicitud.");
		
		if(req.getSession().getAttribute("edicion") == null){
			try{
				int idSolIngsal = solIngsalBien.crearSolicitud(solIngsalBienBean, idUsuario, req.getRemoteAddr());
				req.getSession().setAttribute("idSolIngsal", String.valueOf(idSolIngsal));
				req.getSession().setAttribute("ingresoSalida", String.valueOf(solIngsalBienBean.getIngresoSalida()));
				req.getSession().setAttribute("edicion", "TRUE");
				
			}catch(Exception e){
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Hubo un problema al recepcionar la solicitud.");
				UtilLog.loadError(idUsuario, this.getClass(), e, req.getRemoteAddr());
				RequestContext.getCurrentInstance().showMessageInDialog(message);
				return null;
			}
		}else{
			try{
				solIngsalBien.modificarSolicitud(solIngsalBienBean, idUsuario, req.getRemoteAddr());
			}catch(Exception e){
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Hubo un problema al modificar la solicitud.");
				UtilLog.loadError(idUsuario, this.getClass(), e, req.getRemoteAddr());
			}
		}
		
		return "IngSalBienLisDet?faces-redirect=true";
	}
	
	public String editar(int idSolIngsal, int ingresosalida){
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		req.getSession().setAttribute("idSolIngsal", String.valueOf(idSolIngsal));
		req.getSession().setAttribute("ingresoSalida", String.valueOf(ingresosalida));
		req.getSession().setAttribute("edicion", "TRUE");
		
		return "IngSalBienCrearSol?faces-redirect=true";
	}

	public List<SelectItem> getIngresoSalida() {
		return ingresoSalida;
	}

	public void setIngresoSalida(List<SelectItem> ingresoSalida) {
		this.ingresoSalida = ingresoSalida;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
}
