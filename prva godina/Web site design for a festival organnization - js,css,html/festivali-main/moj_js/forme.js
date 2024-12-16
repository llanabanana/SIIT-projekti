function displayTable() {
  var x = document.getElementById("myTable");
  if (x.style.display === "none") {
    x.style.display = "block";
    showOrganizerTable();
  } else {
    x.style.display = "none";
  }
}

function showOrganizerTable() {
  var table = document.getElementById("table-content");
  table.innerHTML = "";
  for (let i = 0; i < organizerID.length; i++) {
    let organizer = organizers[organizerID[i]];
    let organizersFestivals = organizer.festivali;
    let currentFestivals = Object.keys(festivals[organizersFestivals]);
    table.innerHTML += `
      <tr>
        <td><b>${organizer.naziv}</b></td>
        <td>${organizer.adresa}</td>
        <td>${organizer.godinaOsnivanja}</td>
        <td>${organizer.kontaktTelefon}</td>
        <td>${organizer.email}</td>
      </tr>
      <tr>
        <th></th>
        <th>Naziv</th>
        <th>Tip</th>
        <th>Prevoz</th>
        <th>Cena</th>
        <th>Broj posetilaca</th>
      </tr>
    `;
    for (let j = 0; j < currentFestivals.length; j++) {
      let index = currentFestivals[j];
      let festival = festivals[organizersFestivals][index];
      table.innerHTML += `
        <tr>
          <td></td>
          <td>${festival.naziv}</td>
          <td>${festival.tip}</td>
          <td>${festival.prevoz}</td>
          <td>${festival.cena}</td>
          <td>${festival.maxOsoba}</td>
        </tr>
      `;
    }
  }
}


//Register form
document.getElementById("registerForm").addEventListener("submit", function(event) {
  event.preventDefault(); // Prevent form submission

  // Get form values
  var email = document.getElementById("registerEmail").value;
  var password = document.getElementById("registerPassword").value;
  var confirmPassword = document.getElementById("registerConfirmPassword").value;
  var address = document.getElementById("registerAddress").value;
  var dateOfBirth = document.getElementById("registerDateOfBirth").value;
  var firstName = document.getElementById("registerFirstName").value;
  var lastName = document.getElementById("registerLastName").value;
  var username = document.getElementById("registerUsername").value;
  var phoneNumber = document.getElementById("registerPhoneNumber").value;

  // Perform validation (e.g., password matching) here if needed

  // Create user object
  var userData = {
      email: email,
      address: address,
      dateOfBirth: dateOfBirth,
      firstName: firstName,
      lastName: lastName,
      username: username,
      phoneNumber: phoneNumber
  };

  // Get a reference to the Firebase database
  var database = firebase.database();

  // Push user data to Firebase database
  database.ref('korisnici').push(userData)
      .then(function() {
          // Data successfully saved
          alert("Registration successful!");
          // Close the modal if needed
          //$('#registerModal').modal('hide');
      })
      .catch(function(error) {
          // Handle errors
          console.error("Error saving data: ", error);
          alert("Registration failed. Please try again later.");
      });
});