$(document).ready(function() {

	getSampleData2();

});
 
function renderSampleData(fillArr, dataArr, bombarray ) 
{
 		 d3.select("#container").remove();
 	 d3.select("#bargraph").remove();

	 	 d3.select("body")
					 .append("div")
					 .attr("id", "container")
					 .style("position","relative")
					 .style("width","1200px")
					 .style("height","500px");
	
 			
			var bubblesarray = [];
 
for(var i in bombarray) {

    var jobobject  = bombarray[i];

   bubblesarray.push({ 
   		  radius : 3,
		  fillKey: jobobject.countryCode,
          latitude  : jobobject.latitude,
          longitude   : jobobject.longitude,
		  countryCode : jobobject.countryCode,
		  address : jobobject.address,
          borderWidth: 0.5,
          borderColor: 'black',
    });
}
		 
	var map = new Datamap(
			{
				element : document.getElementById('container'),
				geographyConfig : {
					  
					popupTemplate : function(geo, data) {
						return [
						        '<div class="hoverinfo"><strong>',
						        'Job Count ' + geo.properties.name,
						        ': ' + data.jobs, '</strong></div>' ]
						.join('');
					}
				},
				  fills: {
						  "AR" : '#FF0000', "BR" : '#00FFCC', "CL" : '#7FFF00', "CO" : '#FFFF00',"CR" : '#FF00FF', "EC" : '#F4A460',  "ES" : '#A52A2A', "HN" : '#DC143C',
						 "MX" : '#00FF00', "PE" : '#000099',  "RU" : '#FF0000', "US" : '#A52A2A', "VE" : '#8B008B', "FR" : '#FF0000', "AE" : '#7FFF00', "AG" : '#F4A460',		
						 "AO" : '#000099', "AU" : '#FFFF00', "BB" : '#8B008B', "BE" : '#00FFCC',"BO" : '#00FF7F', "CA" : '#7FFF00', "CH" : '#F4A460', "CI" : '#000099',
						 "CM" : '#A52A2A', "CN" : '#8B008B', "CU" : '#00FFCC', "DE" : '#A52A2A',"DK" : '#7FFF00', "DO" : '#F4A460',  "GB" : '#000099', "GT" : '#A52A2A',
						 "HK" : '#8B008B', "HT" : '#00FFCC', "ID" : '#00FF7F', "IN" : '#FF0000',"IT" : '#7FFF00', "MF" : '#F4A460', "MM" : '#000099',  "MY" : '#A52A2A',
						 "NI" : '#8B008B', "NL" : '#00FFCC', "NO" : '#00FF7F', "PA" : '#7FFF00',"PH" : '#FFFF00', "PT" : '#000099', "PY" : '#A52A2A', "QA" : '#8B008B',
						 "RO" : '#7FFF00', "SA" : '#F4A460', "SG" : '#000099', "SV" : '#A52A2A',"UG" : '#8B008B', "UY" : '#00FFCC',  "VN" :'#7FFF00' ,   defaultFill: '#D0D4D7'
   				 },
				data : dataArr
			});

 map.bubbles( bubblesarray , {
    popupTemplate: function (geo, data) { 
            return ['<div class="hoverinfo">'  +
            '<br/>Latitude: ' +  data.latitude + 
            '<br/>Longitude: ' +  data.longitude +
			'<br/>Address: ' +  data.address +'',
             '</div>'].join('');
    }
});
 
}

