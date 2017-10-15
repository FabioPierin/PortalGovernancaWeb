var subheaders = {};
var queuedItems = [];
var dates = [];
var ajaxCalled = 0;
var listAppOut = [];

var urlBlogs = "/selfnominationtool/api/blogs/conciliation";
var urlCommunities = "/selfnominationtool/api/communities/conciliation";
var urlWikis = "/selfnominationtool/api/wikis/conciliation";
var urlActivities = "/selfnominationtool/api/activities/conciliation";

var urlQueuedBlogs = "/selfnominationtool/api/blogs/queue/conciliation";
var urlQueuedCommunities = "/selfnominationtool/api/communities/queue/conciliation";
var urlQueuedWikis = "/selfnominationtool/api/wikis/queue/conciliation";
var urlQueuedActivities = "/selfnominationtool/api/activities/queue/conciliation";

var urlConflictingNameCloud = "/selfnominationtool/api/communities/validate";
var urlConflictingNameCloudAndW3 = "/selfnominationtool/api/communities/validatenew";

var postURLs = {"Community":"/selfnominationtool/api/communities/queue",
				"Blog":"/selfnominationtool/api/blogs/queue",
				"Activity":"/selfnominationtool/api/activities/queue",
				"Wiki":"/selfnominationtool/api/wikis/queue"
	};
var notMigrateURLs = {"Community":"/selfnominationtool/api/communities/donotmigrate",
		"Blog":"/selfnominationtool/api/blogs/donotmigrate",
		"Activity":"/selfnominationtool/api/activities/donotmigrate",
		"Wiki":"/selfnominationtool/api/wikis/donotmigrate"
};
var delayURL = "/selfnominationtool/api/communities/delay";

function adjustLayout(){
	document.title = $("#ibm-site-title em").html();
	if ( "zh-TW" == navigator.language || "zh-CN" == navigator.language || "tw" == navigator.language){
		$("#buttons input").css("font-size", "16px");
	}
	if ( "ko" == navigator.language ){
		$("#buttons input").css("font-size", "12px");
		$("#buttons input").css("width", "140px");
	}
}

function init(){
	if(ajaxCalled >0 ){
		return false;
	}
	$("#scheduledTable").show();
	$("#delayButton").hover(function(){
		var p = $(this).position();
		$("#delayButtonTip").css({ top: p.top+28, left: p.left-18 });
		$("#delayButtonTip").show();
	});
	$("#delayButton").mouseout(function(){$("#delayButtonTip").hide();});
	$("#QueuedHead th img.tooltip").hover(function(){
		var p = $(this).position();
		$("#delayTip").css({ top: p.top+28, left: p.left-280 });
		$("#delayTip").show();
	});
	$("#QueuedHead th img.tooltip").mouseout(function(){$("#delayTip").hide();});
	inicializeToopTip();
	$("#avaliableTable tr.item").click(function() {
		$("#removeButton").prop('disabled', "disabled");
		$("#doNotMigrateButton").prop('disabled', "");
		$("#scheduleButton").prop('disabled', "");
//		$("#delayButton").prop('disabled', "disabled");

		$("#removeButton").addClass("disabled");
		$("#doNotMigrateButton").removeClass("disabled");
		$("#scheduleButton").removeClass("disabled");
		$("#delayButton").addClass("disabled");

		$("#avaliableTable tr").removeClass("selected");
		$("#scheduledTable tr").removeClass("selected");
		$(this).addClass("selected");
		
		
	});
	$("#scheduledTable tr.item").click(function() {
		if($(this).attr("itemStatus") == "Started" 
			|| $(this).attr("itemStatus") == "Completed"){
			$("#removeButton").prop('disabled', "disabled");
			$("#doNotMigrateButton").prop('disabled', "disabled");
			$("#scheduleButton").prop('disabled', "disabled");
//			$("#delayButton").prop('disabled', "disabled");
			
			$("#removeButton").addClass("disabled");
			$("#doNotMigrateButton").addClass("disabled");
			$("#scheduleButton").addClass("disabled");
			$("#delayButton").addClass("disabled");
		}else{
			$("#removeButton").prop('disabled', "");
			$("#doNotMigrateButton").prop('disabled', "disabled");
			$("#scheduleButton").prop('disabled', "disabled");
			
			$("#removeButton").removeClass("disabled");
			$("#doNotMigrateButton").addClass("disabled");
			$("#scheduleButton").addClass("disabled");
			if($(this).attr("itemStatus") == "Queued" ){
				if ($(this).hasClass("disabled")){
					$("#delayButton").addClass("disabled");
				}else{
					$("#delayButton").removeClass("disabled");
				}
			}else{
				$("#delayButton").addClass("disabled");
			}
		}
		
		$("#avaliableTable tr").removeClass("selected");
		$("#scheduledTable tr").removeClass("selected");
		
		if ($(this).hasClass("disabled")){
			$("#removeButton").prop('disabled', "disabled");
			$("#doNotMigrateButton").prop('disabled', "disabled");
			$("#scheduleButton").prop('disabled', "disabled");
			$("#removeButton").addClass("disabled");
			$("#doNotMigrateButton").addClass("disabled");
			$("#scheduleButton").addClass("disabled");
		}


		$(this).addClass("selected");
	});
	updateNumbersInSubHeader();
	adjustTableBG();
	hideLoading();
	try {
		SemTagSvc.parseDom(null, 'ibm-content');
	} catch (e) {
	}

}

