package com.cable.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cable.model.OperatorModel;
import com.cable.to.OperatorTO;

@Repository
public class OperatorDao {

	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void save(OperatorTO operatorTO){
		OperatorModel operatorModel =  new OperatorModel();
		operatorTO.convertTOToModel(operatorModel);
		sessionFactory.getCurrentSession().saveOrUpdate(operatorModel);
	}

	@Transactional
	public void saveAll(List<OperatorTO> operatorTOs){
		for (OperatorTO operatorTO : operatorTOs) {
			OperatorModel operatorModel =  new OperatorModel();
			operatorTO.convertTOToModel(operatorModel);
			sessionFactory.getCurrentSession().save(operatorModel);
		}
	}

	@Transactional
	public void deleteAll(){
		sessionFactory.getCurrentSession().createQuery("delete from OperatorModel").executeUpdate();
	}

	@Transactional
	public List<OperatorTO> getAll(){
		List<OperatorModel> operatorModels =  sessionFactory.getCurrentSession().createQuery("from OperatorModel").list();
		List<OperatorTO> operatorTOs =  new ArrayList<OperatorTO>();
		for (OperatorModel operatorModel : operatorModels) {
			OperatorTO operatorTO =new OperatorTO();
			operatorTO.convertModelToTO(operatorModel);
			operatorTOs.add(operatorTO);
		}
		return operatorTOs;
	}

	@Transactional
	public OperatorTO getByOperatorId(String id) {
		String hql = "from OperatorModel where operatorId='" + id+"'";
		List<OperatorModel> operatorModelList = sessionFactory.getCurrentSession().createQuery(hql).list();

		if (operatorModelList != null && !operatorModelList.isEmpty()) {
			OperatorTO operatorTO =new OperatorTO();
			operatorTO.convertModelToTO(operatorModelList.get(0));
			return operatorTO;
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
	public List<OperatorTO> findPaginated(int pageNo, int pageSize, OperatorTO searchOperatorTO) {
		
		List<OperatorModel> operatorModels = null;
		String hqlQuery = "from OperatorModel";
		if(searchOperatorTO!=null) {
			boolean whereAdded = false;
			if(searchOperatorTO.getOperatorId()!=null) {
				hqlQuery = hqlQuery +" where operatorId like '"+searchOperatorTO.getOperatorId()+"'";
				whereAdded = true;
			}
			if(searchOperatorTO.getOperatorName()!=null && !"".equals(searchOperatorTO.getOperatorName())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where operatorName like '"+searchOperatorTO.getOperatorName()+"'";
				}else {
					hqlQuery = hqlQuery +" and operatorName like '"+searchOperatorTO.getOperatorName()+"'";
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
		
		List<OperatorTO> operatorTOs =  new ArrayList<OperatorTO>();
		for (OperatorModel operatorModel : operatorModels) {
			OperatorTO operatorTO =new OperatorTO();
			operatorTO.convertModelToTO(operatorModel);
			operatorTOs.add(operatorTO);
		}
		return operatorTOs;
	}

	@Transactional
	public Integer operatorTotalRecordCount() {   
		String hqlString = "select count(*) from OperatorModel";   
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);   
		Integer count = ((Number)query.uniqueResult()).intValue();    

		return count;   
	}  



}
