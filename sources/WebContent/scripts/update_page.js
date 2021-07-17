function numeFieldKeyPressed() {
    var numeField = document.getElementById("nume").value;

    if (event.charCode >= 65 && event.charCode <= 90) {
        // A-Z
        return true;
    } else if (event.charCode >= 95 && event.charCode <= 122) {
        // a-z
        return true;
    } else if (event.charCode == 32 || event.charCode == 45) {
        // space ( ) or dash (-)
        return true;
    }

    return false;
}

function initTataFieldKeyPressed() {
    var initTataField = document.getElementById("inittata").value;

    if (event.charCode >= 65 && event.charCode <= 90) {
        // A-Z
        return true;
    } else if (event.charCode >= 95 && event.charCode <= 122) {
        // a-z
        return true;
    }

    return false;
}

function prenumeFieldKeyPressed() {
    var prenumeField = document.getElementById("prenume").value;

    if (event.charCode >= 65 && event.charCode <= 90) {
        // A-Z
        return true;
    } else if (event.charCode >= 95 && event.charCode <= 122) {
        // a-z
        return true;
    } else if (event.charCode == 32 || event.charCode == 45) {
        // space ( ) or dash (-)
        return true;
    }

    return false;
}

function cnpFieldKeyPressed() {
    var cnpField = document.getElementById("cnp").value;

    if (event.charCode >= 48 && event.charCode <= 57) {
        // 0-9
        return true;
    }

    return false;
}

function liceuAbsolvitFieldKeyPressed() {
    var liceuAbsolvitField = document.getElementById("liceuabsolvit").value;

    if (liceuAbsolvitField.length == 35) {
        return false;
    }

    if (event.charCode >= 48 && event.charCode <= 57) {
        // 0-9
        return true;
    }

    if (event.charCode >= 65 && event.charCode <= 90) {
        // A-Z
        return true;
    } else if (event.charCode >= 95 && event.charCode <= 122) {
        // a-z
        return true;
    } else if (event.charCode == 32 || event.charCode == 46) {
        // space ( ) or dot (.)
        return true;
    }

    return false;
}

function fillCandidatiTable(keyword) {
    $.ajax({
	type: "POST",
	url: "UpdatePage",
	data: {
		numecandidat: keyword,
    	},
	success: function(response) {
    	    // Removing table rows
    	    $("#candidatitable tbody tr").each(function(){
		this.parentNode.removeChild(this); 
    	    });
		
    	    // Adding rows
	    $("#candidatitable > tbody:last-child").append(response.trim());
    	}
    });
}

function displayMessage(msg, color, reload=false) {
    if (color == "red") {
	$("#myAlert").attr('class', 'alert alert-danger alert-dismissible fade show');
    } else if (color == "orange") {
	$("#myAlert").attr('class', 'alert alert-warning alert-dismissible fade show');
    } else if (color == "green") {
	$("#myAlert").attr('class', 'alert alert-success alert-dismissible fade show');
    }
    
    $("#myAlert").css("display", "block");
    $("#close").on("click", function() {
	$("#myAlert").css("display", "none");
	if (reload) {
            window.location.reload();
	}
    });

    $("#alerttest").text(msg);
}

fillCandidatiTable("");

