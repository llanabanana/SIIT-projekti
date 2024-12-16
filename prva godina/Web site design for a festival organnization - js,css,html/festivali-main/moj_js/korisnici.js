let usersData = {};
let userIdToDelete = null; // Variable to store the ID of the user to be deleted


// Fetch users from Firebase
function getUsers() {
  return new Promise((resolve, reject) => {
    let request = new XMLHttpRequest();
    request.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        const users = JSON.parse(this.responseText);
        usersData = users;
        resolve(users);
      } else if (this.readyState == 4) {
        reject('Error fetching users');
      }
    };
    request.open("GET", firebaseUrl + "/korisnici.json");
    request.send();
  });
}

// Display users in the table
function displayUsers(users) {
  const tbody = document.querySelector('table tbody');
  tbody.innerHTML = ''; // Clear existing rows

  Object.keys(users).forEach(userId => {
    const user = users[userId];
    const row = document.createElement('tr');
    row.innerHTML = `
      <td>${user.korisnickoIme}</td>
      <td>${user.lozinka}</td>
      <td>${user.ime}</td>
      <td>${user.prezime}</td>
      <td>${user.email}</td>
      <td>${user.datumRodjenja}</td>
      <td>${user.adresa}</td>
      <td>${user.telefon}</td>
      <td><button class="btn btn-primary" data-id="${userId}" data-bs-toggle="modal" data-bs-target="#updateUserModal" onclick="populateUpdateFormUser('${userId}')">Update</button></td>
      <td><button class="btn btn-danger" data-id="${userId}" onclick="showDeleteConfirmation('${userId}', '${user.ime}')">Delete</button></td>
    `;
    tbody.appendChild(row);
  });
}

// Populate the update form with the current user data
function populateUpdateFormUser(userId) {
  const user = usersData[userId];
  document.getElementById('updateKorisnickoIme').value = user.korisnickoIme;
  document.getElementById('updateLozinka').value = user.lozinka;
  document.getElementById('updateIme').value = user.ime;
  document.getElementById('updatePrezime').value = user.prezime;
  document.getElementById('updateEmail').value = user.email;
  document.getElementById('updateDatumRodjenja').value = user.datumRodjenja;
  document.getElementById('updateAdresa').value = user.adresa;
  document.getElementById('updateTelefon').value = user.telefon;

  // Store userId in a hidden field or data attribute for later use
  document.getElementById('updateUserForm').setAttribute('data-user-id', userId);
}


document.addEventListener("DOMContentLoaded", function() {
  console.log("DOM fully loaded and parsed");
  const form = document.querySelector("#registerForm");
  
  if (form) {
    form.addEventListener("submit", saveUser);
  } else {
    console.error("Form with ID 'registerForm' not found");
  }
});




function saveUserUpdate() {
  const userId = document.getElementById('updateUserForm').getAttribute('data-user-id');
  const updatedUser = {
    korisnickoIme: document.getElementById('updateKorisnickoIme').value,
    lozinka: document.getElementById('updateLozinka').value,
    ime: document.getElementById('updateIme').value,
    prezime: document.getElementById('updatePrezime').value,
    email: document.getElementById('updateEmail').value,
    datumRodjenja: document.getElementById('updateDatumRodjenja').value,
    adresa: document.getElementById('updateAdresa').value,
    telefon: document.getElementById('updateTelefon').value
  };

  // Perform basic validation
  if (!validateEmail(updatedUser.email)) {
    alert('Invalid email address');
    return;
  }

  if (!validatePhoneNumber(updatedUser.telefon)) {
    alert('Invalid phone number. Only numbers and + sign are allowed.');
    return;
  }

  if (!validatePassword(updatedUser.lozinka)) {
    alert('Password must be 8-16 characters long, contain at least one uppercase letter, one symbol, and one number.');
    return;
  }

  

  if (!validateIme(updatedUser.ime)) {
    alert('Invalid first name, must start with capital letter and contain only letters.');
    return;
  }

  if (!validatePrezime(updatedUser.prezime)) {
    alert('Invalid last name, must start with capital letter and contain only letters.');
    return;
  }



  // Save the updated data to Firebase
  const request = new XMLHttpRequest();
  request.open("PUT", `${firebaseUrl}/korisnici/${userId}.json`);
  request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
  request.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      // Update the local data
      usersData[userId] = updatedUser;
      // Refresh the table
      displayUsers(usersData);
      // Close the modal
      const updateUserModalElement = document.getElementById('updateUserModal');
      const updateUserModal = bootstrap.Modal.getInstance(updateUserModalElement);
      updateUserModal.hide();
    } else if (this.readyState == 4) {
      alert('Error saving user data');
    }
  };
  request.send(JSON.stringify(updatedUser));
}


