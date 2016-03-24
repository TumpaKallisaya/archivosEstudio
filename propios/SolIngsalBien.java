package siasi.seguimiento;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import org.apache.tika.parser.txt.TXTParser;

import siasi.comun.sql.QueryUtil;
import siasi.comun.transaccion.TransactionBean;
import siasi.comun.transaccion.UtilTransaction;
import siasi.seguimiento.dao.TxSolIngSalBien;
import thredds.cataloggen.config.DatasetSource;

@Stateless
@LocalBean
public class SolIngsalBien {

	@SuppressWarnings("unchecked")
	public List<SolIngsalBienBean> getListaIngsalBien(String idUsuario){
		//System.out.println("El usuario que llego es el: " + idUsuario);
		List<SolIngsalBienBean> result = new ArrayList<SolIngsalBienBean>();
		StringBuilder 	bl = new StringBuilder("select  isa.id_sol_ingsal, isa.id_usu_solicitante, isa.ingreso_salida, isa.motivo, "); 
						bl.append(" isa.descripcion, isa.fecha_desde, isa.fecha_hasta, isa.id_usu_autorizador, isa.tx_id, isa.tx_fecha, ");
						bl.append(" isa.usuario, isa.estado, u.alias,cv.descripcion as desc_ing_sal, ");
						bl.append(" initcap(lower(p.primer_nombre)) as nomsol, initcap(lower(p.primer_apellido)) as papesol, initcap(lower(p.segundo_apellido)) as sapesol, ");
						bl.append(" null as nomaut, null as papeaut, null as sapeaut ");
						bl.append(" from monitorrfid.sol_ingsal_bien isa, seguridad.tx_usuario u, seguridad.tx_usuario u2, seguridad.tx_persona p, configuraciones.cnf_valor cv ");
						bl.append(" where (isa.id_usu_solicitante = :idUsuario or isa.usuario::int = :idUsuario) ");
						bl.append(" and isa.id_usu_solicitante = u.id_usuario ");
						bl.append(" and isa.usuario::int = u2.id_usuario ");
						bl.append(" and u.id_persona = p.id_persona ");
						bl.append(" and isa.ingreso_salida = cv.id_valor ");
						bl.append(" and isa.id_usu_autorizador is null ");
						bl.append(" union ");
						bl.append(" select  isa.id_sol_ingsal, isa.id_usu_solicitante, isa.ingreso_salida, isa.motivo, ");
						bl.append(" isa.descripcion, isa.fecha_desde, isa.fecha_hasta, isa.id_usu_autorizador, isa.tx_id, isa.tx_fecha, ");
						bl.append(" isa.usuario, isa.estado, u.alias, cv.descripcion as desc_ing_sal, ");
						bl.append(" initcap(lower(p.primer_nombre)) as nomsol, initcap(lower(p.primer_apellido)) as papesol, initcap(lower(p.segundo_apellido)) as sapesol, ");
						bl.append(" initcap(lower(p2.primer_nombre)) as nomaut, initcap(lower(p2.primer_apellido)) as papeaut, initcap(lower(p2.segundo_apellido)) as sapeaut ");
						bl.append(" from monitorrfid.sol_ingsal_bien isa, seguridad.tx_usuario u, seguridad.tx_usuario u2, seguridad.tx_persona p, seguridad.tx_persona p2, configuraciones.cnf_valor cv ");
						bl.append(" where (isa.id_usu_solicitante = :idUsuario or isa.usuario::int = :idUsuario) ");
						bl.append(" and isa.id_usu_solicitante = u.id_usuario ");
						bl.append(" and isa.id_usu_autorizador = u2.id_usuario ");
						bl.append(" and u.id_persona = p.id_persona ");
						bl.append(" and u2.id_persona = p2.id_persona ");
						bl.append(" and isa.ingreso_salida = cv.id_valor ");
						bl.append(" and isa.id_usu_autorizador is not null ");
						bl.append(" order by tx_fecha desc ");
		
		QueryUtil query = new QueryUtil(bl);
		query.setParameter("idUsuario", Integer.parseInt(idUsuario));
		
        List<Object[]> obj = (List<Object[]>) query.getResultList();
        for (Object[] object : obj) {
        	SolIngsalBienBean doc=new SolIngsalBienBean();
        	doc.setIdSolIngsal(((BigDecimal)object[0]).intValue());
        	doc.setIdUsuSolicitante(((BigDecimal)object[1]).intValue());
        	doc.setIngresoSalida(((BigDecimal)object[2]).intValue());
        	doc.setMotivo((String)object[3]);
        	doc.setDescripcion((String)object[4]);
        	doc.setFechaDesde((Date)object[5]);
        	doc.setFechaHasta((Date)object[6]);
        	if(object[7] != null)
        		doc.setIdUsuAutorizador(((BigDecimal)object[7]).intValue());
        	doc.setTxId(((BigDecimal)object[8]).intValue());
        	doc.setTxFecha((Date)object[9]);
        	doc.setUsuario((String)object[10]);
        	doc.setEstado((String)object[11]);
        	doc.setAlias((String)object[12]);
        	doc.setDescIngSal((String)object[13]);
        	doc.setNomsol((String)object[14]);
        	if ((String)object[15] != null)
        		doc.setPapesol((String)object[15]);
        	if((String)object[16] != null)
        		doc.setSapesol((String)object[16]);
        	if((String)object[17] != null)
        		doc.setNomaut((String)object[17]);
        	if((String)object[18] != null)
        		doc.setPapeaut((String)object[18]);
        	if((String)object[19] != null)
        		doc.setSapeaut((String)object[19]);
        	
        	result.add(doc);
        }
		
		return result;
	}
	
