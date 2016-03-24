package siasi.seguimiento;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import siasi.comun.sql.QueryUtil;
import siasi.comun.transaccion.TransactionBean;
import siasi.comun.transaccion.UtilTransaction;
import siasi.seguimiento.dao.TxSolBienDet;
import siasi.seguimiento.dao.TxSolIngSalBien;

@Stateless
@LocalBean
public class IngsalBienDet {

	@SuppressWarnings("unchecked")
	public List<IngsalBienDetBean> getListaIngsalBienDet(String idSolIngsal, String ingresoSalida){
		List<IngsalBienDetBean> result = new ArrayList<IngsalBienDetBean>();
		
		if(Integer.parseInt(ingresoSalida) == 6117){
			//System.out.println("Ingrsa 6117");
			StringBuilder 	bl = new StringBuilder(" select bd.id_sol_bien_det, bd.id_sol_ingsal, bd.bien_ext_descripcion, bd.bien_ext_marca, ");
							bl.append(" bd.bien_ext_serie, bd.bien_ext_accesorios, bd.bien_ext_obs, bd.tx_id, bd.tx_fecha, bd.usuario, bd.estado ");
							bl.append(" from monitorrfid.sol_bien_det bd, monitorrfid.sol_ingsal_bien ib ");
							bl.append(" where bd.id_sol_ingsal = ib.id_sol_ingsal ");
							bl.append(" and ib.ingreso_salida = :ingresoSalida "); // Ingreso
							bl.append(" and bd.id_sol_ingsal = :idSolIngsal ");
							
			QueryUtil query = new QueryUtil(bl);
			query.setParameter("idSolIngsal", Integer.parseInt(idSolIngsal));
			query.setParameter("ingresoSalida", Integer.parseInt(ingresoSalida));
			
			List<Object[]> obj = (List<Object[]>)query.getResultList();
			for(Object[] object : obj){
				IngsalBienDetBean doc = new IngsalBienDetBean();
				doc.setIdSolBienDet(((BigDecimal)object[0]).intValue());
				doc.setIdSolIngsal(((BigDecimal)object[1]).intValue());
				doc.setBienExtDescripcion((String)object[2]);
				doc.setBienExtMarca((String)object[3]);
				doc.setBienExtSerie((String)object[4]);
				doc.setBienExtAccesorios((String)object[5]);			
				doc.setBienExtObs((String)object[6]);
				doc.setTxId(((BigDecimal)object[7]).intValue());
				doc.setTxFecha((Date)object[8]);
				doc.setUsuario((String)object[9]);
				doc.setEstado((String)object[10]);
				
				result.add(doc);
		}
		}else{
			//System.out.println("Ingrsa 6118");
			StringBuilder 	bl = new StringBuilder(" select bd.id_sol_bien_det, bd.id_sol_ingsal, bd.id_activo, bd.acces_no_inv, bd.detalle_acces_no_inv, ");
							bl.append(" bd.tx_id, bd.tx_fecha, bd.usuario, bd.estado ");
							bl.append(" from monitorrfid.sol_bien_det bd, monitorrfid.sol_ingsal_bien ib ");
							bl.append(" where bd.id_sol_ingsal = ib.id_sol_ingsal ");
							bl.append(" and ib.ingreso_salida = :ingresoSalida "); // Salida
							bl.append(" and bd.id_sol_ingsal = :idSolIngsal ");
							
			QueryUtil query = new QueryUtil(bl);
			query.setParameter("idSolIngsal", Integer.parseInt(idSolIngsal));
			query.setParameter("ingresoSalida", Integer.parseInt(ingresoSalida));
				
			List<Object[]> obj = (List<Object[]>)query.getResultList();
			for(Object[] object : obj){
				IngsalBienDetBean doc = new IngsalBienDetBean();
				doc.setIdSolBienDet(((BigDecimal)object[0]).intValue());
				doc.setIdSolIngsal(((BigDecimal)object[1]).intValue());
				doc.setIdActivo((Integer)object[2]);
				doc.setAccesNoInv((String)object[3]);
				doc.setDetalleAccesNoInv((String)object[4]);
				doc.setTxId(((BigDecimal)object[5]).intValue());
				doc.setTxFecha((Date)object[6]);
				doc.setUsuario((String)object[7]);
				doc.setEstado((String)object[8]);
				
				result.add(doc);
			}
		}
		
		return result;		
	}
	
	public int crearIngsalDet(IngsalBienDetBean ingsalBienDetBean, String idUser, String terminal) throws Exception{
		DataSource postgresDs = UtilTransaction.getConnection();
		Connection con = postgresDs.getConnection();
		int idSolBienDet;
		con.setAutoCommit(false);
		try{
			TransactionBean transactionBean = UtilTransaction.generarTransaccion(idUser, terminal);
			idSolBienDet = TxSolBienDet.create(ingsalBienDetBean.getIdSolIngsal(), ingsalBienDetBean.getIdActivo(), ingsalBienDetBean.getAccesNoInv(),
					ingsalBienDetBean.getDetalleAccesNoInv(), ingsalBienDetBean.getBienExtDescripcion(), ingsalBienDetBean.getBienExtMarca(),
					ingsalBienDetBean.getBienExtSerie(), ingsalBienDetBean.getBienExtAccesorios(), ingsalBienDetBean.getBienExtObs(), "A", transactionBean, con);
			con.commit();
		}catch(Exception e){
			con.rollback();
			throw e;
		}finally{
			con.close();
		}
		return idSolBienDet;
	}
	
	@SuppressWarnings("unchecked")
	public List<IngsalTmpActFijoBean> getListaTmpActivosFijos(){
		List<IngsalTmpActFijoBean> result = new ArrayList<IngsalTmpActFijoBean>();
		
		StringBuilder 	bl = new StringBuilder(" distinct(afv.id_activo_fijo), afv.gestion, afv.id_usuario_asignado, afv.observaciones, afv.codigo_extendido, ");
						bl.append(" afv.codigo_rfid, afv.cat_estado_activo_fijo, afv.desc_sub_fam, afv.desc_fam ");
						bl.append(" from monitorrfid.ingsal_act_fijo_view afv ");
						bl.append(" order by afv.id_activo_fijo; ");
						
				QueryUtil query = new QueryUtil(bl);
				
				List<Object[]> obj = (List<Object[]>)query.getResultList();
				
				for(Object[] object : obj){
					IngsalTmpActFijoBean doc = new IngsalTmpActFijoBean();
					
					doc.setIdActivoFijo(((BigDecimal)object[0]).intValue());
					doc.setGestion(((BigDecimal)object[1]).intValue());
					doc.setIdUsuarioAsignado(((BigDecimal)object[2]).intValue());
					doc.setObservaciones((String)object[3]);
					doc.setCodigoExtendido((String)object[4]);
					doc.setCodigoRfid((String)object[5]);			
					doc.setCatEstadoActFijo((String)object[6]);
					doc.setDescSubFam((String)object[7]);
					doc.setDescFam((String)object[8]);
					
					result.add(doc);
				}
				
		return result;
	}
}