function adjustTableBG(){
	$("tr.odd").removeClass("odd");
	var lines = $("#avaliableTable").children();
	for (var i = 0; i < lines.length; i++){
		if ((i % 2) == 1 )
			$("#"+lines[i].id).addClass("odd");
	}
	var lines = $("#scheduledTable").children();
	for (var i = 0; i < lines.length; i++){
		if ((i % 2) == 1 )
			$("#"+lines[i].id).addClass("odd");
	}
}


function showLoading(){
	$("#loadingOverlay").fadeIn(500);
	$("#loading").fadeIn(500);
}
function hideLoading(){
	$("#loading").hide(500);
	$("#loadingOverlay").fadeOut(500);
}
function tryAgain(){
	$("#msgBox").fadeOut(300);
	$("#overlay").fadeOut(500);
	loadContent();
}
function cancel(){
	$("#msgBox").fadeOut(300);
	$("#overlay").fadeOut(500);
	window.location.replace("/selfnominationtool/landingPage");
}

function delaySelectedItem(){
	if ($("#delayButton").hasClass("disabled")){
		return;
	}
	showLoading();
	var line = $("#scheduledTable .selected");
	if (line.length != 1){
		alert($("#selectAnItem").html());
		hideLoading();
		return;
	}
	
	$.ajax({
		headers: { 
	        "Content-Type": "application/json; charset=utf-8;",
        	"Accept": "application/json; charset=utf-8;"
	    },
	    url: delayURL+"/"+line.attr("id"),
	    type: "GET",
	    dataType: "json",
	    success: function (data){
			if (data.timeout) {
			    $("#timeoutRedirect").submit();
			    return;
			}
	    	if(data.appOut){
	    		alert(data.appOut);
	    	}else{
		    	moveToRightTable(line,data);
	    	}
	    	hideLoading();
    	},
	    error: function (data){
	    	alert($("#errorPost").html());
	    	hideLoading();
    	}
		});
}

function fadeOutRename(){
	$("#newName").val("");
	$("#renameBox").fadeOut(100);
	$("#overlay").fadeOut(100);
}


function doNotMigrate(){
	showLoading();
	var line = $("#avaliableTable .selected");
	if (line.length != 1){
		alert($("#selectAnItem").html());
		hideLoading();
		return;
	}
	
	var type = line.attr("itemType");
	var dataPost = "{\"name\":\"#"+(line.attr("name"))+"\"," +
					"\"itemType\":\""+line.attr("itemType")+"\"," +
					"\"uuid\":\""+ line.attr("id")+"\""+ 
					"}"; 
	$.ajax({
		headers: { 
	        "Content-Type": "application/json; charset=utf-8;",
        	"Accept": "application/json; charset=utf-8;"
	    },
	    url: notMigrateURLs[type],
	    type: "POST",
	    dataType: "json",
	    data: dataPost,
	    success: function (data){
			if (data.timeout) {
			    $("#timeoutRedirect").submit();
			    return;
			}
	    	if(data.appOut){
	    		var msg = data.appOut;
	    		if (msg.indexOf(" by ") > 0){
	    			var text = "";
	    			if (msg.indexOf("Queued") >= 0){
	    				text = $("#scheduledByOther").html();
	    			}
	    			if (msg.indexOf("NotToBeMigrated") >= 0){
	    				text = $("#doNotMigratedByOther").html();			    				
	    			}
	    			if (text != "") {
		    			var email = msg.substring(msg.indexOf("by")+3, msg.indexOf(" at "));
		    			var date = msg.substring(msg.indexOf(" at ")+4);
		    			text = text.replace("{EMAIL}", email);
		    			text = text.replace("{DATE}", date);
		    			alert(text);
	    			}else {
	    				alert(data.appOut);
	    			}
	    			moveToRightTable(line, data);
	    			fadeOutRename();
	    		}else{
	    			alert(data.appOut);
	    		}
	    	}else{
		    	moveToRightTable(line,data);
	    	}
	    	hideLoading();
    	},
	    error: function (data){
	    	alert($("#errorPost").html());
	    	hideLoading();
    	}
		});
}
// Queued items control ---- START

