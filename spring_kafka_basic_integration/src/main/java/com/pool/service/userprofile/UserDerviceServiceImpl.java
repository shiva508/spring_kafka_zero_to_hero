package com.pool.service.userprofile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pool.constants.UserProfileConstants;
import com.pool.form.exception.NoRecardsFoundException;
import com.pool.form.response.CommonResponse;
import com.pool.modal.Userprofile;
import com.pool.repository.userprofile.UserprofileRepository;
import com.pool.util.UserProfileUtil;

@Service
public class UserDerviceServiceImpl implements UserProfileService {
	
	@Autowired
	private UserprofileRepository userprofileRepository;
	
	@Autowired
	private UserProfileUtil userProfileUtil;

	@Override
	@Transactional
	public Userprofile saveUserProfile(Userprofile userprofile) {
		return userprofileRepository.save(userprofile);
	}

	@Override
	@Transactional
	public List<Userprofile> getUserProfiles() {
		List<Userprofile> userprofiles=userprofileRepository.findAll();
		if(null ==userprofiles || userprofiles.size()==0) {
			throw new NoRecardsFoundException(UserProfileConstants.NO_RECORDS_FOUND_MESSAGE);
		}
		return userprofiles;
	}

	@Override
	@Transactional
	public Userprofile getUserProfileByUserId(Integer userId) {
		return userprofileRepository.getById(userId);
	}

	@Override
	@Transactional
	public CommonResponse deleteUserProfileByUserId(Integer userId) {
		userprofileRepository.deleteById(userId);
		return userProfileUtil.generateCustomResponse(UserProfileConstants.DELETE_MESSAGE+userId);
	}

	@Override
	@Transactional
	public Userprofile updateUserprofile(Userprofile userprofile) {
		return userprofileRepository.saveAndFlush(userprofile);
	}

}
