package com.cable.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cable.model.OperatorModel;
import com.cable.searchmodel.SearchOperatorModel;

@Repository
public class OperatoDao {

	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void save(OperatorModel operatorModel){
		sessionFactory.getCurrentSession().saveOrUpdate(operatorModel);
	}

	@Transactional
	public void saveAll(List<OperatorModel> operatorModelList){
		for (OperatorModel operatorModel : operatorModelList) {
			sessionFactory.getCurrentSession().save(operatorModel);
		}
	}

	@Transactional
	public void deleteAll(){
		sessionFactory.getCurrentSession().createQuery("delete from OperatorModel").executeUpdate();
	}

	@Transactional
	public List<OperatorModel> getAll(){
		return sessionFactory.getCurrentSession().createQuery("from OperatorModel").list();
	}

	@Transactional
	public OperatorModel getByOperatorId(String id) {
		String hql = "from OperatorModel where operatorId='" + id+"'";
		List<OperatorModel> operatorModelList = sessionFactory.getCurrentSession().createQuery(hql).list();

		if (operatorModelList != null && !operatorModelList.isEmpty()) {
			return operatorModelList.get(0);
		}

		return null;
	}

	@Transactional
	public boolean delete(String id) {
		OperatorModel operatorModel = new OperatorModel();
		operatorModel.setOperatorId(Integer.valueOf(id));
		try {
			sessionFactory.getCurrentSession().delete(operatorModel);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Transactional
	public List<OperatorModel> findPaginated(int pageNo, int pageSize, SearchOperatorModel searchOperatorModel) {
		List<OperatorModel> operatorModels = null;
		String hqlQuery = "from OperatorModel";
		if(searchOperatorModel!=null) {
			boolean whereAdded = false;
			if(searchOperatorModel.getOperatorId()!=null) {
				hqlQuery = hqlQuery +" where operatorId like '"+searchOperatorModel.getOperatorId()+"'";
				whereAdded = true;
			}
			if(searchOperatorModel.getOperatorName()!=null && !"".equals(searchOperatorModel.getOperatorName())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where operatorName like '"+searchOperatorModel.getOperatorName()+"'";
				}else {
					hqlQuery = hqlQuery +" and operatorName like '"+searchOperatorModel.getOperatorName()+"'";
				}
				whereAdded = true;
			}
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hqlQuery);

		if(pageNo==1) {
			query.setFirstResult(pageNo-1);
		}else {
			query.setFirstResult((pageNo-1)*pageSize);
		}
		query.setMaxResults(pageSize);
		operatorModels =  query.list();
		return operatorModels;
	}

	@Transactional
	public Integer operatorTotalRecordCount() {   
		String hqlString = "select count(*) from OperatorModel";   
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);   
		Integer count = ((Number)query.uniqueResult()).intValue();    

		return count;   
	}  



}
