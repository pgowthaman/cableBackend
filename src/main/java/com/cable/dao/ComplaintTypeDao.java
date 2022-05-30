package com.cable.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cable.model.ComplaintTypeModel;
import com.cable.to.ComplaintTypeTO;
import com.cable.utils.StringUtils;

@Repository
public class ComplaintTypeDao {

	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void save(ComplaintTypeTO complaintTypeTO){
		ComplaintTypeModel complaintTypeModel = new ComplaintTypeModel();
		complaintTypeTO.convertTOToModel(complaintTypeModel);
		sessionFactory.getCurrentSession().saveOrUpdate(complaintTypeModel);
	}

	@Transactional
	public void saveAll(List<ComplaintTypeTO> complaintTypeTOs){
		for (ComplaintTypeTO complaintTypeTO : complaintTypeTOs) {
			ComplaintTypeModel complaintTypeModel = new ComplaintTypeModel();
			complaintTypeTO.convertTOToModel(complaintTypeModel);
			sessionFactory.getCurrentSession().save(complaintTypeModel);
		}
	}

	@Transactional
	public void deleteAll(){
		sessionFactory.getCurrentSession().createQuery("delete from ComplaintTypeModel").executeUpdate();
	}

	@Transactional
	public List<ComplaintTypeTO> getAll(){
		List<ComplaintTypeModel> complaintTypeModels = sessionFactory.getCurrentSession().createQuery("from ComplaintTypeModel").list();
		List<ComplaintTypeTO> complaintTypeTOs = new ArrayList<ComplaintTypeTO>();
		for (ComplaintTypeModel complaintTypeModel : complaintTypeModels) {
			ComplaintTypeTO complaintTypeTO = new ComplaintTypeTO();
			complaintTypeTO.convertModelToTO(complaintTypeModel);
			complaintTypeTOs.add(complaintTypeTO);
		}
		
		return complaintTypeTOs;
	}

	@Transactional
	public ComplaintTypeTO getByComplaintTypeId(String id) {
		String hql = "from ComplaintTypeModel where complaintTypeId='" + id+"'";
		List<ComplaintTypeModel> complaintTypeModelList = sessionFactory.getCurrentSession().createQuery(hql).list();

		if (complaintTypeModelList != null && !complaintTypeModelList.isEmpty()) {
			ComplaintTypeTO complaintTypeTO = new ComplaintTypeTO();
			complaintTypeTO.convertModelToTO(complaintTypeModelList.get(0));
			return complaintTypeTO;
		}

		return null;
	}

	@Transactional
	public boolean delete(String id) {
		ComplaintTypeModel complaintTypeModel = new ComplaintTypeModel();
		complaintTypeModel.setComplaintTypeId(Integer.valueOf(id));
		try {
			sessionFactory.getCurrentSession().delete(complaintTypeModel);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Transactional
	public List<ComplaintTypeTO> findPaginated(int pageNo, int pageSize, ComplaintTypeTO searchComplaintTypeModel) {
		List<ComplaintTypeModel> complaintTypeModels = null;
		String hqlQuery = "from ComplaintTypeModel as complaintType";
		if(searchComplaintTypeModel!=null) {
			boolean whereAdded = false;
			if(StringUtils.isNotEmpty(searchComplaintTypeModel.getComplaintTypeId())) {
				hqlQuery = hqlQuery +" where complaintTypeId like '"+searchComplaintTypeModel.getComplaintTypeId()+"'";
				whereAdded = true;
			}
			if(searchComplaintTypeModel.getComplaintTypeDesc()!=null && !"".equals(searchComplaintTypeModel.getComplaintTypeDesc())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where complaintTypeDesc like '"+searchComplaintTypeModel.getComplaintTypeDesc()+"'";
				}else {
					hqlQuery = hqlQuery +" and complaintTypeDesc like '"+searchComplaintTypeModel.getComplaintTypeDesc()+"'";
				}
				whereAdded = true;
			}
			if(Objects.nonNull(searchComplaintTypeModel.getOperatorTO()) && StringUtils.isNotEmpty(searchComplaintTypeModel.getOperatorTO().getOperatorId())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where complaintType.operatorModel.operatorId like '"+searchComplaintTypeModel.getOperatorTO().getOperatorId()+"'";
				}else {
					hqlQuery = hqlQuery +" and complaintType.operatorModel.operatorId like '"+searchComplaintTypeModel.getOperatorTO().getOperatorId()+"'";
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
		complaintTypeModels =  query.list();
		List<ComplaintTypeTO> complaintTypeTOs = new ArrayList<ComplaintTypeTO>();
		for (ComplaintTypeModel complaintTypeModel : complaintTypeModels) {
			ComplaintTypeTO complaintTypeTO = new ComplaintTypeTO();
			complaintTypeTO.convertModelToTO(complaintTypeModel);
			complaintTypeTOs.add(complaintTypeTO);
		}
		return complaintTypeTOs;
	}

	@Transactional
	public Integer complaintTypeTotalRecordCount() {   
		String hqlString = "select count(*) from ComplaintTypeModel";   
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);   
		Integer count = ((Number)query.uniqueResult()).intValue();    

		return count;   
	}  

}
