let organizatoriData = {};
let organizatorIdToDelete = null;
var festivaliData = {};
var organizatoriID = [];
var festivaliID = []; 

function getOrganizatori() {
    console.log('Fn called: getOrganizator');
    return new Promise((resolve, reject) => {
        let request = new XMLHttpRequest();
        request.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                const organizatori = JSON.parse(this.responseText);
                organizatoriData = organizatori;
                organizatoriID = []; // Reset the array to avoid duplicates
                for(let id in organizatoriData){
                    organizatoriID.push(id);
                }
                resolve(organizatori);
            } else if (this.readyState == 4) {
                reject('Error fetching organizatori');
            }
        };
        request.open("GET", firebaseUrl + "/organizatoriFestivala.json");
        request.send();
    });
}

// getOrganizatori();

function getFestivali() {
    console.log('Fn called: getFestivali');
    return new Promise((resolve, reject) => {
        let request = new XMLHttpRequest();
        request.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                const festivali = JSON.parse(this.responseText);
                festivaliData = festivali;
                festivaliID = []; // Reset the array to avoid duplicates
                for(let id in festivaliData){
                    festivaliID.push(id);
                }
                resolve(festivali);
            } else if (this.readyState == 4) {
                reject('Error fetching festivali');
            }
        };
        request.open("GET", firebaseUrl + "/festivali.json");
        request.send();
    });

}

// getFestivali();



function displayOrganizatori() {
    console.log('Fn called: displayOrganizers');

    const tabelaOrganizatori = document.querySelector('table tbody');
    tabelaOrganizatori.innerHTML = '';

    for(let i = 0; i < organizatoriID.length; i++) {
        let organizator = organizatoriData[organizatoriID[i]];
        let organizatorFestivali = organizator.festivali;
        let trenutniFestivali = Object.keys(festivaliData[organizatorFestivali]);

        // Create a row for the organizer
        let organizatorRow = document.createElement('tr');
        organizatorRow.innerHTML = `
            <td><b>${organizator.naziv}</b></td>
            <td>${organizator.adresa}</td>
            <td>${organizator.godinaOsnivanja}</td>
            <td>${organizator.kontaktTelefon}</td>
            <td>${organizator.email}</td>
            <td><button class="btn btn-primary" data-id="${organizatoriID[i]}" data-bs-toggle="modal" data-bs-target="#updateOrganizatorModal" onclick="populateUpdateForm('${organizatoriID[i]}')">Update</button></td>
            <td><button class="btn btn-danger" data-id="${organizatoriID[i]}" onclick="showDeleteConfirmation('${organizatoriID[i]}', '${organizator.naziv}')">Delete</button></td>
            <td><button class="btn btn-primary" data-id="${organizatoriID[i]}" data-bs-toggle="modal" data-bs-target="#addFestivalModal" onclick="populateAddFestivalForm('${organizatoriID[i]}')">Add Festival</button></td>
        `;
        tabelaOrganizatori.appendChild(organizatorRow);


        // Create a header row for the festivals
        if (trenutniFestivali.length > 0) {
            let festivalHeaderRow = document.createElement('tr');
            festivalHeaderRow.innerHTML = `
                <th></th>
                <th></th>
                <th>Naziv</th>
                <th>Tip</th>
                <th>Prevoz</th>
                <th>Cena</th>
                <th>Broj posetilaca</th>
                <th>Obriši festival</th>

            `;
            tabelaOrganizatori.appendChild(festivalHeaderRow);
        }

        // Create rows for each festival
        for(let j = 0; j < trenutniFestivali.length; j++) {
            let index = trenutniFestivali[j];
            let festival = festivaliData[organizatorFestivali][index];
            let festivalRow = document.createElement('tr');
            festivalRow.innerHTML = `
                <td></td>
                <td></td>
                <td>${festival.naziv}</td>
                <td>${festival.tip}</td>
                <td>${festival.prevoz}</td>
                <td>${festival.cena}</td>
                <td>${festival.maxOsoba}</td>
                <td><button class="btn btn-primary" data-id="${organizatoriID[i]}" onclick="showDeleteConfirmationFestival('${organizatoriID[i]}', '${index}')">Delete</button></td>         `;
            tabelaOrganizatori.appendChild(festivalRow);
        }
    }
}