function removeFromQueue(){
	showLoading();
	var line = $("#scheduledTable .selected");
	if (line.length != 1){
		alert($("#selectAnItem").html());
		hideLoading();
		return;
	}
	
	var type = line.attr("itemType");
	$.ajax({
		headers: { 
	        "Content-Type": "application/json; charset=utf-8;",
        	"Accept": "application/json; charset=utf-8;"
	    },
	    url: postURLs[type]+"/"+line.attr("id"),
	    type: "DELETE",
	    dataType: "json",
	    success: function (data){
	    	if (data.timeout) {
			    $("#timeoutRedirect").submit();
			    return;
			}
	    	if(data.appOut){
	    		alert(data.appOut);
	    	}else{
	    		if(data.removed){
	    			var item = Object;
	    			item.title = decodeURI(line.attr("name"));
	    			item.creationDate = line.attr("date");
	    			item.uuid = line.attr("id");
	    			item.lastUpdate = line.attr("lastUpdate");
	    			item.url = $("#"+line[0].id+" > td > a")[0].href;
	    			
	    			
			    	line.remove();
			    	line = mountAvaliableItemLine(item, type);
			    	$("#avaliableTable").append( line );
			    	line = $("#"+item.uuid);
			    	line.remove();
			    	var typeSelected = $('#selectType :selected').val();
			    	if ("all" == typeSelected || typeSelected == type.toLowerCase()){
			    		
			    		var items = $("#avaliableTable").children();
			    		var included = false;
			    		for ( var i = 0; i < items.length; i++){
			    			var tr = items[i];
			    			if (tr.getAttribute("date") != undefined && tr.getAttribute("date") < item.creationDate){
			    				line.insertBefore( tr );
			    				included = true;
			    				break;
			    			}
			    		}
			    		if(!included){
			    			$("#avaliableTable").append( line );			    		
			    		}
			    		$("#avaliableTable p").remove();
			    		var newLine = $("#"+line[0].id);
				    	$("#avaliableTable").scrollTop(0);
				    	$("#avaliableTable").scrollTop(newLine.position().top
				    			 - $("#avaliableTable").position().top);
			    		init();
				    	newLine.click();
			    	}
	    		}else{
	    			alert($("#errorRemove").html());
	    		}
	    	}
	    	hideLoading();
    	},
	    error: function (data){
	    	alert($("#errorPost").html());
	    	hideLoading();
    	}
		});
}

// Queued items control ---- END


