package Model;

public class Member extend Account {
	private int memberId;
	private int userId;
	private String phone;
	private String gender;
	private String membershipType;
	private String packageExpiry;
	private String membershipStatus;
	private String fullName;
	public Member() {

	}

	public Member(int memberId, int userId, String phone, String gender, String membershipType, String packageExpiry,
			String status) {
		super();
		this.memberId = memberId;
		this.userId = userId;
		this.phone = phone;
		this.gender = gender;
		this.membershipType = membershipType;
		this.packageExpiry = packageExpiry;
		this.membershipStatus = "INACTIVE";// Mặc định chưa kích hoạt cho đến khi thanh toán gói
		this.fullName = fullName;
    }
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMembershipType() {
		return membershipType;
	}

	public void setMembershipType(String membershipType) {
		this.membershipType = membershipType;
	}

	public String getPackageExpiry() {
		return packageExpiry;
	}

	public void setPackageExpiry(String packageExpiry) {
		this.packageExpiry = packageExpiry;
	}

	public String getStatus() {
		return status;											
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getFullName() {
	    return fullName;
	}

	public void setFullName(String fullName) {
	    this.fullName = fullName;
	}
	@Override
	public String toString() {
	    return fullName;
	}
}
