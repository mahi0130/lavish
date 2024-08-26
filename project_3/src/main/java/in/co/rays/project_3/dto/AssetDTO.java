package in.co.rays.project_3.dto;

import java.util.Date;

public class AssetDTO extends BaseDTO{
	private Long assetId;
	private String registrationNumber;
	private Date acquisitionDate;
	private Long coverageAmount;
	private String PaintColor;
	
	
	public Long getAssetId() {
		return assetId;
	}
	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public Date getAcquisitionDate() {
		return acquisitionDate;
	}
	public void setAcquisitionDate(Date acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
	}
	
	
	public Long getCoverageAmount() {
		return coverageAmount;
	}
	public void setCoverageAmount(Long coverageAmount) {
		this.coverageAmount = coverageAmount;
	}
	public String getPaintColor() {
		return PaintColor;
	}
	public void setPaintColor(String paintColor) {
		PaintColor = paintColor;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	


}
