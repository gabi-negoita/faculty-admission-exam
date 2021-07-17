function numeFieldKeyPressed(){
    var numeField = document.getElementById("nume").value;

    if (event.charCode >= 65 && event.charCode <= 90){
        // A-Z
        return true;
    }
    else if (event.charCode >= 95 && event.charCode <= 122){
        // a-z
        return true;
    }
    else if (event.charCode == 32 || event.charCode == 45){
        // space ( ) or dash (-)
        return true;
    }
    return false;
}

function initTataFieldKeyPressed(){
    var initTataField = document.getElementById("inittata").value;

    if (event.charCode >= 65 && event.charCode <= 90){
        // A-Z
        return true;
    }
    else if (event.charCode >= 95 && event.charCode <= 122){
        // a-z
        return true;
    }
    
    return false;
}

function prenumeFieldKeyPressed(){
    var prenumeField = document.getElementById("prenume").value;

    if (event.charCode >= 65 && event.charCode <= 90){
        // A-Z
        return true;
    }
    else if (event.charCode >= 95 && event.charCode <= 122){
        // a-z
        return true;
    }
    else if (event.charCode == 32 || event.charCode == 45){
        // space ( ) or dash (-)
        return true;
    }
    return false;
}

function cnpFieldKeyPressed(){
    var cnpField = document.getElementById("cnp").value;
    
    if (event.charCode >= 48 && event.charCode <= 57){
        // 0-9
        return true;
    }
    return false;
}

