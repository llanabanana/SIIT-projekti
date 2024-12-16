let organizatoriID = [];
let organizatori = [];
let festivaliID = [];
let festivali = [];
let festivalGroup = [];

// Fetch organizers
async function getOrganizatori() {
  try {
    const response = await fetch(`${firebaseUrl}/organizatoriFestivala.json`);
    organizatori = await response.json();
    organizatoriID = Object.keys(organizatori);
    displayOrganizatori();
  } catch (error) {
    console.error('Error fetching organizers:', error);
  }
}

// Fetch festivals
async function getFestivali() {
  try {
    const response = await fetch(`${firebaseUrl}/festivali.json`);
    festivali = await response.json();
  } catch (error) {
    console.error('Error fetching festivals:', error);
  }
}

// Display organizers on the main page
function displayOrganizatori() {
  const container = document.getElementById('organizatori-container');
  if (container) {
    container.innerHTML = ''; // Clear the container

    organizatoriID.forEach(id => {
      const organizator = organizatori[id];
      const card = document.createElement('div');
      card.className = 'col';
      card.innerHTML = `
        <a href="festivals.html?organizatorID=${id}">
          <div class="card h-100">
            <img src="${organizator.logo}" class="card-img-top" alt="${organizator.naziv}">
            <div class="card-body">
              <h5 class="card-title">${organizator.naziv}</h5>
              <p class="card-text p-card">${organizator.adresa}</p>
              <p class="card-text p-card">Godina osnivanja: ${organizator.godinaOsnivanja}</p>
              <p class="card-text p-card">Kontakt: ${organizator.kontaktTelefon}</p>
              <p class="card-text p-card">Email: ${organizator.email}</p>
            </div>
          </div>
        </a>
      `;
      container.appendChild(card);
    });
  }
}

// Display festivals for a specific organizer
async function displayFestivals(organizatorID) {
  await getFestivali();
  const container = document.getElementById('festivals-container');
  container.innerHTML = ''; // Clear the container

  const organizer = organizatori[organizatorID];
  const festivalGroupID = organizer.festivali;
  festivalGroup = festivali[festivalGroupID];

  const h1Element = document.querySelector('.etno-h1');
  if (h1Element) {
    h1Element.textContent = `${organizer.naziv}`;
  }

  if (festivalGroup) {
    Object.values(festivalGroup).forEach((festival, index) => {
      const card = document.createElement('div');
      card.className = 'col-lg-4 col-md-6 mb-3'; // Use Bootstrap classes for 4 cards per row
      card.innerHTML = `
        <div class="card h-100">
          <img src="${festival.slike[0]}" class="card-img-top" alt="Festival Image">
          <div class="card-body d-flex flex-column">
            <h5 class="card-title">${festival ? festival.naziv : 'N/A'}</h5>
            <p class="card-text">${festival ? festival.opis : 'N/A'}</p>
            <p class="card-text"><small class="text-muted">Tip: ${festival ? festival.tip : 'N/A'}</small></p>
            <p class="card-text"><small class="text-muted">Cena: ${festival ? festival.cena : 'N/A'}</small></p>
            <div class="mt-auto">
              <button class="btn btn-primary festival-button" data-id="${index}">Više informacija</button>
            </div>
          </div>
        </div>
      `;
      container.appendChild(card);
    });

    // Add event listeners to buttons after they are appended
    const festivalButtons = container.querySelectorAll('.festival-button');
    festivalButtons.forEach(button => {
      button.addEventListener('click', (event) => {
        const festivalIndex = event.target.getAttribute('data-id');
        console.log('Storing Festival Index:', festivalIndex); // Debug log
        localStorage.setItem('selectedFestivalIndex', festivalIndex);
        localStorage.setItem('selectedOrganizerID', organizatorID);
        window.location.href = 'festivalGalerija.html';
      });
    });
  } else {
    console.error('No festivals found for organizer');
  }
}




// Event listener for festivals page
document.addEventListener('DOMContentLoaded', async () => {
  const params = new URLSearchParams(window.location.search);
  const organizatorID = params.get('organizatorID');
  if (organizatorID) {
    await getOrganizatori();
    displayFestivals(organizatorID);
  } else {
    await getOrganizatori(); // Ensure organizers are fetched for index page
    displayOrganizatori(); // Ensure organizers are displayed on index page
  }
});