function displayOrganizatori2() {
    const tabelaOrganizatori = document.querySelector('table tbody');
    tabelaOrganizatori.innerHTML = '';

    for (let i = 0; i < organizatoriID.length; i++) {
        let organizator = organizatoriData[organizatoriID[i]];
        if (!organizator) continue;

        let organizatorFestivali = organizator.festivali;
        let trenutniFestivali = [];

        if (festivaliData[organizatorFestivali]) {
            trenutniFestivali = Object.keys(festivaliData[organizatorFestivali]);
        }

        // Create a row for the organizer
        let organizatorRow = document.createElement('tr');
        organizatorRow.innerHTML = `
            <td>${organizator.naziv}</td>
            <td>${organizator.adresa}</td>
            <td>${organizator.godinaOsnivanja}</td>
            <td>${organizator.kontaktTelefon}</td>
            <td>${organizator.email}</td>
            <td><button class="btn btn-primary" data-id="${organizatoriID[i]}" data-bs-toggle="modal" data-bs-target="#updateOrganizatorModal" onclick="populateUpdateForm('${organizatoriID[i]}')">Update</button></td>
            <td><button class="btn btn-danger" data-id="${organizatoriID[i]}" onclick="showDeleteConfirmation('${organizatoriID[i]}', '${organizator.naziv}')">Delete</button></td>
            <td><button class="btn btn-primary" data-id="${organizatoriID[i]}" data-bs-toggle="modal" data-bs-target="#addFestivalModal" onclick="populateAddFestivalForm('${organizatoriID[i]}')">Add Festival</button></td>
        `;
        tabelaOrganizatori.appendChild(organizatorRow);

        // Create rows for each festival
        if (trenutniFestivali.length > 0) {
            let festivalHeaderRow = document.createElement('tr');
            festivalHeaderRow.innerHTML = `
                <th></th>
                <th>Naziv</th>
                <th>Tip</th>
                <th>Prevoz</th>
                <th>Cena</th>
                <th>Broj posetilaca</th>
                <th>Obriši festival</th>
            `;
            tabelaOrganizatori.appendChild(festivalHeaderRow);

            for (let j = 0; j < trenutniFestivali.length; j++) {
                let index = trenutniFestivali[j];
                let festival = festivaliData[organizatorFestivali][index];
                if (!festival) continue;

                let festivalRow = document.createElement('tr');
                festivalRow.innerHTML = `
                    <td></td>
                    <td>${festival.naziv}</td>
                    <td>${festival.tip}</td>
                    <td>${festival.prevoz}</td>
                    <td>${festival.cena}</td>
                    <td>${festival.maxOsoba}</td>
                    <td><button class="btn btn-primary" data-id="${organizatoriID[i]}" onclick="showDeleteConfirmationFestival('${organizatoriID[i]}', '${index}')">Delete</button></td>
                `;
                tabelaOrganizatori.appendChild(festivalRow);
            }
        }
    }
}




function populateAddFestivalForm(organizatorId) {
    const currentOrganizatorId = organizatorId;
    document.getElementById('addFestivalOrganizatorId').value = currentOrganizatorId;
}

  

