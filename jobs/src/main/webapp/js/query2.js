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
				url : window.apiPath + 'getJobPointsForCountry',
				type : 'POST',
				datatype : 'json',
				data : {'term' : $( "#serachCompany").val() },
				success : function(data) {
					var newArr = [];
					var fillArr = {'defaultFill': '#EDDC4E', 'point' : '#E80C7A'};
					
					for(var i in data) {
						var obj = data[i];
						 if (fillArr[obj['country']] == undefined) {
							fillArr[obj['country']] = getRandomColor();
						}
						
						 /*newArr.push({
					        'radius': 3,					        
					        'fillKey': obj['country'],
					        'latitude': obj['latitude'],
					        'longitude': obj['longitude']
					      }); */ 
						newArr.push({ 'location' : data[i]});
					}
					console.log(newArr);
					renderJobPoints(newArr, fillArr);
				}
			});
		});
		
	});
	
function renderJobPoints(places, fillArr) {
		
		$('#graph').html('<div id="container" style="position: relative; width: 1200px; height: 500px;"></div>');

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
				popupOnHover: false,
				highlightOnHover : true,
				popupTemplate : function(geo, data) {
					return [
					        '<div class="hoverinfo"><strong>',
					        'Jobs in ' + geo.properties.name,
					        ': ' + data.jobs, '</strong></div>' ]
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
		  .data(places)
		  .enter().append("circle")
		  .attr("r", 2)
		  .style("fill", "blue")
		  .attr("transform", function(d) {
		    return "translate(" + projection([
		      d.location.longitude,
		      d.location.latitude
		    ]) + ")";
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