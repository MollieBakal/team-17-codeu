const urlParams = new URLSearchParams(window.location.search);
const parameterUsername = urlParams.get('user');

// URL must include ?user=XYZ parameter. If not, redirect to homepage.
if (!parameterUsername) {
  window.location.replace('/');
}

//Fetch advisors and requests and display them

function fetchAdvisors() {
  const url = '/requests';
  fetch(url).then((response) => {
    return response.json();
  }).then((requests) => {
    const advisorsContainer = document.getElementById('advisors-container');
    var check = 0;
    advisorsContainer.innerHTML = 'You have no advisors.';
    var repeats = [];

    requests.forEach((request) => {
      if (request.requester == parameterUsername && request.requestee != request.requester && request.status == 1) {
        if (check == 0) { advisorsContainer.innerHTML = ''; }
        if (!repeats.includes(request.requestee)) {
          repeats[check] = request.requestee;
          check++;
          const advisorDiv = buildAdvElement(request.requestee);
          advisorsContainer.appendChild(advisorDiv);
        }
      }
    });
  });
}
function fetchAdvisees() {
  const url = '/requests';
  fetch(url).then((response) => {
    return response.json();
  }).then((requests) => {
    const adviseesContainer = document.getElementById('advisees-container');
    var check = 0;
    adviseesContainer.innerHTML = 'You have no advisees.';
    var repeats = [];

    requests.forEach((request) => {
      if (request.requestee == parameterUsername && request.requestee != request.requester && request.status == 1) {
        if (check == 0) { adviseesContainer.innerHTML = ''; }
        if (!repeats.includes(request.requester)) {
          repeats[check] = request.requester;
          check++;
          const advisorDiv = buildAdvElement(request.requester);
          adviseesContainer.appendChild(advisorDiv);
        }
      }
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
    var check = 0;
    requestsContainer.innerHTML = 'You have no requests.';

    requests.forEach((request) => {
      if (request.requestee == parameterUsername && request.requestee != request.requester && request.status == 0) {
        if (check == 0) { requestsContainer.innerHTML = ''; }
        check++;
        const requestDiv = buildReqElement(request);
        requestsContainer.appendChild(requestDiv);
      }
    });
  });


}

function buildReqElement(request) {
  const reqElement = document.createElement('form');
  reqElement.setAttribute("action", "/requests");
  reqElement.setAttribute("method", "POST");

  const requestor = document.createElement('p');
  requestor.appendChild(document.createTextNode(request.requester));

  const hiddenParam1 = document.createElement('input');
  hiddenParam1.setAttribute("type", "hidden");
  hiddenParam1.setAttribute("name", "requester");
  hiddenParam1.setAttribute("value", request.requester);

  const hiddenParam2 = document.createElement('input');
  hiddenParam2.setAttribute("type", "hidden");
  hiddenParam2.setAttribute("name", "reqID");
  hiddenParam2.setAttribute("value", request.id);

  const accept = document.createElement('input');
  accept.setAttribute("type", "radio");
  accept.setAttribute("name", "status");
  accept.setAttribute("value", "Accept");
  accept.setAttribute("checked", "true");

  const deny = document.createElement('input');
  deny.setAttribute("type", "radio");
  deny.setAttribute("name", "status");
  deny.setAttribute("value", "Deny");

  const submit = document.createElement('input');
  submit.setAttribute("type", "submit");

  const outerAccept = document.createElement("label")
  outerAccept.appendChild(accept);
  outerAccept.appendChild(document.createTextNode("Accept "));

  const outerDeny = document.createElement("label");
  outerDeny.appendChild(deny);
  outerDeny.appendChild(document.createTextNode("Deny "));


  reqElement.appendChild(requestor);
  reqElement.appendChild(hiddenParam1);
  reqElement.appendChild(hiddenParam2);
  reqElement.appendChild(outerAccept);
  reqElement.appendChild(outerDeny);
  reqElement.appendChild(submit);


  return reqElement;
}

// Fetch data and populate the UI of the page.
function buildUI() {
  fetchAdvisees();
  fetchAdvisors();
  fetchRequests();
}
