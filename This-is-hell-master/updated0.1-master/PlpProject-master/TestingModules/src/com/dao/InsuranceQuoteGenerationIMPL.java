package com.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.bean.CreateAccountBean;
import com.bean.AgentDetails;
import com.bean.PolicyCreationBean;
import com.bean.QuestionBean;
import com.bean.TemporaryData;
import com.bean.UserViewPolicyBean;
import com.bean.AgentViewPolicyBean;
import com.bean.Business;
import com.bean.CustomerDetails;

import com.bean.LoginBean;
import com.bean.NewPolicyBean;
import com.bean.PolicyDetails;
import com.bean.ProfileCreation;
import com.exception.InsuranceQuoteGenerationException;
import com.util.DBConnection;

public class InsuranceQuoteGenerationIMPL implements InsuranceQuoteGenerationDao {

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultset = null;
	CreateAccountBean agentBean = null;

	// Agent account creation

	@Override
	public void accountCreation(CreateAccountBean createAccountBean) throws InsuranceQuoteGenerationException, SQLException, IOException {
		connection = DBConnection.getConnection();
		try {
			preparedStatement = connection.prepareStatement(QueryMapper.INSERT_QUERY);
			
			preparedStatement.setString(1, createAccountBean.getUsername());
			preparedStatement.setString(2, createAccountBean.getInsuredName());
			preparedStatement.setString(3, createAccountBean.getInsuredStreet());
			preparedStatement.setString(4, createAccountBean.getInsuredCity());
			preparedStatement.setString(5, createAccountBean.getInsuredState());
			preparedStatement.setLong(6, createAccountBean.getInsuredZip());
			preparedStatement.setString(7, createAccountBean.getBusinessSegment());
			preparedStatement.setLong(8, createAccountBean.getAccountNumber());
			preparedStatement.setString(9, createAccountBean.getAgentName());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new InsuranceQuoteGenerationException("Tehnical problem occured. Refer log");
		}

	}
	
	public boolean CheckAccount(String username,String business) throws InsuranceQuoteGenerationException, SQLException, IOException {
		connection = DBConnection.getConnection();
		ResultSet rs=null;
		Business busines=new Business();
		boolean result=false;
		try {
			preparedStatement = connection.prepareStatement("select businessSegment from bussinessreport where username=? and businessSegment=?");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, business);
			rs=preparedStatement.executeQuery();
		result=rs.next();
			

		} catch (SQLException e) {
			//throw new InsuranceQuoteGenerationException("Tehnical problem occured. Refer log");
		}
		return result;

	}
	
	public boolean findAgentName(String agentName) throws InsuranceQuoteGenerationException, SQLException, IOException {
		connection = DBConnection.getConnection();
		boolean validation;
		try {
			preparedStatement = connection.prepareStatement(QueryMapper.FIND_AGENT_NAME);
			preparedStatement.setString(1, agentName);
			resultset = preparedStatement.executeQuery();
			
			validation = resultset.next();
			
	         
		} catch (SQLException e) {

			throw new InsuranceQuoteGenerationException("Tehnical problem occured. Refer log");
		}
		return validation;

	}
	

	@Override
	public List<AgentViewPolicyBean> getPolicyDetails(String agentName) throws InsuranceQuoteGenerationException, SQLException, IOException {

		connection = DBConnection.getConnection();
		AgentViewPolicyBean agentViewPolicyBean = null;
		List<AgentViewPolicyBean> policyList=new ArrayList<>();

						preparedStatement = connection.prepareStatement(QueryMapper.VIEW_POLICY_DETAILS_FOR_AGENT);
						//change agentname to username
			preparedStatement.setString(1, agentName);
			resultset = preparedStatement.executeQuery();

			while (resultset.next()) {
				agentViewPolicyBean = new AgentViewPolicyBean();
				
				agentViewPolicyBean.setPolicyNumber(resultset.getLong(1));
				agentViewPolicyBean.setBusiness_Segement(resultset.getString(2));
				agentViewPolicyBean.setAccountNumber(resultset.getLong(3));
				agentViewPolicyBean.setPolicyPremium(resultset.getDouble(4));
				
                     policyList.add(agentViewPolicyBean);
			}

	return policyList;

	}

	/*@Override
	public ArrayList<QuestionBean> getQuestionAnswer(QuestionBean questionBean) throws InsuranceQuoteGenerationException, IOException {

		PolicyCreationBean agentPolicyCreationBean = new PolicyCreationBean();
		ArrayList<QuestionBean> al = new ArrayList<>();
		try {
			connection = DBConnection.getConnection();
			preparedStatement = connection.prepareStatement(QueryMapper.SEARCH_POLICY);
			preparedStatement.setString(1, questionBean.getBusinessSegment());
			preparedStatement.executeQuery();

			while (resultset.next()) {

				questionBean.setQuestion(resultset.getString(1));
				questionBean.setAnswer1(resultset.getString(2));
				questionBean.setAnswer2(resultset.getString(3));
				questionBean.setAnswer3(resultset.getString(4));

			}
		} catch (SQLException e) {

			throw new InsuranceQuoteGenerationException("Error in Policy Creation");

		}

		return al;
	}
*/
	@Override
	public void policyCreation(PolicyCreationBean policyCreationBean)
			throws SQLException, IOException, InsuranceQuoteGenerationException {
//System.out.println(policyCreationBean);
		connection = DBConnection.getConnection();
		
			preparedStatement = connection.prepareStatement(QueryMapper.CREATE_POLICY);
			preparedStatement.setString(1, policyCreationBean.getQuestion());
			preparedStatement.setString(2, policyCreationBean.getAnswer());
			preparedStatement.setInt(3, policyCreationBean.getWeightage());
			preparedStatement.setLong(4, policyCreationBean.getPolicyNumber());
			preparedStatement.executeUpdate();

	}