function addFestivalToOrganizator() {

    console.log('Fn called addFestivalToOrganizer');

    // Get the festival data from the form
    //array for pics of festivals
    
    const naziv = document.getElementById('addNaziv').value;
    const opis = document.getElementById('addOpis').value;
    const prevoz = document.getElementById('addPrevoz').value;
    const maxPutnika = document.getElementById('addMaxPutnika').value;
    const cena = document.getElementById('addCena').value;
    const tip = document.getElementById('addTip').value;
    const organizatorId = document.getElementById('addFestivalOrganizatorId').value;

    const slike = [];
    document.querySelectorAll('.addPic').forEach(input => {
        if (input.value.trim() !== '') {
            slike.push(input.value.trim());
        }
    });

     // Find the appropriate festival group ID for the organizator
    const festivalGroupId = organizatoriData[organizatorId].festivali;

    if(naziv == '' || opis == '' || prevoz == '' || maxPutnika == '' || cena == '' || tip == ''){
        alert('Sva polja moraju biti popunjena');
        return;
    }

    // Create a new festival object
    const newFestival = {
        naziv: naziv,
        opis: opis,
        prevoz: prevoz,
        maxOsoba: maxPutnika,
        cena: cena,
        tip: tip,
        slike: slike
    };

    let request = new XMLHttpRequest();
    // request.open("POST", firebaseUrl + `/festivali/` + organizatorId.festivali + `.json`);
    request.open("POST", firebaseUrl + `/festivali/${festivalGroupId}.json`);
    request.setRequestHeader('Content-Type', 'application/json');
    request.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            // Close the modal
            const addFestivalModalElement = document.getElementById('addFestivalModal');
            const addFestivalModal = bootstrap.Modal.getInstance(addFestivalModalElement) || new bootstrap.Modal(addFestivalModalElement);

            // const addFestivalModal = new bootstrap.Modal(document.getElementById('addFestivalModal'));
            addFestivalModal.hide();

            // Refresh the display
            getOrganizatori().then(displayOrganizatori);
            getFestivali().then(displayOrganizatori);
        }else if (this.readyState == 4) {
            // Show error message if needed
            alert('Error adding festival: ' + this.responseText);
        }
    };
    request.send(JSON.stringify(newFestival));
}


   
/*
    // Close the modal
    const addFestivalModal = new bootstrap.Modal(document.getElementById('addFestivalModal'));
    addFestivalModal.hide();

    // Refresh the display
    displayOrganizatori();
}*/

function validateEmail(email) {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(email);
}

function validatePhoneNumber(phoneNumber) {
    const phoneRegex = /^[0-9+]+$/;
    return phoneRegex.test(phoneNumber);
}

function validateNaziv(naziv) {
    return naziv.length > 0;
}

function validateAdresa(adresa) {
    const adresaRegex = /^[a-zA-Z0-9\s,]+$/; // Only letters, numbers, spaces, and commas

}




function populateUpdateForm(organizatorId) {
    const organizator = organizatoriData[organizatorId];
    document.getElementById('updateNaziv').value = organizator.naziv;
    document.getElementById('updateAdresa').value = organizator.adresa;
    document.getElementById('updateGodinaOsnivanja').value = organizator.godinaOsnivanja;
    document.getElementById('updateKontaktTelefon').value = organizator.kontaktTelefon;
    document.getElementById('updateEmail').value = organizator.email;
    console.log('organizatorId:', organizatorId, 'organizatorAdresa:', organizator.adresa);
    document.getElementById('updateOrganizatorId').value = organizatorId;
}

function saveOrganizator() {
    const organizatorId = document.getElementById('updateOrganizatorId').value;
    let festivali = [];
    let logo = '';
    
    for(let organizator in organizatoriData){
        if(organizator == organizatorId){
            console.log('organizatorId:', organizatorId);
            console.log('organizator:', organizatoriData[organizator]);
            console.log('organizator:', organizatoriData[organizator].festivali);
            festivali = organizatoriData[organizator].festivali;
            logo = organizatoriData[organizator].logo;
        }
    }


    const organizator = {
        naziv: document.getElementById('updateNaziv').value,
        adresa: document.getElementById('updateAdresa').value,
        godinaOsnivanja: document.getElementById('updateGodinaOsnivanja').value,
        kontaktTelefon: document.getElementById('updateKontaktTelefon').value,
        email: document.getElementById('updateEmail').value,
        festivali : festivali,
        logo : logo    
        
    };
    

    if (!organizator.naziv || !organizator.adresa || !organizator.godinaOsnivanja || !organizator.kontaktTelefon || !organizator.email) {
        alert('All fields must be filled out');
        return;
    }

    if (!validateEmail(organizator.email)) {
        alert('Invalid email address');
        return;
    }

    if (!validatePhoneNumber(organizator.kontaktTelefon)) {
        alert('Invalid phone number. Only numbers and + sign are allowed.');
        return;
    }

    if (!validateNaziv(organizator.naziv)) {
        alert('Naziv ne može biti prazan');
        return;
    }

    if (!validateAdresa(organizator.adresa)) {
        alert('Adresa ne može biti prazna');
        return;
    }

    

    console.log('organizatorId:', organizatorId);
    console.log('organizator:', organizator);

    let request = new XMLHttpRequest();
    let putUrl = firebaseUrl + "/organizatoriFestivala/" + organizatorId + ".json";
    console.log('PUT URL:', putUrl);

    request.open("PUT", putUrl, true);
    request.setRequestHeader('Content-Type', 'application/json');
    request.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            console.log('Organizer updated successfully');

            const updateOrganizatorModalElement = document.getElementById('updateOrganizatorModal');
            let updateOrganizatorModal = bootstrap.Modal.getInstance(updateOrganizatorModalElement);
            if (updateOrganizatorModal) {
                updateOrganizatorModal.hide();
            }

            getOrganizatori().then(displayOrganizatori);
        }
    };
    request.send(JSON.stringify(organizator));
}


