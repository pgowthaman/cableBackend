package com.cable.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cable.model.PaymentModel;
import com.cable.to.PaymentTO;
import com.cable.utils.StringUtils;

@Repository
public class PaymentDao {

	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void save(PaymentTO paymentTO){
		PaymentModel paymentModel = new PaymentModel();
		paymentTO.convertTOToModel(paymentModel);
		sessionFactory.getCurrentSession().saveOrUpdate(paymentModel);
	}

	@Transactional
	public void saveAll(List<PaymentTO> paymentTOs){
		for (PaymentTO paymentTO : paymentTOs) {
			PaymentModel paymentModel = new PaymentModel();
			paymentTO.convertTOToModel(paymentModel);
			sessionFactory.getCurrentSession().save(paymentModel);
		}
	}

	@Transactional
	public void deleteAll(){
		sessionFactory.getCurrentSession().createQuery("delete from PaymentModel").executeUpdate();
	}

	@Transactional
	public List<PaymentTO> getAll(){
		List<PaymentModel> paymentModels = sessionFactory.getCurrentSession().createQuery("from PaymentModel").list();
		List<PaymentTO> paymentTOs =  new ArrayList<PaymentTO>();
		for (PaymentModel paymentModel : paymentModels) {
			PaymentTO paymentTO = new PaymentTO();
			paymentTO.convertModelToTO(paymentModel);
			paymentTOs.add(paymentTO);
		}
		return paymentTOs;
	}

	@Transactional
	public PaymentTO getByPaymentId(String id) {
		String hql = "from PaymentModel where paymentId='" + id+"'";
		List<PaymentModel> paymentModelList = sessionFactory.getCurrentSession().createQuery(hql).list();

		if (paymentModelList != null && !paymentModelList.isEmpty()) {
			PaymentTO paymentTO = new PaymentTO();
			paymentTO.convertModelToTO(paymentModelList.get(0));
			return paymentTO;
		}

		return null;
	}

	@Transactional
	public boolean delete(String id) {
		PaymentModel paymentModel = new PaymentModel();
		paymentModel.setPaymentId(Integer.valueOf(id));
		try {
			sessionFactory.getCurrentSession().delete(paymentModel);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Transactional
	public List<PaymentTO> findPaginated(int pageNo, int pageSize, PaymentTO searchPaymentModel) {
		List<PaymentModel> paymentModels = null;
		String hqlQuery = "from PaymentModel as payment";
		if(searchPaymentModel!=null) {
			boolean whereAdded = false;
			if(StringUtils.isNotEmpty(searchPaymentModel.getPaymentId())) {
				hqlQuery = hqlQuery +" where paymentId like '"+searchPaymentModel.getPaymentId()+"'";
				whereAdded = true;
			}
			if(searchPaymentModel.getUserTO()!=null) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where payment.userModel.userId like '"+searchPaymentModel.getUserTO().getUserId()+"'";
				}else {
					hqlQuery = hqlQuery +" and payment.userModel.userId like '"+searchPaymentModel.getUserTO().getUserId()+"'";
				}
				whereAdded = true;
			}
			if(StringUtils.isNotEmpty(searchPaymentModel.getAmount())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where amount like '"+searchPaymentModel.getAmount()+"'";
				}else {
					hqlQuery = hqlQuery +" and amount like '"+searchPaymentModel.getAmount()+"'";
				}
				whereAdded = true;
			}
			if(searchPaymentModel.getPaidDate()!=null && !"".equals(searchPaymentModel.getPaidDate())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where paidDate = '"+searchPaymentModel.getPaidDate()+"'";
				}else {
					hqlQuery = hqlQuery +" and paidDate = '"+searchPaymentModel.getPaidDate()+"'";
				}
				whereAdded = true;
			}
			if(searchPaymentModel.getCollectorTO()!=null) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where payment.collectorModel.userId like '"+searchPaymentModel.getCollectorTO().getUserId()+"'";
				}else {
					hqlQuery = hqlQuery +" and payment.collectorModel.userId like '"+searchPaymentModel.getCollectorTO().getUserId()+"'";
				}
				whereAdded = true;
			}
			if(searchPaymentModel.getPaymentMode()!=null && !"".equals(searchPaymentModel.getPaymentMode())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where paymentMode like '"+searchPaymentModel.getPaymentMode()+"'";
				}else {
					hqlQuery = hqlQuery +" and paymentMode like '"+searchPaymentModel.getPaymentMode()+"'";
				}
				whereAdded = true;
			}
			if(searchPaymentModel.getOperatorTO()!=null) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where payment.operatorModel.operatorId like '"+searchPaymentModel.getOperatorTO().getOperatorId()+"'";
				}else {
					hqlQuery = hqlQuery +" and payment.operatorModel.operatorId like '"+searchPaymentModel.getOperatorTO().getOperatorId()+"'";
				}
				whereAdded = true;
			}
			if(searchPaymentModel.getAreaTO()!=null) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where payment.areaModel.areaId like '"+searchPaymentModel.getAreaTO().getAreaId()+"'";
				}else {
					hqlQuery = hqlQuery +" and payment.areaModel.areaId like '"+searchPaymentModel.getAreaTO().getAreaId()+"'";
				}
				whereAdded = true;
			}
			if(searchPaymentModel.getComment()!=null && !"".equals(searchPaymentModel.getComment())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where comment like '"+searchPaymentModel.getComment()+"'";
				}else {
					hqlQuery = hqlQuery +" and comment like '"+searchPaymentModel.getComment()+"'";
				}
				whereAdded = true;
			}
			if(searchPaymentModel.getConnectionTO()!=null) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where payment.connectionModel.connectionId like '"+searchPaymentModel.getConnectionTO().getConnectionId()+"'";
				}else {
					hqlQuery = hqlQuery +" and payment.connectionModel.connectionId like '"+searchPaymentModel.getConnectionTO().getConnectionId()+"'";
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
		paymentModels =  query.list();
		List<PaymentTO> paymentTOs =  new ArrayList<PaymentTO>();
		for (PaymentModel paymentModel : paymentModels) {
			PaymentTO paymentTO = new PaymentTO();
			paymentTO.convertModelToTO(paymentModel);
			paymentTOs.add(paymentTO);
		}
		return paymentTOs;
	}

	@Transactional
	public Integer paymentTotalRecordCount() {   
		String hqlString = "select count(*) from PaymentModel";   
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);   
		Integer count = ((Number)query.uniqueResult()).intValue();    

		return count;   
	}  


}