// Schedule session ------  START
function validateConflictingName(){
	showLoading();
	var line = $("#avaliableTable .selected");
	if (line.length != 1){
		alert($("#selectAnItem").html());
		hideLoading();
		return;
	}
	var type = line.attr("itemType");
	if ("Activity" ==  type){
		schedule(line, "");
	}else{
		
		var nameToCheck = "";
		var newName = "";
		var urlValidate = "";
		if ($("#newName").val() == "" || $("#newName").val() == undefined){
			nameToCheck = line.attr("name");
			if(type == "Community"){
				urlValidate = urlConflictingNameCloud;
			} else {
				urlValidate = urlConflictingNameCloudAndW3;
			}
		} else {
			nameToCheck = encodeURI($("#newName").val());
			urlValidate = urlConflictingNameCloudAndW3;
			newName= $("#newName").val();
		}
		var dataPost = '{"name":"#'+ (nameToCheck)+'",' +
		'"uuid":"'+ line.attr('id')+'"'+ 
		'}'; 
//		nameToCheck = encodeURIComponent(nameToCheck);
		
		$.ajax({
			headers: { 
		        "Content-Type": "application/json; charset=UTF-8",
	        	"Accept": "application/json; charset=UTF-8"
		    },
		    url: urlValidate,
		    type: "POST",
		    dataType: "json",
		    data: dataPost,
		    success: function (data){
				  if (data.timeout) {
					    $("#timeoutRedirect").submit();
					    return;
					}
				  if (data.appOut != ""){
					  var msg = data.appOut;
			    		if (msg.indexOf(" by ") > 0){
			    			var text = "";
			    			if (msg.indexOf("Queued") >= 0){
			    				text = $("#scheduledByOther").html();
			    			}
			    			if (msg.indexOf("NotToBeMigrated") >= 0){
			    				text = $("#doNotMigratedByOther").html();			    				
			    			}
			    			if (text != "") {
				    			var email = msg.substring(msg.indexOf("by")+3, msg.indexOf(" at "));
				    			var date = msg.substring(msg.indexOf(" at ")+4);
				    			text = text.replace("{EMAIL}", email);
				    			text = text.replace("{DATE}", date);
				    			alert(text);
			    			}else {
			    				alert(data.appOut);
			    			}
			    			moveToRightTable(line, data);
			    			fadeOutRename();
			    		}else{
			    			alert($("#conflictNameOut").html());
			    		}
					  hideLoading();
				  } else {
					  if (data.nameExists){
						  hideLoading();
						  if ($("#newName").val() == undefined 
								  || $("#newName").val() == ""){
							  $("#overlay").fadeIn(500); 
							  $("#renameBox").fadeIn(500);
						  }else{
							  alert($("#newNameExists").html());
						  }
					  }else{
						  schedule(line, newName);
						  fadeOutRename();
					  }
				  }
			  },
			  error: function (data){
				  alert($("#validateProblem").html()); 
				  hideLoading();
			  }
			});
	}
}

function moveToRightTable(line, data){
	var item = data.activities;
	if (item == undefined)
		item = data.blogs;
	if (item == undefined)
		item = data.communities;
	if (item == undefined)
		item = data.wikis;
	if (item.creationDate == undefined || item.creationDate == ""){
		item.creationDate = line.attr("date");
	}
	if (item.lastUpdate == undefined || item.lastUpdate == ""){
		item.lastUpdate = line.attr("lastUpdate");
	}
	if (item.url == undefined || item.url == ""){
		item.url = $("#"+line[0].id+" > td > a")[0].href;
	}
	if (item.type == undefined || item.type == ""){
		item.type = line.attr("itemType");
	}
	
	var tr = mountQueuedItemLine(item);
	line.remove();
	findPositionInTable(tr, item);
	init();
	var newLine = $("#"+line[0].id);
	$("#scheduledTable").scrollTop(0);
	$("#scheduledTable").scrollTop(newLine.position().top
			 - $("#scheduledTable").position().top);
	newLine.click();
}
function findPositionInTable(newTR, item){
	var lines = $("#scheduledTable").children();
	var included = false;
	
	var i = 0;
	if("Completed" == item.itemStatus){
		while ("CompletedHead" != lines[i].id){
			i++;
		}
	}else
	if("Started" == item.itemStatus){
		while ("InProgressHead" != lines[i].id){
			i++;
		}
	}else
	if("Queued" == item.itemStatus || "OnHold" == item.itemStatus){
		while ("QueuedHead" != lines[i].id){
			i++;
		}
	}else
	if("NotToBeMigrated" == item.itemStatus){
		while ("NotToBeMigratedsHead" != lines[i].id){
			i++;
		}
	}
	var initial = i;
	var elements = 0;
	for (i++; i < lines.length; i++){
		var line = lines[i];
		if(line.id.indexOf("Head") > 0 ){
			break;
		}
		elements++;
		var n = item.newName != "" ? item.newName : item.name;
		if (line.getAttribute("name") != undefined && line.getAttribute("name").toLowerCase() > n.toLowerCase()){
			$("#"+line.id).before(newTR);
			included = true;
			break;
		}
	}
	if(!included){
		line = lines[initial+elements];
		$("#"+line.id).after(newTR);
	}
	init();
}