function getSampleData2(  ) {
	 
	var fillArr = {'defaultFill': '#807E7F'};
	var dataArr = {};
 	
	for (var i in countryName) {
		dataArr[countryName[i]] = { 'jobs' : 0, 'fillKey' : 'defaultFill' };
	}
	var data = [
 	
{"name":"Afghanistan","value":17},
{"name":"Algeria","value":1},
{"name":"Argentina","value":1},
{"name":"Armenia","value":1},
{"name":"Australia","value":365},
{"name":"Azerbaijan","value":1},
{"name":"Bahrain","value":119},
{"name":"Bangladesh","value":56},
{"name":"Barbados","value":2},
{"name":"Belarus","value":2},
{"name":"Belgium","value":13},
{"name":"Bhutan","value":3},
{"name":"Botswana","value":5},
{"name":"Brasil","value":7},
{"name":"Bulgaria","value":1},
{"name":"Cambodia","value":3},
{"name":"Canada","value":615},
{"name":"China","value":10},
{"name":"Cittàdelvaticano","value":1},
{"name":"Congo","value":1},
{"name":"Cuba","value":1},
{"name":"Czech Republic","value":1},
{"name":"Denmark","value":5},
{"name":"Germany","value":133},
{"name":"Dominican Republic","value":1},
{"name":"Spain","value":31},
{"name":"Ethiopia","value":15},
{"name":"Fiji","value":3},
{"name":"France","value":60},
{"name":"Gabon","value":1},
{"name":"Gambia","value":2},
{"name":"Ghana","value":3},
{"name":"Greece","value":3},
{"name":"Guadeloupe","value":4},
{"name":"Hong Kong","value":82},
{"name":"India","value":44006},
{"name":"Indonesia","value":20},
{"name":"Iraq","value":4},
{"name":"Italy","value":23},
{"name":"Jamaica","value":6},
{"name":"Jordan","value":1},
{"name":"Kazakhstan","value":2},
{"name":"Kenya","value":58},
{"name":"Kosovo","value":1},
{"name":"Kyrgyzstan","value":1},
{"name":"Lebanon","value":1},
{"name":"Lithuania","value":1},
{"name":"Malawi","value":5},
{"name":"Malaysia","value":60},
{"name":"Maldives","value":10},
{"name":"Malta","value":1},
{"name":"Mauritius","value":3},
{"name":"Mexico","value":12},
{"name":"Mozambique","value":2},
{"name":"Myanmar","value":2},
{"name":"Namibia","value":1},
{"name":"Netherlands","value":19},
{"name":"Nepal","value":110},
{"name":"New Zealand","value":19},
{"name":"Nicaragua","value":1},
{"name":"Nigeria","value":32},
{"name":"Norway","value":27},
{"name":"Oman","value":125},
{"name":"Austria","value":3},
{"name":"Pakistan","value":766},
{"name":"Peru","value":1},
{"name":"Poland","value":13},
{"name":"Portugal","value":3},
{"name":"Philippines","value":16},
{"name":"Romania","value":6},
{"name":"Morocco","value":3},
{"name":"Russia","value":9},
{"name":"Saintlucia","value":2},
{"name":"Switzerland","value":109},
{"name":"Serbia","value":1},
{"name":"Seychelles","value":1},
{"name":"Sierra Leone","value":5},
{"name":"Singapore","value":180},
{"name":"Slovakia","value":1},
{"name":"South Africa","value":59},
{"name":"Sri Lanka","value":31},
{"name":"Suomi","value":10},
{"name":"Sweden","value":26},
{"name":"Hong Kong","value":3},
{"name":"Tajikistan","value":1},
{"name":"Tanzania","value":8},
{"name":"Tanzania","value":1},
{"name":"Thedemocraticrepublicofthe","value":7},
{"name":"Togo","value":1},
{"name":"Tunisia","value":2},
{"name":"Turkey","value":5},
{"name":"Uganda","value":2},
{"name":"Ukraine","value":5},
{"name":"United Kingdom","value":920},
{"name":"Unitedrepublicof","value":13},
{"name":"United States","value":2554},
{"name":"Uruguay","value":1},
{"name":"Uzbekistan","value":1},
{"name":"Vietnam","value":1},
{"name":"Zambia","value":6},
{"name":"Ireland","value":15 }
  	
	];
	for(var i in data) {
		var obj = data[i];
		var k = countryName[obj['name']];
		fillArr[k] = '#807E7F' ; //getColor2(obj['value']);//'#9467bd'; //getRandomColor();
		dataArr[k] = { 'fillKey' : k, 'jobs' : obj['value'] }; 
	}
 	renderSampleData(fillArr, dataArr);
}
 
function getSampleData1( companyname , jobtype ) {
	var fillArr = {'defaultFill': '#807E7F'};
	var dataArr = {};
	var bombarray = {};
	for (var i in countryName) {
		dataArr[countryName[i]] = { 'jobs' : 0 };
	}
	 
		$.ajax({
			//url: 'http://localhost:8080/jobs/api/controller/sample1',
	//		url: 'http://localhost:8080/jobs/api/controller/sample2/India',
			url: 'http://localhost:8080/jobs/api/controller/sample2/' + companyname+'/'+jobtype,
			type : 'GET',
			dataType: 'json',
			
			success: function(data){
				   
						for(var i in data) {
							var obj = data[i];
							 
							bombarray[i] = { 'countryCode' : obj['countryCode'] ,  'latitude' : obj['latitude'] , 
									'longitude' : obj['longitude'] , 'address' : obj['address'] };
						}
						renderSampleData(fillArr, dataArr , bombarray);
					}
		});
}
 
