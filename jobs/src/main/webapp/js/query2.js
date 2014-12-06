window.apiPath = window.location.protocol + "//" + window.location.host + "/jobs/api/controller/";
	
	$(document).ready(function(){
		
		$("#legend").css("display", "none");
		$("#h3").css("display", "none");
		$("#serachCompany").autocomplete({
	      source: window.apiPath + 'serachCompany',
	      minLength: 3,
	      select: function( event, ui ) {
	    	  console.log(ui.item);
	    	  $( "#serachCompany").val(ui.item);    
	      }
	    });
		
		$('#btnSearchCompanyGrowth').bind('click', function(event){
			$.ajax({
				url : window.apiPath + 'getCompanyGrowthOverTimePoints',
				type : 'POST',
				datatype : 'json',
				data : {'term' : $( "#serachCompany").val() },
				success : function(data) {
					$("#legend").css("display", "block");
					var mapPoints = [];
					
					var fillArr = {'defaultFill': '#EDDC4E', '2012' : "#d67777", '2013': "#4f99b4"};		
					var dataArr = {};
					
					
					for (var i in countryName) {
						dataArr[countryName[i]] = { 'jobs' : 0, 'fillKey' : 'defaultFill' };
					}
					for(var x in data['2012']) {
						var o2 = data['2012'][x];
						var cCode = countryName[o2['country']];
						if(fillArr[cCode] == undefined) {
							fillArr[cCode] = getRandomColor();
						}
						o2['radius'] = 3;
						o2['borderWidth'] = 0.3;
						o2['borderColor'] = '#FFFFFF';
						o2['fillKey'] = '2012';
						o2['country'] = cCode;
						mapPoints.push(o2);
					}
					for(var x in data['2013']) {
						var o2 = data['2013'][x];
						var cCode = countryName[o2['country']];
						if(fillArr[cCode] == undefined) {
							fillArr[cCode] = getRandomColor();
						}
						o2['radius'] = 3;
						o2['borderWidth'] = 0.3;
						o2['borderColor'] = '#FFFFFF';
						o2['fillKey'] = '2013';
						o2['country'] = cCode;
						mapPoints.push(o2);
					}
					
					renderJobPoints(mapPoints, fillArr);
				}
			});
		});
		
		$('#btnSearchCompanyGrowth').bind('click', function(event){
			$.ajax({
				url : window.apiPath + 'getCompanyGrowthOverTime',
				type : 'POST',
				datatype : 'json',
				data : {'term' : $( "#serachCompany").val() },
				success : function(data) {
					
					$("#legend").css("display", "block");
					$("#h3").css("display", "block");
					var d1 = {
							name : data['datasets'][0]['label'],
							label : data['datasets'][0]['label'],
							fillColor: "#4f99b4",
							lineColor: "#4f99b4",
				            data : data['datasets'][0]['data']
					};
					var d2 = {
							name : data['datasets'][1]['label'],
							label : data['datasets'][1]['label'],
							fillColor: "#d67777",
							lineColor: "#d67777",
							data : data['datasets'][1]['data']
					};
					
					var options = {
							showTooltips: true,
							legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].lineColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"
					};
					
					var data1 = [];
					console.log(data['differences']);
					for(var key in data['differences']) {
						var p = {
								value: data['differences'][key],
								color: getRandomColor(),
				                highlight: "#FF5A5E",
				                label: key
						};
						data1.push(p);
					}
					console.log(data1);
					$("#myChart").html('');
					var ctx = document.getElementById("myChart").getContext("2d");
					var myBarChart = new Chart(ctx).Bar({'labels' : data['labels'], 'datasets' : [d1, d2]}, options);
					$("#pieChart").html('');
					var ctx = document.getElementById("pieChart").getContext("2d");
					var myPieChart = new Chart(ctx).Pie(data1,options);
				}
			});
		});
		
	});
	
function renderJobPoints(mapPoints, fillArr) {
		
		$('#graph').html('<div id="container" style="position: relative; width: 1200px; height: 500px;"></div>');

		var map = new Datamap(
		{
			element : document.getElementById('container'),
			geographyConfig : {
				// popupOnHover: false,
				highlightOnHover : false,
				popupTemplate : function(geo, data) {
					return [
					        '<div class="hoverinfo"><strong>',
					        'Jobs in ' + geo.properties.name, '</strong></div>' ]
					.join('');
				}
			},
			fills : fillArr,
			data : {}
		});
		map.bubbles(mapPoints, {
			popupOnHover: false,
	        highlightOnHover: false
		});
	}
	
function getRandomColor() {
	var letters = '0123456789ABCDEF'.split('');
	var color = '#';
	for (var i = 0; i < 6; i++ ) {
		color += letters[Math.floor(Math.random() * 16)];
	}
	return color;
}
	
	// pie chart
$('#btnSearchCompanyGrowth').bind('click', function(event){
	
});

	
	

		
	
	