function updateNumbersInSubHeader(){
	var lines = $("#scheduledTable").children();
	var comp = 0;
	var inPro = 0;
	var queued = 0;
	var not = 0;
	for (var i = 0; i < lines.length; i++){
		if (lines[i].getAttribute("itemStatus")=="Queued"){
			queued++;
		}else
			if (lines[i].getAttribute("itemStatus")=="Completed"){
				comp++;
			}else
				if (lines[i].getAttribute("itemStatus")=="NotToBeMigrated"){
					not++;
				}else
					if (lines[i].getAttribute("itemStatus")=="Started"){
						inPro++;
					}
	}
	$("#CompletedHead span").html(comp);
	$("#InProgressHead span").html(inPro);
	$("#QueuedHead span").html(queued);
	$("#NotToBeMigratedsHead span").html(not);
	
}

function mountQueuedItemLine(item){
	var disable = ($("#user").html() == item.addedBy || "NULL" == item.addedBy)? '':' disabled';
	return '<tr  class="item'+disable+ '"' 
	+ ' name ="' + encodeURI(item.name) + '" '
	+ ' newName ="' + encodeURI(item.newName) + '" '
	+ ' itemType ="' + item.type + '" '
	+ ' date ="' + item.creationDate + '" '
	+ ' id  ="' + item.uuid + '" '
	+ ' addedBy  ="' + item.addedBy + '" '
	+ ' lastUpdate ="' + item.lastUpdate + '" '
	+ ' itemStatus  ="' + item.itemStatus + '" '
	+ ' onHoldUntil  ="' + item.onHoldUntil + '" '
	+'>'
	+'<td style="padding: 8px 5px 5px 3px;">'
	+'<img class="tip_trigger" src="/selfnominationtool/css/images/info.png" ></img>'
	+ mountToolTip(item)
	+'</td>'
	+ '<td  style="padding-left: 0px; width: 274px !important;">'
	+'<a href="'
	+ item.url
	+ '" target="_black">'
	+ (item.newName != '' ? item.newName.replace(/>/g, "&gt;").replace(/</g,"&lt;") : item.name.replace(/>/g, "&gt;").replace(/</g,"&lt;"))
	+ '</a></td>'
	+ '<td style="width: 94px !important; font-size:11px !important;">'
		+ ((item.type.toLowerCase() == 'wiki' || item.type.toLowerCase() == 'blog') ? item.type + ' -> Community' : item.type)  
	+ '</td></tr>';
}
function mountToolTip(item){
	var vcard =
		"<span class='vcard'>"+
		   "<a href='javascript:void(0);' class='fn url bidiAware' role='button'>"+item.addedBy+"</a>"+
		   "<span class='email' style='display: none;'>"+
		   	item.addedBy+
		   "</span>"+
		"</span>";
	var tip = '<div class="tip"><div></div>'
	+ '<img src="/selfnominationtool/css/images/triangle2.png"></img>';
	if(item.itemStatus == "Queued" || item.itemStatus == "OnHold"){
		tip = tip + $("#scheduledBy").html()+ ' ' + vcard + '</br>';
		if(item.itemStatus != "OnHold"){
			tip = tip + $("#estimatedMigration").html() +' '+ item.estimatedDate + '</br>';
		}
		if(item.type == "Blog"){
			tip = tip +'</br>'+ $("#blogToCommunity").html()+ '</br>'; 
		}
		if(item.type == "Wiki"){
			tip = tip +'</br>'+ $("#wikiToCommunity").html() + '</br>'; 
		}
		if(item.newName != ""){
			tip = tip + $("#originalName").html() +' '+ item.name.replace(/>/g, "&gt;").replace(/</g,"&lt;") + "</br>"; 
		}
		if(item.itemStatus == "OnHold"){
			tip = tip + $("#onHoldUntil").html().replace("{DATE}", item.onHoldUntil); 
		}
	}else if(item.itemStatus == "Completed"){
		tip = tip + $("#scheduledBy").html() + ' ' + vcard + '</br>';
		if(item.type == "Blog"){
			tip = tip +'</br>'+ $("#blogToCommunity").html() +'</br>'; 
		}
		if(item.type == "Wiki"){
			tip = tip +'</br>'+ $("#wikiToCommunity").html() + '</br>'; 
		}
		if(item.newName != ""){
			tip = tip + $("#originalName").html() +' '+ item.name.replace(/>/g, "&gt;").replace(/</g,"&lt;"); 
		}
	}else if(item.itemStatus == "Started"){
		tip = tip + $("#scheduledBy").html()+ ' ' + vcard + '</br>';
		if(item.type == "Blog"){
			tip = tip +'</br>'+ $("#blogToCommunity").html() + '</br>'; 
		}
		if(item.type == "Wiki"){
			tip = tip +'</br>'+ $("#wikiToCommunity").html() + '</br>'; 
		}
		if(item.newName != ""){
			tip = tip + $("#originalName").html() +' '+ item.name.replace(/>/g, "&gt;").replace(/</g,"&lt;"); 
		}
	}else if(item.itemStatus == "NotToBeMigrated"){
		tip = tip + $("#notMigrateBy").html()+ ' ' + vcard ;
	}
	tip = tip + '</div>';
	return tip;
}
function wrapText(name){
	var wrapT = name;
	var s = wrapT.length;
	var x = 1;
	var size = 40;
	while (s > size){
		wrapT = wrapT.slice(0,size * x)+"\n"+wrapT.slice(size * x);
		s = s - size;
		x++;
	}
	return wrapT;
}
function schedule(line, newName){
	var type = line.attr("itemType");
	var dataPost = '{"name":"#'+(line.attr('name'))+'",' +
					'"newName":"#'+ encodeURI(newName)+'",' +
					'"itemType":"'+line.attr('itemType')+'",' +
					'"uuid":"'+ line.attr('id')+'"'+ 
					'}'; 
	$.ajax({
		headers: { 
	        "Content-Type": "application/json; charset=utf-8;",
        	"Accept": "application/json; charset=utf-8;"
	    },
	    url: postURLs[type],
	    type: "POST",
	    dataType: "json",
	    data: dataPost,
	    success: function (data){
	    	if (data.timeout) {
			    $("#timeoutRedirect").submit();
			    return;
			}
	    	if(data.appOut){
	    		var msg = data.appOut;
	    		if (msg.indexOf(" by ") > 0){

	    			var text = "";
	    			if (msg.indexOf("Queued") >= 0){
	    				text = $("#scheduledByOther").html();
	    			}
	    			if (msg.indexOf("NotToBeMigrated") >= 0){
	    				text = $("#doNotMigratedByOther").html();			    				
	    			}
	    			if (text != "") {
		    			var email = msg.substring(msg.indexOf("by")+3, msg.indexOf(" at "));
		    			var date = msg.substring(msg.indexOf(" at ")+4);
		    			text = text.replace("{EMAIL}", email);
		    			text = text.replace("{DATE}", date);
		    			alert(text);
	    			}
	    			moveToRightTable(line, data);
	    		}else {
    				alert(data.appOut);
    			}
	    	}else{
		    	moveToRightTable(line,data);
	    	}
	    	
	    	hideLoading();
    	},
	    error: function (data){
	    	alert($("#errorPost").html());
	    	hideLoading();
    	}
		});
}