function showDeleteConfirmation(organizatorId, naziv) {
    console.log('organizatorId', organizatorId);
    console.log('naziv', naziv);
    console.log('Fn called: showDeleteConfirmation');
    organizatorIdToDelete = organizatorId;
    document.getElementById('deleteOrganizatorMessage').textContent = `Da li ste sigurni da želite obrisati organizatora ${naziv}?`;
    const deleteOrgModal = new bootstrap.Modal(document.getElementById('deleteOrganizatorModal'));   
    console.log('deleteOrgModal', deleteOrgModal);
    deleteOrgModal.show();
}

function showDeleteConfirmationFestival(organizatorId, festivalId) {
    organizatorIdToDelete = organizatorId;
    festivalIdToDelete = festivalId;
    document.getElementById('deleteFestivalMessage').textContent = `Da li ste sigurni da želite obrisati festival?`;
    const deleteFestivalModal = new bootstrap.Modal(document.getElementById('deleteFestivalModal'));
    deleteFestivalModal.show();
}



function confirmDeleteFestival() {
    console.log('Fn called: confirmDeleteFestival');
    console.log('organizatorIdToDelete:', organizatorIdToDelete);
    console.log('festivalIdToDelete:', festivalIdToDelete);

    var organizerFestivals = organizatoriData[organizatorIdToDelete].festivali;

    if (organizatorIdToDelete && festivalIdToDelete) {
        let request = new XMLHttpRequest();
        let deleteUrl = firebaseUrl + "/festivali/" + organizerFestivals + "/" + festivalIdToDelete + ".json";
        console.log('DELETE URL:', deleteUrl);
        
        request.open("DELETE", deleteUrl, true);
        request.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                console.log('Festival deleted successfully');

                // Hide the modal
                let deleteFestivalModalElement = document.getElementById('deleteFestivalModal');
                let deleteFestivalModal = bootstrap.Modal.getInstance(deleteFestivalModalElement);
                if (deleteFestivalModal) {
                    deleteFestivalModal.hide();
                }

                // Re-fetch and display organizers
                getFestivali()
                    .then(getOrganizatori)
                    .then(displayOrganizatori);
            }
        };
        request.send();
    }
}

function confirmDeleteOrganizator() {
    console.log('Fn called: confirmDeleteOrganizator');
    if (organizatorIdToDelete) {
        let request = new XMLHttpRequest();
        request.open("DELETE", firebaseUrl + "/organizatoriFestivala/" + organizatorIdToDelete + ".json", true);
        request.onreadystatechange = function() {
            console.log(`ReadyState: ${this.readyState}, Status: ${this.status}`);
            if (this.readyState == 4) {
                if (this.status == 200) {
                    console.log('Delete successful');
                    const deleteOrgModalElement = document.getElementById('deleteOrganizatorModal');
                    const deleteOrgModal = bootstrap.Modal.getInstance(deleteOrgModalElement);
                    deleteOrgModal.hide();
                    getOrganizatori().then(displayOrganizatori);
                } else {
                    console.error('Delete failed', this.responseText);
                }
            }
        };
        request.onerror = function() {
            console.error('Request error');
        };
        request.send();
    } else {
        console.error('No organizatorIdToDelete set');
    }
}


document.addEventListener('DOMContentLoaded', async () => {
    try {
        await getOrganizatori();
        await getFestivali();
        displayOrganizatori();
    } catch (error) {
        console.error(error);
    }
});

// document.addEventListener('DOMContentLoaded', async () => {
//     getOrganizatori().then(organizatori => {
//         displayOrganizatori(organizatori);
//     }).catch(error => {
//         console.error(error);
//     });
// });