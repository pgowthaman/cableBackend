package com.cable.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cable.daointerface.UserInterfaceDao;
import com.cable.model.UserModel;
import com.cable.to.UserTO;
import com.cable.utils.StringUtils;

@Repository
public class UserDao implements UserInterfaceDao{

	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void save(UserTO userTO){
		UserModel userModel = new UserModel();
		userTO.convertTOToModel(userModel);
		sessionFactory.getCurrentSession().saveOrUpdate(userModel);
	}
	
	@Transactional
	public List<UserTO> getAll(){
		 List<UserModel> userModels = sessionFactory.getCurrentSession().createQuery("from UserModel").list();
		 List<UserTO> userTOs = new ArrayList<UserTO>();
			for (UserModel userModel : userModels) {
				UserTO userTO =  new UserTO();
				userTO.convertModelToTO(userModel);
				userTOs.add(userTO);
			}
		return userTOs;
	}

	@Transactional
	public UserTO getByUserId(String id) {
		String hql = "from UserModel where userId='" + id+"'";
		List<UserModel> userModelList = sessionFactory.getCurrentSession().createQuery(hql).list();

		if (userModelList != null && !userModelList.isEmpty()) {
			UserTO userTO =  new UserTO();
			userTO.convertModelToTO(userModelList.get(0));
			return userTO;
		}

		return null;
	}

	@Transactional
	public Integer userTotalRecordCount(UserTO searchUserTo) {   
		String hqlQuery = "select count(*) from UserModel as user"; 
		if(searchUserTo!=null) {
			boolean whereAdded = false;
			if(searchUserTo.getUserRoleTO()!=null) {
				if(searchUserTo.getUserRoleTO().getUserRoleId()!=null) {
					hqlQuery = hqlQuery +" where user.userRoleModel.userRoleId = '"+searchUserTo.getUserRoleTO().getUserRoleId()+"'";
					whereAdded = true;
				}
			}
			if(searchUserTo.getOperatorTO()!=null) {
				if(searchUserTo.getOperatorTO().getOperatorId()!=null) {
					if(!whereAdded) {
						hqlQuery = hqlQuery +" where user.operatorModel.operatorId = '"+searchUserTo.getOperatorTO().getOperatorId()+"'";
					}else {
						hqlQuery = hqlQuery +" and user.operatorModel.operatorId = '"+searchUserTo.getOperatorTO().getOperatorId()+"'";
					}
					whereAdded = true;
				}
			}
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hqlQuery);   
		Integer count = ((Number)query.uniqueResult()).intValue();    

		return count;   
	}

