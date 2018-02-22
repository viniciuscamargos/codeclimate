package br.ufu.facom.osrat.persist.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.ufu.facom.hpcs.agent.bean.RecordBean;
import br.ufu.facom.osrat.model.SearchFilter;
import br.ufu.facom.osrat.model.TypeLog;

public class RecordDAOJPA extends AbstractDAO<RecordBean, Integer> implements RecordDAO {

	public RecordDAOJPA(EntityManager em) {
		super(em);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RecordBean> findRecordBeans(SearchFilter filter){
		Criteria crit = getSession().createCriteria(RecordBean.class);
		
		if(filter.getGroup() != null){
			crit.createCriteria("sdf").add(Restrictions.eq("group", filter.getGroup()));
		}
		
		if(filter.getSdf() != null){
			crit.add(Restrictions.eq("sdf", filter.getSdf()));
		}
		
		if(filter.getTypeLog() != null && !TypeLog.ALL.equals(filter.getTypeLog())){
			crit.add(Restrictions.eq("typeLog", filter.getTypeLog()));
		}
		
		if(filter.getPeriodStart() != null){
			crit.add(Restrictions.ge("timeGenerated", filter.getPeriodStart()));
		}

		if(filter.getPeriodEnd() != null){
			crit.add(Restrictions.le("timeGenerated", filter.getPeriodEnd()));
		}
		
		crit.addOrder(Order.asc("timeGenerated"));
		
		return crit.list();
	}

	@Override
	public List<Double> getInfoCT(Integer idGroup) {
		Query query = getSession()
				.createSQLQuery(
						"WITH RECURSIVE tree (id, nome, nivel, id_sdf, NAME_SDF, TOTAL) AS ( "
								+ "SELECT grp.id, grp.name AS NOME,  1 AS Nivel, sdf.id as id_sdf, sdf.name as NAME_SDF, COUNT(REC.ID) AS TOTAL "
								+ "FROM SDF_GROUP grp   "
								+ "INNER JOIN SDF sdf ON grp.id = sdf.group_id "
								+ "INNER JOIN PUBLIC.TB_RECORD REC ON SDF.ID = REC.SDF_ID "
								+ "WHERE grp.id = ? "
								+ "group by GRP.ID, GRP.NAME, SDF.ID "
								+

								"  UNION "
								+

								"  SELECT m.id,  m.name AS NOME, (c.Nivel + 1) AS Nivel, sdf.id as id_sdf, sdf.name as NAME_SDF, COUNT(REC.ID) AS TOTAL "
								+ "FROM SDF_GROUP m INNER JOIN tree c ON m.PARENT_ID = c.id "
								+ "INNER JOIN SDF sdf ON m.id = sdf.group_id "
								+ "INNER JOIN PUBLIC.TB_RECORD REC ON SDF.ID = REC.SDF_ID "
								+ "group by m.id, m.name,  (c.Nivel + 1),  sdf.id, sdf.name "
								+ "  ) SELECT TOTAL FROM tree");
		query.setInteger(0, idGroup);
		List<BigInteger> list = (List<BigInteger>)query.list();
		List<Double> nrs = new ArrayList<Double>();
		for (BigInteger bigInt : list) {
			nrs.add(bigInt.doubleValue());
		}
		return nrs;
	}

	@Override
	public List<Double> getInfoNF(Integer idGroup) {
		Query query = getSession()
				.createSQLQuery(
						"WITH RECURSIVE tree (id, nome, nivel, id_sdf, NAME_SDF, TT) AS ( "
								+ "SELECT grp.id, grp.name AS NOME,  1 AS Nivel, sdf.id as id_sdf, sdf.name as NAME_SDF, REC.TIMEGENERATED AS TT "
								+ "FROM SDF_GROUP grp   "
								+ "INNER JOIN SDF sdf ON grp.id = sdf.group_id "
								+ "INNER JOIN PUBLIC.TB_RECORD REC ON SDF.ID = REC.SDF_ID "
								+ "WHERE grp.id = ? "
								+ "group by GRP.ID, GRP.NAME, SDF.ID, REC.TIMEGENERATED "
								+
								
								"  UNION "
								+
								
								"  SELECT m.id,  m.name AS NOME, (c.Nivel + 1) AS Nivel, sdf.id as id_sdf, sdf.name as NAME_SDF, REC.TIMEGENERATED AS TT "
								+ "FROM SDF_GROUP m INNER JOIN tree c ON m.PARENT_ID = c.id "
								+ "INNER JOIN SDF sdf ON m.id = sdf.group_id "
								+ "INNER JOIN PUBLIC.TB_RECORD REC ON SDF.ID = REC.SDF_ID "
								+ "group by m.id, m.name,  (c.Nivel + 1),  sdf.id, sdf.name, REC.TIMEGENERATED "
								+ "  ) SELECT TT FROM tree");
		query.setInteger(0, idGroup);
		List<BigInteger> list = (List<BigInteger>)query.list();
		List<Double> nrs = new ArrayList<Double>();
		for (BigInteger bigInt : list) {
			nrs.add(bigInt.doubleValue());
		}
		return nrs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getTbfs(SearchFilter filter){
		Criteria crit = getSession().createCriteria(RecordBean.class);
		
		if(filter.getGroup() != null){
			crit.createCriteria("sdf").add(Restrictions.eq("group", filter.getGroup()));
		}
		
		if(filter.getSdf() != null){
			crit.add(Restrictions.eq("sdf", filter.getSdf()));
		}
		
		if(filter.getTypeLog() != null && !TypeLog.ALL.equals(filter.getTypeLog())){
			crit.add(Restrictions.eq("typeLog", filter.getTypeLog()));
		}
		
		if(filter.getPeriodStart() != null){
			crit.add(Restrictions.ge("timeGenerated", filter.getPeriodStart()));
		}

		if(filter.getPeriodEnd() != null){
			crit.add(Restrictions.le("timeGenerated", filter.getPeriodEnd()));
		}
		
		crit.setProjection(Projections.property("timeGenerated"));
		
		crit.addOrder(Order.asc("timeGenerated"));
		return crit.list();
	}
}