function liceuAbsolvitFieldKeyPressed(){
    var liceuAbsolvitField = document.getElementById("liceuabsolvit").value;

    if (event.charCode >= 48 && event.charCode <= 57){
        // 0-9
        return true;
    }

    if (event.charCode >= 65 && event.charCode <= 90){
        // A-Z
        return true;
    }
    else if (event.charCode >= 95 && event.charCode <= 122){
        // a-z
        return true;
    }
    else if (event.charCode == 32 || event.charCode == 46){
        // space ( ) or dot (.)
        return true;
    }
    return false;
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

$(document).ready(function () {
    $("#universitatecombobox").change(function () {
        var value = $(this).val();

        $.ajax({
            type: "POST",
            url: "AddPage",
            data: {selectedfacultate: value},
            success: function(response){
                $("#facultatecombobox").html(response.trim());
            }
        });
    });

    $("#facultatecombobox").change(function () {
        var value = $(this).val();

        $.ajax({
            type: "POST",
            url: "AddPage",
            data: {selecteddomeniu: value},
            success: function(response){
                $("#domeniucombobox").html(response.trim());
            }
        });
    });

    $("#domeniucombobox").change(function () {
        var value = $(this).val();

        $.ajax({
            type: "POST",
            url: "AddPage",
            data: {selectedspecializare: value},
            success: function(response){
                $("#specializarecombobox").html(response.trim());
            }
        });
    });

    $("#specializarecombobox").change(function () {
        var value = $(this).val();

        $.ajax({
            type: "POST",
            url: "AddPage",
            data: {selectedbugettaxa: value},
            success: function(response){
                $("#bugettaxacombobox").html(response.trim());
            }
        });
    });

    globalCount = 0;
    $("#addoption").click(function () {
        var ok = true;

        let selectedUniversitate = $("#universitatecombobox").val();
        let selectedFacultate = $("#facultatecombobox").val();
        let selectedDomeniu = $("#domeniucombobox").val();
        let selectedSpecializare = $("#specializarecombobox").val();
        let selectedBugetTaxa = $("#bugettaxacombobox").val();
        
        if (selectedUniversitate == "Selectati o universitate") {
            ok = false;
        }
        else if (selectedFacultate == "Selectati o facultate") {
            ok = false;
        }
        else if (selectedDomeniu == "Selectati un domeniu") {
            ok = false;
        }
        else if (selectedSpecializare == "Selectati o specializare") {
            ok = false;
        }
        else if (selectedBugetTaxa == "Selectati o forma de invatamant") {
            ok = false;
        }

        if (!ok) {
            return;
        }
        
        if (globalCount > 0)
        {
            $('#optionstable tr').each(function() {
                let universitate = $(this).find('td:eq(1)').html();
                let facultate = $(this).find('td:eq(2)').html();
                let domeniu = $(this).find('td:eq(3)').html();
                let specializare = $(this).find('td:eq(4)').html();
                let bugetTaxa = $(this).find('td:eq(5)').html();
                
                if (selectedUniversitate === universitate &&
                    selectedFacultate === facultate &&
                    selectedDomeniu === domeniu &&
                    selectedSpecializare === specializare &&
                    selectedBugetTaxa === bugetTaxa) {
                    	displayMessage("Ati adaugat deja aceasta optiune.", "orange");
                        ok = false;
                }
            });
        }

        if (!ok) {
            return;
        }

        globalCount ++;
        var row;
        row = "<tr>";

        row += "<td><b>";
        row += globalCount;
        row += "</td></b>";

        row += "<td>";
        row += $("#universitatecombobox").val();
        row += "</td>";
        
        row += "<td>";
        row += $("#facultatecombobox").val();
        row += "</td>";
        
        row += "<td>";
        row += $("#domeniucombobox").val();
        row += "</td>";
        
        row += "<td>";
        row += $("#specializarecombobox").val();
        row += "</td>";

        row += "<td>";
        row += $("#bugettaxacombobox").val();
        row += "</td>";

        row += "<td>";
        row += "<button type=\"button\" id=\"deleteoption\" class=\"btn btn-danger\">STERGE</button>";
        row += "</td>";

        row += "</tr>";
        
        $("#optionstable > tbody").append(row);
    });

    $(document).on("click","#optionstable tbody tr td button.btn", function() {
        var index = $(this).parent().parent().index();                              
        $("table tr:eq(" + (index + 1)+")").remove();
        
        globalCount--;
        let localCount = 0;
        $('#optionstable tr').each(function() {
            let col = $(this).find('td:eq(0)');
            col.html("<b>" + localCount + "</b>");
            localCount++;
        });
    });

    $("#submit").click(function (event) {
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
        var bugetTaxa = [];
        var specializare = [];
        
        var index = 0;
        $('#optionstable tr').each(function() {
            if (index != 0) {
                let sp = $(this).find('td:eq(4)');
                let bt = $(this).find('td:eq(5)');
                bugetTaxa.push(bt.html());
                specializare.push(sp.html());
            }
            index++;
        });

        tempDataNastere = new Date($('#datanastere').val());
        if (nume == "" || nume.length < 3
        || initTata == ""
        || prenume == "" || prenume.length < 3 
        || cnp == "" || cnp.length != 13
        || liceuAbsolvit == "" || liceuAbsolvit.length < 10
        || tempDataNastere == "Invalid Date" || (tempDataNastere > new Date()) || (tempDataNastere < new Date("Jan 01 1900"))) {
            return;
        }
        
        if (index == 1){
            displayMessage("Trebuie sa adaugati cel putin o optiune.", "orange");
            return;
        }

        var jsonStringBugetTaxa = JSON.stringify(bugetTaxa);
        var jsonStringSpecializare = JSON.stringify(specializare);
        
        $.ajax({
            type: "POST",
            url: "AddPage",
            data: {
                formsubmit: "ok",
                checkcnpforduplicates: cnp,
                nume: nume,
                inittata: initTata,
                prenume: prenume,
                cnp: cnp,
                datanastere: dataNastere,
                liceuabsolvit: liceuAbsolvit,
                medieliceu: medieLiceu,
                mediebac: medieBac,
                bugettaxa: jsonStringBugetTaxa,
                specializare: jsonStringSpecializare
            },
            success: function(response){
                if (response.trim() == "duplicated") {
                    displayMessage("Un alt candidat cu acelasi CNP a fost deja adaugat.", "orange");
                } else if (response.trim() == "error") {
                    displayMessage("S-a produs o eroare. Va rugam sa incercati din nou!", "red");
                } else {
                    displayMessage("Candidat adaugat cu succes! Inchideti acest mesaj pentru a reincarca pagina.", "green", true);
                }
            },
        });
    });
});