package siasi.seguimiento.dao;

import java.sql.Connection;

import siasi.comun.sql.QueryUtil;
import siasi.comun.sql.UtilSql;
import siasi.comun.transaccion.TransactionBean;
import siasi.comun.util.UtilString;

public class TxSolBienDet {

	public static int create(int idSolIngsal, int idActivo, String accesNoInv, String detalleAccesNoInv, String bienExtDescripcion, String bienExtMarca, 
			String bienExtSerie, String bienExtAccesorios, String bienExtObs, String estado, TransactionBean transactioBean, Connection con){
		
		String idSolBienDet = UtilSql.getSecuencia("monitorrfid.seq_sol_bdet");
		
		StringBuilder 	bl = new StringBuilder(" INSERT INTO monitorrfid.sol_bien_det ");
						bl.append(" ( id_sol_bien_det, id_sol_ingsal, id_activo, acces_no_inv, detalle_acces_no_inv, bien_ext_descripcion, bien_ext_marca, bien_ext_serie, bien_ext_accesorios, bien_ext_obs, tx_id, tx_fecha,usuario, estado ) ");
						bl.append(" VALUES( :idSolBienDet, :idSolIngsal, :idActivo, :accesNoInv, :detalleAccesNoInv, :bienExtDescripcion, :bienExtMarca, :bienExtSerie, :bienExtAccesorios, :bienExtObs, :txId, :txFecha, :usuario, :estado ) ");
						
		QueryUtil query = new QueryUtil(bl);
		
		query.setParameter("idSolBienDet", Integer.parseInt(idSolBienDet));
		query.setParameter("idSolIngsal", idSolIngsal);
		if(idActivo != 0)
			query.setParameter("idActivo", idActivo);
		else
			query.setParameter("idActivo", null);
		query.setParameter("accesNoInv", accesNoInv);
		query.setParameter("detalleAccesNoInv", detalleAccesNoInv);
		query.setParameter("bienExtDescripcion", bienExtDescripcion);
		query.setParameter("bienExtMarca", bienExtMarca);
		query.setParameter("bienExtSerie", bienExtSerie);
		query.setParameter("bienExtAccesorios", bienExtAccesorios);
		query.setParameter("bienExtObs", bienExtObs);
		query.setParameter("txId", transactioBean.getTxId());
		query.setParameter("txFecha", transactioBean.getFecha());
		query.setParameter("usuario", transactioBean.getUsuario());
		query.setParameter("estado", UtilString.toUpper(estado));
		query.executeUpdate(con);
						
		return Integer.parseInt(idSolBienDet);
	}
}
