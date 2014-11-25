$(document).ready(function() {

	getSampleData2();
});

function renderSampleData(fillArr, dataArr) 
{
	var map = new Datamap(
			{
				element : document.getElementById('container'),
				geographyConfig : {
					// popupOnHover: false,
					highlightOnHover : false,
					popupTemplate : function(geo, data) {
						return [
						        '<div class="hoverinfo"><strong>',
						        'Jobs in ' + geo.properties.name,
						        ': ' + data.jobs, '</strong></div>' ]
						.join('');
					}
				},
				fills : fillArr,
				data : dataArr
			});
}

function getSampleData2() {
	var fillArr = {'defaultFill': '#EDDC4E'};
	var dataArr = {};
	for (var i in countryName) {
		dataArr[countryName[i]] = { 'jobs' : 0, 'fillKey' : 'defaultFill' };
	}
	var data = [{"name":"","value":2},{"name":"Angola","value":36},{"name":"Antigua And Barbuda","value":2},{"name":"Argentina","value":91271},{"name":"Australia","value":19},{"name":"Barbados","value":16},{"name":"Belgium","value":9},{"name":"Bolivia","value":92},{"name":"Brazil","value":458},{"name":"Cameroon","value":1},{"name":"Canada","value":49},{"name":"Chile","value":617},{"name":"Colombia","value":370251},{"name":"Costa Rica","value":40},{"name":"Cote d Ivoire (Ivory Coast)","value":14},{"name":"Cuba","value":5},{"name":"Denmark","value":4},{"name":"Dominican Republic","value":497},{"name":"Ecuador","value":847},{"name":"El Salvador","value":167},{"name":"France","value":85},{"name":"Germany","value":50},{"name":"Guatemala","value":29},{"name":"Haiti","value":1},{"name":"Honduras","value":10366},{"name":"Hong Kong","value":1},{"name":"India","value":2},{"name":"Indonesia","value":19},{"name":"Italy","value":185},{"name":"Malawi","value":1},{"name":"Malaysia","value":4},{"name":"Mexico","value":460156},{"name":"Myanmar","value":1},{"name":"Netherlands","value":1},{"name":"Nicaragua","value":12},{"name":"Norway","value":1},{"name":"Panama","value":99},{"name":"Paraguay","value":16},{"name":"People s Republic of China","value":10},{"name":"Peru","value":269161},{"name":"Philippines","value":16},{"name":"Portugal","value":57},{"name":"Qatar","value":19},{"name":"Romania","value":4},{"name":"Russia","value":742},{"name":"Saint Martin","value":42},{"name":"Saudi Arabia","value":1},{"name":"Singapore","value":1},{"name":"Spain","value":1573},{"name":"Switzerland","value":3},{"name":"Uganda","value":1},{"name":"United Arab Emirates","value":16},{"name":"United Kingdom","value":22},{"name":"United States","value":3297},{"name":"Uruguay","value":55},{"name":"Venezuela","value":29146},{"name":"Vietnam","value":13}];
	for(var i in data) {
		var obj = data[i];
		var k = countryName[obj['name']];
		fillArr[k] = '#9467bd'; //getRandomColor();
		dataArr[k] = { 'fillKey' : k, 'jobs' : obj['value'] }; 
	}
	console.log(fillArr);
	console.log(dataArr);
	renderSampleData(fillArr, dataArr);
}


function getSampleData1() {
	var fillArr = {'defaultFill': '#EDDC4E'};
	var dataArr = {};
	for (var i in countryName) {
		dataArr[countryName[i]] = { 'jobs' : 0, 'fillKey' : 'defaultFill' };
	}

	$.ajax({
		url: 'http://localhost:1111/jobs/api/controller/sample1',
		type : 'GET',
		dataType: 'json',
		success: function(data){
			for(var i in data) {
				var obj = data[i];
				var k = countryName[obj['name']];
				fillArr[k] = getRandomColor();
				dataArr[k] = { 'fillKey' : k, 'jobs' : obj['value'] }; 
			}
			console.log(fillArr);
			console.log(dataArr);
			renderSampleData(fillArr, dataArr);

		}
	});
};


function getRandomColor() {
	var letters = '0123456789ABCDEF'.split('');
	var color = '#';
	for (var i = 0; i < 6; i++ ) {
		color += letters[Math.floor(Math.random() * 16)];
	}
	return color;
}