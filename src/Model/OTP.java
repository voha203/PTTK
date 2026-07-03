package Model;

import java.time.LocalDateTime;

public class OTP {
	private int otpId;
	private int uid;
	private String otp;
	private LocalDateTime expiredTime;
	public OTP() {
		super();
	}
	public OTP(int otpId, int uid, String otp, LocalDateTime expiredTime) {
		super();
		this.otpId = otpId;
		this.uid = uid;
		this.otp = otp;
		this.expiredTime = expiredTime;
	}
	public int getOtpId() {
		return otpId;
	}
	public void setOtpId(int otpId) {
		this.otpId = otpId;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public LocalDateTime getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(LocalDateTime expiredTime) {
		this.expiredTime = expiredTime;
	}
	@Override
	public String toString() {
		return "OTP [otpId=" + otpId + ", uid=" + uid + ", otp=" + otp + ", expiredTime=" + expiredTime + "]";
	}
	
	
}