$(document).ready(function() {
    
    // Preventing form submition by pressing ENTER key (code 13)
    $("#searchfield").on('keypress',function(e) {
        if(e.which == 13) {
            e.preventDefault();
            return;
        }
    });
    
    // Search field key up 
    $("#searchfield").on('keyup',function() {
	var keyword = $(this).val();
	fillCandidatiTable(keyword);
    });
        
    var cnp;
    // If rows are added dynamically, the click event won't work
    // Event delegation has to be used instead for dynamic events
    $(document).on("click", "#candidatitable tbody tr", function(e) {
        cnp = $(this).find('td:eq(2)').html();

        $('#candidatitable tr').removeClass('highlighted');
        $(this).toggleClass('highlighted');

        // Candidate data
        $.ajax({
    	type: "POST",
    	url: "UpdatePage",
    	data: {
                cnp_pentru_nume: cnp
            },
            success: function(response) {
                $("#nume").val(response.trim());
            }
        });

        $.ajax({
    	type: "POST",
    	url: "UpdatePage",
    	data: {
                cnp_pentru_inittata: cnp
            },
            success: function(response) {
                $("#inittata").val(response.trim());
            }
        });

        $.ajax({
    	type: "POST",
    	url: "UpdatePage",
    	data: {
                cnp_pentru_prenume: cnp
            },
            success: function(response) {
                $("#prenume").val(response.trim());
            }
        });

        $.ajax({
    	type: "POST",
    	url: "UpdatePage",
    	data: {
                cnp_pentru_cnp: cnp
            },
            success: function(response) {
                $("#cnp").val(response.trim());
            }
        });

        $.ajax({
    	type: "POST",
    	url: "UpdatePage",
    	data: {
                cnp_pentru_datanastere: cnp
            },
            success: function(response) {
                $("#datanastere").val(response.trim());
            }
        });

        $.ajax({
    	type: "POST",
    	url: "UpdatePage",
    	data: {
                cnp_pentru_liceuabsolvit: cnp
            },
            success: function(response) {
                $("#liceuabsolvit").val(response.trim());
            }
        });

        $.ajax({
    	type: "POST",
    	url: "UpdatePage",
    	data: {
                cnp_pentru_medieliceu: cnp
            },
            success: function(response) {
                $("#medieliceu").val(response.trim());
            }
        });

        $.ajax({
    	type: "POST",
    	url: "UpdatePage",
    	data: {
                cnp_pentru_mediebac: cnp
            },
            success: function(response) {
                $("#mediebac").val(response.trim());
            }
        });

        // Exams table 
        $.ajax({
    	type: "POST",
    	url: "UpdatePage",
    	data: {
                cnp_pentru_examstable: cnp
            },
            success: function(response) {
                $("#examstable tbody tr").each(function() {
                    this.parentNode.removeChild(this);
                });
                $("#examstable > tbody:last-child").append(response.trim());
            }
        });
    });

    $("#delete").click(function() {
        if (cnp == undefined) {
            displayMessage("Trebuie sa selectati un candidat.", "orange");
            return;
        }

        $.ajax({
    	type: "POST",
    	url: "UpdatePage",
    	data: {
                cnp_pentru_delete: cnp
            },
            success: function(response) {
        	displayMessage("Candidat eliminat cu succes! Inchideti acest mesaj pentru a reincarca pagina.", "green", true);
            }
        });
    });

    $("#update").click(function() {
	event.preventDefault();

        var nume = $("#nume").val();
        var initTata = $("#inittata").val();
        var prenume = $("#prenume").val();
        var cnp = $("#cnp").val();
        var dataNastere = [
            new Date($('#datanastere').val()).getFullYear(),
            new Date($('#datanastere').val()).getMonth() + 1,
            new Date($('#datanastere').val()).getDate()
        ].join("-");
        var liceuAbsolvit = $("#liceuabsolvit").val();
        var medieLiceu = $("#medieliceu").val();
        var medieBac = $("#mediebac").val();

        var numeExamen = [];
        var notaExamen = [];
        var index = 0;
        $('#examstable tr').each(function() {
            if (index != 0) {
                let examen = $(this).find('td:eq(1)');
                let nota = $(this).find('td:eq(4) input[type=\'number\']');
                numeExamen.push(examen.html());
                notaExamen.push(nota.val());
            }
            index++;
        });

        tempDataNastere = new Date($('#datanastere').val());
        if (nume == "" || nume.length < 3 ||
            initTata == "" ||
            prenume == "" || prenume.length < 3 ||
            cnp == "" || cnp.length != 13 ||
            liceuAbsolvit == "" || liceuAbsolvit.length < 10 ||
            tempDataNastere == "Invalid Date" || (tempDataNastere > new Date()) || (tempDataNastere < new Date("Jan 01 1900"))) {
            return;
        }
        
        var jsonStringNumeExamen = "";
        var jsonStringNotaExamen = "";
        
        if (numeExamen.length > 0 && notaExamen.length > 0) {
            jsonStringNumeExamen = JSON.stringify(numeExamen);
            jsonStringNotaExamen = JSON.stringify(notaExamen);
        }
        
        $.ajax({
    	type: "POST",
    	url: "UpdatePage",
    	data: {
                formsubmit: "ok",
                nume: nume,
                inittata: initTata,
                prenume: prenume,
                cnp: cnp,
                datanastere: dataNastere,
                liceuabsolvit: liceuAbsolvit,
                medieliceu: medieLiceu,
                mediebac: medieBac,
                numeexamen: jsonStringNumeExamen,
                notaexamen: jsonStringNotaExamen
            },
            success: function(response) {
                if (response.trim() == "error") {
                    displayMessage("S-a produs o eroare. Va rugam sa incercati din nou!", "orange");
                } else {
                    displayMessage("Datele candidatului au fost actualizate cu succes! Inchideti acest mesaj pentru a reincarca pagina.", "green", true);
                }
            },
        });
    });
});