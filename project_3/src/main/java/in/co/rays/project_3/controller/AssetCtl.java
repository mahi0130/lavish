package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.AssetDTO;
import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.AssetModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "AssetCtl", urlPatterns = { "/ctl/AssetCtl" })

public class AssetCtl extends BaseCtl{

	
	
	@Override
	protected void preload(HttpServletRequest request) {
		

		Map<Integer, String> map1 = new HashMap();
		map1.put(1, "Red");
		map1.put(2, "Blue");
		map1.put(3, "Black");
		map1.put(4, "Yellow");
		
		request.setAttribute("map", map1);

		
		 	}
	

	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("assetId"))) {
			request.setAttribute("assetId", PropertyReader.getValue("error.require", "assetId"));
			pass = false;
		}
		else if (DataValidator.isTooLong(request.getParameter("assetId"),15)) {
			request.setAttribute("assetId", " assetId  not  contains more than 15 digit");

			pass = false;
		}

		
		
		if (DataValidator.isNull(request.getParameter("registrationNumber"))) {
			request.setAttribute("registrationNumber", PropertyReader.getValue("error.require", "registrationNumber"));
			pass = false;
		}

		

		if (DataValidator.isNull(request.getParameter("acquisitionDate"))) {
			request.setAttribute("acquisitionDate", PropertyReader.getValue("error.require", " acquisitionDate"));

			pass = false;
		

		}
		if (DataValidator.isNull(request.getParameter("coverageAmount"))) {
			request.setAttribute("coverageAmount", PropertyReader.getValue("error.require", "coverageAmount"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("paintColor"))) {
			request.setAttribute("paintColor", PropertyReader.getValue("error.require", "paintColor"));
			pass = false;
		}



			  
		 

		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		AssetDTO dto = new AssetDTO();


		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setAssetId(DataUtility.getLong(request.getParameter("assetId")));

		

		dto.setRegistrationNumber(DataUtility.getString(request.getParameter("registrationNumber")));
        dto.setAcquisitionDate(DataUtility.getDate(request.getParameter("acquisitionDate")));

		dto.setCoverageAmount(DataUtility.getLong(request.getParameter("coverageAmount")));
		dto.setPaintColor(DataUtility.getString(request.getParameter("paintColor")));



		populateBean(dto, request);

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		AssetModelInt model = ModelFactory.getInstance().getAssetModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			AssetDTO dto;
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
		AssetModelInt model = ModelFactory.getInstance().getAssetModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			AssetDTO dto = (AssetDTO) populateDTO(request);
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
				
				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			AssetDTO dto = (AssetDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.ASSET_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ASSET_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ASSET_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ASSET_VIEW;
	}





}
