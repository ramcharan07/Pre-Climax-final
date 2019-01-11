package com.dao;

public interface QueryMapper {
	
	public String INSERT_QUERY = "INSERT INTO accountDetails VALUES(?,?,?,?,?,?,?,?,?)";
	public String FIND_AGENT_NAME = "select username from userlogin where username=?";
	public String VIEW_POLICY_DETAILS_QUERY = " select a.policy_number,b.businesssegment,a.account_number,a.premium from policyDetails a,bussinessReport b, accountdetails c where c.username=? and c.account_number=a.account_number and  a.policy_number=b.policy_number";
	public String SEARCH_POLICY = "select question,answer1,answer2,answer3 from question_bank where business_Segment=?";
	public String CREATE_POLICY = "insert into reportgeneration values(?,?,?,?)";
	public String FIND_ROLE = "select rolecode from userlogin where username=?";
	public String CREATE_PROFILE = "Insert into userlogin values(?,?,?)";
    public String VALIDATE_USER="Select rolecode from userlogin where username=? and password=?";
    public String CHECK_ACCOUNT="select username from accounDetails where username=?";
    public String VIEW_POLICY_DETAILS_FOR_AGENT = " select a.policy_number,b.businesssegment,a.account_number,a.premium from policyDetails a,bussinessReport b, accountdetails c where c.agent_Name=? and c.account_number=a.account_number and  a.policy_number=b.policynumber";
}
