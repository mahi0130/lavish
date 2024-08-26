package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.TransactionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.TransactionModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "TransactionCtl", urlPatterns = { "/ctl/TransactionCtl" })

public class TransactionCtl extends BaseCtl{
	
	
	@Override
	protected void preload(HttpServletRequest request) {
		
		Map<Integer, String> map = new HashMap();
		map.put(1, "7896543211234");
		map.put(2, "5612368923445");
		map.put(3, "4122321245678");
		map.put(4, "3678923134545");
		map.put(5, "96543762176543");
		map.put(6, "7684209810542");
		map.put(7, "000034251783");
		map.put(8, "675221354677");
		request.setAttribute("account_idd", map);	
		
		Map<Integer, String> map1 = new HashMap();
		map1.put(1, "Cash");
		map1.put(2, "online");
		map1.put(3, "netbanking");
		map1.put(4, "cheque");
		
		request.setAttribute("transaction_typee", map1);

		
		 	}
	

	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "description"));
			pass = false;
		}

		
		
		if (DataValidator.isNull(request.getParameter("transaction_type"))) {
			request.setAttribute("transaction_type", PropertyReader.getValue("error.require", "transaction_type"));
			pass = false;
		}

		

		if (DataValidator.isNull(request.getParameter("transaction_date"))) {
			request.setAttribute("transaction_date", PropertyReader.getValue("error.require", " transaction_date"));

			pass = false;
		

		}
		if (DataValidator.isNull(request.getParameter("account_id"))) {
			request.setAttribute("account_id", PropertyReader.getValue("error.require", "account_id"));
			pass = false;
		}

			  
		 

		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		TransactionDTO dto = new TransactionDTO();

		System.out.println(request.getParameter("dob"));

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setDescription(DataUtility.getString(request.getParameter("description")));
		dto.setAccount_id(DataUtility.getLong(request.getParameter("account_id")));
		dto.setTransaction_type(DataUtility.getString(request.getParameter("transaction_type")));
        dto.setTransaction_date(DataUtility.getDate(request.getParameter("transaction_date")));



		populateBean(dto, request);

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		TransactionModelInt model = ModelFactory.getInstance().getTransactionModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			TransactionDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		TransactionModelInt model = ModelFactory.getInstance().getTransactionModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			TransactionDTO dto = (TransactionDTO) populateDTO(request);
			try {
				if (id > 0) {
					model.update(dto);

					ServletUtility.setSuccessMessage("Data is successfully Update", request);
				} else {

					try {
						model.add(dto);
						ServletUtility.setDto(dto, request);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
					} catch (ApplicationException e) {
						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Login id already exists", request);
					}

				}
				System.out.println(dto.getTransaction_type()+"kkkkkkkkkkkkkkkkkk");
				
				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			TransactionDTO dto = (TransactionDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.TRANSACTION_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TRANSACTION_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TRANSACTION_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.TRANSACTION_VIEW;
	}



}