@Override
	public String checkUser(String username) throws InsuranceQuoteGenerationException, SQLException, IOException {
	String role=null;
		connection = DBConnection.getConnection();
		try {
			preparedStatement = connection.prepareStatement(QueryMapper.FIND_ROLE);
			preparedStatement.setString(1, username);
			resultset = preparedStatement.executeQuery();
		
			while(resultset.next()) {
			 role = resultset.getString(1);
			}
		} catch (SQLException e) {

			throw new InsuranceQuoteGenerationException("Error in check user");
		}
		return role;
	}

	@Override
	public void addProfile(ProfileCreation profileCreation) throws InsuranceQuoteGenerationException, SQLException, IOException {
		
		connection = DBConnection.getConnection();	
		try
		{		
			preparedStatement=connection.prepareStatement(QueryMapper.CREATE_PROFILE);

			preparedStatement.setString(1,profileCreation.getUserName());			
			preparedStatement.setString(2,profileCreation.getPassword());
			preparedStatement.setString(3,profileCreation.getRoleCode());
			preparedStatement.executeUpdate();
		}
		catch(SQLException sqlException)
		{
			throw new InsuranceQuoteGenerationException("Error in policy creation");
				
		}
	}

	@Override
	public List<AgentDetails> viewPolicy() throws SQLException, IOException {
		Connection con=DBConnection.getConnection();
		int donorCount = 0;
		
		PreparedStatement ps=null;
		ResultSet resultset = null;
		
		List<AgentDetails> al=new ArrayList<AgentDetails>();
		try
		{
			ps=con.prepareStatement("select * from agent");
			resultset=ps.executeQuery();
			
			while(resultset.next())
			{	
				AgentDetails agent=new AgentDetails();
				//agent.setAgentId(resultset.getString(1));
				agent.setAgentName(resultset.getString(1));
				agent.setNoOfCustomers(resultset.getInt(2));
				al.add(agent);
				
				
			
			}			
			
		} catch (SQLException e) {
		e.printStackTrace();
		}
		return al;

	}

	@Override
	public List<CustomerDetails> viewCustomers(String customerName) throws SQLException, IOException {
		// TODO Auto-generated method stub
		Connection con=DBConnection.getConnection();
		PreparedStatement ps=null;
		ResultSet resultset = null;
		
		List<CustomerDetails> arrayList=new ArrayList<>();
		try
		{
			ps=con.prepareStatement("select c.insuredname,c.agentname,c.businesssegment from agent_create_account c,agent a where c.agentname=a.agentname and a.agentname=?");
		
			ps.setString(1,customerName); 
				 

			resultset=ps.executeQuery();
			
			while(resultset.next())
			{	
				CustomerDetails customer=new CustomerDetails();
				customer.setInsuredName(resultset.getString(1));
				customer.setAgentName(resultset.getString(2));
				customer.setBusinessSegment(resultset.getString(3));
				arrayList.add(customer);
			}		
			
		} catch (SQLException e) {
		e.printStackTrace();
		}
		return arrayList;
	}

	@Override
	public List<CustomerDetails> customerDetails() throws SQLException, IOException {
		// TODO Auto-generated method stub
		Connection con=DBConnection.getConnection();
		//PreparedStatement ps=null;
		ResultSet resultset = null;
		Statement st=null;
		List<CustomerDetails> a3=new ArrayList<CustomerDetails>();
		try
		{
			st=con.createStatement();
			resultset=st.executeQuery("select agentname,insuredname,accountnumber,businesssegment,policynumber,premiumamount from agent_create_account");
		
			
			
			while(resultset.next())
			{	
				CustomerDetails customerdetails=new CustomerDetails();
				
				
				
				customerdetails.setAgentName(resultset.getString(1));
				
				customerdetails.setInsuredName(resultset.getString(2));
				customerdetails.setAccountNumber(resultset.getLong(3));
				customerdetails.setBusinessSegment(resultset.getString(4));
				customerdetails.setPolicyNumber(resultset.getLong(5));
				customerdetails.setPremiumAmount(resultset.getLong(6));
				
				
				a3.add(customerdetails);
				
			}		
			
		} catch (SQLException e) {
		System.out.println("enter");
		}
		return a3;
	}
	public List<QuestionBean> createPolicy(String businessSegment) {
		ArrayList<QuestionBean> al = new ArrayList<>();
		try {
			Connection con=DBConnection.getConnection();
			PreparedStatement ps=null;
			ResultSet resultset = null;
			ps=con.prepareStatement("select * from policycreation where business_segment=?");
			ps.setString(1, businessSegment);
		    resultset=ps.executeQuery();
			
			
			while(resultset.next()) {
			QuestionBean policyBean=new QuestionBean();
				policyBean.setBusinessSegment(resultset.getString(1));
				policyBean.setQuestion(resultset.getString(2));
				policyBean.setAnswer1(resultset.getString(3));
				policyBean.setWeightage1(resultset.getInt(4));
				policyBean.setAnswer2(resultset.getString(5));
				policyBean.setWeightage2(resultset.getInt(6));
				policyBean.setAnswer3(resultset.getString(7));
				policyBean.setWeightage3(resultset.getInt(8));
				al.add(policyBean);
			}
			
		}
		catch(Exception e) {
			System.out.println("here");
		}
		
		return al;
		}

	@Override
	public void PolicyQuestion(PolicyCreationBean questionBean) throws SQLException, IOException {
	
		
			Connection con=DBConnection.getConnection();
			PreparedStatement ps=null;
			
			  ps=con.prepareStatement("insert into ReportGeneration values (?,?,?,?)");
			  
			 ps.setString(1, questionBean.getQuestion());
			 ps.setString(2, questionBean.getAnswer());
			 ps.setInt(3, questionBean.getWeightage());
		      ps.setLong(4, questionBean.getPolicyNumber());
		     	 ps.executeUpdate();
			
			  
	
		
	}

	@Override
	public void createNewScheme(NewPolicyBean newPolicySchemeBean) throws SQLException, IOException {
		Connection con=DBConnection.getConnection();
		PreparedStatement ps=null;
		ps=con.prepareStatement("insert into policycreation values(?,?,?,?,?,?,?,?)");
		ps.setString(1,newPolicySchemeBean.getBus_seg_name() );
		ps.setString(2,newPolicySchemeBean.getPol_ques_desc() );
		ps.setString(3,newPolicySchemeBean.getPol_ques_ans1() );
		ps.setInt(4,newPolicySchemeBean.getPol_ques_ans1_weightage());
		ps.setString(5,newPolicySchemeBean.getPol_ques_ans2()  );
		ps.setInt(6,newPolicySchemeBean.getPol_ques_ans2_weightage());
		ps.setString(7,newPolicySchemeBean.getPol_ques_ans3());
		ps.setInt(8,newPolicySchemeBean.getPol_ques_ans3_weightage() );
		ps.executeUpdate();
		
	}

	@Override
	public Long policy_Details(PolicyDetails policyDetails) throws SQLException, IOException {
		Connection con=DBConnection.getConnection();
		PreparedStatement ps=null;
		ResultSet resultset = null;
		Statement st=null;
		long policyNumber=0;
		ps=con.prepareStatement("insert into policyDetails values(?,?,?)");
		ps.setInt(1, policyDetails.getPremium());
		ps.setLong(2, policyDetails.getPolicyNumber());
		
		ps.setLong(3, policyDetails.getAccountNumber());
		ps.executeUpdate();
		st=con.createStatement();
		resultset=st.executeQuery("select policy_number from policyDetails where account_number=' "+policyDetails.getAccountNumber()+"'");
		if(resultset.next())
			policyNumber=resultset.getLong(1);
		return policyNumber;
	}

	@Override
	public String validateUser(LoginBean loginBean) throws SQLException, IOException, InsuranceQuoteGenerationException {
		connection = DBConnection.getConnection();
		try {
			preparedStatement = connection.prepareStatement(QueryMapper.VALIDATE_USER);
			preparedStatement.setString(1, loginBean.getUserName());
			preparedStatement.setString(2, loginBean.getPassword());
			resultset = preparedStatement.executeQuery();
			String role="";
			if(resultset.next())
			 role = resultset.getString(1);
			return role;
		} catch (SQLException e) {

			throw new InsuranceQuoteGenerationException("Error in check user");
		}

	}

	@Override
	public boolean checkSegment(String username) throws SQLException, IOException, InsuranceQuoteGenerationException {
		connection = DBConnection.getConnection();
		String validation;
		try {
			preparedStatement = connection.prepareStatement("select username from user_segments where username=?");
			preparedStatement.setString(1, username);
			resultset = preparedStatement.executeQuery();
			resultset.next();
			validation = resultset.getString(1);
			if (username.equals(validation) ) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {

			throw new InsuranceQuoteGenerationException("Tehnical problem occured. Refer log");
		}
	}

	@Override
	public boolean checkCreateAccount(String username) throws SQLException, IOException, InsuranceQuoteGenerationException {
		connection = DBConnection.getConnection();
		String validation;
		try {
			preparedStatement = connection.prepareStatement(QueryMapper.CHECK_ACCOUNT);
			preparedStatement.setString(1, username);
			resultset = preparedStatement.executeQuery();
			resultset.next();
			validation = resultset.getString(1);
			if (username.equals(validation) ) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {

			throw new InsuranceQuoteGenerationException("Tehnical problem occured. Refer log");
		}
	}

	@Override
	public void businessReport(Business businessValues) throws SQLException, IOException {
		Connection con=DBConnection.getConnection();
		PreparedStatement ps=null;
		ResultSet resultset = null;
		Statement st=null;
		long policyNumber=0;
		ps=con.prepareStatement("insert into bussinessReport values(?,?,?)");
		ps.setString(1, businessValues.getBusiness_Segment());

		ps.setString(2, businessValues.getUsername());
		ps.setLong(3, businessValues.getPolicyNumber());
		
		ps.executeUpdate();
		
	}

	@Override
	public void questionStore(TemporaryData temporaryData) throws SQLException, IOException {
		
		
		Connection con=DBConnection.getConnection();
		PreparedStatement ps=null;
		ResultSet resultset = null;
		Statement st=null;
		long policyNumber=0;
		ps=con.prepareStatement("insert into temporarydata values(?,?,?,?)");
		ps.setString(1, temporaryData.getQuestion());
		ps.setString(2,temporaryData.getAnswer() );
		ps.setInt(3,temporaryData.getWeightage() );
		ps.setString(4,temporaryData.getUserName() );
	
		ps.executeUpdate();
		
	}

	@Override
	public List<TemporaryData> getQuestionStore(String username) throws SQLException, IOException {
		Connection con=DBConnection.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<TemporaryData> forList=new ArrayList<>();
		ps=con.prepareStatement("Select question,answer,weightage from temporarydata where username=?");
		ps.setString(1, username);
	         rs=   ps.executeQuery();
	         while(rs.next()) {
	        	 TemporaryData tempdata=new TemporaryData();
	        	 tempdata.setQuestion((rs.getString(1)));
	        	 tempdata.setAnswer(rs.getString(2));
	        	 tempdata.setWeightage(rs.getInt(3));
	        	 
	        	 forList.add(tempdata);
	         }
	return forList;
	}

	@Override
	public void deleteQuestionStore() throws SQLException, IOException {
		Connection con=DBConnection.getConnection();
		PreparedStatement ps=con.prepareStatement("truncate table temporarydata");
		ps.executeUpdate();
		
		
	}

	@Override
	public boolean checkAccountNumber(Long accountNumber) throws SQLException, IOException {
		Connection con=DBConnection.getConnection();
		ResultSet resultSet=null;
		boolean result=false;
		PreparedStatement preparedStatement=con.prepareStatement("select account_number from accountdetails where account_number=?");
		preparedStatement.setLong(1, accountNumber);
		preparedStatement.executeQuery();
		resultSet=preparedStatement.executeQuery();
		result=resultSet.next();
		return result;
			
	}

	@Override
	public List<AgentViewPolicyBean> getPolicyDetails1(String userName) throws SQLException, IOException {
		connection = DBConnection.getConnection();
		AgentViewPolicyBean agentViewPolicyBean = null;
		List<AgentViewPolicyBean> policyList=new ArrayList<>();

						preparedStatement = connection.prepareStatement(QueryMapper.VIEW_POLICY_DETAILS_QUERY);
						
			preparedStatement.setString(1, userName);
			resultset = preparedStatement.executeQuery();

			while (resultset.next()) {
				agentViewPolicyBean = new AgentViewPolicyBean();
				
				agentViewPolicyBean.setPolicyNumber(resultset.getLong(1));
				agentViewPolicyBean.setBusiness_Segement(resultset.getString(2));
				agentViewPolicyBean.setAccountNumber(resultset.getLong(3));
				agentViewPolicyBean.setPolicyPremium(resultset.getDouble(4));
				
                     policyList.add(agentViewPolicyBean);
			}

	return policyList;

	}


	}

