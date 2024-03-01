package com.pool.service.userprofile;

import java.util.List;

import com.pool.form.response.CommonResponse;
import com.pool.modal.Userprofile;

public interface UserProfileService {
	public Userprofile saveUserProfile(Userprofile userprofile);

	public List<Userprofile> getUserProfiles();

	public Userprofile getUserProfileByUserId(Integer userId);

	public CommonResponse deleteUserProfileByUserId(Integer userId);

	public Userprofile updateUserprofile(Userprofile userprofile);
}