//Schedule session ------  END


//Load YourContent session ----- START
function loadContent(){
	subheaders = {};
	queuedItems = [];
	dates = [];
	ajaxCalled = 0;
	$("#scheduledTable").hide();
	$("#avaliableTable tr").remove();
	$("#avaliableTable p").remove();
	$("#scheduledTable tr.item").remove();
	showLoading();
	
	var item = $('#selectType :selected').val(); 
	  var urlsToCall = [];
	  urlsToCall.push(urlQueuedCommunities);
	  urlsToCall.push(urlQueuedBlogs);
	  urlsToCall.push(urlQueuedWikis);
	  urlsToCall.push(urlQueuedActivities);
	  if (item == "community" || item == "all"){
		  urlsToCall.push(urlCommunities);
	  }
	  if (item == "blog" || item == "all"){
		  urlsToCall.push(urlBlogs);
	  }
	  if (item == "wiki" || item == "all"){
		  urlsToCall.push(urlWikis);
	  }
	  if (item == "activity" || item == "all"){
		  urlsToCall.push(urlActivities);
	  }
	  callAPI(urlsToCall);
}

function callAPI(urlsToCall) {
	listAppOut = [];
	ajaxCalled = urlsToCall.length;
	while ( urlsToCall.length > 0){
		var url = urlsToCall.pop();
		$.getJSON(url, {
			format : "json"
		})
		.done(
			function(data) {
				var type = "";
				var content = "";
				var message = data.appOut;
				if (message != null && "" != message) {
					listAppOut.push(message);
				}else{
					content = data.blogs;
					if (content) {
						type = "Blog";
					} else {
						content = data.communities;
						if (content) {
							type = "Community";
						} else {
							content = data.wikis;
							if (content) {
								type = "Wiki";
							} else {
								content = data.activities;
								if (content) {
									type = "Activity";
								}else {
									content = data.timeout;
									if (content) {
									    $("#timeoutRedirect").submit();
									}
								}
							}
						}
					}
				}
				ajaxCalled--;
				if(data.isOnQueue){
					processQueuedContent(content, type);
				}else{
					processContent(content, type);
				}
				updateTable();
			}
		);
	}
}