	@Transactional
	public List<UserTO> findPaginated(Integer pageNo, Integer pageSize, UserTO searchUserTo) {
		List<UserModel> userModels = null;
		String hqlQuery = "from UserModel as user";
		if(searchUserTo!=null) {
			boolean whereAdded = false;
			if(searchUserTo.getUserId()!=null) {
				hqlQuery = hqlQuery +" where userId like '"+searchUserTo.getUserId()+"'";
				whereAdded = true;
			}
			if(searchUserTo.getUsername()!=null && !"".equals(searchUserTo.getUsername())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where username like '"+searchUserTo.getUsername()+"'";
				}else {
					hqlQuery = hqlQuery +" and username like '"+searchUserTo.getUsername()+"'";
				}
				whereAdded = true;
			}
			if(searchUserTo.getPhoneNumber()!=null) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where phoneNumber ="+searchUserTo.getPhoneNumber();
				}else {
					hqlQuery = hqlQuery +" and phoneNumber = "+searchUserTo.getPhoneNumber();
				}
				whereAdded = true;
			}
			if(searchUserTo.getFirebaseId()!=null && !"".equals(searchUserTo.getFirebaseId())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where firebaseId like '"+searchUserTo.getFirebaseId()+"'";
				}else {
					hqlQuery = hqlQuery +" and firebaseId like '"+searchUserTo.getFirebaseId()+"'";
				}
				whereAdded = true;
			}
			if(searchUserTo.getFirebaseToken()!=null && !"".equals(searchUserTo.getFirebaseToken())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where firebaseToken like '"+searchUserTo.getFirebaseToken()+"'";
				}else {
					hqlQuery = hqlQuery +" and firebaseToken like '"+searchUserTo.getFirebaseToken()+"'";
				}
				whereAdded = true;
			}
			if(searchUserTo.getUserRoleTO()!=null) {
				if(searchUserTo.getUserRoleTO().getUserRoleId()!=null) {
					if(!whereAdded) {
						hqlQuery = hqlQuery +" where user.userRoleModel.userRoleId = '"+searchUserTo.getUserRoleTO().getUserRoleId()+"'";
					}else {
						hqlQuery = hqlQuery +" and user.userRoleModel.userRoleId = '"+searchUserTo.getUserRoleTO().getUserRoleId()+"'";
					}
					whereAdded = true;
				}
			}
			if(searchUserTo.getOperatorTO()!=null) {
				if(searchUserTo.getOperatorTO().getOperatorId()!=null) {
					if(!whereAdded) {
						hqlQuery = hqlQuery +" where user.operatorModel.operatorId = '"+searchUserTo.getOperatorTO().getOperatorId()+"'";
					}else {
						hqlQuery = hqlQuery +" and user.operatorModel.operatorId = '"+searchUserTo.getOperatorTO().getOperatorId()+"'";
					}
					whereAdded = true;
				}
			}
		}
		System.out.println(hqlQuery);
		Query query = sessionFactory.getCurrentSession().createQuery(hqlQuery);

		if(pageNo==1) {
			query.setFirstResult(pageNo-1);
		}else {
			query.setFirstResult((pageNo-1)*pageSize);
		}
		query.setMaxResults(pageSize);
		userModels =  query.list();
		
		List<UserTO> userTOs = new ArrayList<UserTO>();
		for (UserModel userModel : userModels) {
			UserTO userTO =  new UserTO();
			userTO.convertModelToTO(userModel);
			userTOs.add(userTO);
		}
		
		return userTOs;
	}

	@Transactional
	public void deleteAll() {
		sessionFactory.getCurrentSession().createQuery("delete from UserModel").executeUpdate();
		
	}
	
	@Transactional
	public void delete(String userId) {

		UserModel userModel = new UserModel();
		userModel.setUserId(Integer.valueOf(userId));
		try {
			sessionFactory.getCurrentSession().delete(userModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Transactional
	public void saveAll(List<UserTO> userTOs){
		for (UserTO userTO : userTOs) {
			UserModel userModel =  new UserModel();
			userTO.convertTOToModel(userModel);	
			sessionFactory.getCurrentSession().save(userModel);
		}
	}
	
	@Transactional
	public Optional<UserModel> findbyPhoneNumber(String phoneNumber) throws UsernameNotFoundException{
		UserModel userModel = null;
		String hql = "from UserModel where phoneNumber='" + phoneNumber+"'";
		List<UserModel> userModelList = sessionFactory.getCurrentSession().createQuery(hql).list();

		if (userModelList != null && !userModelList.isEmpty()) {
			 userModel = userModelList.get(0);
			userModel.setPassword(userModel.getFirebaseId());
			
			return Optional.of(userModel);
		}

		return Optional.empty();
	
	}

	@Override
	public UserModel loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserModel userModel =  new UserModel();
		return userModel ;
	}

	@Override
	@Transactional
	public Optional<UserTO> getByUserIdAndPhoneNumber(String userId, String phoneNumber) {
		UserModel userModel = null;
		UserTO userTO = null;
		String hql = "from UserModel where userId ="+ userId+" and phoneNumber="+phoneNumber;
		
		List<UserModel> userModelList = sessionFactory.getCurrentSession().createQuery(hql).list();

		if (userModelList != null && !userModelList.isEmpty()) {
			userModel = userModelList.get(0);
			userModel.setPassword(userModel.getFirebaseId());
			userTO = new UserTO();
			userTO.convertModelToTO(userModel);
			return Optional.of(userTO);
		}
		return Optional.empty();
	}
	
	
	
	
}
