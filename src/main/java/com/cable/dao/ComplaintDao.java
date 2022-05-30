package com.cable.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cable.model.ComplaintModel;
import com.cable.to.ComplaintTO;
import com.cable.utils.StringUtils;

@Repository
public class ComplaintDao {


	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void save(ComplaintTO complaintTO){
		ComplaintModel complaintModel =  new ComplaintModel();
		complaintTO.convertTOToModel(complaintModel);
		sessionFactory.getCurrentSession().saveOrUpdate(complaintModel);
	}

	@Transactional
	public void saveAll(List<ComplaintTO> complaintTOs){
		for (ComplaintTO complaintTO : complaintTOs) {
			ComplaintModel complaintModel =  new ComplaintModel();
			complaintTO.convertTOToModel(complaintModel);
			sessionFactory.getCurrentSession().save(complaintModel);
		}
	}

	@Transactional
	public void deleteAll(){
		sessionFactory.getCurrentSession().createQuery("delete from ComplaintModel").executeUpdate();
	}

	@Transactional
	public List<ComplaintTO> getAll(){
		
		List<ComplaintModel> complaintModels = sessionFactory.getCurrentSession().createQuery("from ComplaintModel").list();
		List<ComplaintTO> complaintTOs =  new ArrayList<ComplaintTO>();
		for (ComplaintModel complaintModel : complaintModels) {
			ComplaintTO complaintTO = new ComplaintTO();
			complaintTO.convertModelToTO(complaintModel);
			complaintTOs.add(complaintTO);
		}
		
		return complaintTOs;
	}

	@Transactional
	public ComplaintTO getByComplaintId(String id) {
		String hql = "from ComplaintModel where complaintId='" + id+"'";
		List<ComplaintModel> complaintModelList = sessionFactory.getCurrentSession().createQuery(hql).list();

		if (complaintModelList != null && !complaintModelList.isEmpty()) {
			ComplaintTO complaintTO = new ComplaintTO();
			complaintTO.convertModelToTO(complaintModelList.get(0));
			return complaintTO;
		}

		return null;
	}

	@Transactional
	public boolean delete(String id) {
		ComplaintModel complaintModel = new ComplaintModel();
		complaintModel.setComplaintId(Integer.valueOf(id));
		try {
			sessionFactory.getCurrentSession().delete(complaintModel);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Transactional
	public List<ComplaintTO> findPaginated(int pageNo, int pageSize, ComplaintTO searchComplaintModel) {
		List<ComplaintModel> complaintModels = null;
		String hqlQuery = "from ComplaintModel as complaint";
		if(searchComplaintModel!=null) {
			boolean whereAdded = false;
			if(StringUtils.isNotEmpty(searchComplaintModel.getComplaintId())) {
				hqlQuery = hqlQuery +" where complaintId like '"+searchComplaintModel.getComplaintId()+"'";
				whereAdded = true;
			}
			if(searchComplaintModel.getComplaintTypeTO()!=null) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where complaint.complaintTypeModel.complaintTypeId like '"+searchComplaintModel.getComplaintTypeTO().getComplaintTypeId()+"'";
				}else {
					hqlQuery = hqlQuery +" and complaint.complaintTypeModel.complaintTypeId like '"+searchComplaintModel.getComplaintTypeTO().getComplaintTypeId()+"'";
				}
				whereAdded = true;
			}
			if(searchComplaintModel.getComplaintStatus()!=null && !"".equals(searchComplaintModel.getComplaintStatus())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where complaintStatus like '"+searchComplaintModel.getComplaintStatus()+"'";
				}else {
					hqlQuery = hqlQuery +" and complaintStatus like '"+searchComplaintModel.getComplaintStatus()+"'";
				}
				whereAdded = true;
			}
			if(searchComplaintModel.getUserTO()!=null) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where complaint.userModel.userId like '"+searchComplaintModel.getUserTO().getUserId()+"'";
				}else {
					hqlQuery = hqlQuery +" and complaint.userModel.userId like '"+searchComplaintModel.getUserTO().getUserId()+"'";
				}
				whereAdded = true;
			}
			if(searchComplaintModel.getCollectorComments()!=null  && !"".equals(searchComplaintModel.getCollectorComments())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where collectorComments like '"+searchComplaintModel.getCollectorComments()+"'";
				}else {
					hqlQuery = hqlQuery +" and collectorComments like '"+searchComplaintModel.getCollectorComments()+"'";
				}
				whereAdded = true;
			}
			if(searchComplaintModel.getCustomerComments()!=null  && !"".equals(searchComplaintModel.getCustomerComments())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where customerComments like '"+searchComplaintModel.getCustomerComments()+"'";
				}else {
					hqlQuery = hqlQuery +" and customerComments like '"+searchComplaintModel.getCustomerComments()+"'";
				}
				whereAdded = true;
			}
			if(searchComplaintModel.getCollectorTO()!=null) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where complaint.collectorModel.userId like '"+searchComplaintModel.getCollectorTO().getUserId()+"'";
				}else {
					hqlQuery = hqlQuery +" and complaint.collectorModel.userId like '"+searchComplaintModel.getCollectorTO().getUserId()+"'";
				}
				whereAdded = true;
			}
			if(searchComplaintModel.getOperatorTO()!=null) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where complaint.operatorModel.operatorId like '"+searchComplaintModel.getOperatorTO().getOperatorId()+"'";
				}else {
					hqlQuery = hqlQuery +" and complaint.collectorModel.operatorId like '"+searchComplaintModel.getOperatorTO().getOperatorId()+"'";
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
		complaintModels =  query.list();
		List<ComplaintTO> complaintTOs =  new ArrayList<ComplaintTO>();
		for (ComplaintModel complaintModel : complaintModels) {
			ComplaintTO complaintTO = new ComplaintTO();
			complaintTO.convertModelToTO(complaintModel);
			complaintTOs.add(complaintTO);
		}
		
		return complaintTOs;
	}

	@Transactional
	public Integer complaintTotalRecordCount() {   
		String hqlString = "select count(*) from ComplaintModel";   
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);   
		Integer count = ((Number)query.uniqueResult()).intValue();    

		return count;   
	}  

}
