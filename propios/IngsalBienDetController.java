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
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

import siasi.comun.transaccion.UtilLog;
import siasi.configuraciones.ValorCatalogoBean;
import siasi.principal.CabeceraEjb;
import siasi.principal.DatosUsuarioBean;
import siasi.utilities.ControllerBean;

@ManagedBean(name = "ingsalBienDetController")
@SessionScoped
public class IngsalBienDetController extends ControllerBean implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	CabeceraEjb cabeceraEjb;
	
	@EJB
	IngsalBienDet ingsalBienDetEjb;
	
	private List<IngsalBienDetBean> listaIngsalBienDet;
	private IngsalBienDetBean ingsalBienDetBean;
	private String idUsuario;
	List<IngsalTmpActFijoBean> activosFijosFiltrados;
	
	
	@PostConstruct
	public void inicio() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		//GLOBAL$IDUSUARIO
		DatosUsuarioBean datosUsuarioBean =(DatosUsuarioBean ) req.getSession().getAttribute("GLOBAL$USUARIO");
		idUsuario=datosUsuarioBean.getIdUsuario();
		
		ingsalBienDetBean = new IngsalBienDetBean();
	}
	
	public void initIngsalDet(){
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String idSolBienDet = (String)req.getSession().getAttribute("idSolBienDet");
		
		if(idSolBienDet != null){
			//solIngsalBienBean = solIngsalBien.getDatosSolIngsalBien(idSolIngsal);
		}else{
			ingsalBienDetBean = new IngsalBienDetBean();
		}
	}
	
	public void listaIngsalBienDet(){
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String idSolIngsal = (String)req.getSession().getAttribute("idSolIngsal");
		String ingresoSalida = (String)req.getSession().getAttribute("ingresoSalida");
		
		listaIngsalBienDet = ingsalBienDetEjb.getListaIngsalBienDet(idSolIngsal, ingresoSalida);
		
	}

	public List<IngsalBienDetBean> getListaIngsalBienDet() {
		return listaIngsalBienDet;
	}

	public void setListaIngsalBienDet(List<IngsalBienDetBean> listaIngsalBienDet) {
		this.listaIngsalBienDet = listaIngsalBienDet;
	}

	public IngsalBienDetBean getIngsalBienDetBean() {
		return ingsalBienDetBean;
	}

	public void setIngsalBienDetBean(IngsalBienDetBean ingsalBienDetBean) {
		this.ingsalBienDetBean = ingsalBienDetBean;
	}
	
	public void crearIngsalDet(){
		
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("draggable", false);
		options.put("resizable", false);
		options.put("contentHeight", 500);
		options.put("contentWidth", 700);
		ingsalBienDetBean = null;
		
		RequestContext.getCurrentInstance().openDialog("IngsalBienDetCrear");
	}
	
	public void guardarIngsalDet(){
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Se recepcionpó la solicitud.");
		
		int idSolIngsal = Integer.parseInt((String)req.getSession().getAttribute("idSolIngsal"));
		
		try{
			ingsalBienDetBean.setIdSolIngsal(idSolIngsal);
			ingsalBienDetEjb.crearIngsalDet(ingsalBienDetBean, idUsuario, req.getRemoteAddr());
			this.listaIngsalBienDet();
			//message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "El detalle se guardo correctamente.");
			//RequestContext.getCurrentInstance().showMessageInDialog(message);
			RequestContext.getCurrentInstance().closeDialog("IngsalBienDetCrear");
			
			
		}catch(Exception e){
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Hubo un problema al recepcionar el detalle de la solicitud.");
			UtilLog.loadError(idUsuario, this.getClass(), e, req.getRemoteAddr());
			RequestContext.getCurrentInstance().showMessageInDialog(message);
			//return null;
		}
	}
	
	public void autocompletarTmpActFijos(String query){
		List<IngsalTmpActFijoBean> activosFijosCompleto = ingsalBienDetEjb.getListaTmpActivosFijos();
		activosFijosFiltrados = new ArrayList<IngsalTmpActFijoBean>();
		
		for (int i = 0; i<activosFijosCompleto.size();i++){
			IngsalTmpActFijoBean tmpActivoFijo = activosFijosCompleto.get(i);
			if(tmpActivoFijo.getCodigoExtendido().toLowerCase().startsWith(query)){
				activosFijosFiltrados.add(tmpActivoFijo);
			}
		}
		
		//return activosFijosFiltrados;
	}

	public List<IngsalTmpActFijoBean> getActivosFijosFiltrados() {
		return activosFijosFiltrados;
	}

	public void setActivosFijosFiltrados(
			List<IngsalTmpActFijoBean> activosFijosFiltrados) {
		this.activosFijosFiltrados = activosFijosFiltrados;
	}
	
	
	
}