// Search functionality
document.addEventListener('DOMContentLoaded', () => {
  const searchForm = document.getElementById('searchForm');
  const searchInput = document.getElementById('searchInput');
  const organizersContainer = document.getElementById('organizatori-container');
  const festivalsContainer = document.getElementById('festivals-container');
  searchForm.addEventListener('submit', (event) => {
    event.preventDefault(); // Prevent default form submission behavior
    const query = searchInput.value.toLowerCase();
    handleSearch(query);
  });
  searchForm.addEventListener('input', (e) => {
    e.preventDefault();
    const query = searchInput.value.toLowerCase();

    // Example for the organizers page
    if (organizersContainer) {
      const organizers = organizersContainer.querySelectorAll('.card');
      organizers.forEach(organizer => {
        const text = organizer.textContent.toLowerCase();
        if (text.includes(query)) {
          organizer.style.display = 'block';
        } else {
          organizer.style.display = 'none';
        }
      });
    }

    // Example for the festivals page
    if (festivalsContainer) {
      const festivals = festivalsContainer.querySelectorAll('.card');
      festivals.forEach(festival => {
        const text = festival.textContent.toLowerCase();
        if (text.includes(query)) {
          festival.style.display = 'block';
        } else {
          festival.style.display = 'none';
        }
      });
    }
  });
});


document.addEventListener('DOMContentLoaded', async () => {
  const params = new URLSearchParams(window.location.search);
  const organizatorID = params.get('organizatorID');
  
  if (organizatorID) {
    await getOrganizatori();
    displayFestivals(organizatorID);
  } else {
    await getOrganizatori(); // Ensure organizers are fetched for index page
    displayOrganizatori(); // Ensure organizers are displayed on index page
  }

  const searchNazivInput = document.getElementById('searchNaziv');
  const searchTipSelect = document.getElementById('searchTip');
  const festivalsContainer = document.getElementById('festivals-container');

  if (searchNazivInput && searchTipSelect && festivalsContainer) {
    searchNazivInput.addEventListener('input', () => filterFestivals());
    searchTipSelect.addEventListener('change', () => filterFestivals());
  }
});

function filterFestivals() {
  const searchNaziv = document.getElementById('searchNaziv').value.toLowerCase();
  const searchTip = document.getElementById('searchTip').value;
  const container = document.getElementById('festivals-container');
  container.innerHTML = ''; // Clear the container

  const filteredFestivals = Object.values(festivalGroup).filter(festival => {
    const matchesNaziv = festival.naziv.toLowerCase().includes(searchNaziv);
    const matchesTip = searchTip === '' || festival.tip === searchTip;
    return matchesNaziv && matchesTip;
  });

  filteredFestivals.forEach((festival, index) => {
    const card = document.createElement('div');
    card.className = 'col-lg-4 col-md-6 mb-3';
    card.innerHTML = `
      <div class="card h-100">
        <img src="${festival.slike[0]}" class="card-img-top" alt="Festival Image">
        <div class="card-body d-flex flex-column">
          <h5 class="card-title">${festival ? festival.naziv : 'N/A'}</h5>
          <p class="card-text">${festival ? festival.opis : 'N/A'}</p>
          <p class="card-text"><small class="text-muted">Tip: ${festival ? festival.tip : 'N/A'}</small></p>
          <p class="card-text"><small class="text-muted">Cena: ${festival ? festival.cena : 'N/A'}</small></p>
          <div class="mt-auto">
            <button class="btn btn-primary festival-button" data-id="${index}">Više informacija</button>
          </div>
        </div>
      </div>
    `;
    container.appendChild(card);
  });

  // Add event listeners to buttons after they are appended
  const festivalButtons = container.querySelectorAll('.festival-button');
  festivalButtons.forEach(button => {
    button.addEventListener('click', (event) => {
      const festivalIndex = event.target.getAttribute('data-id');
      console.log('Storing Festival Index:', festivalIndex); // Debug log
      localStorage.setItem('selectedFestivalIndex', festivalIndex);
      localStorage.setItem('selectedOrganizerID', organizatorID);
      window.location.href = 'festivalGalerija.html';
    });
  });
}
