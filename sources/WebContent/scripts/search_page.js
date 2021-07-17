$(document).ready(function () {    
    // Preloading table header
    var header = "";
    header += "<th>";
    header += "#";
    header += "</th>";

    header += "<th>";
    header += "NUME";
    header += "</th>";
    
    header += "<th>";
    header += "LICEU ABSOLVIT";
    header += "</th>";
    
    header += "<th>";
    header += "MEDIE LICEU";
    header += "</th>";
    
    header += "<th>";
    header += "MEDIE BACALAUREAT";
    header += "</th>";

    header += "</tr>";

    $("#resultstable > thead").html(header);
    
    function searchCandidat(keyword, liceu) {
	$.ajax({
	    type: "GET",
	    url: "SearchPage",
	    data: {
	    	searchforcandidat: keyword, liceucombobox: liceu},
	    success: function(response){
	    	// Removing table rows
		$("#resultstable tbody tr").each(function(){
		    this.parentNode.removeChild(this); 
		});
			
		// Adding rows
		$("#resultstable > tbody:last-child").append(response.trim());

	        // Setting results found text
	        if (response.trim().length == 0){
	            $("#resultslabel").html("<i>Rezultate gasite: <b>0</b><i>");
	        } else {
	            $("#resultslabel").html("<i>Rezultate gasite: <b>" + ($("table#resultstable tr:last").index() + 1) + "</b><i>");
	        }
	    }
	});
    }
    
    function searchUniversitate(keyword) {
	$.ajax({
	    type: "GET",
	    url: "SearchPage",
            data: {searchforuniversitate: keyword},
            success: function(response){
        	// Removing table rows
		$("#resultstable tbody tr").each(function(){
		    this.parentNode.removeChild(this); 
		});
    		    
                // Adding rows
                $("#resultstable > tbody:last-child").append(response.trim());
                
                // Setting results found text
                if (response.trim().length == 0){
                    $("#resultslabel").html("<i>Rezultate gasite: <b>0</b><i>");
                } else {
                    $("#resultslabel").html("<i>Rezultate gasite: <b>" + ($("table#resultstable tr:last").index() + 1) + "</b><i>");
                }
            }
        });
    }
    
    function searchFacultate(keyword) {
	$.ajax({
	    type: "GET",
	    url: "SearchPage",
            data: {searchforfacultate: keyword},
            success: function(response){
        	// Removing table rows
        	$("#resultstable tbody tr").each(function(){
                    this.parentNode.removeChild(this); 
                });
                // Adding rows
                $("#resultstable > tbody:last-child").append(response.trim());
                
                // Setting results found text
                if (response.trim().length == 0){
                    $("#resultslabel").html("<i>Rezultate gasite: <b>0</b><i>");
                } else {
                    $("#resultslabel").html("<i>Rezultate gasite: <b>" + ($("table#resultstable tr:last").index() + 1) + "</b><i>");
                }
            }
        });
    }
    
    function searchDomeniu(keyword) {
	$.ajax({
	    type: "GET",
	    url: "SearchPage",
            data: {searchfordomeniu: keyword},
            success: function(response){
        	// Removing table rows
        	$("#resultstable tbody tr").each(function(){
                    this.parentNode.removeChild(this); 
                });
        	
                // Adding rows
                $("#resultstable > tbody:last-child").append(response.trim());
                
                // Setting results found text
                if (response.trim().length == 0){
                    $("#resultslabel").html("<i>Rezultate gasite: <b>0</b><i>");
                } else {
                    $("#resultslabel").html("<i>Rezultate gasite: <b>" + ($("table#resultstable tr:last").index() + 1) + "</b><i>");
                }
            }
        });
    }
    
    function searchSpecializare(keyword) {
	$.ajax({
	    type: "GET",
	    url: "SearchPage",
            data: {searchforspecializare: keyword},
            success: function(response){
        	// Removing table rows
        	$("#resultstable tbody tr").each(function(){
                    this.parentNode.removeChild(this); 
                });
        	
                // Adding rows
                $("#resultstable > tbody:last-child").append(response.trim());
                
                // Setting results found text
                if (response.trim().length == 0){
                    $("#resultslabel").html("<i>Rezultate gasite: <b>0</b><i>");
                } else {
                    $("#resultslabel").html("<i>Rezultate gasite: <b>" + ($("table#resultstable tr:last").index() + 1) + "</b><i>");
                }
            }
        });
    }
    
    // Filling the table on page load
    searchCandidat("", "");
    
    // Preventing form submition by pressing ENTER key (code 13)
    $("#searchfield").on('keypress',function(e) {
        if(e.which == 13) {
            e.preventDefault();
            return;
        }
    });
    
    // Search field key up 
    $("#searchfield").on('keyup',function(e) {
	var keyword = $(this).val();
        var combobox = $("#combobox").val();

        switch(combobox) {
            case "Candidat": {
        	var liceu = $("#liceucombobox").val();
                searchCandidat(keyword, liceu);
                break;
            }
        	
            case "Universitate": {
                searchUniversitate(keyword);
                break;
            }
        	
            case "Facultate": {
                searchFacultate(keyword);
                break;
            }
        	
            case "Domeniu": {
        	searchDomeniu(keyword);
        	break;
            }
        	
            case "Specializare": {
        	searchSpecializare(keyword);
        	break;
            }
        }
    });

    // Combobox item changed
    $("#combobox").change(function() {
        var combobox = $(this).val();
        var header = "";

        // Clearing the table rows
        $("#resultstable tbody tr").each(function(){
            this.parentNode.removeChild(this); 
        });

        // Clearing the search field
        $("#searchfield").val("");

        // Clearing the results found label
        $("#resultslabel").html("");

        switch (combobox) {
            case "Candidat": {
        	$("#liceucombobox").css("visibility", "visible");
    	    
                header = "<tr>";

                header += "<th>";
                header += "#";
                header += "</th>";

                header += "<th>";
                header += "NUME";
                header += "</th>";
                
                header += "<th>";
                header += "LICEU ABSOLVIT";
                header += "</th>";
                
                header += "<th>";
                header += "MEDIE LICEU";
                header += "</th>";
                
                header += "<th>";
                header += "MEDIE BACALAUREAT";
                header += "</th>";

                header += "</tr>";
                
                var liceu = $("#liceucombobox").val();
    	    	searchCandidat("", liceu);
    	    	break;
            }
	
            case "Universitate": {
        	$("#liceucombobox").css("visibility", "hidden");
        	    
                header = "<tr>";
    
                header += "<th>";
                header += "#";
                header += "</th>";

                header += "<th>";
                header += "DENUMIRE";
                header += "</th>";
                
                header += "<th>";
                header += "ADRESA";
                header += "</th>";

                header += "</tr>";
                
                searchUniversitate("");
                break;
            }
    	
            case "Facultate": {
        	$("#liceucombobox").css("visibility", "hidden");
                    
                header = "<tr>";

                header += "<th>";
                header += "#";
                header += "</th>";

                header += "<th>";
                header += "DENUMIRE";
                header += "</th>";

                header += "<th>";
                header += "UNIVERSITATE";
                header += "</th>";
                
                header += "<th>";
                header += "ADRESA";
                header += "</th>";

                header += "</tr>"; 
                
                searchFacultate("");
                break;
            }
        	
            case "Domeniu": {
        	$("#liceucombobox").css("visibility", "hidden");
        	    
                header = "<tr>";

                header += "<th>";
                header += "#";
                header += "</th>";

                header += "<th>";
                header += "DENUMIRE";
                header += "</th>";
                
                header += "<th>";
                header += "FACULTATE";
                header += "</th>";

                header += "</tr>";   
                
                searchDomeniu("");
    	    	break;
            }
    	
    	    case "Specializare": {
    		$("#liceucombobox").css("visibility", "hidden");
    	    
                header = "<tr>";

                header += "<th>";
                header += "#";
                header += "</th>";

                header += "<th>";
                header += "DENUMIRE";
                header += "</th>";
                
                header += "<th>";
                header += "DOMENIU";
                header += "</th>";
                

                header += "<th>";
                header += "DURATA (ani)";
                header += "</th>";

                header += "<th>";
                header += "REGULA ADMITERE";
                header += "</th>";

                header += "<th>";
                header += "LOCURI BUGET";
                header += "</th>";

                header += "<th>";
                header += "LOCURI TAXA";
                header += "</th>";

                header += "</tr>";
                
    	    	searchSpecializare("");
    	    	break;
    	    }
        }

        // Setting the header 
        $("#resultstable > thead").html(header);                            
    });
    
    // Liceu combobox item changed   
    $("#liceucombobox").change(function () {
        var keyword = $("#searchfield").val();
        var liceu = $(this).val();
        
        searchCandidat(keyword, liceu);
    });
});