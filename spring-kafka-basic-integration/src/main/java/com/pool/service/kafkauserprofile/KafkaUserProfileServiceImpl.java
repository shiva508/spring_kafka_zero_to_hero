package com.pool.service.kafkauserprofile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pool.constants.UserProfileConstants;
import com.pool.form.exception.NoRecardsFoundException;
import com.pool.form.response.CommonResponse;
import com.pool.modal.KafkaUserprofile;
import com.pool.modal.Userprofile;
import com.pool.repository.kafkauserprofile.KafkaUserprofileRepository;
import com.pool.repository.userprofile.UserprofileRepository;
import com.pool.util.UserProfileUtil;

@Service
public class KafkaUserProfileServiceImpl implements KafkaUserProfileService {
	
	@Autowired
	private KafkaUserprofileRepository kafkaUserprofileRepository;
	
	@Autowired
	private UserProfileUtil userProfileUtil;

	@Override
	public KafkaUserprofile saveUserProfile(KafkaUserprofile userprofile) {
		
		return kafkaUserprofileRepository.save(userprofile);
	}

	@Override
	public List<KafkaUserprofile> getUserProfiles() {
		
		return kafkaUserprofileRepository.findAll();
	}

	@Override
	public KafkaUserprofile getUserProfileByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommonResponse deleteUserProfileByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KafkaUserprofile updateUserprofile(KafkaUserprofile userprofile) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
