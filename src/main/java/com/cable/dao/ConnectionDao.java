package com.cable.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cable.model.ConnectionModel;
import com.cable.to.ConnectionTO;
import com.cable.utils.StringUtils;

@Repository
public class ConnectionDao {
	
	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void save(ConnectionTO connectionTO){
		ConnectionModel connectionModel =  new ConnectionModel();
		connectionTO.convertTOToModel(connectionModel);
		sessionFactory.getCurrentSession().saveOrUpdate(connectionModel);
	}

	@Transactional
	public void saveAll(List<ConnectionTO> connectionTOs){
		for (ConnectionTO connectionTO : connectionTOs) {
			ConnectionModel connectionModel =  new ConnectionModel();
			connectionTO.convertTOToModel(connectionModel);
			sessionFactory.getCurrentSession().save(connectionModel);
		}
	}

	@Transactional
	public void deleteAll(){
		sessionFactory.getCurrentSession().createQuery("delete from ConnectionModel").executeUpdate();
	}

	@Transactional
	public List<ConnectionTO> getAll(){
		List<ConnectionModel> connectionModels = sessionFactory.getCurrentSession().createQuery("from ConnectionModel").list();
		List<ConnectionTO> connectionTOs = new ArrayList<ConnectionTO>();
		for (ConnectionModel connectionModel : connectionModels) {
			ConnectionTO connectionTO = new ConnectionTO();
			connectionTO.convertModelToTO(connectionModel);
			connectionTOs.add(connectionTO);
		}
		return connectionTOs;
	}

	@Transactional
	public ConnectionTO getByConnectionId(String id) {
		String hql = "from ConnectionModel where connectionId='" + id+"'";
		List<ConnectionModel> connectionModelList = sessionFactory.getCurrentSession().createQuery(hql).list();
		if (connectionModelList != null && !connectionModelList.isEmpty()) {
			ConnectionTO connectionTO = new ConnectionTO();
			connectionTO.convertModelToTO(connectionModelList.get(0));
			return connectionTO;
		}

		return null;
	}

	@Transactional
	public boolean delete(String id) {
		ConnectionModel connectionModel = new ConnectionModel();
		connectionModel.setConnectionId(Integer.valueOf(id));
		try {
			sessionFactory.getCurrentSession().delete(connectionModel);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Transactional
	public List<ConnectionTO> findPaginated(int pageNo, int pageSize, ConnectionTO searchConnectionModel) {
		List<ConnectionModel> connectionModels = null;
		String hqlQuery = "from ConnectionModel as connection";
		if(searchConnectionModel!=null) {
			boolean whereAdded = false;
			if(StringUtils.isNotEmpty(searchConnectionModel.getConnectionId())) {
				hqlQuery = hqlQuery +" where connectionId like '"+searchConnectionModel.getConnectionId()+"'";
				whereAdded = true;
			}
			if(searchConnectionModel.getUserTO()!=null ) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where connection.userModel.userId like '"+searchConnectionModel.getUserTO().getUserId()+"'";
				}else {
					hqlQuery = hqlQuery +" and connection.userModel.userId like '"+searchConnectionModel.getUserTO().getUserId()+"'";
				}
				whereAdded = true;
			}
			if(searchConnectionModel.getOperatorTO()!=null) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where connection.operatorModel.operatorId like '"+searchConnectionModel.getOperatorTO().getOperatorId()+"'";
				}else {
					hqlQuery = hqlQuery +" and connection.operatorModel.operatorId like '"+searchConnectionModel.getOperatorTO().getOperatorId()+"'";
				}
				whereAdded = true;
			}
			if(searchConnectionModel.getAreaTO()!=null) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where connection.areaModel.areaId like '"+searchConnectionModel.getAreaTO().getAreaId()+"'";
				}else {
					hqlQuery = hqlQuery +" and connection.areaModel.areaId like '"+searchConnectionModel.getAreaTO().getAreaId()+"'";
				}
				whereAdded = true;
			}
			if(searchConnectionModel.getSetupboxTO()!=null) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where connection.setupboxModel.setupboxId like '"+searchConnectionModel.getSetupboxTO().getSetupboxId()+"'";
				}else {
					hqlQuery = hqlQuery +" and connection.setupboxModel.setupboxId like '"+searchConnectionModel.getSetupboxTO().getSetupboxId()+"'";
				}
				whereAdded = true;
			}
			if(searchConnectionModel.getConnectionStatus()!=null && !"".equals(searchConnectionModel.getConnectionStatus())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where connectionStatus like '"+searchConnectionModel.getConnectionStatus()+"'";
				}else {
					hqlQuery = hqlQuery +" and connectionStatus like '"+searchConnectionModel.getConnectionStatus()+"'";
				}
				whereAdded = true;
			}
			if(searchConnectionModel.getConnectionNumber()!=null) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where connectionNumber like '"+searchConnectionModel.getConnectionNumber()+"'";
				}else {
					hqlQuery = hqlQuery +" and connectionNumber like '"+searchConnectionModel.getConnectionNumber()+"'";
				}
				whereAdded = true;
			}
			if(searchConnectionModel.getConnectionDate()!=null && !"".equals(searchConnectionModel.getConnectionDate())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where connectionDate = '"+searchConnectionModel.getConnectionDate()+"'";
				}else {
					hqlQuery = hqlQuery +" and connectionDate = '"+searchConnectionModel.getConnectionDate()+"'";
				}
				whereAdded = true;
			}
			if(searchConnectionModel.getDueAmount()!=null && !"".equals(searchConnectionModel.getDueAmount())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where dueAmount like '"+searchConnectionModel.getDueAmount()+"'";
				}else {
					hqlQuery = hqlQuery +" and dueAmount like '"+searchConnectionModel.getDueAmount()+"'";
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
		connectionModels =  query.list();
		
		List<ConnectionTO> connectionTOs = new ArrayList<ConnectionTO>();
		for (ConnectionModel connectionModel : connectionModels) {
			ConnectionTO connectionTO = new ConnectionTO();
			connectionTO.convertModelToTO(connectionModel);
			connectionTOs.add(connectionTO);
		}
		return connectionTOs;
	}

	@Transactional
	public Integer connectionTotalRecordCount() {   
		String hqlString = "select count(*) from ConnectionModel";   
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);   
		Integer count = ((Number)query.uniqueResult()).intValue();    

		return count;   
	}  



}
