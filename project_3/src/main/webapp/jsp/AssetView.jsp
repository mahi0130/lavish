
<%@page import="in.co.rays.project_3.controller.AssetCtl"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script>
	function validateMobileNo(event) {
		const input = event.target;
		input.value = input.value.replace(/[^0-9.]/g, '');
		if (input.value.length > 0 && input.value[0] <= '5') {

			input.value = '';
		}
	}
	function validateVersion(event) {
		const input = event.target;
		input.value = input.value.replace(/[^0-9.]/g, '');
	}
</script>

<script>
	function validateNumericInput(inputField) {
		// Get the value entered by the user
		var inputValue = inputField.value;

		// Regular expression to check if the input is numeric
		var numericPattern = /^\d*$/;

		// Test the input value against the numeric pattern
		if (!numericPattern.test(inputValue)) {
			// If input is not numeric, clear the field
			inputField.value = inputValue.replace(/[^\d]/g, ''); // Remove non-numeric characters
		}
	}
</script>
<script src="<%=ORSView.APP_CONTEXT%>/js/ValidateToInput.js"></script>
<style type="text/css">
i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

.input-group-addon {
	/* box-shadow: 9px 8px 7px #001a33; */
	background-image: linear-gradient(to bottom right, orange, black);
	background-repeat: no repeat;
	background-size: 100%;
	padding-bottom: 11px;
}

.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/unsplash.jpg');
	background-size: cover;
	padding-top: 6%;
}
</style>

</head>
<body class="hm">
	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>
	</div>
	<div>

		<main>
		<form action="<%=ORSView.ASSET_CTL%>" method="post">
			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.AssetDTO"
				scope="request"></jsp:useBean>
			<div class="row pt-3">
				<!-- Grid column -->
				<div class="col-md-4 mb-4"></div>
				<div class="col-md-4 mb-4">
					<div class="card input-group-addon">
						<div class="card-body">

							<%
								long id = DataUtility.getLong(request.getParameter("id"));

								if (dto.getId() != null && id > 0) {
							%>
							<h3 class="text-center default-text text-primary">Update
								Asset</h3>
							<%
								} else {
							%>
							<h3 class="text-center default-text text-primary">Add Asset</h3>
							<%
								}
							%>
							<!--Body-->
							<div>
								<%
									List list = (List) request.getAttribute("roleList");
									List list1 = (List) request.getAttribute("list1");
									Map map = (Map) request.getAttribute("map");
								%>

								<H4 align="center">
									<%
										if (!ServletUtility.getSuccessMessage(request).equals("")) {
									%>
									<div class="alert alert-success alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=ServletUtility.getSuccessMessage(request)%>
									</div>
									<%
										}
									%>
								</H4>

								<H4 align="center">
									<%
										if (!ServletUtility.getErrorMessage(request).equals("")) {
									%>
									<div class="alert alert-danger alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=ServletUtility.getErrorMessage(request)%>
									</div>
									<%
										}
									%>

								</H4>

								<input type="hidden" name="id" value="<%=dto.getId()%>">

							</div>

							<span class="pl-sm-5"><b>AssetId</b> <span
								style="color: red;">*</span></span> </br>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-user-alt grey-text" style="font-size: 1rem;"></i>
										</div>
									</div>
									<input type="text" class="form-control" name="assetId"
										placeholder="Enter assetId"
										oninput="validateNumericInput(this)" maxlength="16"
										value="<%=DataUtility.getStringData(dto.getAssetId()).equals("0") ? ""
					: DataUtility.getStringData(dto.getAssetId())%>">

								</div>
							</div>
							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("assetId", request)%></font></br>



							<span class="pl-sm-5"><b>RegistrantionNumber</b> <span
								style="color: red;">*</span></span> </br>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-user-alt grey-text" style="font-size: 1rem;"></i>
										</div>
									</div>
									<input type="text" class="form-control"
										name="registrationNumber"
										placeholder="Enter registration Number" maxlength="16"
										value="<%=DataUtility.getStringData(dto.getRegistrationNumber())%>">

								</div>
							</div>
							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("registrationNumber", request)%></font></br>










							<span class="pl-sm-5"><b> AcquisitionDate</b> <span
								style="color: red;">*</span></span></br>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-list grey-text" style="font-size: 1rem;"></i>
										</div>


									</div>
									<input type="text" class="form-control" name="acquisitionDate"
										readonly="readonly" rows="3" cols="3" id="datepicker"
										placeholder="Enter acquisition Date " maxlength="10"
										value="<%=DataUtility.getDateString(dto.getAcquisitionDate())%>">
								</div>
							</div>
							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("acquisitionDate", request)%></font></br>



							<span class="pl-sm-5"><b> Coverage Amount</b> <span
								style="color: red;">*</span></span></br>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-list grey-text" style="font-size: 1rem;"></i>
										</div>
									</div>
									<input type="text" class="form-control" name="coverageAmount"
										oninput="validateNumericInput(this)" maxlength="16"
										placeholder="coverage Amount"
										value="<%=DataUtility.getStringData(dto.getCoverageAmount()).equals("0") ? ""
					: DataUtility.getStringData(dto.getCoverageAmount())%>">
								</div>
							</div>
							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("coverageAmount", request)%></font></br>


							<span class="pl-sm-5"><b>PaintColor</b><span
								style="color: red;">*</span></span> </br>

							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-venus-mars grey-text"
												style="font-size: 1rem;"></i>
										</div>
									</div>

									<%=HTMLUtility.getList1("paintColor", String.valueOf(dto.getPaintColor()), map)%>
								</div>
							</div>
							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("paintColor", request)%></font></br>



							<%
								if (dto.getId() != null && id > 0) {
							%>

							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=AssetCtl.OP_UPDATE%>"> <input type="submit"
									name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=AssetCtl.OP_CANCEL%>">

							</div>
							<%
								} else {
							%>
							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=AssetCtl.OP_SAVE%>"> <input type="submit"
									name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=AssetCtl.OP_RESET%>">
							</div>

						</div>
						<%
							}
						%>
					</div>
				</div>
		</form>
		</main>
		<div class="col-md-4 mb-4"></div>

	</div>

</body>
<%@include file="FooterView.jsp"%>

</body>
</html>