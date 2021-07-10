package com.cable.daointerface;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.cable.model.UserModel;
import com.cable.to.UserTO;

@Repository
public interface UserInterfaceDao extends UserDetailsService{

	public void save(UserTO userModel);
	
	public List<UserTO> getAll();

	public UserTO getByUserId(String id);

	public Integer userTotalRecordCount();

	public List<UserTO> findPaginated(Integer pageNo, Integer pageSize, UserTO searchUserModel) ;

	public void deleteAll() ;
	
	public void delete(String userId);
	
	public void saveAll(List<UserTO> userModelList);
	
	public Optional<UserModel> findbyPhoneNumber(String phoneNumber) throws UsernameNotFoundException;

	@Override
	public UserModel loadUserByUsername(String username) throws UsernameNotFoundException ;
	
	
	
	
}
