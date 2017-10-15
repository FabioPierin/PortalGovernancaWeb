<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page	language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	
<html xmlns="http://www.w3.org/1999/xhtml" lang="en-US" xml:lang="en-US">
<head>
	<jsp:include page="header.jsp"></jsp:include>		
	<script src="/profiles/ibm_semanticTagServlet/javascript/semanticTagService.js?inclDojo=true"></script>	
	<link href="/PGW/css/yourContent.css" rel="stylesheet" type="text/css" ></link>
	
	<script type="text/javascript">
		 var s=document.getElementsByTagName('script')[0];
		 var sc=document.createElement('script');
		 sc.type='text/javascript';
		 sc.async=true;
		 sc.src='/PGW/js/yourContent.js?v' + Math.random(); 
		 s.parentNode.insertBefore(sc,s);
		 window.onload = function () {
		 	adjustLayout();
		 	loadContent();
// 		 	init();
		 }
	</script>
</head>
<body id="ibm-com">
        <script type="text/javascript">
	        var SemTagSvcConfig = {
			baseUrl:"/communities",
			communitiesSvc:"/communities",
			isBidiRTL:false,
			loadCssFiles:true,
			profilesSvc:"/profiles",
			resourcesSvc:"/common/resources",
			sametimeAwarenessEnabled:true};
        </script>

	<div id="ibm-top" class="ibm-landing-page">

		<!-- MASTHEAD_BEGIN -->

		<div id="ibm-masthead">
			<div id="ibm-mast-options">
				<ul>

					<li id="ibm-home"><a href="//w3.ibm.com/">w3</a></li>
				</ul>
			</div>
			<div id="ibm-universal-nav">
				<p id="ibm-site-title">
					<em><spring:message code="label.ConnectionsMigrationTool" /></em>
				
<!-- 				<span style="float: right">  -->
<!-- 				<a id="sorting" onclick="poc();">PoC</a> |  -->
<!-- 				<a id="noSorting" onclick="nonpoc();">non-PoC</a></span> -->
				</p>
				<ul id="ibm-menu-links">
				</ul>
				<div id="ibm-search-module">
					<form id="ibm-search-form"
						action="//w3.ibm.com/search/do/search" method="get">
						<p>
							<label for="q"><span class="ibm-access"><spring:message code="label.Search" /></span>
							</label> <input type="text" maxlength="100" value="" name="qt" id="q" />
							<input type="hidden" value="17" name="v" /> <input value="en"
								type="hidden" name="langopt" /> <input value="all" type="hidden"
								name="la" /> <input type="submit" id="ibm-search"
								class="ibm-btn-search" value="Submit" />
						</p>
					</form>
				</div>
			</div>
		</div>
		<!-- MASTHEAD_END -->
		<!-- LEADSPACE_BEGIN -->
		<!-- LEADSPACE_END -->
		<!-- CONTENT_NAV_BEGIN -->

		<!-- CONTENT_NAV_END -->
		<div id="ibm-pcon">

			<!-- CONTENT_BEGIN -->
			<div id="ibm-content" >
				<p id="user" style="display:none">${userEmail}</p>
				<p id="disableSNT" style="display:none">${RunSNTReadOnly}</p>
				<p id="disableDelay" style="display:none">${disableDelayButton}</p>
				
				<div id="delayTip" class="tip">
					<img src="/PGW/css/images/triangle2.png"></img>
					<span><spring:message code="label.delayToolTip" /></span>
				</div>
				<div id="delayButtonTip" class="tip">
					<img src="/PGW/css/images/triangle2.png"></img>
					<span><spring:message code="label.delayButtonToolTip" /></span>
				</div>
				
				<div id="overlay" style="display:none"></div>
				<div id="loadingOverlay" style="display:none"></div>
				<div id="loading" style="display:none">
					<center>
						<img src="/PGW/css/images/loading.gif" class="loading" />
					</center>
				</div>
				<div id="msgBox" style="display:none">
					<div>
						<center>
							<p><span id="appsOut" ></span> <spring:message code="label.yourContent.applicationTemporarilyUnavailable" /></p></br>
							<input class="ibm-btn-pri" name="tryAgain" value="Try again" onclick="tryAgain()" type="button"/>
							<input class="ibm-btn-sec" name="cancel" value="Cancel" onclick="cancel()" type="button"/>
						</center>
					</div>
				</div>
				<div id="renameBox" style="display:none">
					<div>
							<h6><spring:message code="label.Migrate" /> <span class="appName"><spring:message code="label.community" /></span></h6>
							<p><spring:message code="label.newCommunityNameAlreadyExist" /></p></br>
							<p><spring:message code="label.newName" /></p>
							<input name="newName" type="text" size="30" id="newName"  /></br>
							<input class="ibm-btn-pri" name="ok" value="OK" onclick="validateConflictingName();" type="button"/>
							<input class="ibm-btn-sec" name="cancel" value="Cancel" onclick="fadeOutRename();" type="button"/>
					</div>
				</div>
				<!-- CONTENT_BODY -->
				<div id="ibm-content-body">
					<div id="ibm-content-main">
						<center id="main-center">
						<div>
