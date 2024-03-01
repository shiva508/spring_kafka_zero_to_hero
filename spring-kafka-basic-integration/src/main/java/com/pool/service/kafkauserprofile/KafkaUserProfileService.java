package com.pool.service.kafkauserprofile;

import com.pool.modal.KafkaUserprofile;
import java.util.List;

import com.pool.form.response.CommonResponse;
import com.pool.modal.Userprofile;

public interface KafkaUserProfileService {
	public KafkaUserprofile saveUserProfile(KafkaUserprofile userprofile);

	public List<KafkaUserprofile> getUserProfiles();

	public KafkaUserprofile getUserProfileByUserId(Integer userId);

	public CommonResponse deleteUserProfileByUserId(Integer userId);

	public KafkaUserprofile updateUserprofile(KafkaUserprofile userprofile);
}
