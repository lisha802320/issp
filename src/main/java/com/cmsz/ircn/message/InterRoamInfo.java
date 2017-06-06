package com.cmsz.ircn.message;

public class InterRoamInfo {
	
	private String MSISDN;// 手机号码
    private String oprTIMSI;// 操作处理时间
    
    
    /**
     * @return the mSISDN
     */
    public String getMSISDN()
    {
        return MSISDN;
    }
    
    /**
     * @param mSISDN
     *            the mSISDN to set
     */
    public void setMSISDN(String mSISDN)
    {
        MSISDN = mSISDN;
    }
    

	public String getOprTIMSI() {
		return oprTIMSI;
	}

	public void setOprTIMSI(String oprTIMSI) {
		this.oprTIMSI = oprTIMSI;
	}

	@Override
	public String toString() {
		return "InterRoamInfo [MSISDN=" + MSISDN + ", oprTIMSI=" + oprTIMSI + "]";
	}
    
    
}
