$(document).ready(function () {
    var admisi,
    	respinsi;
    
    function drawChart() {
	var options = {
		title: 'Rata studentilor admisi/respinsi',
		width: 320,
		height: 536,
		'legend':'bottom',
		backgroundColor: '#EBF5FB',
		colors: ['#008000', '#800000']
	};
        var data = google.visualization.arrayToDataTable([
          ['Rezultat', 'Numar studenti'],
          ["Admis", admisi],
          ["Respins", respinsi]]);
        var chart = new google.visualization.PieChart(document.getElementById('piechart'));
        chart.draw(data, options);
    }
    
    function fillTable(specializare) {
	$.ajax({
	    type: "POST",
	    url: "ResultsPage",
	    data: {
	    	fillresults: "ok",
	    	specializarecombobox: specializare
	    },
	    success: function(response){	
		// Removing table rows
                $("#resultstable tbody tr").each(function(){
                    this.parentNode.removeChild(this); 
                });
                
                // Filling table
	        $("#resultstable > tbody:last-child").append(response.trim().split("#")[0]);
	        
	        // Drawing pie chart
	        admisi = parseInt(response.trim().split("#")[1]);
	        respinsi = parseInt(response.trim().split("#")[2]);
	        
	        google.charts.load('current', {'packages':['corechart']});
	        google.charts.setOnLoadCallback(drawChart);
	    }
	});
    }

    // Filling results table
    // Drawing the pie chart
    fillTable("");
    
    $("#pdfexport").click(function () {
	event.preventDefault();
	var nume = [];
	var medie = [];
	var specs = [];
	var formaF= [];
	var rez = [];
        
	let header = true;
        $('#resultstable tr').each(function() {
            // Skipping first step (table header)
            if (header) {
        	header = false;
            }
            else {
        	nume.push($(this).find('td:eq(1)').html());
        	medie.push($(this).find('td:eq(2)').html());
        	specs.push($(this).find('td:eq(3)').html());
        	formaF.push($(this).find('td:eq(4)').html());
        	rez.push($(this).find('td:eq(5)').html());  
            }
        });
        
        var JSONNume = JSON.stringify(nume);
        var JSONMedie = JSON.stringify(medie);
        var JSONSpecializare = JSON.stringify(specs);
        var JSONFormaFinantare = JSON.stringify(formaF);
        var JSONRezultat = JSON.stringify(rez);
        
            
        $.ajax({
            type: "POST",
            url: "ResultsPage",
            data: {
            	pdfexport: "ok",
            	numecomplet: JSONNume,
            	medieconcurs: JSONMedie,
            	specializari: JSONSpecializare,
            	bugettaxa: JSONFormaFinantare,
            	rezultat: JSONRezultat
            },
            success: function(response) {
        	window.open("pdf_download.jsp");
            }
        });
    });
    
    $("#xlsexport").click(function () {
	event.preventDefault();
	var nume = [];
	var medie = [];
	var specs = [];
	var formaF= [];
	var rez = [];
        
	let header = true;
        $('#resultstable tr').each(function() {
            // Skipping first step (table header)
            if (header) {
        	header = false;
            }
            else {
        	nume.push($(this).find('td:eq(1)').html());
        	medie.push($(this).find('td:eq(2)').html());
        	specs.push($(this).find('td:eq(3)').html());
        	formaF.push($(this).find('td:eq(4)').html());
        	rez.push($(this).find('td:eq(5)').html());  
            }
        });
        
        var JSONNume = JSON.stringify(nume);
        var JSONMedie = JSON.stringify(medie);
        var JSONSpecializare = JSON.stringify(specs);
        var JSONFormaFinantare = JSON.stringify(formaF);
        var JSONRezultat = JSON.stringify(rez);
        
            
        $.ajax({
            type: "POST",
            url: "ResultsPage",
            data: {
            	xlsexport: "ok",
            	numecomplet: JSONNume,
            	medieconcurs: JSONMedie,
            	specializari: JSONSpecializare,
            	bugettaxa: JSONFormaFinantare,
            	rezultat: JSONRezultat
            },
            success: function(response) {
        	window.open("xls_download.jsp");
            }
        });
    });
    
    $("#combobox").change(function () {
	var den_s = $("#combobox").val();
		
	if (den_s == "Selectati o specializare") {
	    den_s = "";
	}
	
	// Fill logs table
	// Drawing the pie chart
	fillTable(den_s);
    });
});