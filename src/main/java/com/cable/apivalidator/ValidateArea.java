package com.cable.apivalidator;

import java.util.List;

import com.cable.dao.AreaDao;
import com.cable.to.AreaTO;
import com.cable.utils.StringUtils;

public class ValidateArea {
	
	private AreaDao areaDao;
	
	public ValidateArea(AreaDao areaDao){
		this.areaDao = areaDao;
	}

	private void validateAreaDetails(AreaTO areaTO) throws CableException {
		if(StringUtils.isEmpty(areaTO.getAreaName())) {
			throw new CableException("Area Name is mandatory");
		}
		if(areaTO.getOperatorTO()==null || StringUtils.isEmpty(areaTO.getOperatorTO().getOperatorId())) {
			throw new CableException("Operator is mandatory");
		}
		
	}
	
	public void validateAreaDetailsBeforeSave(AreaTO areaTO) throws CableException {
		validateAreaDetails(areaTO);
		List<AreaTO> dbAreaTOList = areaDao.getByAreaName(areaTO.getAreaName(), areaTO.getOperatorTO().getOperatorId());
		if(dbAreaTOList!= null && !dbAreaTOList.isEmpty()) {
			throw new CableException("Area Name already exists for this operator");
		}
	}
	
	public void validateAreaDetailsBeforeUpdate(AreaTO areaTO) throws CableException {
		if(StringUtils.isEmpty(areaTO.getAreaId())) {
			throw new CableException("Area Id is mandatory");
		}
		validateAreaDetails(areaTO);
		List<AreaTO> dbAreaTOList = areaDao.getByAreaName(areaTO.getAreaName(), areaTO.getOperatorTO().getOperatorId());
		if(dbAreaTOList!= null && !dbAreaTOList.isEmpty()) {
			for (AreaTO dbAreaTO : dbAreaTOList) {
				if(!dbAreaTO.getAreaId().equals(areaTO.getAreaId())) {
					throw new CableException("Area Name already exists for this operator");
				}
			}
		}
	}
}