<!-- <h3 style="color:red">We are executing negative tests, the app is not working correctly for now</h3> -->
						
								<p><spring:message code="label.description" /></p>
						</div>
						<div id="selectType">
							<span><spring:message code="label.view" />:</span>
							<span>
								<select name="salutation" id="showType" onchange="loadContent()" >
									<option value="all" selected="selected" ><spring:message code="label.All" /></option>
									<option value="activity"><spring:message code="label.Activity" /></option>
									<option value="blog"><spring:message code="label.Blog" /></option>
									<option value="community"><spring:message code="label.Community" /></option>
									<option value="wiki"><spring:message code="label.Wiki" /></option>
								</select>
							</span>	
						</div>
						<div id="avaliable">
							<table summary="Your content" class="ibm-data-table ibm-alternating" cellspacing="0" cellpadding="0" border="0" summary="Data table with different highlight color" aria-labelledby="tablesorter7c9945daf3bae8caption">
								<caption>
									<em><spring:message code="label.avaliableTableTitle" /></em>
								</caption>
								<thead>
							    <tr class="tablesorter-headerRow">
							        <th scope="col" style="width: 290px !important;"><span>&nbsp;&nbsp;<spring:message code="label.collumnName" />&nbsp;&nbsp;</span></th>
							        <th scope="col" style="width: 60px !important;"><span>&nbsp;&nbsp;<spring:message code="label.collumnType" />&nbsp;&nbsp;</span></th>
							        <th scope="col" style="width: 75px !important;"><span>&nbsp;&nbsp;<spring:message code="label.collumnLastUpdate" />&nbsp;&nbsp;</span></th>
							    </tr>
							    </thead>
								    <tbody id="avaliableTable" class="" >
