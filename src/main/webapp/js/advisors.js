const urlParams = new URLSearchParams(window.location.search);
const parameterUsername = urlParams.get('user');

// URL must include ?user=XYZ parameter. If not, redirect to homepage.
if (!parameterUsername) {
  window.location.replace('/');
}

//Fetch advisors and requests and display them
function setPageTitle() {
  document.getElementById('page-title').innerText = parameterUsername;
  document.title = parameterUsername + ' - User Page';
}

function fetchAdvisors() {
  const url = '/advisors?user=' + parameterUsername;
  fetch(url).then((response) => {
    return response.json();
  }).then((advisors) => {
    const advisorsContainer = document.getElementById('advisors-container');


    if (advisors.length == 0 || advisors == "") {
      advisorsContainer.innerHTML = '<p>You have no advisors yet.</p>';
    }
    else {
      advisorsContainer.innerHTML = '<p> Your advisors: </p>';
    }
    advisors.forEach((advisor) => {
      const advisorDiv = buildAdvElement(advisor);
      advisorsContainer.appendChild(advisorDiv);
    });

  });
}

function buildAdvElement(advString) {
  const advElement = document.createElement('p');
  advElement.appendChild(document.createTextNode(advString));
  return advElement;
}


function fetchRequests() {
  const url = '/requests';

  fetch(url).then((response) => {
    return response.json();
  }).then((requests) => {
    const requestsContainer = document.getElementById('requests-container');
    requestsContainer.innerHTML = ""
    requests.forEach((request) => {
      //requestsContainer.innerHTML += request.requester;
      if (request.requestee == parameterUsername) {
        const requestDiv = buildReqElement(request);
        requestsContainer.appendChild(requestDiv);
      }
    });


  });
}

function buildReqElement(request) {
  const reqElement = document.createElement('p');
  reqElement.appendChild(document.createTextNode(request.requester));
  return reqElement;
}

// Fetch data and populate the UI of the page.
function buildUI() {
  setPageTitle();
  fetchAdvisors();
  fetchRequests();
}

function addAdvisor() {

}

