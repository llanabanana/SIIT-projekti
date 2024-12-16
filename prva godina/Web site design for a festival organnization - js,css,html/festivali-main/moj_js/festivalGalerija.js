const firebaseUrl = 'https://festivalisrbije-55c99-default-rtdb.europe-west1.firebasedatabase.app';
let organizatori = [];
let festivali = [];

// Fetch festivals
async function getFestivali() {
  try {
    const response = await fetch(`${firebaseUrl}/festivali.json`);
    festivali = await response.json();
  } catch (error) {
    console.error('Error fetching festivals:', error);
  }
}

// Fetch organizers
async function getOrganizatori() {
  try {
    const response = await fetch(`${firebaseUrl}/organizatoriFestivala.json`);
    organizatori = await response.json();
  } catch (error) {
    console.error('Error fetching organizers:', error);
  }
}

// Fetch and display festival details
document.addEventListener('DOMContentLoaded', async () => {
  const festivalIndex = localStorage.getItem('selectedFestivalIndex');
  const organizatorID = localStorage.getItem('selectedOrganizerID');
  console.log('Selected Festival Index:', festivalIndex); // Debug log
  console.log('Selected Organizer ID:', organizatorID); // Debug log
  if (festivalIndex !== null && organizatorID !== null) {
    try {
      await getOrganizatori();
      await getFestivali();
      const organizer = organizatori[organizatorID];
      if (organizer && organizer.festivali) {
        const festivalGroupID = organizer.festivali;
        const festivalGroup = festivali[festivalGroupID];
        const festival = Object.values(festivalGroup)[festivalIndex];
        console.log('Found Festival:', festival); // Debug log
        if (festival) {
          displayFestivalDetails(festival);
        } else {
          console.error('Festival not found');
        }
      } else {
        console.error('No festivals found for organizer');
      }
    } catch (error) {
      console.error('Error fetching festivals', error);
    }
  } else {
    console.error('No Festival Index or Organizer ID found in local storage');
  }
});

// Display festival details
function displayFestivalDetails(festival) {
  console.log('Displaying Festival Details:', festival); // Debug log

  // Set festival title and description
  document.getElementById('festivalTitle').textContent = festival.naziv;
  document.getElementById('festivalDescription').textContent = festival.opis;

  // Display images
  const imageGallery = document.getElementById('imageGallery');
  imageGallery.innerHTML = ''; // Clear existing images
  festival.slike.forEach(imageUrl => {
    const imgElement = document.createElement('img');
    imgElement.src = imageUrl;
    imgElement.className = 'col-2 image-gallery';
    imgElement.alt = 'Gallery Image';
    imgElement.setAttribute('data-bs-toggle', 'modal');
    imgElement.setAttribute('data-bs-target', '#imageModal');
    imageGallery.appendChild(imgElement);
  });

  // Set modal image on thumbnail click
  document.querySelectorAll('.image-gallery').forEach(item => {
    item.addEventListener('click', event => {
      document.getElementById('modalImage').src = item.src;
    });
  });

  // Display festival details in table
  const festivalDetails = document.getElementById('festivalDetails');
  festivalDetails.innerHTML = `
    <tr>
      <td data-label="Tip festivala">${festival.tip}</td>
      <td data-label="Prevoz">${festival.prevoz}</td>
      <td data-label="Cena">${festival.cena}</td>
      <td data-label="Maksimalan broj putnika">${festival.maxOsoba}</td>
    </tr>
  `;
}