<!-- 								    <tr id="43105c97-ff64-4b6f-87f7-ca240b6bc183" lastupdate="22 Apr 2016" date="2016-04-19T22:22:29.068Z" itemtype="Community" name="Email Defect 04~!@#$%&amp;()áéíóúà06 (Owners , co-owners and Members)" class="item odd"><td style="width: 262px !important;"><a target="_black" href="https://w3-icdev4.ahe.boulder.ibm.com/communities/service/html/communitystart?communityUuid=43105c97-ff64-4b6f-87f7-ca240b6bc183">Email Defect 04~!@#$%&amp;()áéíóúà06 (Owners , co-owners and Members)</a></td><td style="width: 55px !important;font-size:11px !important;">Community</td><td style="width: 65px !important;font-size:11px !important;">22 Apr 2016</td></tr> -->
								    </tbody>
							</table>
						</div>
						
						<% if ("true".equals(request.getAttribute("RunSNTReadOnly"))) {						%>

						<div id="buttons">
							<center>
								<input class="ibm-btn-pri disabled" disabled="disabled" name="schedule" value="<spring:message code="label.buttonSchedule" /> &gt;"  type="button"/><br/>
								<input class="ibm-btn-pri disabled" disabled="disabled" name="doNotMigrate" value="<spring:message code="label.scheduledTableNotToBeMigrated" /> X"  type="button"/>
								<input class="ibm-btn-pri disabled" disabled="disabled" name="remove" value="&lt; <spring:message code="label.buttonRemove" />" type="button"/>
							</center>
						</div>
						<%  }else{		%>
						<div id="buttons">
							<center>
								<input id="scheduleButton" class="ibm-btn-pri" name="schedule" value="<spring:message code="label.buttonSchedule" /> &gt;" onclick="validateConflictingName()" type="button"/><br/>
								<input id="doNotMigrateButton" class="ibm-btn-pri" name="doNotMigrate" value="<spring:message code="label.scheduledTableNotToBeMigrated" /> X" onclick="doNotMigrate()" type="button"/>
								<input id="removeButton" class="ibm-btn-pri" name="remove" value="&lt; <spring:message code="label.buttonRemove" />" onclick="removeFromQueue()" type="button"/>
							</center>
						</div>
						<%  }		%>
						
						<div id="scheduled">
							<table summary="Scheduled for migration" class="ibm-data-table ibm-alternating ibm-sortable-table tablesorter tablesorter-default" cellspacing="0" cellpadding="0" border="0" summary="Data table with different highlight color" aria-labelledby="tablesorter7c9945daf3bae8caption">
								<caption>
									<em><spring:message code="label.scheduledTableScheduled" /></em>
								</caption>
								<thead>
							    <tr class="tablesorter-headerRow">
							        <th scope="col" style="width: 290px !important;"><span>&nbsp;&nbsp;<spring:message code="label.collumnName" />&nbsp;&nbsp;</span></th>
							         <th scope="col" style="width: 135px !important;"><span>&nbsp;&nbsp;<spring:message code="label.collumnType" />&nbsp;&nbsp;</span></th>
							    </tr>
							    </thead>
							    <tbody id="scheduledTable" class="" style="display: none" >
									<tr id="CompletedHead">
										<th colspan="3"><spring:message code="label.scheduledTableCompleted" /> (<span>0</span>)</th>
									</tr>							    
									<tr id="InProgressHead">
										<th colspan="3"><spring:message code="label.scheduledTableInProgress" /> (<span>0</span>)</th>
									</tr>							    
									<tr id="QueuedHead">
										<th colspan="3"><spring:message code="label.scheduledTableScheduled" /> (<span>0</span>)<img class="tooltip" src="/PGW/css/images/infoBlack.png"/>
											<% if ("true".equals(request.getAttribute("disableDelayButton")) || "true".equals(request.getAttribute("RunSNTReadOnly"))) {						%>
												<a id="delayButtonDisabled" class="tooltip disabled" "><spring:message code="label.delayButton" /></a>
											<%  }else{		%>
												<a id="delayButton" class="tooltip" onclick="delaySelectedItem();"><spring:message code="label.delayButton" /></a>
											<%  }		%>
										</th>
									</tr>							    
									<tr id="NotToBeMigratedsHead">
										<th colspan="3"><spring:message code="label.scheduledTableNotToBeMigrated" /> (<span>0</span>)</th>
									</tr>							    
							    </tbody>
							</table>
						</div>
						
						</center>


					</div>
					<form id="timeoutRedirect" action="/PGW/login" method="post">
					   <input type="hidden" name="timeout" value="true"/>
					</form>
									
					<p id="selectAnItem" style="display:none"><spring:message code="label.selectAnItem" /></p>
					<p id="errorRemove" style="display:none"><spring:message code="label.errorRemove" /></p>
					<p id="newNameExists" style="display:none"><spring:message code="label.newNameExists" /></p>
					<p id="conflictNameOut" style="display:none"><spring:message code="label.conflictNameOut" /></p>
					<p id="validateProblem" style="display:none"><spring:message code="label.validateProblem" /></p>
					<p id="vamosaOut" style="display:none"><spring:message code="label.vamosaOut" /></p>
					<p id="scheduledBy" style="display:none"><spring:message code="label.scheduledBy" /></p>
					<p id="notMigrateBy" style="display:none"><spring:message code="label.notMigrateBy" /></p>
					<p id="estimatedMigration" style="display:none"><spring:message code="label.estimatedMigration" /></p>
					<p id="originalName" style="display:none"><spring:message code="label.originalName" /></p>
					<p id="blogToCommunity" style="display:none"><spring:message code="label.blogToCommunity" /></p>
					<p id="wikiToCommunity" style="display:none"><spring:message code="label.wikiToCommunity" /></p>
					<p id="scheduledByOther" style="display:none"><spring:message code="label.scheduledByOther" /></p>
					<p id="doNotMigratedByOther" style="display:none"><spring:message code="label.doNotMigratedByOther" /></p>
					<p id="onHoldUntil" style="display:none"><spring:message code="label.onHoldUntil" /></p>
					<p id="errorPost" style="display:none"><spring:message code="label.errorPost" /></p>
					<!-- CONTENT_BODY_END -->
				</div>
			</div>
			<!-- CONTENT_END -->
			<!-- NAVIGATION_END -->
			<div id="hiddenContent" style="display: none;">
				<div id="noContent">
					<p><spring:message code="label.noContent" /></p>
				</div>
			</div>
		</div>


		<!-- FOOTER_BEGIN -->
		<div id="ibm-footer-module"></div>

		<div id="ibm-footer">
			<h2 class="ibm-access">Footer links</h2>
			<ul>
				<li><a href="//w3.ibm.com/w3/info_terms_of_use.html">Terms
						of use</a></li>
			</ul>
		</div>
		<!-- FOOTER_END -->

	</div>
	<div id="ibm-metrics">
		<script src="//w3.ibm.com/w3webmetrics/js/ntpagetag.js"
			type="text/javascript">
			//
		</script>
		<script type="text/javascript">
 	
			function getConfirmation(){
				
				document.getElementById("ibm-confirmation-form").submit();
				
			}
		
			
		</script>
	</div>
</body>
</html>