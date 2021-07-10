package com.cable.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cable.model.ProviderModel;
import com.cable.to.ProviderTO;
import com.cable.utils.StringUtils;

@Repository
public class ProviderDao {

	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void save(ProviderTO providerTO){
		ProviderModel providerModel = new ProviderModel();
		providerTO.convertTOToModel(providerModel);
		sessionFactory.getCurrentSession().saveOrUpdate(providerModel);
	}

	@Transactional
	public void saveAll(List<ProviderTO> providerTOs){
		for (ProviderTO providerTO : providerTOs) {
			ProviderModel providerModel = new ProviderModel();
			providerTO.convertTOToModel(providerModel);
			sessionFactory.getCurrentSession().save(providerModel);
		}
	}

	@Transactional
	public void deleteAll(){
		sessionFactory.getCurrentSession().createQuery("delete from ProviderModel").executeUpdate();
	}

	@Transactional
	public List<ProviderTO> getAll(){
		
		List<ProviderModel> providerModels = sessionFactory.getCurrentSession().createQuery("from ProviderModel").list();
		List<ProviderTO> providerTOs =  new ArrayList<ProviderTO>();
		for (ProviderModel providerModel : providerModels) {
			ProviderTO providerTO = new ProviderTO();
			providerTO.convertModelToTO(providerModel);
			providerTOs.add(providerTO);
		}
		
		return providerTOs;
	}

	@Transactional
	public ProviderTO getByProviderId(String id) {
		String hql = "from ProviderModel where providerId='" + id+"'";
		List<ProviderModel> providerModelList = sessionFactory.getCurrentSession().createQuery(hql).list();

		if (providerModelList != null && !providerModelList.isEmpty()) {
			ProviderTO providerTO = new ProviderTO();
			providerTO.convertModelToTO(providerModelList.get(0));
			return providerTO;
		}

		return null;
	}

	@Transactional
	public boolean delete(String id) {
		ProviderModel providerModel = new ProviderModel();
		providerModel.setProviderId(Integer.valueOf(id));
		try {
			sessionFactory.getCurrentSession().delete(providerModel);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Transactional
	public List<ProviderTO> findPaginated(int pageNo, int pageSize, ProviderTO searchProviderModel) {
		List<ProviderModel> providerModels = null;
		String hqlQuery = "from ProviderModel as payment";
		if(searchProviderModel!=null) {
			boolean whereAdded = false;
			if(StringUtils.isNotEmpty(searchProviderModel.getProviderId())) {
				hqlQuery = hqlQuery +" where providerId like '"+searchProviderModel.getProviderId()+"'";
				whereAdded = true;
			}
			if(searchProviderModel.getProviderName()!=null && !"".equals(searchProviderModel.getProviderName())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where providerName like '"+searchProviderModel.getProviderName()+"'";
				}else {
					hqlQuery = hqlQuery +" and providerName like '"+searchProviderModel.getProviderName()+"'";
				}
				whereAdded = true;
			}
			if(searchProviderModel.getProviderType()!=null && !"".equals(searchProviderModel.getProviderType())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where providerType like '"+searchProviderModel.getProviderType()+"'";
				}else {
					hqlQuery = hqlQuery +" and providerType like '"+searchProviderModel.getProviderType()+"'";
				}
				whereAdded = true;
			}
			
			if(searchProviderModel.getOperatorTO()!=null) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where payment.operatorModel.operatorId like '"+searchProviderModel.getOperatorTO().getOperatorId()+"'";
				}else {
					hqlQuery = hqlQuery +" and payment.operatorModel.operatorId like '"+searchProviderModel.getOperatorTO().getOperatorId()+"'";
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
		providerModels =  query.list();
		List<ProviderTO> providerTOs =  new ArrayList<ProviderTO>();
		for (ProviderModel providerModel : providerModels) {
			ProviderTO providerTO = new ProviderTO();
			providerTO.convertModelToTO(providerModel);
			providerTOs.add(providerTO);
		}
		
		return providerTOs;
	}

	@Transactional
	public Integer providerTotalRecordCount() {   
		String hqlString = "select count(*) from ProviderModel";   
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);   
		Integer count = ((Number)query.uniqueResult()).intValue();    

		return count;   
	}  



}