function mountAvaliableItemLine(item, type){
	return ' <tr class="item"'
	+ ' name ="' + encodeURI(item.title) + '" '
	+ ' itemType ="' + type + '" '
	+ ' date ="' + item.creationDate + '" '
	+ ' lastUpdate ="' + item.lastUpdate + '" '
	+ ' id  ="' + item.uuid + '" '
			+'>'
			+ '<td  style="width: 262px !important;"><a href="'
			+ item.url
			+ '" target="_black">'
			+ item.title.replace(/>/g, "&gt;").replace(/</g,"&lt;")
			+ '</a></td>'
			+ '<td style="width: 55px !important;font-size:11px !important;">'+ type + '</td>' 
			+ '<td style="width: 65px !important;font-size:11px !important;">'+ item.lastUpdate + '</td>' 
		+'</tr>';
}

function processContent(availableContent, type){
	if (listAppOut.length == 0){
		$(availableContent).each(
				function(i, item) {
					hasContent = true;
					if (item.creationDate == undefined){
						item.creationDate == "9999";
					}
					var tr = item.creationDate+item.title+"<sortTag>";
					
					tr = tr+mountAvaliableItemLine(item, type);
					var date = item.creationDate
							.substring(0, 4);
					if (subheaders[date]) {
						var trs = subheaders[date];
						trs.push(tr);
						subheaders[date] = trs;
					} else {
						dates.push(date);
						var trs = [];
						trs.push(tr);
						subheaders[date] = trs;
					}
				}
			);
	}
}
function processQueuedContent(queuedContent, type){
	if (listAppOut.length == 0){
		$(queuedContent).each(
				function(i, item) {
					if (item.creationDate == undefined){
						item.creationDate == "9999";
					}
					var tr = mountQueuedItemLine(item);
					findPositionInTable(tr, item);
				}
		);
		init();
	}
}

function updateTable(){
	if(ajaxCalled == 0){
	  if (listAppOut.length > 0 || dates.length < 1){
		  $("#avaliableTable").append($("#noContent").html());
		  if (listAppOut.length > 0){
			  $("#appsOut").html(listAppOut.toString());
			  $("#overlay").fadeIn(500);
			  $("#msgBox").fadeIn(500);
		  }
	  } else {
		  dates.sort();
		  dates.reverse();
		  for (var i in dates){
			  var key = dates[i];
			  if (i != undefined){
				  if(key != 9999){
					  $("#avaliableTable").append("<tr date='"+key+"-12-31T23:59:59Z\"><th colspan=\"3\">"+key+"</th></tr>");
				  }
				  var trs = subheaders[key];
				  trs.sort();
				  trs.reverse();
				  for(n in trs){
					  var t = trs[n];
					  t = t.substring(t.lastIndexOf("<sortTag>")+9);
					  $("#avaliableTable").append(t);
				  }
			  }
		  }
		  
		  init();
		  $("#scheduledTable").scrollTop(0);
		  $("#avaliableTable").scrollTop(0);
  		
	  }
	  
	}
}

function updateScheduledTable(){
	$("#scheduledTable").append(queuedItems);
	for (var i = 0; i < queuedItems.length; i++){
		$("#scheduledTable").append(queuedItems[i]);
		var newItem = $("#scheduledTable:last-child");
		newItem.remove();
		findPositionInTable(queuedItems[i], newItem);
	}
}

function inicializeToopTip() {
        $(".tip_trigger").hover(function(){
                $(".tip").hide();
                var p = $(this).position();
                tip = $(this).parent().find('.tip');
                tip.css({ top: p.top+28, left: p.left-20 });
                tip.show(); 
        });
        $(".tip").mouseleave(function(){
                $(this).hide();
        });
}
