package Model;

public class Voucher {
    private int voucherId;
    private int promotionId;
    private String voucherCode;
    private Integer discountPercent;
    private Double minimumAmount;
    private Integer quantity;
    private int usedCount;
    private String expireDate;
    private String status;

    public Voucher() {}

    public Voucher(int voucherId, int promotionId, String voucherCode, Integer discountPercent, Double minimumAmount,
			Integer quantity, int usedCount, String expireDate, String status) {
		super();
		this.voucherId = voucherId;
		this.promotionId = promotionId;
		this.voucherCode = voucherCode;
		this.discountPercent = discountPercent;
		this.minimumAmount = minimumAmount;
		this.quantity = quantity;
		this.usedCount = usedCount;
		this.expireDate = expireDate;
		this.status = status;
	}

	public int getVoucherId() { return voucherId; }
    public void setVoucherId(int voucherId) { this.voucherId = voucherId; }

    public int getPromotionId() { return promotionId; }
    public void setPromotionId(int promotionId) { this.promotionId = promotionId; }

    public String getVoucherCode() { return voucherCode; }
    public void setVoucherCode(String voucherCode) { this.voucherCode = voucherCode; }

    public Integer getDiscountPercent() { return discountPercent; }
    public void setDiscountPercent(Integer discountPercent) { this.discountPercent = discountPercent; }

    public Double getMinimumAmount() { return minimumAmount; }
    public void setMinimumAmount(Double minimumAmount) { this.minimumAmount = minimumAmount; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public int getUsedCount() { return usedCount; }
    public void setUsedCount(int usedCount) { this.usedCount = usedCount; }

    public String getExpireDate() { return expireDate; }
    public void setExpireDate(String expireDate) { this.expireDate = expireDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}