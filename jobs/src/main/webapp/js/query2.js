window.apiPath = window.location.protocol + "//" + window.location.host + "/jobs/api/controller/";
	
	$(document).ready(function(){
		
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
					
					
					/*
					var data2012 = [];
					for(var x in data['2012']) {
						data2012.push({'location' : data['2012'][x]});
					}
					var data2013 = [];
					for(var x in data['2013']) {
						data2013.push({'location' : data['2013'][x]});
					}
					console.log(data2012.length);
					console.log(data2013.length);
					*/
					//renderJobPoints(data2012, data2013, fillArr);
					renderJobPoints(mapPoints, fillArr);
				}
			});
		});
		
		$('#btnSearchCompanyGrowthChart').bind('click', function(event){
			//alert('came here');
			$.ajax({
				url : window.apiPath + 'getCompanyGrowthOverTime',
				type : 'POST',
				datatype : 'json',
				data : {'term' : $( "#serachCompany").val() },
				success : function(data) {
					
					
					var d1 = {
							name : data['datasets'][0]['label'],
							label : data['datasets'][0]['label'],
							fillColor: "#4f99b4",
							lineColor: "#4f99b4",
				            /*strokeColor: "rgba(220,220,220,0.8)",
				            highlightFill: "rgba(220,220,220,0.75)",
				            highlightStroke: "rgba(220,220,220,1)",*/
				            data : data['datasets'][0]['data']
					};
					var d2 = {
							name : data['datasets'][1]['label'],
							label : data['datasets'][1]['label'],
							fillColor: "#d67777",
							lineColor: "#d67777",
							/*strokeColor: "rgba(151,187,205,0.8)",
							highlightFill: "rgba(151,187,205,0.75)",
							highlightStroke: "rgba(151,187,205,1)",*/
							data : data['datasets'][1]['data']
					};
					
					var options = {
							showTooltips: true,
							legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].lineColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"
					};
					$("#myChart").html('');
					var ctx = document.getElementById("myChart").getContext("2d");
					var myBarChart = new Chart(ctx).Bar({'labels' : data['labels'], 'datasets' : [d1, d2]}, options);
					
					
/*					
					var jobs = [];
					for(var key in data){
						jobs.push(data[key]);
					}
					console.log(data);
					
					var results = function(){ 
						return  jobs;
					};
					console.log(results());
					nv.addGraph(function() {
					    var chart = nv.models.multiBarHorizontalChart()
					        .x(function(d) { return d.label})
					        .y(function(d) { return d.value})
					        .margin({top: 30, right: 20, bottom: 50, left: 175})
					        .showValues(true)           //Show bar value next to each bar.
					        .tooltips(true)             //Show tooltips on hover.
					        .transitionDuration(350)
					        .showControls(true);        //Allow user to switch between "Grouped" and "Stacked" mode.

					    chart.yAxis
					        .tickFormat(d3.format(',.2f'));

					    d3.select('#chart1 svg')
					        .datum(results())
					        .call(chart);

					    nv.utils.windowResize(chart.update);
					    return chart;
					});
					*/
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
		//console.log(mapPoints);
		map.bubbles(mapPoints, {
			popupOnHover: false,
	        highlightOnHover: false
		});
		/*
		var width = 1200;
		var height = 500;			 
		var projection = d3.geo.equirectangular()
		      .scale(153)
		      .translate([width / 2, height / 2])
		      .precision(.1);
			 
		 var map = new Datamap(
		{
			element : document.getElementById('container'),
			geographyConfig : {
				popupOnHover: true,
				highlightOnHover : true,
				popupTemplate : function(geo, data) {
					return [
					        '<div class="hoverinfo"><strong>',
					         + geo.properties.name, '</strong></div>' ]
					.join('');
				},
				projection: ''
			},
			setProjection: function(element) {
	          var projection = d3.geo.equirectangular()
			      .scale(153)
			      .translate([width / 2, height / 2])
			      .precision(.1);
	          var path = d3.geo.path()
	            .projection(projection);
	          
	          return {path: path, projection: projection};
	        },
			fills : fillArr,
			data : {}
		}); 
		
	   var svg = d3.select("#container svg");
	  
	   svg.selectAll("circle")
		  .data(data2012)
		  .enter().append("circle")
		  .attr("r", 1.5)
		  .style("fill", "red")
		  .attr("transform", function(d) {
		    return "translate(" + projection([
		      d.location.longitude,
		      d.location.latitude
		    ]) + ")";
		  }); 
	   
	   svg.selectAll("circle")
		  .data(data2013)
		  .enter().append("circle")
		  .attr("r", 1.5)
		  .style("fill", "blue")
		  .attr("transform", function(d) {
		    return "translate(" + projection([
		      d.location.longitude,
		      d.location.latitude
		    ]) + ")";
		  }); 
		  */
	}
	
function getRandomColor() {
	var letters = '0123456789ABCDEF'.split('');
	var color = '#';
	for (var i = 0; i < 6; i++ ) {
		color += letters[Math.floor(Math.random() * 16)];
	}
	return color;
}
	
	// chart
	
	

		
	
	