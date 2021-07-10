package com.cable.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cable.model.AreaModel;
import com.cable.to.AreaTO;
import com.cable.utils.StringUtils;

@Repository
public class AreaDao {


	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void save(AreaTO areaTO){
		AreaModel areaModel = new AreaModel();
		areaTO.convertTOToModel(areaModel);
		sessionFactory.getCurrentSession().saveOrUpdate(areaModel);
	}

	@Transactional
	public void saveAll(List<AreaTO> areaTOs){
		for (AreaTO areaTO : areaTOs) {
			AreaModel areaModel = new AreaModel();
			areaTO.convertTOToModel(areaModel);
			sessionFactory.getCurrentSession().save(areaModel);
		}
	}

	@Transactional
	public void deleteAll(){
		sessionFactory.getCurrentSession().createQuery("delete from AreaModel").executeUpdate();
	}

	@Transactional
	public List<AreaTO> getAll(){
		List<AreaModel> areaModels = sessionFactory.getCurrentSession().createQuery("from AreaModel").list();
		List<AreaTO> areaTOs =  new ArrayList<AreaTO>();
		for (AreaModel areaModel : areaModels) {
			AreaTO areaTO =  new AreaTO();
			areaTO.convertModelToTO(areaModel);
			areaTOs.add(areaTO);
		}
		return areaTOs;
	}

	@Transactional
	public AreaTO getByAreaId(String id) {
		String hql = "from AreaModel where areaId='" + id+"'";
		List<AreaModel> areaModelList = sessionFactory.getCurrentSession().createQuery(hql).list();

		if (areaModelList != null && !areaModelList.isEmpty()) {
			AreaTO areaTO =  new AreaTO();
			areaTO.convertModelToTO(areaModelList.get(0));
			return areaTO;
		}

		return null;
	}

	@Transactional
	public boolean delete(String id) {
		AreaModel areaModel = new AreaModel();
		areaModel.setAreaId(Integer.valueOf(id));
		try {
			sessionFactory.getCurrentSession().delete(areaModel);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Transactional
	public List<AreaTO> findPaginated(int pageNo, int pageSize, AreaTO searchAreaTO) {
		List<AreaModel> areaModels = null;
		String hqlQuery = "from AreaModel as area";
		if(searchAreaTO!=null) {
			boolean whereAdded = false;
			if(StringUtils.isNotEmpty(searchAreaTO.getAreaId())) {
				hqlQuery = hqlQuery +" where areaId like '"+searchAreaTO.getAreaId()+"'";
				whereAdded = true;
			}
			if(searchAreaTO.getAreaName()!=null && !"".equals(searchAreaTO.getAreaName())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where areaName like '"+searchAreaTO.getAreaName()+"'";
				}else {
					hqlQuery = hqlQuery +" and areaName like '"+searchAreaTO.getAreaName()+"'";
				}
				whereAdded = true;
			}
			if(Objects.nonNull(searchAreaTO.getOperatorTO()) && StringUtils.isNotEmpty(searchAreaTO.getOperatorTO().getOperatorId())) {
				
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where area.operatorModel.operatorId like '"+searchAreaTO.getOperatorTO().getOperatorId()+"'";
				}else {
					hqlQuery = hqlQuery +" and area.operatorModel.operatorId like '"+searchAreaTO.getOperatorTO().getOperatorId()+"'";
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
		areaModels =  query.list();
		
		List<AreaTO> areaTOs =  new ArrayList<AreaTO>();
		for (AreaModel areaModel : areaModels) {
			AreaTO areaTO =  new AreaTO();
			areaTO.convertModelToTO(areaModel);
			areaTOs.add(areaTO);
		}
		return areaTOs;
	}

	@Transactional
	public Integer areaTotalRecordCount() {   
		String hqlString = "select count(*) from AreaModel";   
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);   
		Integer count = ((Number)query.uniqueResult()).intValue();    

		return count;   
	}  

}
