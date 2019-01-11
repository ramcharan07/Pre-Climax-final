package com.service;

import java.io.IOException;
import java.sql.SQLException;
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
import com.bean.BussinessSegment;
import com.bean.CustomerDetails;
import com.bean.LoginBean;
import com.bean.NewPolicyBean;
import com.bean.PolicyDetails;
import com.bean.ProfileCreation;
import com.exception.InsuranceQuoteGenerationException;

public interface InsuranceQuoteGenerationService {

	public void accountCreation(CreateAccountBean createAccountBean) throws InsuranceQuoteGenerationException, SQLException, IOException;
	public List<AgentViewPolicyBean> getPolicyDetails(String agentName) throws InsuranceQuoteGenerationException, SQLException, IOException ;
	//public ArrayList<QuestionBean> getQuestionAnswer(QuestionBean questionBean) throws InsuranceQuoteGenerationException, IOException;
	public void policyCreation(PolicyCreationBean PolicyCreationBean) throws SQLException, IOException, InsuranceQuoteGenerationException;
	public void  addProfile(ProfileCreation profileCreation) throws InsuranceQuoteGenerationException, SQLException, IOException;
	public List<AgentDetails> viewPolicy() throws SQLException, IOException;
	public List<CustomerDetails> viewCustomers(String string) throws SQLException, IOException;
	public List<CustomerDetails> customerDetails() throws SQLException, IOException;
	public List<QuestionBean> createPolicy(String segment);
	public void PolicyQuestion(PolicyCreationBean policyCreationBean) throws SQLException, IOException;
	public void createNewScheme(NewPolicyBean newPolicySchemeBean) throws SQLException, IOException;
	public String checkUser(String username) throws InsuranceQuoteGenerationException, SQLException, IOException;
	public boolean CheckAccount(String username, String business_Segment) throws InsuranceQuoteGenerationException, SQLException, IOException;
	public long policy_Details(PolicyDetails policyDetails) throws SQLException, IOException;
	public String validateUser(LoginBean loginBean) throws SQLException, IOException, InsuranceQuoteGenerationException;
	public boolean findAgentName(String agentName) throws InsuranceQuoteGenerationException, SQLException, IOException;
	public boolean checkSegment(String username) throws SQLException, IOException, InsuranceQuoteGenerationException;
	public boolean checkCreateAccount(String username) throws SQLException, IOException, InsuranceQuoteGenerationException;
	 public void businessReport(Business businessValues) throws SQLException, IOException;
	 public void questionStore(TemporaryData temporaryData) throws SQLException, IOException ;
	 public void deleteQuestionStore() throws SQLException, IOException;
	 public List<TemporaryData> getQuestionStore(String username) throws SQLException, IOException;
	 public boolean checkAccountNumber(Long accountNumber) throws SQLException, IOException;
	 public List<AgentViewPolicyBean> getPolicyDetails1(String userName) throws SQLException, IOException;
}

