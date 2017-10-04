package com.votingsytem.pojo;

public class Vote_POJO {

	String party_name, holder_name, profile_pic, party_logo;
	int candidate_id;

	public String getParty_name() {
		return party_name;
	}

	public void setParty_name(String party_name) {
		this.party_name = party_name;
	}

	public String getHolder_name() {
		return holder_name;
	}

	public void setHolder_name(String holder_name) {
		this.holder_name = holder_name;
	}

	public int getCandidate_id() {
		return candidate_id;
	}

	public void setCandidate_id(int candidate_id) {
		this.candidate_id = candidate_id;
	}

	public String getProfile_pic() {
		return profile_pic;
	}

	public void setProfile_pic(String profile_pic) {
		this.profile_pic = profile_pic;
	}

	public String getParty_logo() {
		return party_logo;
	}

	public void setParty_logo(String party_logo) {
		this.party_logo = party_logo;
	}

}
