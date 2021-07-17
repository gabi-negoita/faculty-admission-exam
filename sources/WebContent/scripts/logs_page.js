$(document).ready(function () {    
    function fillLogsTable(keyword, rol, actiune, data) {
	if (rol == "Selectati un rol") {
	    rol = "";
	}
	    
	if (actiune == "Selectati o actiune") {
	    actiune = "";
	}
	    
	// Filling logs table
        $.ajax({
            type: "POST",
            url: "LogsPage",
            data: { 
    	    filllogs: "ok",
    	    searchfield: keyword,
    	    rolcombobox: rol,
    	    actiunecombobox: actiune,
    	    date: data
        	},
            success: function(response){                
                // Removing table rows
                $("#logstable tbody tr").each(function(){
                    this.parentNode.removeChild(this); 
                });
                
                // Filling table
                $("#logstable > tbody:last-child").append(response);
                
                // Setting results found text
                if (response.trim().length == 0){
            	    $("#resultslabel").html("<i>Rezultate gasite: <b>0</b><i>");
                } else {
            	    $("#resultslabel").html("<i>Rezultate gasite: <b>" + ($("table#logstable tr:last").index() + 1) + "</b><i>");
                }
            }
        });
    }
    
    var keyword = $("#searchfield").val(),
	rol = $("#rolcombobox").val(),
	actiune = $("#actiunecombobox").val(),
	date = $("#data").val();     
    
    // Fill logs table on page load
    fillLogsTable(keyword, rol, actiune, date);
    
    // Preventing form submition by pressing ENTER key (code 13)
    $("#searchfield").on('keypress',function(e) {
        if(e.which == 13) {
            e.preventDefault();
            return;
        }
    });
    
    // Search field key up 
    $("#searchfield").on('keyup',function(e) {
	var keyword = $("#searchfield").val(),
	    rol = $("#rolcombobox").val(),
	    actiune = $("#actiunecombobox").val(),
	    date = $("#data").val();    
	
	// Fill logs table
	fillLogsTable(keyword, rol, actiune, date);
    });
    
    // Rol combobox change value
    $("#rolcombobox").change(function () {
	var keyword = $("#searchfield").val(),
	    rol = $("#rolcombobox").val(),
	    actiune = $("#actiunecombobox").val(),
	    date = $("#data").val();    
		
	// Fill logs table
	fillLogsTable(keyword, rol, actiune, date);
    });
    
    // Actiune combobox change value
    $("#actiunecombobox").change(function () {
	var keyword = $("#searchfield").val(),
	    rol = $("#rolcombobox").val(),
	    actiune = $("#actiunecombobox").val(),
	    date = $("#data").val();    
		
	// Fill logs table
	fillLogsTable(keyword, rol, actiune, date);
    });
    
    // Date change value
    $("#data").change(function () {
	var keyword = $("#searchfield").val(),
	    rol = $("#rolcombobox").val(),
	    actiune = $("#actiunecombobox").val(),
	    date = $("#data").val();    
		
	// Fill logs table
	fillLogsTable(keyword, rol, actiune, date);
    });
    
    
    
    
});