	public SolIngsalBienBean getDatosSolIngsalBien(String idSolIngsal){
		SolIngsalBienBean result = new SolIngsalBienBean();
		StringBuilder 	bl = new StringBuilder(" select isa.id_sol_ingsal, isa.id_usu_solicitante, isa.ingreso_salida, isa.motivo, ");
						bl.append(" isa.descripcion, isa.fecha_desde, isa.fecha_hasta, isa.id_usu_autorizador, isa.tx_id, isa.tx_fecha, ");
						bl.append(" isa.usuario, isa.estado ");
						bl.append(" from monitorrfid.sol_ingsal_bien isa ");
						bl.append(" where isa.id_sol_ingsal = :idSolIngsal ");
		QueryUtil query = new QueryUtil(bl);
		query.setParameter("idSolIngsal", Integer.parseInt(idSolIngsal));
		Object[] obj = (Object[])query.getSingleResult();
				
		result.setIdSolIngsal(((BigDecimal)obj[0]).intValue());
    	result.setIdUsuSolicitante(((BigDecimal)obj[1]).intValue());
    	result.setIngresoSalida(((BigDecimal)obj[2]).intValue());
    	result.setMotivo((String)obj[3]);
    	result.setDescripcion((String)obj[4]);
    	result.setFechaDesde((Date)obj[5]);
    	result.setFechaHasta((Date)obj[6]);
    	if(obj[7] != null)
    		result.setIdUsuAutorizador(((BigDecimal)obj[7]).intValue());
    	result.setTxId(((BigDecimal)obj[8]).intValue());
    	result.setTxFecha((Date)obj[9]);
    	result.setUsuario((String)obj[10]);
    	result.setEstado((String)obj[11]);
		
		return result;
	}
	
	public int crearSolicitud(SolIngsalBienBean solIngsalBienBean, String idUser, String terminal) throws Exception{
		DataSource postgresDs = UtilTransaction.getConnection();
		Connection con = postgresDs.getConnection();
		int idSolIngsal;
		con.setAutoCommit(false);
		try{
			TransactionBean transactionBean = UtilTransaction.generarTransaccion(idUser, terminal);
			idSolIngsal = TxSolIngSalBien.create(solIngsalBienBean.getIdUsuSolicitante(), solIngsalBienBean.getIngresoSalida(), solIngsalBienBean.getMotivo(), 
					solIngsalBienBean.getDescripcion(), solIngsalBienBean.getFechaDesde(), solIngsalBienBean.getFechaHasta(), "A", transactionBean, con);
			con.commit();
		}catch(Exception e){
			con.rollback();
			throw e;
		}finally{
			con.close();
		}
		return idSolIngsal;
		
	}
	
	public void modificarSolicitud(SolIngsalBienBean solIngsalBienBean, String idUser, String terminal) throws Exception{
		DataSource postgresDs = UtilTransaction.getConnection();
		Connection con = postgresDs.getConnection();
		con.setAutoCommit(false);
		
		try{
			TransactionBean transactionBean = UtilTransaction.generarTransaccion(idUser, terminal);
			int idSolIngsal = TxSolIngSalBien.update(solIngsalBienBean.getIdSolIngsal(), solIngsalBienBean.getIdUsuSolicitante(), solIngsalBienBean.getIngresoSalida(), solIngsalBienBean.getMotivo(), 
					solIngsalBienBean.getDescripcion(), solIngsalBienBean.getFechaDesde(), solIngsalBienBean.getFechaHasta(), "A", transactionBean, con);
			con.commit();
		}catch(Exception e){
			con.rollback();
		}finally{
			con.close();
		}
	}
}
