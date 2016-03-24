package siasi.seguimiento.dao;

import java.sql.Connection;
import java.util.Date;

import siasi.comun.sql.QueryUtil;
import siasi.comun.sql.UtilSql;
import siasi.comun.transaccion.TransactionBean;
import siasi.comun.util.UtilString;

public class TxSolIngSalBien {

	public static int create(int idUsuSolicitante, int ingresoSalida, String motivo, String descripcion, Date fechaDesde, Date fechaHasta, String estado, 
			TransactionBean transactioBean, Connection con){
		String idSolIngsal = UtilSql.getSecuencia("monitorrfid.seq_sol_ingsal");
	
		StringBuilder 	bl = new StringBuilder(" INSERT INTO monitorrfid.sol_ingsal_bien ");
						bl.append("( id_sol_ingsal, id_usu_solicitante, ingreso_salida, motivo, descripcion, fecha_desde, fecha_hasta, tx_id, tx_fecha, usuario, estado) ");
						bl.append("VALUES ( :idSolIngsal, :idUsuSolicitante, :ingresoSalida, :motivo, :descripcion, :fechaDesde, :fechaHasta, :txId, :txFecha, :usuario, :estado )");		
		QueryUtil query = new QueryUtil(bl);
		
		query.setParameter("idSolIngsal", Integer.parseInt(idSolIngsal));
		query.setParameter("idUsuSolicitante", idUsuSolicitante);
		query.setParameter("ingresoSalida", ingresoSalida);
		query.setParameter("motivo", motivo);
		query.setParameter("descripcion", descripcion);
		query.setParameter("fechaDesde", fechaDesde);
		query.setParameter("fechaHasta", fechaHasta);
		//query.setParameter("id_usu_autorizador", );
		query.setParameter("txId", transactioBean.getTxId());
		query.setParameter("txFecha", transactioBean.getFecha());
		query.setParameter("usuario", transactioBean.getUsuario());
		query.setParameter("estado", UtilString.toUpper(estado));
		query.executeUpdate(con);
		
		return Integer.parseInt(idSolIngsal);
	}
	
	public static int update(int idSolIngsal, int idUsuSolicitante, int ingresoSalida, String motivo, String descripcion, Date fechaDesde, 
			Date fechaHasta, String estado, TransactionBean transactioBean, Connection con){
		
		
		StringBuilder 	bl = new StringBuilder(" UPDATE monitorrfid.sol_ingsal_bien set ");
						bl.append(" id_usu_solicitante = :idUsuSolicitante, ingreso_salida = :ingresoSalida, motivo = :motivo, descripcion = :descripcion, ");
						bl.append(" fecha_desde = :fechaDesde, fecha_hasta = :fechaHasta, tx_id = :txId, tx_fecha = :txFecha, usuario = :usuario, estado = :estado ");
						bl.append(" where id_sol_ingsal = :idSolIngsal ");
		
		QueryUtil query = new QueryUtil(bl);
		
		query.setParameter("idSolIngsal", idSolIngsal);
		query.setParameter("idUsuSolicitante", idUsuSolicitante);
		query.setParameter("ingresoSalida", ingresoSalida);
		query.setParameter("motivo", motivo);
		query.setParameter("descripcion", descripcion);
		query.setParameter("fechaDesde", fechaDesde);
		query.setParameter("fechaHasta", fechaHasta);
		query.setParameter("txId", transactioBean.getTxId());
		query.setParameter("txFecha", transactioBean.getFecha());
		query.setParameter("usuario", transactioBean.getUsuario());
		query.setParameter("estado", UtilString.toUpper(estado));
		query.executeUpdate(con);
		
		return idSolIngsal;
	}
}
