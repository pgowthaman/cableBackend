package com.cable.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cable.model.SetupboxModel;
import com.cable.to.SetupboxTO;
import com.cable.utils.StringUtils;

@Repository
public class SetupboxDao {

	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void save(SetupboxTO setupboxTO){
		SetupboxModel setupboxModel = new SetupboxModel();
		setupboxTO.convertTOToModel(setupboxModel);
		sessionFactory.getCurrentSession().saveOrUpdate(setupboxModel);
	}

	@Transactional
	public void saveAll(List<SetupboxTO> setupboxTOs){
		for (SetupboxTO setupboxTO : setupboxTOs) {
			SetupboxModel setupboxModel = new SetupboxModel();
			setupboxTO.convertTOToModel(setupboxModel);
			sessionFactory.getCurrentSession().save(setupboxModel);
		}
	}

	@Transactional
	public void deleteAll(){
		sessionFactory.getCurrentSession().createQuery("delete from SetupboxModel").executeUpdate();
	}

	@Transactional
	public List<SetupboxTO> getAll(){
		
		List<SetupboxModel> setupboxModels = sessionFactory.getCurrentSession().createQuery("from SetupboxModel").list();
		List<SetupboxTO> setupboxTOs = new ArrayList<SetupboxTO>();
		for (SetupboxModel setupboxModel : setupboxModels) {
			SetupboxTO setupboxTO =  new SetupboxTO();
			setupboxTO.convertModelToTO(setupboxModel);
			setupboxTOs.add(setupboxTO);
		}
		
		return setupboxTOs;
	}

	@Transactional
	public SetupboxTO getBySetupboxId(String id) {
		String hql = "from SetupboxModel where setupboxId='" + id+"'";
		List<SetupboxModel> setupboxModelList = sessionFactory.getCurrentSession().createQuery(hql).list();

		if (setupboxModelList != null && !setupboxModelList.isEmpty()) {
			SetupboxTO setupboxTO =  new SetupboxTO();
			setupboxTO.convertModelToTO(setupboxModelList.get(0));
			return setupboxTO;
		}

		return null;
	}

	@Transactional
	public boolean delete(String id) {
		SetupboxModel setupboxModel = new SetupboxModel();
		setupboxModel.setSetupboxId(Integer.valueOf(id));
		try {
			sessionFactory.getCurrentSession().delete(setupboxModel);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Transactional
	public List<SetupboxTO> findPaginated(int pageNo, int pageSize, SetupboxTO searchSetupboxModel) {
		List<SetupboxModel> setupboxModels = null;
		String hqlQuery = "from SetupboxModel as setupbox";
		if(searchSetupboxModel!=null) {
			boolean whereAdded = false;
			if(StringUtils.isNotEmpty(searchSetupboxModel.getSetupboxId())) {
				hqlQuery = hqlQuery +" where setupboxId like '"+searchSetupboxModel.getSetupboxId()+"'";
				whereAdded = true;
			}
			if(searchSetupboxModel.getOperatorTO()!=null) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where setupbox.operatorModel.operatorId like '"+searchSetupboxModel.getOperatorTO().getOperatorId()+"'";
				}else {
					hqlQuery = hqlQuery +" and setupbox.operatorModel.operatorId like '"+searchSetupboxModel.getOperatorTO().getOperatorId()+"'";
				}
				whereAdded = true;
			}
			if(searchSetupboxModel.getConnectionStatus()!=null && !"".equals(searchSetupboxModel.getConnectionStatus())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where connectionStatus like '"+searchSetupboxModel.getConnectionStatus()+"'";
				}else {
					hqlQuery = hqlQuery +" and connectionStatus like '"+searchSetupboxModel.getConnectionStatus()+"'";
				}
				whereAdded = true;
			}
			if(searchSetupboxModel.getSetupboxStatus()!=null && !"".equals(searchSetupboxModel.getSetupboxStatus())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where setupboxStatus like '"+searchSetupboxModel.getSetupboxStatus()+"'";
				}else {
					hqlQuery = hqlQuery +" and setupboxStatus like '"+searchSetupboxModel.getSetupboxStatus()+"'";
				}
				whereAdded = true;
			}
			if(searchSetupboxModel.getSetupboxType()!=null && !"".equals(searchSetupboxModel.getSetupboxType())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where setupboxType like '"+searchSetupboxModel.getSetupboxType()+"'";
				}else {
					hqlQuery = hqlQuery +" and setupboxType like '"+searchSetupboxModel.getSetupboxType()+"'";
				}
				whereAdded = true;
			}
			if(searchSetupboxModel.getProviderTO()!=null) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where setupbox.providerModel.providerId like '"+searchSetupboxModel.getProviderTO().getProviderId()+"'";
				}else {
					hqlQuery = hqlQuery +" and setupbox.providerModel.providerId like '"+searchSetupboxModel.getProviderTO().getProviderId()+"'";
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
		setupboxModels =  query.list();
		List<SetupboxTO> setupboxTOs = new ArrayList<SetupboxTO>();
		for (SetupboxModel setupboxModel : setupboxModels) {
			SetupboxTO setupboxTO =  new SetupboxTO();
			setupboxTO.convertModelToTO(setupboxModel);
			setupboxTOs.add(setupboxTO);
		}
		
		return setupboxTOs;
	}

	@Transactional
	public Integer setupboxTotalRecordCount() {   
		String hqlString = "select count(*) from SetupboxModel";   
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);   
		Integer count = ((Number)query.uniqueResult()).intValue();    

		return count;   
	}  



}