// Save the registered user data
function saveUser(event) {
  event.preventDefault();
  console.log("Saving user data...");


  const email = document.getElementById('registerEmail').value;
  const password = document.getElementById('registerPassword').value;
  const confirmPassword = document.getElementById('registerConfirmPassword').value;
  const address = document.getElementById('registerAddress').value;
  const dateOfBirth = document.getElementById('registerDateOfBirth').value;
  const firstName = document.getElementById('registerFirstName').value;
  const lastName = document.getElementById('registerLastName').value;
  const username = document.getElementById('registerUsername').value;
  const phoneNumber = document.getElementById('registerPhoneNumber').value;

  if(!email || !password || !confirmPassword || !address || !dateOfBirth || !firstName || !lastName || !username || !phoneNumber) {
    alert('All fields are required');
    return;
  }

  // Perform basic validation
  if (!validateEmail(email)) {
    alert('Invalid email address');
    return;
  }


  if (!validatePhoneNumber(phoneNumber)) {
    alert('Invalid phone number. Only numbers and + sign are allowed.');
    return;
  }

 // alert('Valid phn. Saving user...');

  if (!validatePassword(password)) {
    alert('Password must be 8-16 characters long, contain at least one uppercase letter and one number.');
    return;
  }

  if (!validateKorisnickoIme(username)) {
    if(username.length < 6)
      alert('Username must be at least 6 characters long');
    else
      alert('Username already exists');
    return;
  }

  if (password !== confirmPassword) {
    alert('Passwords do not match');
    return;
  }

  if (!validateIme(firstName)) {
    alert('Invalid first name, must start with capital letter and contain only letters.');
    return;
  }

  if (!validatePrezime(lastName)) {
    alert('Invalid last name, must start with capital letter and contain only letters.');
    return;
  }

  alert('Valid data. Saving user...');

  // Create the user object
  const newUser = {
    email: email,
    adresa: address,
    datumRodjenja: dateOfBirth,
    ime: firstName,
    prezime: lastName,
    korisnickoIme: username,
    telefon: phoneNumber,
    lozinka: password
  };

  // Get the Firebase URL from your JavaScript file



  // Save the user data to Firebase
  const request = new XMLHttpRequest();
  request.open("POST", `${firebaseUrl}/korisnici.json`);
  request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
  request.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      // Registration successful
      alert('Registration successful!');
      document.getElementById('registerForm').reset();
      // Optionally, you can redirect the user or perform other actions here
    } else if (this.readyState == 4) {
      // Registration failed
      alert('Error registering user');
    }
  };
  request.send(JSON.stringify(newUser));
}



// Validate email using regex
function validateEmail(email) {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
}

// Validate phone number to contain only numbers and + sign
function validatePhoneNumber(phone) {
  const phoneRegex = /^[0-9+]+$/;
  return phoneRegex.test(phone);
}

// Validate password to be 8-16 characters, at least one uppercase letter, one symbol, and one number

function validatePassword(password) {
  // Password must be 8-16 characters long, contain at least one uppercase letter, one symbol, and one number
  const re = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)[A-Za-z\d]{8,16}$/;
  return re.test(password);
}

function validateKorisnickoIme(korisnickoIme) {
  // Username must be at least 6 characters long
  for (let userId in usersData) {
    if (usersData[userId].korisnickoIme === korisnickoIme) {
      return false;
    }
  }
  return korisnickoIme.length >= 6;
}

function validateIme(ime) {
  // Username must be at least 6 characters long
  const reIme = /^[A-Z][a-z]+$/; 
  return reIme.test(ime);
}

function validatePrezime(prezime) {
  // Username must be at least 6 characters long
  const rePrezime = /^[A-Z][a-z]+$/;
  return rePrezime.test(prezime);
}


// Show delete confirmation modal
function showDeleteConfirmation(userId, userName) {
  userIdToDelete = userId; // Store the userId to delete
  document.getElementById('deleteUserMessage').textContent = `Da li ste sigurni da zelite obrisati korisnika ${userName}?`;
  const deleteUserModal = new bootstrap.Modal(document.getElementById('deleteUserModal'));
  deleteUserModal.show();
}

// Confirm delete user
function confirmDeleteUser() {
  if (!userIdToDelete) return;

  const request = new XMLHttpRequest();
  request.open("DELETE", `${firebaseUrl}/korisnici/${userIdToDelete}.json`);
  request.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      // Remove the user from local data
      delete usersData[userIdToDelete];
      // Refresh the table
      displayUsers(usersData);
      // Hide the modal
      const deleteUserModalElement = document.getElementById('deleteUserModal');
      const deleteUserModal = bootstrap.Modal.getInstance(deleteUserModalElement);
      deleteUserModal.hide();
    } else if (this.readyState == 4) {
      alert('Error deleting user');
    }
  };
  request.send();
}

// Load users on page load
document.addEventListener('DOMContentLoaded', () => {
  getUsers().then(users => {
    displayUsers(users);
  }).catch(error => {
    console.error(error);
  });
});
